TOPIC : System Overview

The Zycus Logistics backend is a mission-critical Spring Boot application designed to manage real-time logistics operations. Its primary responsibility is not just CRUD operations for Orders and Agents, but acting as an Intelligent Orchestration Engine. When operational disruptions occur (e.g., an agent going offline), the system must immediately and autonomously compute the best reassignment strategies without locking the main thread or causing data inconsistencies.
🏗️ Detailed Architecture Design

The application utilizes a Decoupled Layered Architecture augmented with Event-Driven Domain updates and the Strategy Design Pattern for behavioral injection.

    The API Layer (controller, dto, utility):
    RESTful controllers map incoming HTTP requests to DTOs. The DtoMapper ensures that internal database entities (Order, Agent) never leak to the client, preventing mass-assignment vulnerabilities and lazy-initialization exceptions.

    The Transaction Layer (service, repository):
    Services handle ACID transactions using Spring Data JPA. AgentService and OrderService are strictly concerned with their own domain aggregates. They do not cross-communicate heavily; instead, they communicate state changes via the Event Layer.

    The Event Layer (event):
    Using Spring's ApplicationEventPublisher, domain services broadcast domain events. For example, when AgentService updates an agent's status to OFFLINE, it fires an AgentOfflineEvent.

    The Orchestration & Strategy Layer (strategy):
    The AgentOfflineListener catches the event and invokes the ReassignmentOrchestrator. The Orchestrator inspects the context and injects the appropriate ReassignmentStrategy (either RuleBasedReassignmentStrategy or AiReassignmentStrategy) to calculate the next best steps.

⚖️ Challenges & Trade-Offs

    Trade-Off: Event-Driven vs. Direct Method Invocation

        Challenge: When an agent goes offline, triggering a reassignment directly inside AgentService.updateStatus() creates a circular dependency and massive coupling between Agent, Order, and AI domains.

        Decision: We used Spring Application Events.

        Consequence: Debugging becomes slightly harder because the execution flow is not linear in the code. However, the system is highly decoupled; if the AI strategy fails, it does not rollback the Agent's status update.

    Trade-Off: Strategy Pattern vs. If/Else Logic

        Challenge: Reassignment logic changes based on order priority, geographic distance, and load balancing. Using massive if/else blocks creates unmaintainable "spaghetti code."

        Decision: Implemented the Strategy Pattern via a shared ReassignmentStrategy interface.

        Consequence: Increases the initial number of classes (interfaces, concrete implementations, orchestrator). However, adding a new recommendation engine in the future requires zero changes to existing code (Open/Closed Principle).

    Trade-Off: Synchronous vs. Asynchronous Processing

        Challenge: AI-based reassignment might require calling external machine learning APIs, which introduces latency.

        Decision: Currently processed synchronously for data consistency, but the event-driven architecture is primed for Spring's @Async. Moving to async will require implementing WebSockets or polling on the frontend to notify the user when the AI finishes computing.

TOPIC "Core Domain Orchestration, Resilience, and Asynchronous Workflows

Context: The backend must handle dynamic logistics reassignments using both deterministic (rule-based) and non-deterministic (LLM-based) logic. It must remain highly responsive to operational inputs while managing complex, potentially slow, or failing background processes.
1. Boundary Enforcement: Where Routing Logic Lives

Pattern Used: Application Service (via Strategy Orchestration)

We explicitly rejected the anti-pattern of allowing AgentService or OrderService to bloat into "God classes" that manage CRUD, emit events, and calculate routing. Routing and reassignment logic is entirely isolated within the ReassignmentOrchestrator, which functions as an Application Service.

    The Boundary: AgentService handles persistence and state mutations for the Agent aggregate. When state changes, its responsibility ends at publishing an event. It knows nothing about routing.

    The Orchestrator: The ReassignmentOrchestrator is the sole coordinator of the reassignment domain. It fetches necessary domain entities, selects the appropriate algorithm, executes it, and persists the resulting ReassignmentSuggestion.

    Why here? If routing logic lived in OrderService, testing the routing algorithm would require mocking database connections for unrelated order CRUD operations. By isolating it in an Application Service, the routing logic is highly cohesive, independently testable, and respects the Single Responsibility Principle (SRP).

2. Runtime Strategy Switching

Mechanism: Spring Dependency Injection & Strategy Pattern

The system supports multiple routing algorithms via the ReassignmentStrategy interface. Strategy selection occurs dynamically at runtime.

    How it works (HTTP Path): When an operator clicks "Generate Suggestion" in the UI (hitting SuggestionController), the request includes a RecommendationType parameter (e.g., AI or RULE_BASED). The ReassignmentOrchestrator uses this parameter to fetch the exact strategy from a Spring-injected Map<String, ReassignmentStrategy> and executes it synchronously, returning the DTO directly to the client.

    How it works (Async Path): When triggered by a system event (e.g., agent offline), the Orchestrator reads application properties or feature flags to determine the default ambient strategy (currently configured to default to AI).

    Plugging in a 3rd Strategy (Sprint 2): When we introduce a new algorithm (e.g., GeospatialReassignmentStrategy), we simply create a class implementing ReassignmentStrategy and annotate it with @Component("GEOSPATIAL"). Spring's IoC container automatically injects it into the Orchestrator's Strategy Map on startup. Zero modifications are required in the Orchestrator or Controller to support this new strategy (adhering perfectly to the Open/Closed Principle).

3. LLM Resilience and Fallback Mechanisms

LLM integration (AiReassignmentStrategy) introduces non-deterministic failure modes. The system enforces strict resilience policies to prevent LLM volatility from degrading logistics operations.

    Timeouts & Quota Exhaustion: The LLM client is wrapped in a Resilience4j CircuitBreaker and TimeLimiter. If the LLM takes longer than 3 seconds or the API returns a 429 (Rate Limit), the circuit breaks.

    Malformed JSON & Hallucinations: The LLM output is immediately parsed into a strongly-typed ReassignmentResult object. Before returning, the Strategy queries the AgentRepository to verify the AI-suggested agentId actually exists and is currently AVAILABLE. If the JSON is malformed (fails parsing) or the ID is hallucinated (validation fails), a StrategyExecutionException is thrown.

    The Async Fallback Guarantee: When the ReassignmentOrchestrator runs in the background, a silent failure is catastrophic (an order gets dropped). The Orchestrator implements a strict try-catch fallback structure:
    Java

    try {
        return aiStrategy.calculate(order);
    } catch (StrategyExecutionException | CircuitBreakerOpenException e) {
        log.warn("AI Strategy failed [{}], falling back to Rule-Based", e.getMessage());
        return ruleBasedStrategy.calculate(order);
    }

    If the AI fails for any reason, the system instantly degrades gracefully to the deterministic rule-based engine, ensuring a suggestion is always generated.

4. Decoupling the Agentic Loop

Mechanism: Spring @ApplicationEventPublisher with @Async @EventListener

The PATCH /agents/{id}/status endpoint must acknowledge the status change in milliseconds. Re-planning a logistics route can take seconds.

    The Decoupling: Inside AgentService.updateStatus(), after the database transaction commits, we call eventPublisher.publishEvent(new AgentOfflineEvent(agentId, activeOrders)). The HTTP thread immediately returns a 200 OK to the frontend.

    The Agentic Loop: The AgentOfflineListener observes this event. Crucially, the listener method is annotated with @Async. This hands the execution off to a dedicated Spring ThreadPoolTaskExecutor.

    Why over alternatives?

        Why not direct Thread execution? Raw new Thread() lacks lifecycle management and context propagation.

        Why not Kafka/RabbitMQ? Introducing a distributed message broker for MVP adds unnecessary infrastructure complexity and network latency for events that are entirely internal to this bounded context. Spring Application Events provide thread-boundary isolation with zero infrastructure overhead.

    Failsafe: If the entire async re-plan process fails (e.g., database goes down during the async thread execution), the @Async exception handler catches it, alerts monitoring (via logs/metrics), and flags the Order in the database with a REQUIRES_MANUAL_INTERVENTION status. No order is silently dropped.

5. Extensibility vs. Deliberate Deferrals

Designed to Extend (Sprint 2/3 Seam):
The primary extension seam is the ReassignmentStrategy interface and its standardized output, ReassignmentResult. If Sprint 3 requires routing based on real-time traffic data, we simply inject a TrafficAwareStrategy. Because the frontend and the ReassignmentOrchestrator strictly consume the abstract ReassignmentResult (which normalizes confidence scores and agent IDs), the complex new traffic logic can be deployed without touching the UI, the Orchestrator, or the database schema.

Deliberately Deferred (Priority Call):
We deliberately chose not to build WebSockets/Server-Sent Events (SSE) for real-time frontend notifications when the async loop completes.

    The Frame: Implementing WebSockets requires maintaining persistent connections, managing disconnects, and securing a new protocol layer. Our immediate correctness requirement is ensuring the routing engine calculates valid assignments and fails over safely. A perfectly real-time UI notification is an enhancement; a reliable database state is a correctness requirement. We deferred WebSockets to Sprint 3. For now, the UI relies on user-initiated refreshes or simple short-polling, allowing us to focus engineering cycles entirely on the resilience of the Strategy Orchestrator.
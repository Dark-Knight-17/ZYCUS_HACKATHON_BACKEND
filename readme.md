# Zycus Logistics Reassignment Backend

A Spring Boot application providing the core backend services for an intelligent logistics Ops Command Center. The system manages orders, agents, and dynamic task reassignments using both AI and rule-based strategies.

## 🚀 Tech Stack
* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data JPA** (Database persistence)
* **Spring Web** (REST API)
* **Spring ApplicationEvents** (Event-Driven Architecture)

## 📁 Architecture & Folder Structure

The application follows a standard layered architecture with specific design patterns applied for scalability:

* `config/`: Application configuration (Security, CORS).
* `controller/`: REST endpoints (`AgentController`, `OrderController`, `SuggestionController`).
* `dto/`: Data Transfer Objects for API contracts.
* `entity/`: JPA entities mapping to database tables.
* `event/`: Event classes and listeners (e.g., `AgentOfflineEvent` triggering async reassignments).
* `model/`: Application enums and constants (Statuses, Trigger Reasons).
* `repository/`: Data access layer interfaces.
* `service/`: Core business logic and transaction management.
* `strategy/`: Implementation of the **Strategy Pattern** for dynamic reassignment (`AiReassignmentStrategy`, `RuleBasedReassignmentStrategy`).
* `utility/`: Mappers and global constants.

## ⚙️ Getting Started

1. **Configure Database:** Update `src/main/resources/application.properties` with your database credentials.
2. **Build the Project:**
   ```bash
   ./mvnw clean install
3.Run the Application:
   ./mvnw spring-boot:run
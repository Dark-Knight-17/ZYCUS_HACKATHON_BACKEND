package com.backend.zycus.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.repository.AgentRepository;
import com.backend.zycus.service.OrderService;
import com.backend.zycus.service.ReassignmentSuggestionService;
import com.backend.zycus.strategy.ReassignmentOrchestrator;
import com.backend.zycus.strategy.ReassignmentResult;


@Component
public class AgentOfflineListener {
    private final OrderService orderService;
    private final AgentRepository agentRepository;
    private final ReassignmentSuggestionService suggestionService;
    private final ReassignmentOrchestrator orchestrator;

    public AgentOfflineListener(OrderService os, AgentRepository ar, ReassignmentSuggestionService rss, ReassignmentOrchestrator ro) {
        this.orderService = os; this.agentRepository = ar; this.suggestionService = rss; this.orchestrator = ro;
    }

    @Async
    @EventListener
    public void handleAgentOffline(AgentOfflineEvent event) {
        var orders = orderService.getActiveOrdersForAgent(event.getAgentId());
        var candidates = agentRepository.findByStatus(AgentStatus.AVAILABLE);

        for (var order : orders) {
            ReassignmentResult result = orchestrator.getBestResult(order, candidates);
            if (!result.recommendations().isEmpty()) {
                 suggestionService.createSuggestionIfValid(order, result, TriggerReason.AGENT_OFFLINE);
            }
        }
    }
}
package com.backend.zycus.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.backend.zycus.service.OrderService;
import com.backend.zycus.service.ReassignmentSuggestionService;

@Component
public class AgentOfflineListener {

    private final OrderService orderService;
    private final ReassignmentSuggestionService suggestionService;

    public AgentOfflineListener(OrderService orderService, ReassignmentSuggestionService suggestionService) {
        this.orderService = orderService;
        this.suggestionService = suggestionService;
    }

    @Async // Runs in a separate thread, non-blocking!
    @EventListener
    public void handleAgentOffline(AgentOfflineEvent event) {
        String agentId = event.getAgentId();
        
        // 1. Fetch active orders (using OrderService)
        var activeOrders = orderService.getActiveOrdersForAgent(agentId);
        
        // 2. Loop through orders and trigger the Routing Strategy
        // Logic to interface with your RuleBased/AI strategy goes here
        activeOrders.forEach(order -> {
             // Example: suggestionService.createSuggestionIfValid(...);
        });
    }
    }
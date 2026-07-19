package com.backend.zycus.strategy;
import org.springframework.stereotype.Service;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;

import java.util.List;

@Service
public class ReassignmentOrchestrator {
    private final AiReassignmentStrategy ai;
    private final RuleBasedReassignmentStrategy rules;

    public ReassignmentOrchestrator(AiReassignmentStrategy ai, RuleBasedReassignmentStrategy rules) {
        this.ai = ai;
        this.rules = rules;
    }

    public ReassignmentResult getBestResult(Order order, List<Agent> candidates) {
        try {
            return ai.recommend(order, candidates);
        } catch (Exception e) {
            System.err.println("AI Strategy failed: " + e.getMessage() + ". Falling back to Rules.");
            return rules.recommend(order, candidates);
        }
    }
}
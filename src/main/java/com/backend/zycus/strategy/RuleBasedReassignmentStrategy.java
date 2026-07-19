package com.backend.zycus.strategy;

import org.springframework.stereotype.Component;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.model.AgentStatus;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class RuleBasedReassignmentStrategy implements ReassignmentStrategy {
	@Override
    public ReassignmentResult recommend(Order order, List<Agent> candidates) {
        
        List<Agent> ranked = candidates.stream()
            // 1. Filter out OFFLINE agents
            .filter(a -> a.getStatus() != AgentStatus.OFFLINE)
            // 2. Sort Logic:
            // .comparing(...) returns boolean. Boolean.compare(false, true) puts false (Available) first.
            // This ensures all AVAILABLE agents are listed before BUSY agents.
            // Then .thenComparingInt sorts by active order count within those groups.
            .sorted(Comparator
                .comparing((Agent a) -> a.getStatus() == AgentStatus.BUSY)
                .thenComparingInt(Agent::getActiveOrderCount)
            )
            .toList();

        // 3. Assign confidence scores based on the new ranking
        // Index 0 (Best) gets 1.0, Index 1 gets 0.9, etc.
        List<AgentRecommendation> recs = IntStream.range(0, ranked.size())
            .mapToObj(i -> new AgentRecommendation(
                ranked.get(i), 
                BigDecimal.valueOf(Math.max(0.1, 1.0 - (i * 0.1)))
            ))
            .toList();

        return new ReassignmentResult(recs, "Rule-based: Prioritized AVAILABLE agents, then lowest workload.");
    }
}

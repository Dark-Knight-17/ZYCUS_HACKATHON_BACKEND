package com.backend.zycus.utility;

import org.springframework.stereotype.Component;

import com.backend.zycus.dto.AgentDto;
import com.backend.zycus.dto.OrderDto;
import com.backend.zycus.dto.OrderRequestDto;
import com.backend.zycus.dto.SuggestionResponseDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.entity.ReassignmentSuggestion;
import com.backend.zycus.model.OrderStatus;
import com.backend.zycus.model.SuggestionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DtoMapper {

    // ==========================================
    // ENTITY -> DTO (For Outgoing Responses)
    // ==========================================

    public AgentDto toAgentDto(Agent agent) {
        if (agent == null) return null;
        
        return new AgentDto(
                agent.getId(),
                agent.getName(),
                agent.getActiveOrderCount(),
                agent.getStatus()
        );
    }

    public OrderDto toOrderDto(Order order) {
        if (order == null) return null;
        
        return new OrderDto(
                order.getId(),
                order.getDescription(),
                toAgentDto(order.getAssignedAgent()),
                order.getStatus()
        );
    }
    
    public SuggestionResponseDto toSuggestionResponseDto(ReassignmentSuggestion suggestion) {
        if (suggestion == null) return null;

        return new SuggestionResponseDto(
            suggestion.getId(),                                     // 1. Long id
            suggestion.getOrder().getId(),          // 2. Long orderId
            suggestion.getOrder().getDescription(),                 // 3. String orderDescription
            String.valueOf(suggestion.getRecommendedAgent().getId()),// 4. String recommendedAgentId
            suggestion.getRecommendedAgent().getName(),             // 5. String recommendedAgentName
            suggestion.getPreviousAgentId(),                        // 6. Long previousAgentId
            (suggestion.getTriggerReason() != null) ? suggestion.getTriggerReason().name() : null, // 7. String triggerReason
            suggestion.getConfidenceScore(),                        // 8. BigDecimal confidenceScore
            suggestion.getReasoning(),                              // 9. String reasoning
            suggestion.getAlternativeRecommendationsJson(),         // 10. String alternativeRecommendationsJson
            suggestion.getStatus(),                                 // 11. SuggestionStatus status
            suggestion.getCreatedAt()                               // 12. LocalDateTime createdAt
        );
    }

    // ==========================================
    // DTO -> ENTITY (For Incoming Requests)
    // ==========================================

    public Order toOrderEntity(OrderRequestDto request) {
        if (request == null) return null;

        Order order = new Order();
        order.setDescription(request.description());
        
        // Note: We do NOT set the Agent here. 
        // We set the agent in the Service layer after fetching it from the DB.
        return order;
    }
}
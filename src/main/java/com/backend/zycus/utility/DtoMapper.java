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
                suggestion.getId(),
                suggestion.getOrder().getId(),
                suggestion.getOrder().getDescription(),
                suggestion.getRecommendedAgent().getId(),
                suggestion.getRecommendedAgent().getName(),
                suggestion.getConfidenceScore(),
                suggestion.getReasoning(),
                suggestion.getStatus(),
                suggestion.getCreatedAt()
        );
    }

    // ==========================================
    // DTO -> ENTITY (For Incoming Requests)
    // ==========================================

    public Order toOrderEntity(OrderRequestDto request, Agent assignedAgent) {
        if (request == null) return null;
        
        Order order = new Order();
        order.setId(UUID.randomUUID().toString()); // Generate ID here securely
        order.setDescription(request.description());
        order.setAssignedAgent(assignedAgent);
        order.setStatus(OrderStatus.ASSIGNED);
        order.setCreatedAt(LocalDateTime.now());
        
        return order;
    }
}
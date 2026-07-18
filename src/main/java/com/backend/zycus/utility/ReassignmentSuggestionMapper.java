package com.backend.zycus.utility;

import java.time.LocalDateTime;

import com.backend.zycus.dto.RecommendationDto;
import com.backend.zycus.dto.SuggestionResponseDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.entity.ReassignmentSuggestion;


public final class ReassignmentSuggestionMapper {

    private ReassignmentSuggestionMapper() {
    }

    public static SuggestionResponseDto toResponseDto(
            ReassignmentSuggestion suggestion) {

        if (suggestion == null) {
            return null;
        }

        SuggestionResponseDto dto = new SuggestionResponseDto();

        dto.setSuggestionId(suggestion.getId());

        dto.setOrderId(suggestion.getOrder().getId());

       

        dto.setRecommendedAgentId(
                suggestion.getRecommendedAgent().getId());

        dto.setRecommendedAgentName(
                suggestion.getRecommendedAgent().getName());

        dto.setRecommendationType(
                suggestion.getRecommendationType());

        dto.setConfidenceScore(
                suggestion.getConfidenceScore());

        dto.setReasoning(
                suggestion.getReasoning());

        dto.setTriggerReason(
                suggestion.getTriggerReason());

        dto.setStatus(
                suggestion.getStatus());

        return dto;
    }

    /**
     * Used by both RuleBasedStrategy and AiRoutingStrategy.
     */
    public static ReassignmentSuggestion toEntity(
            RecommendationDto recommendation,
            Order order,
            Agent recommendedAgent) {

        ReassignmentSuggestion suggestion =
                new ReassignmentSuggestion();

        suggestion.setOrder(order);

        suggestion.setRecommendedAgent(recommendedAgent);

        suggestion.setRecommendationType(
                recommendation.getRecommendationType());

        suggestion.setConfidenceScore(
                recommendation.getConfidenceScore());

        suggestion.setReasoning(
                recommendation.getReasoning());

        suggestion.setStatus(
        		ReassignmentStatusConstants.PENDING);

        suggestion.setTriggerReason(
                TriggerReasonConstants.AGENT_OFFLINE);

        suggestion.setCreatedAt(LocalDateTime.now());

        return suggestion;
    }

}

package com.backend.zycus.dto;
 

import com.backend.zycus.model.SuggestionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SuggestionResponseDto(
    Long id, 
    Long orderId, 
    String orderDescription, 
    String recommendedAgentId, 
    String recommendedAgentName,
    Long previousAgentId,
    String triggerReason,
    BigDecimal confidenceScore,
    String reasoning,
    String alternativeRecommendationsJson,
    SuggestionStatus status,
    LocalDateTime createdAt
) {}

 
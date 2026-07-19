package com.backend.zycus.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.zycus.model.SuggestionStatus;

public record SuggestionResponseDto(
    Long id, 
    String orderId, 
    String orderDescription, 
    String recommendedAgentId, 
    String recommendedAgentName,
    BigDecimal confidenceScore,
    String reasoning,
    SuggestionStatus status,
    LocalDateTime createdAt
) {}
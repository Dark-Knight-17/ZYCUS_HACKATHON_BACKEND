package com.backend.zycus.strategy;

import java.math.BigDecimal;

import com.backend.zycus.entity.Agent;

public record AgentRecommendation(Agent agent, BigDecimal confidenceScore) {}
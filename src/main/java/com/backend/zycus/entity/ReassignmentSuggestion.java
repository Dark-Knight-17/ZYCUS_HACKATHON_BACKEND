package com.backend.zycus.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.zycus.model.SuggestionStatus;
import com.backend.zycus.model.TriggerReason;

@Entity
@Table(name = "reassignment_suggestions")
public class ReassignmentSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recommended_agent_id")
    private Agent recommendedAgent;
    
    @Column(name = "confidence_score", precision = 3, scale = 2)
    private BigDecimal confidenceScore;
    
    @Column(columnDefinition = "TEXT")
    private String reasoning;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuggestionStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_reason", nullable = false)
    private TriggerReason triggerReason;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // --- Constructors ---
    public ReassignmentSuggestion() {
    }

    public ReassignmentSuggestion(Order order, Agent recommendedAgent, BigDecimal confidenceScore, 
                                  String reasoning, SuggestionStatus status, TriggerReason triggerReason, 
                                  LocalDateTime createdAt) {
        this.order = order;
        this.recommendedAgent = recommendedAgent;
        this.confidenceScore = confidenceScore;
        this.reasoning = reasoning;
        this.status = status;
        this.triggerReason = triggerReason;
        this.createdAt = createdAt;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Agent getRecommendedAgent() { return recommendedAgent; }
    public void setRecommendedAgent(Agent recommendedAgent) { this.recommendedAgent = recommendedAgent; }

    public BigDecimal getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(BigDecimal confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getReasoning() { return reasoning; }
    public void setReasoning(String reasoning) { this.reasoning = reasoning; }

    public SuggestionStatus getStatus() { return status; }
    public void setStatus(SuggestionStatus status) { this.status = status; }

    public TriggerReason getTriggerReason() { return triggerReason; }
    public void setTriggerReason(TriggerReason triggerReason) { this.triggerReason = triggerReason; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
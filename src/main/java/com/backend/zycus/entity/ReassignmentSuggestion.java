package com.backend.zycus.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.backend.zycus.model.SuggestionStatus;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.strategy.AgentRecommendation;
import com.fasterxml.jackson.databind.ObjectMapper;
 

@Entity
@Table(name = "reassignment_suggestions")
public class ReassignmentSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY) // Removed optional=false for safety
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_agent_id", nullable = false)
    private Agent recommendedAgent;
    
    @Column(name = "previous_agent_id")
    private Long previousAgentId;
    
    @Column(name = "confidence_score", precision = 3, scale = 2)
    private BigDecimal confidenceScore;
    
    @Column(columnDefinition = "TEXT")
    private String reasoning;
    
    @Column(columnDefinition = "TEXT")
    private String alternativeRecommendationsJson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SuggestionStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_reason", nullable = false)
    private TriggerReason triggerReason;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;
    
    // --- Constructors ---
    public ReassignmentSuggestion() {}

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Agent getRecommendedAgent() { return recommendedAgent; }
    public void setRecommendedAgent(Agent recommendedAgent) { this.recommendedAgent = recommendedAgent; }
    public Long getPreviousAgentId() { return previousAgentId; }
    public void setPreviousAgentId(Long id) { this.previousAgentId = id; }
    public BigDecimal getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(BigDecimal score) { this.confidenceScore = score; }
    public String getReasoning() { return reasoning; }
    public void setReasoning(String reason) { this.reasoning = reason; }
    public SuggestionStatus getStatus() { return status; }
    public void setStatus(SuggestionStatus status) { this.status = status; }
    public TriggerReason getTriggerReason() { return triggerReason; }
    public void setTriggerReason(TriggerReason trigger) { this.triggerReason = trigger; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime time) { this.createdAt = time; }
    public String getAlternativeRecommendationsJson() { return alternativeRecommendationsJson; }
    public void setAlternativeRecommendationsJson(String json) { this.alternativeRecommendationsJson = json; }
    
    public void setAlternatives(List<AgentRecommendation> recommendations, ObjectMapper mapper) {
        try {
            this.alternativeRecommendationsJson = mapper.writeValueAsString(recommendations.stream().skip(1).toList());
        } catch (Exception e) {
            this.alternativeRecommendationsJson = "[]";
        }
    }
}
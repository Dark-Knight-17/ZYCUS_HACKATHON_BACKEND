package com.backend.zycus.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="reassignment_suggestions")
public class ReassignmentSuggestion {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="order_id",nullable=false)
   private Order order;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="recommended_agent_id",nullable=false)
   private Agent recommendedAgent;

   @Column(name="recommendation_type",nullable=false,length=20)
   private String recommendationType;

   @Column(name="confidence_score")
   private Double confidenceScore;

   @Column(length=2000)
   private String reasoning;

   @Column(nullable=false,length=30)
   private String status;

   @Column(name="trigger_reason",nullable=false,length=30)
   private String triggerReason;

   @Column(name="created_at")
   private LocalDateTime createdAt;

   public ReassignmentSuggestion() {
   }

   public Long getId() {
       return id;
   }

   public void setId(Long id) {
       this.id=id;
   }

   public Order getOrder() {
       return order;
   }

   public void setOrder(Order order) {
       this.order=order;
   }

   public Agent getRecommendedAgent() {
       return recommendedAgent;
   }

   public void setRecommendedAgent(Agent recommendedAgent) {
       this.recommendedAgent=recommendedAgent;
   }

   public String getRecommendationType() {
       return recommendationType;
   }

   public void setRecommendationType(String recommendationType) {
       this.recommendationType=recommendationType;
   }

   public Double getConfidenceScore() {
       return confidenceScore;
   }

   public void setConfidenceScore(Double confidenceScore) {
       this.confidenceScore=confidenceScore;
   }

   public String getReasoning() {
       return reasoning;
   }

   public void setReasoning(String reasoning) {
       this.reasoning=reasoning;
   }

   public String getStatus() {
       return status;
   }

   public void setStatus(String status) {
       this.status=status;
   }

   public String getTriggerReason() {
       return triggerReason;
   }

   public void setTriggerReason(String triggerReason) {
       this.triggerReason=triggerReason;
   }

   public LocalDateTime getCreatedAt() {
       return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
       this.createdAt=createdAt;
   }

}
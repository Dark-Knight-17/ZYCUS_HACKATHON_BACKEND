package com.backend.zycus.dto;


public class RecommendationDto {

    private Long recommendedAgentId;

    private Double confidenceScore;

    private String reasoning;

    /**
     * Example:
     * RecommendationTypeConstants.RULE_BASED
     * RecommendationTypeConstants.AI_BASED
     */
    private String recommendationType;

    /**
     * Example:
     * TriggerReasonConstants.AGENT_OFFLINE
     */
    private String triggerReason;

    public RecommendationDto() {
    }

    public RecommendationDto(Long recommendedAgentId,
                             Double confidenceScore,
                             String reasoning,
                             String recommendationType,
                             String triggerReason) {

        this.recommendedAgentId = recommendedAgentId;
        this.confidenceScore = confidenceScore;
        this.reasoning = reasoning;
        this.recommendationType = recommendationType;
        this.triggerReason = triggerReason;
    }

    public Long getRecommendedAgentId() {
        return recommendedAgentId;
    }

    public void setRecommendedAgentId(Long recommendedAgentId) {
        this.recommendedAgentId = recommendedAgentId;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }

    public String getTriggerReason() {
        return triggerReason;
    }

    public void setTriggerReason(String triggerReason) {
        this.triggerReason = triggerReason;
    }

}

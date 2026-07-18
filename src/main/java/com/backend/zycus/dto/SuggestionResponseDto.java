package com.backend.zycus.dto;

public class SuggestionResponseDto {

    private Long suggestionId;

    private Long orderId;

    private String customerName;

    private Long recommendedAgentId;

    private String recommendedAgentName;

    private String recommendationType;

    private Double confidenceScore;

    private String reasoning;

    private String triggerReason;

    private String status;

    public SuggestionResponseDto() {
    }

	public Long getSuggestionId() {
		return suggestionId;
	}

	public void setSuggestionId(Long suggestionId) {
		this.suggestionId = suggestionId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getRecommendedAgentId() {
		return recommendedAgentId;
	}

	public void setRecommendedAgentId(Long recommendedAgentId) {
		this.recommendedAgentId = recommendedAgentId;
	}

	public String getRecommendedAgentName() {
		return recommendedAgentName;
	}

	public void setRecommendedAgentName(String recommendedAgentName) {
		this.recommendedAgentName = recommendedAgentName;
	}

	public String getRecommendationType() {
		return recommendationType;
	}

	public void setRecommendationType(String recommendationType) {
		this.recommendationType = recommendationType;
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

	public String getTriggerReason() {
		return triggerReason;
	}

	public void setTriggerReason(String triggerReason) {
		this.triggerReason = triggerReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    // getters setters
}
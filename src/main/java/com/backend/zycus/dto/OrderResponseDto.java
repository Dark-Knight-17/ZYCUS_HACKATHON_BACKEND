package com.backend.zycus.dto;

public class OrderResponseDto {

    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAssignedAgentId() {
		return assignedAgentId;
	}

	public void setAssignedAgentId(Long assignedAgentId) {
		this.assignedAgentId = assignedAgentId;
	}

	public String getAssignedAgentName() {
		return assignedAgentName;
	}

	public void setAssignedAgentName(String assignedAgentName) {
		this.assignedAgentName = assignedAgentName;
	}


    private String description;

    private String status;

    private Long assignedAgentId;

    private String assignedAgentName;

    public OrderResponseDto() {
    }

    // getters setters
}

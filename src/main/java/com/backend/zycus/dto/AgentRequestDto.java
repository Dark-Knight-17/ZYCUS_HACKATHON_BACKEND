package com.backend.zycus.dto;


public class AgentRequestDto {

    private String name;

    private String status;

    public AgentRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

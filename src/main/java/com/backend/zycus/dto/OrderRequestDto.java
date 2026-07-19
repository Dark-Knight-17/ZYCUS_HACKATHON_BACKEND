package com.backend.zycus.dto;


public record OrderRequestDto(
	    String description,
	    Long assignedAgentId
	) {}
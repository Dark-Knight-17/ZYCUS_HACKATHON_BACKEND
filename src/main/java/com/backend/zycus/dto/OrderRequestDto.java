package com.backend.zycus.dto;


public record OrderRequestDto(
	    String description,
	    String assignedAgentId
	) {}
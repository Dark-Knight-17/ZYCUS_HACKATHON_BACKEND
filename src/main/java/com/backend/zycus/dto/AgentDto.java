package com.backend.zycus.dto;

import com.backend.zycus.model.AgentStatus;

public record AgentDto(
		Long id, 
	    String name, 
	    int activeOrderCount, 
	    AgentStatus status
	) {}
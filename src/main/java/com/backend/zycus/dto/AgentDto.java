package com.backend.zycus.dto;

import com.backend.zycus.model.AgentStatus;

public record AgentDto(
	    String id, 
	    String name, 
	    int activeOrderCount, 
	    AgentStatus status
	) {}
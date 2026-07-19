package com.backend.zycus.dto;

import com.backend.zycus.model.OrderStatus;

public record OrderDto(
		Long id, 
	    String description, 
	    AgentDto assignedAgent, 
	    OrderStatus status
	) {}
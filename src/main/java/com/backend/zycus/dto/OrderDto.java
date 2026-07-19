package com.backend.zycus.dto;

import com.backend.zycus.model.OrderStatus;

public record OrderDto(
	    String id, 
	    String description, 
	    AgentDto assignedAgent, 
	    OrderStatus status
	) {}
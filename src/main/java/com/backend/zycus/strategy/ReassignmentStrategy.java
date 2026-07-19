package com.backend.zycus.strategy;

import java.util.List;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;

public interface ReassignmentStrategy {
	ReassignmentResult recommend(Order order, List<Agent> candidates);
	}
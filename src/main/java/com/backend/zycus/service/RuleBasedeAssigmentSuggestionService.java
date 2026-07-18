package com.backend.zycus.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.utility.AgentStatusConstants;
@Service
public class RuleBasedeAssigmentSuggestionService {
	
	public List<Agent> recommendAgents(List<Agent> agents){
		
		List<Agent> recommendedAgent = agents.stream()
		        .filter(agent -> !AgentStatusConstants.OFFLINE.equals(agent.getStatus()))
		        .sorted(
		                Comparator
		                        .comparing((Agent agent) ->
		                                AgentStatusConstants.BUSY.equals(agent.getStatus()))
		                        .thenComparingInt(Agent::getActiveOrderCount)
		        )
		        .toList();
		
		return recommendedAgent;
	}

}

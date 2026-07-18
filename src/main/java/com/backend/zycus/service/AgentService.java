package com.backend.zycus.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.zycus.dto.AgentResponseDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.repository.AgentRepository;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.utility.AgentMapper;
import com.backend.zycus.utility.OrderMapper;

@Service
public class AgentService {
	
	public final AgentRepository agentRepository;
	public final AgentMapper agentMapper;

	
	public AgentService (AgentRepository agentRepository,AgentMapper agentMapper) {
		this.agentRepository= agentRepository;
		this.agentMapper=agentMapper;
	}
	
	public AgentResponseDto updateAgentAvailability(Long agentId,String status) {
		
		AgentResponseDto response=null;
		Optional<Agent> agent= agentRepository.findById(agentId);
		try {
				if(agent.isPresent()) {
					Agent a= agent.get();
					a.setStatus(status);
				    Agent saved= agentRepository.save(a);
				    response= agentMapper.toResponseDto(saved);
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}

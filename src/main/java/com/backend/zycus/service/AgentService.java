package com.backend.zycus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.zycus.dto.AgentDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.event.AgentOfflineEvent;
import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.repository.AgentRepository;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.utility.DtoMapper;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AgentService {
	
	
	private static final Logger log =
            LoggerFactory.getLogger(AgentService.class);
    private final AgentRepository agentRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final DtoMapper dtoMapper;

    public AgentService(AgentRepository agentRepository, ApplicationEventPublisher eventPublisher, DtoMapper dtoMapper) {
        this.agentRepository = agentRepository;
        this.eventPublisher = eventPublisher;
        this.dtoMapper = dtoMapper;
    }

    
    public List<Agent> findAll(){
    	try {
    	return agentRepository.findAll();}
    	catch(Exception e) {
    		e.printStackTrace();
    		return new ArrayList<Agent>();
    	}
    }
    
    public List<Agent> findByStatus(AgentStatus status){
    	
    	try {
    	    if (status == null ) {
                return findAll();
            }
            return agentRepository.findByStatus(status);

    	}
    	
    	catch(Exception e ) {
    		
    		e.printStackTrace();
            return findAll();

    	}
    	
    
    }
    
    
    
//    @Transactional
    public AgentDto updateStatus(String agentId, AgentStatus newStatus) {
    	
    	try {
    		
    	log.info("INSIDE  AgentDto updateStatus ");	
        Agent agent = agentRepository.findById(Long.valueOf(agentId))
                .orElseThrow(() -> new IllegalArgumentException("Agent not found: " + agentId));

        if (!agent.getStatus().canTransitionTo(newStatus)) {
            throw new IllegalStateException("Invalid transition: " + agent.getStatus() + " -> " + newStatus);
        }

        agent.setStatus(newStatus);
        Agent savedAgent = agentRepository.save(agent);

        if (newStatus == AgentStatus.OFFLINE) {
            eventPublisher.publishEvent(new AgentOfflineEvent(this, savedAgent.getId()));
        }

        return dtoMapper.toAgentDto(savedAgent);
    }
    
    catch(Exception e) {
    	
    	e.printStackTrace();
    	return null;
    }}
}
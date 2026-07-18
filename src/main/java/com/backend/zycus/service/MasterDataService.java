package com.backend.zycus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.model.OrderStatus;
import com.backend.zycus.model.TriggerReason;

 
 
@Service

public interface MasterDataService {
	
	@Transactional(readOnly = true)
    List<OrderStatus> getOrderStatuses();

	@Transactional(readOnly = true)
    List<AgentStatus> getAgentStatuses();

	@Transactional(readOnly = true)
    List<TriggerReason> getTriggerReasons();

}
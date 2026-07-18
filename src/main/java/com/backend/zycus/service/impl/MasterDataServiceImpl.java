package com.backend.zycus.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.model.OrderStatus;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.repository.AgentStatusRepository;
import com.backend.zycus.repository.OrderStatusRepository;
import com.backend.zycus.repository.TriggerReasonRepository;
import com.backend.zycus.service.MasterDataService;

@Service
public class MasterDataServiceImpl  implements MasterDataService {

    private final OrderStatusRepository orderStatusRepository;
    private final AgentStatusRepository agentStatusRepository;
    private final TriggerReasonRepository triggerReasonRepository;

    public MasterDataServiceImpl(
            OrderStatusRepository orderStatusRepository,
            AgentStatusRepository agentStatusRepository,
            TriggerReasonRepository triggerReasonRepository) {

        this.orderStatusRepository = orderStatusRepository;
        this.agentStatusRepository = agentStatusRepository;
        this.triggerReasonRepository = triggerReasonRepository;
    }

    @Override
    public List<OrderStatus> getOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    @Override
    public List<AgentStatus> getAgentStatuses() {
        return agentStatusRepository.findAll();
    }

    @Override
    public List<TriggerReason> getTriggerReasons() {
        return triggerReasonRepository.findAll();
    }

}
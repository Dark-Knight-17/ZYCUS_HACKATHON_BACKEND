package com.backend.zycus.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.model.OrderStatus;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.service.MasterDataService;


@RestController
@RequestMapping("/api/master")
public class MasterDataController {

    private final MasterDataService masterDataService;

    public MasterDataController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    @GetMapping("/order-statuses")
    public List<OrderStatus> getOrderStatuses() {
        return masterDataService.getOrderStatuses();
    }

    @GetMapping("/agent-statuses")
    public List<AgentStatus> getAgentStatuses() {
        return masterDataService.getAgentStatuses();
    }

    @GetMapping("/trigger-reasons")
    public List<TriggerReason> getTriggerReasons() {
        return masterDataService.getTriggerReasons();
    }
}
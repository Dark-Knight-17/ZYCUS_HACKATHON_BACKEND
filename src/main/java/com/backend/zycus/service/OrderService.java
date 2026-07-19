package com.backend.zycus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.zycus.dto.OrderDto;
import com.backend.zycus.dto.OrderRequestDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.model.OrderStatus;
import com.backend.zycus.repository.AgentRepository;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.utility.DtoMapper;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AgentRepository agentRepository;
    private final DtoMapper dtoMapper;

    public OrderService(OrderRepository orderRepository, AgentRepository agentRepository, DtoMapper dtoMapper) {
        this.orderRepository = orderRepository;
        this.agentRepository = agentRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public OrderDto createOrder(OrderRequestDto request) {
        Agent agent = agentRepository.findById(request.assignedAgentId())
                .orElseThrow(() -> new IllegalArgumentException("Agent not found: " + request.assignedAgentId()));

        // Use the mapper to convert DTO to Entity
        Order order = dtoMapper.toOrderEntity(request, agent);
        Order savedOrder = orderRepository.save(order);
        
        return dtoMapper.toOrderDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<Order> getActiveOrdersForAgent(String agentId) {
        return orderRepository.findActiveOrdersByAgentId(
                agentId, 
                List.of(OrderStatus.ASSIGNED, OrderStatus.REASSIGNMENT_PENDING)
        );
    }
}

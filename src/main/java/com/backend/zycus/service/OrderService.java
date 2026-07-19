package com.backend.zycus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.zycus.dto.OrderDto;
import com.backend.zycus.dto.OrderRequestDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.model.OrderStatus;
import com.backend.zycus.repository.AgentRepository;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.utility.DtoMapper;

import java.util.ArrayList;
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
       

        // Use the mapper to convert DTO to Entity
        Order order = dtoMapper.toOrderEntity(request);
        Order savedOrder = orderRepository.save(order);   
        return dtoMapper.toOrderDto(savedOrder);
    }
    
    
    public List<Order> findAll(){
    	try {
    	return orderRepository.findAll();}
    	catch(Exception e) {
    		e.printStackTrace();
    		return new ArrayList<Order>();
    	}
    }
    

    @Transactional(readOnly = true)
    public List<Order> getActiveOrdersForAgent(Long agentId) {
        return orderRepository.findActiveOrdersByAgentId(
                agentId, 
                List.of(OrderStatus.ASSIGNED, OrderStatus.REASSIGNMENT_PENDING)
        );
    }
    
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
    
    public Order create(Order order) {
        return orderRepository.save(order);
    }
    
    public List<Order> findByStatus(String status) {
        if (status == null || status.isEmpty()) {
            return orderRepository.findAll();
        }
        return orderRepository.findByStatus(status);
    }
    
 // Specifically for the Reassignment orchestration
    public List<Agent> getAvailableAgents() {
        return agentRepository.findByStatus(AgentStatus.AVAILABLE);
    }
}

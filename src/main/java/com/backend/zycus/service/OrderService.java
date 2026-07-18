package com.backend.zycus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.zycus.dto.OrderResponseDto;
import com.backend.zycus.entity.Order;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.utility.OrderMapper;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	
	public OrderService (OrderRepository orderRepository,OrderMapper orderMapper) {
		this. orderRepository= orderRepository;
		this.orderMapper=orderMapper;
	}
	
	public List<OrderResponseDto> getOrderResponse(){
		List<OrderResponseDto> response = new ArrayList<>();
		List<Order> list= orderRepository.findAllWithAssignedAgent();
		for(Order o: list) {
			OrderResponseDto dto=orderMapper.toResponseDto(o);
			response.add(dto);	
		}
		return response;	
	}
	
	public OrderResponseDto saveOrder(Order order) {
		Order o=null;
		OrderResponseDto response=null;
		try {
			 o=orderRepository.save(order);	
			 response=orderMapper.toResponseDto(o);
		}	
		catch(Exception e) {
			e.printStackTrace();
		}
		return response;	
	}

	
	public List<OrderResponseDto> findByStatus(String status){
		
		List<OrderResponseDto> response = new ArrayList<>();
		List<Order> list= orderRepository.findByStatus(status);
		for(Order o: list) {
			OrderResponseDto dto=orderMapper.toResponseDto(o);
			response.add(dto);	
		}
		return response;	
		
	}
	
	
}

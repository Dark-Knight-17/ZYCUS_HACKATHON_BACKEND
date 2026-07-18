package com.backend.zycus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.zycus.dto.AgentResponseDto;
import com.backend.zycus.dto.OrderRequestDto;
import com.backend.zycus.dto.OrderResponseDto;
import com.backend.zycus.entity.Order;
import com.backend.zycus.service.AgentService;
import com.backend.zycus.service.OrderService;

@RestController
@RequestMapping("/public/v1")
public class PublicApiController {
	
	private final OrderService orderService;
	private final AgentService agentService;

	
	PublicApiController(OrderService orderService,AgentService agentService){
		this.orderService=orderService;
		this.agentService=agentService;
	};
	
	   @PostMapping("/orders")
	    public ResponseEntity<OrderResponseDto> createOrder(
	            @RequestBody Order request) {
           try {
		   OrderResponseDto order = orderService.saveOrder(request);
	        return new ResponseEntity<>(order, HttpStatus.CREATED);}
           catch(Exception e) {
        	   
        	   return  new ResponseEntity<>( HttpStatus.BAD_REQUEST);
           }
	    } 
	   
	   @GetMapping("/orders")
	   public ResponseEntity<List<OrderResponseDto>> getOrdersByStatus(@RequestParam("status") String status){
		   
		   List<OrderResponseDto> response =null;
		   
		   try {
			response=orderService.findByStatus(status);   
	        return new ResponseEntity<>(response, HttpStatus.OK);
		   }
		   
		   catch(Exception e) {
			   e.printStackTrace();
        	   return  new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		   }   
	   }
	   
	   
	   @PatchMapping("/agents/{id}/{status}")
	   public ResponseEntity<AgentResponseDto> updateAgentStatus(@PathVariable String id,@PathVariable String status){
		   
		   AgentResponseDto response =null;
		   
		   try {
			   // add check if valid status
			   response=agentService.updateAgentAvailability(Long.valueOf(id),status);
		       return new ResponseEntity<>(response, HttpStatus.OK);
		   }
		   
		   catch(Exception e) {
			   e.printStackTrace();
        	   return  new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		   }   
	   }
	   
	   @PatchMapping("/suggestions/{id}")
	   public ResponseEntity<AgentResponseDto> acceptOrRejectSuggestion(@PathVariable String id,@PathVariable String status){
		   
		   AgentResponseDto response =null;
		   
		   try {
			   // add check if valid status
			   response=agentService.updateAgentAvailability(Long.valueOf(id),status);
		       return new ResponseEntity<>(response, HttpStatus.OK);
		   }
		   
		   catch(Exception e) {
			   e.printStackTrace();
        	   return  new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		   }   
	   }
	   
	   
	

}

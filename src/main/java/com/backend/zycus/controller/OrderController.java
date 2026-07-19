package com.backend.zycus.controller;

 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.zycus.dto.OrderDto;
import com.backend.zycus.dto.OrderRequestDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.service.OrderService;
import com.backend.zycus.service.ReassignmentSuggestionService;
import com.backend.zycus.strategy.ReassignmentOrchestrator;
import com.backend.zycus.strategy.ReassignmentResult;

import java.util.List;

@RestController
@RequestMapping("/public/v1/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final ReassignmentOrchestrator orchestrator;
    private final ReassignmentSuggestionService suggestionService;

    public OrderController(OrderService orderService, ReassignmentOrchestrator orchestrator, ReassignmentSuggestionService suggestionService) {
        this.orderService = orderService;
        this.orchestrator = orchestrator;
        this.suggestionService = suggestionService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto order) {
        if (order == null) return ResponseEntity.badRequest().body("Order request cannot be empty");
        try {
            return ResponseEntity.ok(orderService.createOrder(order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order");
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> listOrders(@RequestParam(name = "status",required = false) String status) {
    	if(status==null || status.equals("ALL")) {
            return ResponseEntity.ok(orderService.findAll());

    	}
        return ResponseEntity.ok(orderService.findByStatus(status));
    }

    @PostMapping("/{id}/suggest")
    public ResponseEntity<?> triggerSuggestion(@PathVariable("id") String id) {
        try {
            Order order = orderService.findById(Long.valueOf(id));
            if (order == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            
            List<Agent> candidates = orderService.getAvailableAgents();
            ReassignmentResult result = orchestrator.getBestResult(order, candidates);
            
            suggestionService.createSuggestionIfValid(order, result, TriggerReason.INITIAL);
            return ResponseEntity.ok().build();
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid Order ID format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Orchestration failed");
        }
    }
}
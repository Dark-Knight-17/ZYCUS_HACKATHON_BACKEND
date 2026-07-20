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
import java.util.Map;

@RestController
@RequestMapping("/public/v1/orders")

public class OrderController {

    private final OrderService orderService;
    private final ReassignmentOrchestrator orchestrator;
    private final ReassignmentSuggestionService suggestionService;

    public OrderController(
            OrderService orderService,
            ReassignmentOrchestrator orchestrator,
            ReassignmentSuggestionService suggestionService) {

        this.orderService = orderService;
        this.orchestrator = orchestrator;
        this.suggestionService = suggestionService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto order) {

        if (order == null) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "message", "Order request cannot be empty"
                    )
            );

        }

        try {

            OrderDto createdOrder = orderService.createOrder(order);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Order created successfully",
                            "data", createdOrder
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "success", false,
                            "message", "Failed to create order"
                    )
            );

        }

    }

    @GetMapping
    public ResponseEntity<?> listOrders(
            @RequestParam(name = "status", required = false) String status) {

        try {

            List<Order> orders;

            if (status == null || status.equals("ALL")) {
                orders = orderService.findAll();
            } else {
                orders = orderService.findByStatus(status);
            }

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "data", orders
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "success", false,
                            "message", "Unable to fetch orders"
                    )
            );

        }

    }

    @PostMapping("/{id}/suggest")
    public ResponseEntity<?> triggerSuggestion(
            @PathVariable("id") Long id) {

        try {

            Order order = orderService.findById(id);

            if (order == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of(
                                "success", false,
                                "message", "Order not found"
                        )
                );

            }

            List<Agent> candidates = orderService.getAvailableAgents();

            ReassignmentResult result =
                    orchestrator.getBestResult(order, candidates);

            suggestionService.createSuggestionIfValid(
                    order,
                    result,
                    TriggerReason.INITIAL
            );

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Suggestion generated successfully",
                            "orderId", id
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "success", false,
                            "message", "Suggestion generation failed"
                    )
            );

        }

    }

}
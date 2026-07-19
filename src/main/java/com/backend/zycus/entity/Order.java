package com.backend.zycus.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.backend.zycus.model.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    
    private Long id;
    
    @Column(nullable = false)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id")
    private Agent assignedAgent;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // --- Constructors ---
    public Order() {
    }

    public Order(Long id, String description, Agent assignedAgent, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.assignedAgent = assignedAgent;
        this.status = status;
        this.createdAt = createdAt;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Agent getAssignedAgent() { return assignedAgent; }
    public void setAssignedAgent(Agent assignedAgent) { this.assignedAgent = assignedAgent; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
package com.backend.zycus.entity;

import com.backend.zycus.model.AgentStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long  id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "active_order_count")
    private int activeOrderCount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentStatus status;

    // --- Constructors ---
    public Agent() {
    }

    public Agent(Long id, String name, int activeOrderCount, AgentStatus status) {
        this.id = id;
        this.name = name;
        this.activeOrderCount = activeOrderCount;
        this.status = status;
    }

    // --- Getters and Setters ---
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getActiveOrderCount() { return activeOrderCount; }
    public void setActiveOrderCount(int activeOrderCount) { this.activeOrderCount = activeOrderCount; }

    public AgentStatus getStatus() { return status; }
    public void setStatus(AgentStatus status) { this.status = status; }
}
package com.backend.zycus.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="description",nullable=false)
    private String description;

    @Column(nullable=false,length=30)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assigned_agent_id",nullable=false)
    private Agent assignedAgent;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }



    public String getDescription() {
        return description;
    }

    @Override
	public String toString() {
		return "Order [id=" + id + ", description=" + description + ", status=" + status + ", assignedAgent="
				+ assignedAgent + "]";
	}

	public void setDescription(String description) {
        this.description=description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    public Agent getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(Agent assignedAgent) {
        this.assignedAgent=assignedAgent;
    }
}
package com.backend.zycus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 30)
    private String status;

    @Column(name = "active_order_count",nullable = false)
    private Integer activeOrderCount;

    public Agent() {
    }

    public Long getId() {
        return id;
    }

    @Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", status=" + status + ", activeOrderCount=" + activeOrderCount
				+ "]";
	}

	public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public Integer getActiveOrderCount() {
        return activeOrderCount;
    }

    public void setActiveOrderCount(Integer activeOrderCount) {
        this.activeOrderCount = activeOrderCount;
    }
}

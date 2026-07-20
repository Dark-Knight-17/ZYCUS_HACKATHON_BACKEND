package com.backend.zycus.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.zycus.entity.Order;
import com.backend.zycus.model.OrderStatus;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Triggered during the async reactive loop.
     * Fetches all active orders for an agent who just went OFFLINE.
     * Uses JOIN FETCH to pull the Agent data in the same query, preventing N+1 
     * if the Routing Engine needs agent details to calculate the next move.
     */
    @Query("SELECT o FROM Order o " +
           "JOIN FETCH o.assignedAgent a " +
           "WHERE a.id = :agentId AND o.status IN :statuses")
    List<Order> findActiveOrdersByAgentId(
            @Param("agentId") Long agentId, 
            @Param("statuses") List<OrderStatus> statuses
    );
    
    List<Order> findByStatus(OrderStatus status);
}
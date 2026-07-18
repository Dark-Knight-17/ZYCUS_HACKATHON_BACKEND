package com.backend.zycus.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.zycus.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

   /**
    * Orders assigned to an agent.
    */
   List<Order> findByAssignedAgentId(Long agentId);

   /**
    * Used by GET /orders?status=
    */
   List<Order> findByStatus(String status);

   /**
    * Prevents N+1 while returning orders.
    */
   @Query("""
       select o
       from Order o
       join fetch o.assignedAgent
       """)
   List<Order> findAllWithAssignedAgent();

   /**
    * Used after agent goes offline.
    */
   @Query("""
       select o
       from Order o
       join fetch o.assignedAgent
       where o.assignedAgent.id = :agentId
       and o.status = 'ASSIGNED'
       """)
   List<Order> findAssignedOrdersOfAgent(Long agentId);
   

}
package com.backend.zycus.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.zycus.entity.ReassignmentSuggestion;


public interface ReassignmentSuggestionRepository
       extends JpaRepository<ReassignmentSuggestion, Long> {

   /**
    * Suggestion history of an order.
    */
   List<ReassignmentSuggestion> findByOrderId(Long orderId);

   /**
    * Pending approvals.
    */
   List<ReassignmentSuggestion> findByStatus(String status);

   /**
    * Prevents N+1.
    */
   @Query("""
       select s
       from ReassignmentSuggestion s
       join fetch s.order
       join fetch s.recommendedAgent
       """)
   List<ReassignmentSuggestion> findAllSuggestions();

   /**
    * Ops dashboard.
    */
   @Query("""
       select s
       from ReassignmentSuggestion s
       join fetch s.order
       join fetch s.recommendedAgent
       where s.status='PENDING'
       """)
   List<ReassignmentSuggestion> findPendingSuggestions();

}

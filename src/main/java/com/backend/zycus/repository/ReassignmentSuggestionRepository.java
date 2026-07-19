package com.backend.zycus.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.backend.zycus.entity.ReassignmentSuggestion;
import com.backend.zycus.model.SuggestionStatus;
import com.backend.zycus.model.TriggerReason;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReassignmentSuggestionRepository extends JpaRepository<ReassignmentSuggestion, Long> {

    /**
     * SOLVES N+1 QUERY PROBLEM:
     * Used by the Frontend Dashboard to display pending suggestions.
     * A standard findAll() would trigger 1 query for suggestions, N queries for Orders, 
     * and N queries for Agents. This single JOIN FETCH pulls everything at once.
     */
    @Query("SELECT s FROM ReassignmentSuggestion s " +
           "JOIN FETCH s.order " +
           "JOIN FETCH s.recommendedAgent " +
           "WHERE s.status = :status " +
           "ORDER BY s.createdAt DESC")
    List<ReassignmentSuggestion> findByStatusWithDetails(@Param("status") SuggestionStatus status);

    /**
     * ENFORCES IDEMPOTENCY:
     * Before generating a new suggestion in the async loop, the engine MUST check this.
     * If an agent flickers OFFLINE/AVAILABLE rapidly, this prevents generating 
     * duplicate PENDING suggestions for the same order and trigger.
     */
    boolean existsByOrderIdAndStatusAndTriggerReason(
    		Long orderId, 
            SuggestionStatus status, 
            TriggerReason triggerReason
    );
}

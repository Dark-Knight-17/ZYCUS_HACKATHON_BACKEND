package com.backend.zycus.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.model.AgentStatus;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    
    /**
     * Used by the Routing Engine to fetch all currently AVAILABLE agents 
     * when calculating reassignment recommendations.
     */
    List<Agent> findByStatus(AgentStatus status);
    List<Agent> findByStatus(String status);

}
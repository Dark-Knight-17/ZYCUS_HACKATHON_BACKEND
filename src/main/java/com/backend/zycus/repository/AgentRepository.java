package com.backend.zycus.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.zycus.entity.Agent;


@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

   Optional<Agent> findById(Long id);

   List<Agent> findByStatus(String status);

   /**
    * Used by RuleBasedRoutingStrategy.
    */
   List<Agent> findByStatusOrderByActiveOrderCountAsc(String status);

   /**
    * Used by AI Strategy.
    */
   @Query("""
       select a
       from Agent a
       where a.status <> 'OFFLINE'
       order by a.activeOrderCount asc
       """)
   List<Agent> findAvailableAgents();

}
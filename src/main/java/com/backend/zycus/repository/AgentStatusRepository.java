package com.backend.zycus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.zycus.model.AgentStatus;

public interface AgentStatusRepository  extends JpaRepository<AgentStatus, Long>{
	
    Optional<AgentStatus> findByCode(String code);

}

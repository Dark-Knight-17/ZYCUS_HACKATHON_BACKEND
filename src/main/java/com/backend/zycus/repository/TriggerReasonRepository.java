package com.backend.zycus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.zycus.model.TriggerReason;

public interface TriggerReasonRepository  extends JpaRepository<TriggerReason, Long>{
	
	  Optional<TriggerReason> findByCode(String code);

}

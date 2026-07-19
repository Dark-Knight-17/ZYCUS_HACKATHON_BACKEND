package com.backend.zycus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.zycus.dto.SuggestionResponseDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.entity.ReassignmentSuggestion;
import com.backend.zycus.model.SuggestionStatus;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.repository.ReassignmentSuggestionRepository;
import com.backend.zycus.utility.DtoMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReassignmentSuggestionService {

    private final ReassignmentSuggestionRepository suggestionRepository;
    private final DtoMapper dtoMapper;

    public ReassignmentSuggestionService(ReassignmentSuggestionRepository suggestionRepository, DtoMapper dtoMapper) {
        this.suggestionRepository = suggestionRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public void createSuggestionIfValid(Order order, Agent recommendedAgent, BigDecimal confidence, String reasoning, TriggerReason trigger) {
        
        boolean alreadyPending = suggestionRepository.existsByOrderIdAndStatusAndTriggerReason(
                order.getId(), SuggestionStatus.PENDING, trigger
        );

        if (alreadyPending) return;

        ReassignmentSuggestion suggestion = new ReassignmentSuggestion(
                order, recommendedAgent, confidence, reasoning, SuggestionStatus.PENDING, trigger, LocalDateTime.now()
        );
        
        suggestionRepository.save(suggestion);
    }

    @Transactional(readOnly = true)
    public List<SuggestionResponseDto> getPendingSuggestions() {
        return suggestionRepository.findByStatusWithDetails(SuggestionStatus.PENDING)
                .stream()
                .map(dtoMapper::toSuggestionResponseDto) // Cleaner method reference
                .collect(Collectors.toList());
    }
}
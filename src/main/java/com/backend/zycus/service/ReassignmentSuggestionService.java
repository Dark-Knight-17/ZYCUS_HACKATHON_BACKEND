package com.backend.zycus.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.zycus.dto.SuggestionResponseDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.entity.ReassignmentSuggestion;
import com.backend.zycus.model.SuggestionStatus;
import com.backend.zycus.model.TriggerReason;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.repository.ReassignmentSuggestionRepository;
import com.backend.zycus.strategy.AgentRecommendation;
import com.backend.zycus.strategy.ReassignmentResult;
import com.backend.zycus.utility.DtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReassignmentSuggestionService {

	
    private final ReassignmentSuggestionRepository suggestionRepository;
    private final DtoMapper dtoMapper;
    private final ObjectMapper objectMapper = new ObjectMapper ();
    private final OrderRepository orderRepository;
    
    public ReassignmentSuggestionService(ReassignmentSuggestionRepository suggestionRepository, DtoMapper dtoMapper
    		,OrderRepository orderRepository) {
        this.suggestionRepository = suggestionRepository;
        this.dtoMapper = dtoMapper;
         this.orderRepository=orderRepository;
    }

    @Transactional
    public void createSuggestionIfValid(Order order, ReassignmentResult result, TriggerReason trigger) { 
        
        boolean alreadyPending = suggestionRepository.existsByOrderIdAndStatusAndTriggerReason(
                order.getId(), SuggestionStatus.PENDING, trigger
        );

        if (alreadyPending) return;

        AgentRecommendation topPick = result.recommendations().get(0);
        ReassignmentSuggestion suggestion = new ReassignmentSuggestion();
        suggestion.setOrder(order);
        suggestion.setRecommendedAgent(topPick.agent());
        suggestion.setPreviousAgentId(order.getAssignedAgent() != null ? order.getAssignedAgent().getId() : null);
        suggestion.setConfidenceScore(topPick.confidenceScore());
        suggestion.setReasoning(result.reasoning());
        suggestion.setStatus(SuggestionStatus.PENDING);
        suggestion.setTriggerReason(trigger);
        suggestion.setCreatedAt(LocalDateTime.now());
        
        // Save the alternatives!
        try {
            // Skips the first one (which is the top pick), saves the rest
            var alternatives = result.recommendations().stream().skip(1).toList();
            suggestion.setAlternativeRecommendationsJson(objectMapper.writeValueAsString(alternatives));
        } catch (Exception e) {
        	e.printStackTrace();
            suggestion.setAlternativeRecommendationsJson("[]");
        }        
        suggestionRepository.save(suggestion);
        
        suggestionRepository.save(suggestion);
    }

    
    @Transactional
    public void updateStatus(Long id, String statusString) {
        ReassignmentSuggestion suggestion = suggestionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Suggestion not found with id: " + id));

        SuggestionStatus status = SuggestionStatus.valueOf(statusString.toUpperCase());
        suggestion.setStatus(status);

        // If ACCEPTED, we perform the actual reassignment
        if (status == SuggestionStatus.ACCEPTED) {
            Order order = suggestion.getOrder();
            order.setAssignedAgent(suggestion.getRecommendedAgent());
            // Update order status if  lifecycle requires it
            orderRepository.save(order); 
        }

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
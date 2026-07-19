package com.backend.zycus.strategy;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
 import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AiReassignmentStrategy implements ReassignmentStrategy {
	    private final RestClient restClient;
	    private final ObjectMapper mapper = new ObjectMapper();
	    @Value("${llm.api.key}")
	    private   String API_KEY ; // Use environment variables for safety
	    private  final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

	    public AiReassignmentStrategy() {
	        this.restClient = RestClient.create();
	    }

	    @Override
	    public ReassignmentResult recommend(Order order, List<Agent> candidates) {
	    	
	    	String prompt = "Order: "+ order.getDescription() + "was assigned to "+order.getAssignedAgent()+"but they are not available to fulfill the delivery"
	    			+ ". Available agents: " + candidates + ". Rank available agents that can be assigned to this order. "
	    			+ "Return JSON: {\"recommendations\": [{\"agentId\": \"...\", \"score\": 0.9}], \"reasoning\": \"...\"}, "
	                + "the score has to be between 0 and 1 (decimal) , 1 representing the most fit agent while 0 the least fit ";
	        
	        Map<String, Object> body = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));

	        try {
	            // Perform request with explicit error handling
	            Map<String, Object> response = restClient.post()
	                    .uri(URL)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .body(body)
	                    .retrieve()
	                    // Handle 4xx/5xx errors specifically
	                    .onStatus(HttpStatusCode::isError, (request, res) -> {
	                        throw new RuntimeException("AI Provider returned error status: " + res.getStatusCode());
	                    })
	                    .body(Map.class);

	            // Extract and clean response
	            String text = extractTextFromResponse(response);
	            String cleanJson = text.replaceAll("```json", "").replaceAll("```", "").trim();
	            
	            // Parse JSON and map to ReassignmentResult
	            JsonNode root = mapper.readTree(cleanJson);
	            List<AgentRecommendation> recs = new ArrayList<>();
	            
	            for (JsonNode node : root.get("recommendations")) {
	                String id = node.get("agentId").asText();
	                BigDecimal score = new BigDecimal(node.get("score").asText());
	                candidates.stream().filter(a -> a.getId().equals(id)).findFirst()
	                          .ifPresent(a -> recs.add(new AgentRecommendation(a, score)));
	            }
	            return new ReassignmentResult(recs, root.get("reasoning").asText());

	        } catch (Exception e) {
	            // This propagates to the Orchestrator, which catches it and falls back to Rules
	            throw new RuntimeException("AI Routing failed: " + e.getMessage(), e);
	        }
	    }

	    private String extractTextFromResponse(Map<String, Object> response) {
	        try {
	            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
	            List<Map<String, Object>> content = (List<Map<String, Object>>) candidates.get(0).get("content");
	            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get(0).get("parts");
	            return (String) parts.get(0).get("text");
	        } catch (Exception e) {
	            throw new RuntimeException("AI response structure malformed", e);
	        }
	    }
	}
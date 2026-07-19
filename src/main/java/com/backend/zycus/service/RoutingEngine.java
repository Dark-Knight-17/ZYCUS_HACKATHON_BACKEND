package com.backend.zycus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.model.RecommendationType;
import com.backend.zycus.repository.AgentRepository;
import com.backend.zycus.repository.OrderRepository;
import com.backend.zycus.utility.GlobalConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service

public class RoutingEngine {
	
	private final GeminiReassignmentService geminiReassignmentService;
	private final RuleBasedeAssigmentSuggestionService ruleBasedReassignmentSuggestion;
	private final OrderRepository orderRep;

	private final AgentRepository agentRep;


	 RoutingEngine(GeminiReassignmentService gm ,
			 RuleBasedeAssigmentSuggestionService ruleBasedReassignmentSuggestion
			 ,AgentRepository agentRep,
			 OrderRepository orderRep) 
	 {
		this.geminiReassignmentService=gm;
		this.ruleBasedReassignmentSuggestion=ruleBasedReassignmentSuggestion;
		this.agentRep=agentRep;
		this.orderRep=orderRep;
		
	}
	
 	
	public JsonNode getReAssignmentSuggestion() {
		ObjectMapper mapper = new ObjectMapper();

		ObjectNode json = mapper.createObjectNode();
		try {
			
			List<Agent> agentList=agentRep.findAll();
			List<Order> orderList=orderRep.findAll();
			if(geminiReassignmentService.testGeminiAvailability())
			{
				String responseFromGemini=geminiReassignmentService.askGemini(agentList, orderList);
			    ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonArray = objectMapper.readTree(responseFromGemini);
				System.out.println(jsonArray.isArray()); // true
				json.put("responseModelRecommendor", GlobalConstants.AI_BASED);
				json.put("response", jsonArray);
				return json;	
			}
			
			else {
				// default to rule based
				List result=ruleBasedReassignmentSuggestion.recommendAgents(agentList);
				json.put("responseModelRecommendor", GlobalConstants.RULE_BASED);
				json.put("response", ruleBasedReassignmentSuggestion.toString());
				return json;	

			}
		}
		
		catch(Exception e) {
			json.put("success",false);
			json.put("errorMessage","Error occured while processing your request");

			e.printStackTrace();
			return json;	 

		
	}
	
}
}
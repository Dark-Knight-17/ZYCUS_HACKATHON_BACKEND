package com.backend.zycus.strategy;

 import java.util.List;

 
public record ReassignmentResult(
	    List<AgentRecommendation> recommendations,
	    String reasoning
	) {}
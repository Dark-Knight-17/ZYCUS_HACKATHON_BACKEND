package com.backend.zycus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.backend.zycus.dto.GeminiRequest;
import com.backend.zycus.dto.GeminiResponse;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.entity.Order;
import com.backend.zycus.utility.GlobalConstants;


@Service
public class GeminiReassignmentService {

    @Value("${llm.api.key}")
    private String apiKey;

    private final RestClient restClient;

    // Best Practice: Constructor Injection
//    public GeminiReassignmentService(RestClient.Builder restClientBuilder) {
//        this.restClient = restClientBuilder.build();
//    }
//     the above constructor code threw this error Description:

//Parameter 0 of constructor in com.backend.backend_server.service.GeminiService required a bean of type 'org.springframework.web.client.RestClient$Builder' that could not be found.
//Action:
//
//Consider defining a bean of type 'org.springframework.web.client.RestClient$Builder' in your configuration.
    
    
 // FIXED: Changed from constructor injection to a default constructor.
    // This builds the RestClient instantly and bypasses the missing bean issue entirely.
    public GeminiReassignmentService() {
        this.restClient = RestClient.builder().build();
    }
    

    public boolean testGeminiAvailability() {
    	String prompt="Is java a static or dynamic language ,asnwer in one word";
        // Constructing the direct Gemini Flash endpoint
    	String endpointUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + apiKey;GeminiRequest payload = GeminiRequest.of(prompt);

        try {
            GeminiResponse response = restClient.post()
                    .uri(endpointUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(GeminiResponse.class);
            if(response.getText().contains(GlobalConstants.geminiError)) {
            	return false;	
            }
            
            else {
            	// add more logic to test model unavialbilty  else
            	return true;
            	
            }

            
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }
    
    

    public String askGemini(List<Agent> agentList ,List<Order> orderList) {
    	String prompt="I have a list of orders and agents that delivers those orders recommend the agents and give response in a JSON format with "
    			+ "objects having fields of agents and agent object having a feild orderid so that order can be assigned to that agent  "
    			+ "agentList = "+agentList.toString()
    			+ " orderList = "+orderList.toString();

        // Constructing the direct Gemini Flash endpoint
    	String endpointUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + apiKey;
    	GeminiRequest payload = GeminiRequest.of(prompt);

        try {
            GeminiResponse response = restClient.post()
                    .uri(endpointUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(GeminiResponse.class);
            if(response.getText().contains(GlobalConstants.geminiError)) {
            	return response.getText();	
            }
            
            else {
            	// add more logic to test model unavialbilty  else
            	return "No Response";
            	
            }

            
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
}

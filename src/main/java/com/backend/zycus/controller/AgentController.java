package com.backend.zycus.controller;

 import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.zycus.dto.AgentDto;
import com.backend.zycus.entity.Agent;
import com.backend.zycus.model.AgentStatus;
import com.backend.zycus.service.AgentService;

@RestController
@RequestMapping("/public/v1/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping
    public ResponseEntity<?> getAgents(
            @RequestParam(name = "status", required = false) String status) {

        try {

            List<Agent> agents = (status == null)
                    ? agentService.findAll()
                    : agentService.findByStatus(status);

            return ResponseEntity.ok(agents);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to fetch agents.");
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable("id") String agentId,
            @RequestParam(name = "newStatus") AgentStatus newStatus) {

        try {

            AgentDto agent = agentService.updateStatus(agentId, newStatus);

            return ResponseEntity.ok(agent);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());

        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to update agent.");
        }
    }
}
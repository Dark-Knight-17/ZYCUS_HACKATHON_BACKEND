package com.backend.zycus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.zycus.dto.SuggestionResponseDto;
import com.backend.zycus.service.ReassignmentSuggestionService;
@RestController
@RequestMapping("/public/v1/suggestions")
public class SuggestionController {

    private final ReassignmentSuggestionService suggestionService;

    public SuggestionController(ReassignmentSuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSuggestionStatus(
            @PathVariable("id") Long id,
            @RequestBody String status) {

        try {

            suggestionService.updateStatus(id, status);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Suggestion updated successfully");
            response.put("suggestionId", id);
            response.put("status", status);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid status format");

            return ResponseEntity.badRequest().body(response);

        } catch (RuntimeException e) {

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Update failed");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }
}

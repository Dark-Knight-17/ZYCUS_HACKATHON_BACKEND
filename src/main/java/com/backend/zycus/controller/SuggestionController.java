package com.backend.zycus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.zycus.service.ReassignmentSuggestionService;
@RestController
@RequestMapping("/public/v1/suggestions")
public class SuggestionController {

    private final ReassignmentSuggestionService suggestionService;

    public SuggestionController(ReassignmentSuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateSuggestionStatus(
            @PathVariable String id, 
            @RequestBody String status) {
        
        try {
            Long suggestionId = Long.valueOf(id);
            suggestionService.updateStatus(suggestionId, status);
            return ResponseEntity.noContent().build();
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
        }
    }
}

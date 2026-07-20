package com.backend.zycus.controller;

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

            suggestionService.updateStatus(id, status.trim());

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Suggestion updated successfully.",
                            "suggestionId", id,
                            "status", status.trim()
                    )
            );

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "message", "Invalid status format."
                    )
            );

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    )
            );

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of(
                            "success", false,
                            "message", "Unable to update suggestion."
                    )
            );

        }

    }

    @GetMapping
    public ResponseEntity<?> getSuggestionStatus() {

        try {

            List<SuggestionResponseDto> suggestions =
                    suggestionService.getPendingSuggestions();

            return ResponseEntity.ok(suggestions);

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "success", false,
                                    "message", "Unable to fetch suggestions."
                            )
                    );

        }

    }

}

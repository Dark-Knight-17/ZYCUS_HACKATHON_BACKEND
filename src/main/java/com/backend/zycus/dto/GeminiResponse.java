package com.backend.zycus.dto;

import java.util.List;
import com.backend.zycus.utility.GlobalConstants;

public record GeminiResponse(List<Candidate> candidates) {
    
    public record Candidate(Content content) {}
    public record Content(List<Part> parts) {}
    public record Part(String text) {}

    // Safe helper method to extract the nested response text
    public String getText() {
        if (candidates != null && !candidates.isEmpty()) {
            var content = candidates.get(0).content();
            if (content != null && content.parts() != null && !content.parts().isEmpty()) {
                return content.parts().get(0).text();
            }
        }
        return GlobalConstants.geminiError;
    }
}

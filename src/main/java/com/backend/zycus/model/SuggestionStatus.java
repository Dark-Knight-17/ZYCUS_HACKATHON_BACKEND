package com.backend.zycus.model;

public enum SuggestionStatus {
    PENDING,
    ACCEPTED,
    REJECTED;

    public boolean canTransitionTo(SuggestionStatus newStatus) {
        if (this == newStatus) return false;
        
        // PENDING is the only mutable state
        return this == PENDING && (newStatus == ACCEPTED || newStatus == REJECTED);
    }
}
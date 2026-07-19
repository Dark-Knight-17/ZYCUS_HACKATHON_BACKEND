package com.backend.zycus.model;

 
public enum AgentStatus {
    AVAILABLE,
    BUSY,
    OFFLINE;

    // Any status can switch to OFFLINE. 
    // OFFLINE can only go to AVAILABLE.
    public boolean canTransitionTo(AgentStatus newStatus) {
        if (this == newStatus) return false;
        if (newStatus == OFFLINE) return true;
        
        return switch (this) {
            case AVAILABLE -> newStatus == BUSY;
            case BUSY -> newStatus == AVAILABLE;
            case OFFLINE -> newStatus == AVAILABLE;
        };
    }
}
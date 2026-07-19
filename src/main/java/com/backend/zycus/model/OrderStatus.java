package com.backend.zycus.model;

 
public enum OrderStatus {
    ASSIGNED,
    REASSIGNMENT_PENDING,
    REASSIGNED,
    DELIVERED;

    public boolean canTransitionTo(OrderStatus newStatus) {
        if (this == newStatus) return false;
        
        return switch (this) {
            case ASSIGNED -> newStatus == REASSIGNMENT_PENDING || newStatus == DELIVERED;
            case REASSIGNMENT_PENDING -> newStatus == REASSIGNED;
            case REASSIGNED -> newStatus == DELIVERED;
            case DELIVERED -> false; // Terminal state
        };
    }
}
package com.backend.zycus.model;

 
 
public enum TriggerReason {
    INITIAL,
    AGENT_OFFLINE;
    
    // No transition logic needed here, as this is simply a label 
    // for why a suggestion was generated, not a lifecycle state.
}
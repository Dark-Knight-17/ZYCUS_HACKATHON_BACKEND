package com.backend.zycus.event;

import org.springframework.context.ApplicationEvent;

public class AgentOfflineEvent extends ApplicationEvent {
    private final Long agentId;

    public AgentOfflineEvent(Object source, Long agentId) {
        super(source);
        this.agentId = agentId;
    }

    public Long getAgentId() {
        return agentId;
    }
}

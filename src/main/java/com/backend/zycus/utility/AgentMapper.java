package com.backend.zycus.utility;

import org.springframework.stereotype.Component;

import com.backend.zycus.dto.AgentResponseDto;
import com.backend.zycus.entity.Agent;

@Component
public final class AgentMapper {

    private AgentMapper() {
    }

    public static AgentResponseDto toResponseDto(Agent agent) {

        if (agent == null) {
            return null;
        }

        AgentResponseDto dto = new AgentResponseDto();

        dto.setId(agent.getId());
        dto.setName(agent.getName());
        dto.setStatus(agent.getStatus());
        dto.setActiveOrderCount(agent.getActiveOrderCount());

        return dto;
    }

    public static Agent toEntity(Agent agent, String name, String status) {

        if (agent == null) {
            agent = new Agent();
        }

        agent.setName(name);
        agent.setStatus(status);

        return agent;
    }
}

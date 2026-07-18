package com.backend.zycus.dto;


public class AgentResponseDto {

    private Long id;

    private String name;

    private String status;

    private Integer activeOrderCount;

    public AgentResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }

    public Integer getActiveOrderCount() {
        return activeOrderCount;
    }

    public void setActiveOrderCount(Integer activeOrderCount) {
        this.activeOrderCount=activeOrderCount;
    }

}
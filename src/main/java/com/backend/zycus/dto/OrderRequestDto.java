package com.backend.zycus.dto;


public class OrderRequestDto {

   private String customerName;

   private String deliveryAddress;

   private Long assignedAgentId;

   public OrderRequestDto() {
   }

   public String getCustomerName() {
       return customerName;
   }

   public void setCustomerName(String customerName) {
       this.customerName=customerName;
   }

   public String getDeliveryAddress() {
       return deliveryAddress;
   }

   public void setDeliveryAddress(String deliveryAddress) {
       this.deliveryAddress=deliveryAddress;
   }

   public Long getAssignedAgentId() {
       return assignedAgentId;
   }

   public void setAssignedAgentId(Long assignedAgentId) {
       this.assignedAgentId=assignedAgentId;
   }

}
package com.backend.zycus.utility;

import org.springframework.stereotype.Component;

import com.backend.zycus.dto.OrderResponseDto;
import com.backend.zycus.entity.Order;
@Component

public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponseDto toResponseDto(Order order) {

        if (order == null) {
            return null;
        }

        OrderResponseDto dto = new OrderResponseDto();

        dto.setId(order.getId());
        dto.setDescription(order.getDescription());
        dto.setStatus(order.getStatus());

        if (order.getAssignedAgent() != null) {

            dto.setAssignedAgentId(
                    order.getAssignedAgent().getId());

            dto.setAssignedAgentName(
                    order.getAssignedAgent().getName());
        }

        return dto;
    }

    public static Order toEntity(Order order,
                                 String description,
                                 String deliveryAddress,
                                 String status) {

        if (order == null) {
            order = new Order();
        }

         order.setDescription(description);
        order.setStatus(status);

        return order;
    }

}

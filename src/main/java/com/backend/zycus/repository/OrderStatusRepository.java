package com.backend.zycus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.zycus.model.OrderStatus;
public interface OrderStatusRepository
extends JpaRepository<OrderStatus, Long> {

Optional<OrderStatus> findByCode(String code);

}
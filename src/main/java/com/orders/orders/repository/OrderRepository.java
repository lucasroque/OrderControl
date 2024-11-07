package com.orders.orders.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orders.orders.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClientAndDate(String client, LocalDate date);

}

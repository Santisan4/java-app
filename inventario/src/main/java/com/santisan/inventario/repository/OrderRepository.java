package com.santisan.inventario.repository;

import com.santisan.inventario.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

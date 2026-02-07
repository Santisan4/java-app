package com.santisan.inventario.service;

import com.santisan.inventario.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO createOrder(OrderDTO newOrder);
    OrderDTO updateOrder(Long id, OrderDTO orderToUpdate);
    void deleteOrder(Long id);
    OrderDTO getOrderById(Long id);
}

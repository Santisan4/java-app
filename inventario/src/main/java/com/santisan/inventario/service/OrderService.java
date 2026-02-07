package com.santisan.inventario.service;

import com.santisan.inventario.dto.OrderDTO;
import com.santisan.inventario.dto.OrderItemDTO;
import com.santisan.inventario.exception.NotFoundException;
import com.santisan.inventario.mapper.Mapper;
import com.santisan.inventario.model.Order;
import com.santisan.inventario.model.OrderItem;
import com.santisan.inventario.model.Product;
import com.santisan.inventario.repository.OrderRepository;
import com.santisan.inventario.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public OrderDTO createOrder(OrderDTO newOrderDTO) {
        Order newOrder = new Order();
        newOrder.setDate(newOrderDTO.getDate());
        newOrder.setStatus(newOrderDTO.getStatus());

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemDTO item: newOrderDTO.getOrderItems()) {
            Product p = productRepository.findById(item.getProductId()).orElse(null);
            if(p == null) throw new RuntimeException("Product not found with id: " + item.getProductId());

            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setProduct(p);
            newOrderItem.setPrice(item.getProductPrice());
            newOrderItem.setQuantity(item.getProductQuantity());
            newOrderItem.setOrder(newOrder);
            newOrder.setTotal(item.getProductPrice() * item.getProductQuantity());

            orderItems.add(newOrderItem);
        }

        newOrder.setOrderItems(orderItems);

        return Mapper.toDTO(orderRepository.save(newOrder));
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDto) {
        Order orderToUpdate = orderRepository.findById(id).orElse(null);
        if(orderToUpdate == null) throw new NotFoundException("Order not found with id: " + id);

        if(orderDto.getDate() != null) orderToUpdate.setDate(orderDto.getDate());
        if(orderDto.getStatus() != null) orderToUpdate.setStatus(orderDto.getStatus());

        orderRepository.save(orderToUpdate);

        return Mapper.toDTO(orderToUpdate);
    }

    @Override
    public void deleteOrder(Long id) {
        Order orderToDelete = orderRepository.findById(id).orElse(null);
        if(orderToDelete == null) throw new NotFoundException("Order not found with id: " + id);
        orderRepository.delete(orderToDelete);
    }

    @Override
    public OrderDTO getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        return Mapper.toDTO(order);
    }
}

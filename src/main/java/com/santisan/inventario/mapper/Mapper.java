package com.santisan.inventario.mapper;

import com.santisan.inventario.dto.CategoryDTO;
import com.santisan.inventario.dto.OrderDTO;
import com.santisan.inventario.dto.OrderItemDTO;
import com.santisan.inventario.dto.ProductDTO;
import com.santisan.inventario.model.Category;
import com.santisan.inventario.model.Order;
import com.santisan.inventario.model.Product;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static CategoryDTO toDTO(Category c) {
        if (c == null) return null;

        return CategoryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .build();
    }


    public static ProductDTO toDTO(Product p){
        if(p == null) return null;

        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .stock(p.getStock())
                .price(p.getPrice())
                .categories(p.getCategory() == null
                        ? Set.of()
                        : p.getCategory()
                        .stream()
                        .map(Mapper::toDTO)
                        .collect(Collectors.toSet())
                )
                .build();
    }

    public static OrderDTO toDTO(Order order){
        if(order == null) return null;

        var orderItems = order.getOrderItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .productPrice(item.getProduct().getPrice())
                        .productQuantity(item.getProduct().getStock())
                        .subtotal(item.getPrice() * item.getQuantity())
                        .build()
        ).collect(Collectors.toList());

        var total = orderItems.stream().map(OrderItemDTO::getSubtotal).reduce(0.0, Double::sum);

        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .status(order.getStatus())
                .orderItems(orderItems)
                .total(total)
                .build();
    }
}

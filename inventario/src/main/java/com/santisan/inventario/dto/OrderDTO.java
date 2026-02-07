package com.santisan.inventario.dto;

import com.santisan.inventario.model.OrderItem;
import com.santisan.inventario.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;

    @NotBlank
    private LocalDate date;

    @NotBlank
    private OrderStatus status;

    @NotBlank
    private List<OrderItemDTO> orderItems;

    private Double total;
}

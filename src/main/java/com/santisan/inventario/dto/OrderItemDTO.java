package com.santisan.inventario.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Integer productQuantity;
    private Double productPrice;
    private Double subtotal;
}

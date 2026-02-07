package com.santisan.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 2,max = 100)
    private String description;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer stock;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private Set<CategoryDTO> categories;
}

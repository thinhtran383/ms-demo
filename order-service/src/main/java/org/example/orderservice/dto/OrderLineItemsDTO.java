package org.example.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDTO {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}

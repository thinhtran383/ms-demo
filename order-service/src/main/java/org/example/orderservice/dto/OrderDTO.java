package org.example.orderservice.dto;

import lombok.*;
import org.example.orderservice.entities.OrderLineItems;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private List<OrderLineItemsDTO> orderLineItemsDtoList;
}

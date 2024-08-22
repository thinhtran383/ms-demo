package org.example.orderservice.response;

import lombok.*;
import org.example.orderservice.dto.OrderLineItemsDTO;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderNumber;
    private List<OrderLineItemsDTO> orderLineItemsList;
}

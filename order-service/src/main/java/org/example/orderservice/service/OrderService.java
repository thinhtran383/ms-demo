package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.OrderDTO;
import org.example.orderservice.dto.OrderLineItemsDTO;
import org.example.orderservice.entities.Order;
import org.example.orderservice.entities.OrderLineItems;
import org.example.orderservice.repositories.OrderRepository;
import org.example.orderservice.response.InventoryResponse;
import org.example.orderservice.response.OrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderDTO.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> listSkus = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] result = webClient.get()
                .uri("http://localhost:8082/inventory-service/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", listSkus).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        log.info("Checking inventory for skuCode: {}", listSkus);
        log.info("Inventory response: {}", Arrays.toString(result));

        boolean allProdInStock = Arrays.stream(result)
                .allMatch(InventoryResponse::getIsInStock);

        if (allProdInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Inventory is not available");
        }

        return order;

    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();

        return orderList.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDto) {
        return modelMapper.map(orderLineItemsDto, OrderLineItems.class);
    }

}

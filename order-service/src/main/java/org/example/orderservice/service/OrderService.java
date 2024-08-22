package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderDTO;
import org.example.orderservice.dto.OrderLineItemsDTO;
import org.example.orderservice.entities.Order;
import org.example.orderservice.entities.OrderLineItems;
import org.example.orderservice.repositories.OrderRepository;
import org.example.orderservice.response.OrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;


    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderDTO.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        return orderRepository.save(order);

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

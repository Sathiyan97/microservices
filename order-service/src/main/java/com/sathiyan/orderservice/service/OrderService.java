package com.sathiyan.orderservice.service;

import com.sathiyan.orderservice.dto.OrderLineItemsDto;
import com.sathiyan.orderservice.dto.OrderRequest;
import com.sathiyan.orderservice.model.Order;
import com.sathiyan.orderservice.model.OrderLineItems;
import com.sathiyan.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        //call inventory service, and place the order if product  is in stock
        Boolean result = webClient.get()
                .uri("\"http://localhost:8083/api/inventory\"")
                .retrieve()
                .bodyToMono(boolean.class)
                .block();

        if(result) {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product not in stock , please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;

    }
}

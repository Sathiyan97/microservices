package com.sathiyan.orderservice.controller;

import com.sathiyan.orderservice.dto.OrderRequest;
import com.sathiyan.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Place Successfully";
    }

    public String fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return "Oops! Something went wrong, Please order after some time!";
    }
}

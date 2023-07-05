package com.sathiyan.orderservice.controller;

import com.sathiyan.orderservice.dto.OrderRequest;
import com.sathiyan.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));

    }

    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, Please order after some time!");
    }
}

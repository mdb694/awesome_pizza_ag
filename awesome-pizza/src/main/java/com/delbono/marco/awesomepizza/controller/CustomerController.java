package com.delbono.marco.awesomepizza.controller;

import com.delbono.marco.awesomepizza.dto.PizzaOrderDto;
import com.delbono.marco.awesomepizza.dto.PizzaOrderStatusDto;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;
import com.delbono.marco.awesomepizza.service.PizzaOrderService;
import com.delbono.marco.awesomepizza.service.impl.PizzaOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Log4j2
public class CustomerController {
    private final PizzaOrderService pizzaOrderService;

    @PostMapping("/order")
    public ResponseEntity<PizzaOrderStatusDto> placePizzaOrder(@RequestBody PizzaOrderDto orderDto) {
        log.info("Placed new order: {}", orderDto);
        return ResponseEntity.ok(pizzaOrderService.placeNewPizzaOrder(orderDto));
    }

    @GetMapping("/monitoring/{orderCode}")
    public ResponseEntity<PizzaOrderStatusDto> getPizzaOrderStatus(@PathVariable String orderCode) {
        log.info("Requested monitoring for order: {}", orderCode);
        Optional<PizzaOrderStatusDto> pizzaOrderOptional = pizzaOrderService.monitoring(orderCode);
        return pizzaOrderOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}

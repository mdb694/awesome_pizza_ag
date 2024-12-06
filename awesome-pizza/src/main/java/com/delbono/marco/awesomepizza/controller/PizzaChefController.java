package com.delbono.marco.awesomepizza.controller;

import com.delbono.marco.awesomepizza.dto.PizzaToPrepareDto;
import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;
import com.delbono.marco.awesomepizza.service.PizzaOrderService;
import com.delbono.marco.awesomepizza.service.QueueService;
import com.delbono.marco.awesomepizza.service.impl.QueueServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pizza-chef")
@RequiredArgsConstructor
@Log4j2
public class PizzaChefController {
    private final PizzaOrderService pizzaOrderService;

    @GetMapping("/pizza")
    public ResponseEntity<PizzaToPrepareDto> getPizza() {
        log.info("Requested a new Pizza to prepare");
        Optional<PizzaToPrepareDto> pizzaToPrepareDto = pizzaOrderService.getNextPizzaToPrepare();
        return pizzaToPrepareDto.map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pizza available"));
    }
}

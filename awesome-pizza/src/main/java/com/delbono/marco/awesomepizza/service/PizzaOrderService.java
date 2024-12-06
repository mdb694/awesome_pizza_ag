package com.delbono.marco.awesomepizza.service;

import com.delbono.marco.awesomepizza.dto.PizzaOrderDto;
import com.delbono.marco.awesomepizza.dto.PizzaOrderStatusDto;
import com.delbono.marco.awesomepizza.dto.PizzaToPrepareDto;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;

import java.util.Optional;

public interface PizzaOrderService {
    PizzaOrderStatusDto placeNewPizzaOrder(PizzaOrderDto pizzaOrderDto);

    Optional<PizzaOrderStatusDto> monitoring(String orderCode);

    Optional<PizzaToPrepareDto> getNextPizzaToPrepare();
}

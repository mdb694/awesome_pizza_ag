package com.delbono.marco.awesomepizza.mapper;

import com.delbono.marco.awesomepizza.dto.PizzaOrderStatusDto;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PizzaStatusMapper {
    PizzaOrderStatusDto orderToStatus(PizzaOrder pizzaOrder);
}

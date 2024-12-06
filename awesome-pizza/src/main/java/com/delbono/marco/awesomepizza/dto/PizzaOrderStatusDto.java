package com.delbono.marco.awesomepizza.dto;

import com.delbono.marco.awesomepizza.enums.OrderStatus;
import com.delbono.marco.awesomepizza.enums.OrderedPizzaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PizzaOrderStatusDto {
    private String code;
    private OrderStatus status;
}

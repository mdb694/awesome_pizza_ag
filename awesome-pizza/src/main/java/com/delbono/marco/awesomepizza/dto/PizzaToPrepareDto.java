package com.delbono.marco.awesomepizza.dto;

import com.delbono.marco.awesomepizza.enums.PizzaSize;
import lombok.Data;

@Data
public class PizzaToPrepareDto {
    private String customerName;
    private String customerSurname;
    private String orderCode;
    private PizzaSize pizzaSize;
    private String pizzaName;
    private String ingredients;
}

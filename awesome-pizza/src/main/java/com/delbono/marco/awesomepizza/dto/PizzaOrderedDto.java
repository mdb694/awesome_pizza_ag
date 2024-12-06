package com.delbono.marco.awesomepizza.dto;

import com.delbono.marco.awesomepizza.enums.PizzaSize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PizzaOrderedDto {
    private Long id;
    private PizzaSize size;
}

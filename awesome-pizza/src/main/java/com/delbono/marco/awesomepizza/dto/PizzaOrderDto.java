package com.delbono.marco.awesomepizza.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PizzaOrderDto {
    String name;
    String surname;
    String address;
    String city;
    String zipCode;
    String county;

    List<PizzaOrderedDto> orderedPizzaIdSizeList;
}

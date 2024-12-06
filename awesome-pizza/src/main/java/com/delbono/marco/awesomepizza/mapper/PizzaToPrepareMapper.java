package com.delbono.marco.awesomepizza.mapper;

import com.delbono.marco.awesomepizza.dto.PizzaToPrepareDto;
import com.delbono.marco.awesomepizza.entity.Ingredient;
import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PizzaToPrepareMapper {
    @Named("ingredientSetToSeparatedString")
    static String ingredientSetToSeparatedString(Set<Ingredient> ingredientSet) {
        return ingredientSet.stream().map(Ingredient::getName).collect(Collectors.joining(", "));
    }

    @Mapping(target = "ingredients", source = "pizza.ingredientSet", qualifiedByName = "ingredientSetToSeparatedString")
    @Mapping(target = "pizzaSize", source = "size")
    @Mapping(target = "pizzaName", source = "pizza.name")
    @Mapping(target = "orderCode", source = "order.code")
    @Mapping(target = "customerSurname", source = "order.orderOwner.surname")
    @Mapping(target = "customerName", source = "order.orderOwner.name")
    PizzaToPrepareDto orderedPizzaToPizzaToPrepare(OrderedPizza orderedPizza);
}

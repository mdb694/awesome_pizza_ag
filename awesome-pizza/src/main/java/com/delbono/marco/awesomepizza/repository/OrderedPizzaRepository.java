package com.delbono.marco.awesomepizza.repository;

import com.delbono.marco.awesomepizza.entity.Ingredient;
import com.delbono.marco.awesomepizza.entity.OrderedPizza;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderedPizzaRepository extends ListCrudRepository<OrderedPizza, Long>, JpaSpecificationExecutor<OrderedPizza> {
}

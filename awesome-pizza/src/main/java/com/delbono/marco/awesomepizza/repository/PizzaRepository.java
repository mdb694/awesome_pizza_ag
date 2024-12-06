package com.delbono.marco.awesomepizza.repository;

import com.delbono.marco.awesomepizza.entity.Ingredient;
import com.delbono.marco.awesomepizza.entity.Pizza;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface PizzaRepository extends ListCrudRepository<Pizza, Long>, JpaSpecificationExecutor<Pizza> {
    List<Pizza> findByIdIn(Collection<Long> ids);
}

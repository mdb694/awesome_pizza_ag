package com.delbono.marco.awesomepizza.repository;

import com.delbono.marco.awesomepizza.entity.Ingredient;
import com.delbono.marco.awesomepizza.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

public interface PizzaOrderRepository extends ListCrudRepository<PizzaOrder, Long>, JpaSpecificationExecutor<PizzaOrder> {
    Optional<PizzaOrder> findByCode(String code);
}

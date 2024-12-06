package com.delbono.marco.awesomepizza.repository;

import com.delbono.marco.awesomepizza.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IngredientRepository extends ListCrudRepository<Ingredient, Long>, JpaSpecificationExecutor<Ingredient>  {
}

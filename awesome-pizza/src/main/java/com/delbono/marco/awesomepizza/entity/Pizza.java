package com.delbono.marco.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Pizza extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "pizza_id")
    private Long id;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @ManyToMany
    private Set<Ingredient> ingredientSet;
}

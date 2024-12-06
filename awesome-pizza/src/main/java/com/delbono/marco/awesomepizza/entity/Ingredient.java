package com.delbono.marco.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private String description;
}

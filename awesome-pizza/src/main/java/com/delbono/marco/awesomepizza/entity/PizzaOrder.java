package com.delbono.marco.awesomepizza.entity;

import com.delbono.marco.awesomepizza.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzaOrder extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @Column
    private String code;
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.ACCEPTED;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private OrderOwner orderOwner;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderedPizza> pizzaSet;
}

package com.delbono.marco.awesomepizza.entity;

import com.delbono.marco.awesomepizza.enums.OrderedPizzaStatus;
import com.delbono.marco.awesomepizza.enums.PizzaSize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedPizza extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "ordered_pizza_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PizzaSize size = PizzaSize.NORMAL;
    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderedPizzaStatus status = OrderedPizzaStatus.TO_PREPARE;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private PizzaOrder order;
}

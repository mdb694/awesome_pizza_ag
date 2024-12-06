package com.delbono.marco.awesomepizza.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderOwner extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_owner_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column
    private String county;
}

package com.bullish.shopping.entity.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CART")
@SequenceGenerator(name="seq", initialValue=100, allocationSize=100)

public class CartEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private BigInteger id;

    @Column(name = "SESSION_ID")
    private BigInteger sessionId;

    @Column(name = "PRODUCT_ID")
    private BigInteger productId;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Set<ProductEntity> products;

    //For simplicity we are considering the quantity as 1, this can be expanded by adding a quantity column here.

    //This can also be expanded by adding timestamps.
}


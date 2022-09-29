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
@Table(name = "SHOPPING_SESSION")
@SequenceGenerator(name="seq", initialValue=100, allocationSize=100)
public class ShoppingSessionEntity {

    @Id
    @Column(name ="SESSION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private BigInteger sessionId;

    @Column(name = "CUSTOMER_ID")
    private BigInteger customerId;

    @Column(name = "ACTIVE_FLAG")
    private Character activeFlag;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID")
    private Set<CartEntity> cartItems;

    //More Details like timestamps can be added
}

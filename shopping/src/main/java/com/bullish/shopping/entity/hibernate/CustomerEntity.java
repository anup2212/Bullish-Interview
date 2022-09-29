package com.bullish.shopping.entity.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER")
@SequenceGenerator(name="seq", initialValue=100, allocationSize=100)

public class CustomerEntity {

    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private BigInteger customerId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

}

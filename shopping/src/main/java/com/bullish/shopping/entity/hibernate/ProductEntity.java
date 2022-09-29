package com.bullish.shopping.entity.hibernate;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = "PRODUCTS")
@SequenceGenerator(name="seq", initialValue=100, allocationSize=100)
public class ProductEntity {

    @Column(name = "PRODUCT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private BigInteger productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "INSERT_TS")
    private Timestamp insertTs;

    @Column(name = "UPDATE_TS")
    private Timestamp updateTs;

    @Column(name = "INSERTED_BY")
    private String insertedBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @OneToMany (fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Set<DiscountEntity> discounts;

}

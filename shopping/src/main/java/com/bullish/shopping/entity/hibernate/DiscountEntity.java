package com.bullish.shopping.entity.hibernate;

import com.bullish.shopping.entity.DiscountValue;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "DISCOUNTS")
@SequenceGenerator(name="seq", initialValue=100, allocationSize=100)

public class DiscountEntity {

    @Column(name = "DISCOUNT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private BigInteger discountId;

    @Column(name = "PRODUCT_ID")
    private BigInteger productId;

    @Column(name = "DISCOUNT_NAME")
    @Enumerated(EnumType.STRING)
    private DiscountValue discountName;

    @Column(name = "INSERT_TS")
    private Timestamp insertTs;

    @Column(name = "INSERTED_BY")
    private String insertedBy;

    @Column(name = "ACTIVE_FLAG")
    private Character activeFlag; // Y or N

}

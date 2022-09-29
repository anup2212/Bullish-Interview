package com.bullish.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    private BigInteger discountId;
    private DiscountValue discountName;
    private Timestamp insertTs;
    private String insertedBy;
    private Character activeFlag;
}

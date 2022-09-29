package com.bullish.shopping.entity;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Product {

    private BigInteger productId;
    private String productName;
    private double price;
    private Timestamp insertTs;
    private Timestamp updateTs;
    private String insertedBy;
    private String updatedBy;
    private List<Discount> discounts;
}

package com.bullish.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt extends BaseResponse {

    List<Product> products;
    double priceAfterDiscount;

}

package com.bullish.shopping.util;

import com.bullish.shopping.entity.Discount;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.hibernate.ProductEntity;

import java.util.stream.Collectors;

public class Mapper {

   public static Product map(ProductEntity src, Product dest) {

        if (src == null) {
            return dest;
        }

        dest.setProductId(src.getProductId());
        dest.setProductName(src.getProductName());
        dest.setPrice(src.getPrice());
        dest.setInsertTs(src.getInsertTs());
        dest.setInsertedBy(src.getInsertedBy());
        dest.setUpdatedBy(src.getUpdatedBy());
        dest.setUpdateTs(src.getUpdateTs());

        if (src.getDiscounts() != null && !src.getDiscounts().isEmpty()) {

            dest.setDiscounts(src.getDiscounts()
                    .stream()
                    .map(d -> new Discount(
                            d.getDiscountId(),
                            d.getDiscountName(),
                            d.getInsertTs(),
                            d.getInsertedBy(),
                            d.getActiveFlag()
                    ))
                    .collect(Collectors.toList())
            );


        }

        return dest;
    }
}

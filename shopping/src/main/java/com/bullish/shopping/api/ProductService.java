package com.bullish.shopping.api;


import com.bullish.shopping.entity.BaseResponse;
import com.bullish.shopping.entity.DiscountValue;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.ProductResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public interface ProductService {

    List<Product> fetchProducts();

    ProductResponse addProducts(List<Product> productRequest);

    BaseResponse addDiscount(BigInteger productId, DiscountValue discount);

    BaseResponse removeProduct(BigInteger productId);


}

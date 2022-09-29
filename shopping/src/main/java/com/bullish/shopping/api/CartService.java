package com.bullish.shopping.api;


import com.bullish.shopping.entity.BaseResponse;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.ProductResponse;
import com.bullish.shopping.entity.Receipt;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public interface CartService {

    BaseResponse actionProduct(BigInteger productId, BigInteger customerId, String action);

    Receipt checkout(BigInteger customer);



}

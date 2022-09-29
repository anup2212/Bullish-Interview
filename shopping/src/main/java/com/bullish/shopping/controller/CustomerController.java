package com.bullish.shopping.controller;

import com.bullish.shopping.api.CartService;
import com.bullish.shopping.api.ProductService;
import com.bullish.shopping.entity.BaseResponse;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.ProductResponse;
import com.bullish.shopping.entity.Receipt;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@Api (value = "Shopping cart rest controller")
public class CustomerController {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @RequestMapping(value = "/customer/cart/add", method = RequestMethod.POST)
    public BaseResponse addProduct(@RequestParam (required = true) BigInteger productId,
                                      @RequestParam (required = true) BigInteger customerId) {

        return cartService.actionProduct(productId, customerId, "ADD");
    }

    @RequestMapping(value = "/customer/cart/delete", method = RequestMethod.POST)
    public BaseResponse removeProduct(@RequestParam (required = true) BigInteger product,
                                      @RequestParam (required = true) BigInteger customer) {

        return cartService.actionProduct(product, customer, "DELETE");
    }

    @RequestMapping(value = "/customer/cart/checkout", method = RequestMethod.POST)
    public Receipt removeProduct(@RequestParam (required = true) BigInteger customer) {

        return cartService.checkout(customer);
    }
}

package com.bullish.shopping.controller;

import com.bullish.shopping.api.ProductService;
import com.bullish.shopping.entity.BaseResponse;
import com.bullish.shopping.entity.DiscountValue;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.ProductResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@Api (value = "Shopping cart rest controller")
public class AdminController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/admin/product/fetch", method = RequestMethod.GET)
    public List<Product> fetchProducts() {

        return productService.fetchProducts();
    }

    @RequestMapping(value = "/admin/product/add", method = RequestMethod.POST)
    public ProductResponse addProduct(@RequestBody List<Product> products) {

        return productService.addProducts(products);
    }


    @RequestMapping(value = "/admin/discount/add", method = RequestMethod.POST)
    public BaseResponse addProduct(@RequestParam (required = true) BigInteger productId,
                                   @RequestParam (required = true) DiscountValue discount) {

        return productService.addDiscount(productId, discount);
    }

    @RequestMapping(value = "/admin/product/delete", method = RequestMethod.POST)
    public BaseResponse removeProduct(@RequestParam (required = true) BigInteger product) {

        return productService.removeProduct(product);
    }
}

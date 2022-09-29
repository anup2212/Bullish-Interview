package com.bullish.shopping.impl;

import com.bullish.shopping.api.ProductService;
import com.bullish.shopping.entity.*;
import com.bullish.shopping.entity.hibernate.*;
import com.bullish.shopping.repository.DiscountRepository;
import com.bullish.shopping.repository.ProductRepository;
import com.bullish.shopping.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public List<Product> fetchProducts() {

        List<ProductEntity> productEntities = productRepository.findAll();

        if (productEntities == null || productEntities.isEmpty()) {
            return null;
        }

        return productEntities.stream()
                .map(p -> Mapper.map(p, new Product()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    @Override
    public BaseResponse addDiscount(BigInteger productId, DiscountValue discount) {

        BaseResponse response = new BaseResponse();

        ProductEntity productEntity = productRepository.findById(productId).orElse(null);

        if (productEntity == null) {
            response.setResponseCode("200");
            response.setResponseMessage("Product not found");
            return response;
        }

        DiscountEntity discountEntity = new DiscountEntity();

        discountEntity.setDiscountName(DiscountValue.FIFTY_PERCENT_OFF);
        discountEntity.setProductId(productId);
        discountEntity.setInsertedBy("user");
        discountEntity.setInsertTs(new Timestamp(System.currentTimeMillis()));
        discountEntity.setActiveFlag('Y');

        discountRepository.save(discountEntity);

        response.setResponseCode("200");

        return response;

    }

    @Override
    public ProductResponse addProducts(List<Product> productRequest) {

        ProductResponse response = new ProductResponse();

        if (productRequest == null || productRequest.isEmpty()) {

            response.setResponseCode("200");
            response.setResponseMessage("No Products to save");

            return response;

        }

        List<ProductEntity> productEntities = productRequest.stream()
                .map(p -> map(p, new ProductEntity())).collect(Collectors.toList());

        productEntities =  productRepository.saveAll(productEntities);

        response.setResponseMessage("Products saved successfully");
        response.setResponseCode("200");
        response.setProducts(productEntities.stream().map(p -> Mapper.map(p, new Product())).collect(Collectors.toList()));

        return response;


    }

    ProductEntity map(Product src, ProductEntity dest) {
        if (src == null) {
            return dest;
        }

        dest.setProductName(src.getProductName());
        dest.setPrice(src.getPrice());
        dest.setInsertedBy("user");
        dest.setInsertTs(new Timestamp(System.currentTimeMillis()));
        dest.setUpdatedBy("user");
        dest.setUpdateTs(new Timestamp(System.currentTimeMillis()));

        return dest;
    }

    @Override
    public BaseResponse removeProduct(BigInteger productId) {

        BaseResponse response = new BaseResponse();

        ProductEntity productEntity = productRepository.findById(productId).orElse(null);

        if (productEntity == null) {
            response.setResponseCode("200");
            response.setResponseMessage("Product not found");
            return response;
        }

        productRepository.delete(productEntity);
        response.setResponseCode("200");
        response.setResponseMessage("Product deleted successfully");

        return response;

    }

}

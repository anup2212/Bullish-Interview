package com.bullish.shopping.impl;

import com.bullish.shopping.api.CartService;
import com.bullish.shopping.entity.BaseResponse;
import com.bullish.shopping.entity.Discount;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.Receipt;
import com.bullish.shopping.entity.hibernate.CartEntity;
import com.bullish.shopping.entity.hibernate.ProductEntity;
import com.bullish.shopping.entity.hibernate.ShoppingSessionEntity;
import com.bullish.shopping.repository.CartRepository;
import com.bullish.shopping.repository.CustomerRepository;
import com.bullish.shopping.repository.ProductRepository;
import com.bullish.shopping.repository.ShoppingSessionRepository;
import com.bullish.shopping.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShoppingSessionRepository sessionRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public BaseResponse actionProduct(BigInteger productId,
                                      BigInteger customerId,
                                      String action) {

        BaseResponse response = new BaseResponse();

        if (!isProductValid(productId)) {
            response.setResponseMessage("200");
            response.setResponseMessage("Product is invalid, please reach out to admin to add the product");

            return response;
        }

        if (!isCustomerValid(customerId)) {
            response.setResponseMessage("200");
            response.setResponseMessage("Customer is invalid, please create a new customer");

            return response;
        }

        switch (action) {

            case "ADD" :

                CartEntity cartEntity = new CartEntity();

                cartEntity.setSessionId(getSessionId(customerId));
                cartEntity.setProductId(productId);

                cartRepository.save(cartEntity);

                response.setResponseCode("200");
                response.setResponseMessage("Product successfully added to cart");

                return response;

            case "DELETE" :

               ShoppingSessionEntity sessionEntity = sessionRepository.findByCustomerIdAndActiveFlag(customerId, 'Y');

               if (sessionEntity == null || sessionEntity.getSessionId() == null) {
                   response.setResponseCode("200");
                   response.setResponseMessage("No active Cart for this customer");

                   return response;
               }

                /**
                 * TODO :  To keep the implementation simple we are deleting all the products, we can enhance this by excepting value from customer
                 */
                List<CartEntity> cartEntities = cartRepository.findBySessionIdAndProductId(sessionEntity.getSessionId(), productId);

               if (cartEntities != null && !cartEntities.isEmpty()) {

                   cartRepository.deleteAll(cartEntities);

                   response.setResponseCode("200");
                   response.setResponseMessage("Products successfully removed");

                   return response;

               }

            default:
                return null;

        }
    }

    @Override
    public Receipt checkout(BigInteger customer) {

        Receipt response = new Receipt();

        ShoppingSessionEntity sessionEntity = sessionRepository.findByCustomerIdAndActiveFlag(customer, 'Y');

        if (sessionEntity == null || sessionEntity.getSessionId() == null) {
            response.setResponseCode("200");
            response.setResponseMessage("No active Cart for this customer");

            return response;
        }

        List<CartEntity> cartEntities = cartRepository.findBySessionId(sessionEntity.getSessionId());

        if (cartEntities == null || cartEntities.isEmpty()) {

            response.setResponseCode("200");
            response.setResponseMessage("Cart is Empty");

            return response;

        }

        List<ProductEntity> productEntities = cartEntities.stream()
                .map(c -> productRepository.findById(c.getProductId()).orElse(null))
                .collect(Collectors.toList());

        double totalPriceAfterDiscount = 0;

        Map<BigInteger, Integer> productCountMap = new HashMap();
        List<Product> products = new ArrayList<>();

        if (productEntities != null && !productEntities.isEmpty()) {

            products.addAll(productEntities.stream().map(p -> Mapper.map(p, new Product())).collect(Collectors.toList()));

            for (Product p : products) {

                if (p.getDiscounts() == null || p.getDiscounts().isEmpty()) {

                    //No Discounts available for this product
                    totalPriceAfterDiscount = totalPriceAfterDiscount + p.getPrice();

                } else {

                    //For simplicity assuming each product has one discount
                    for (Discount d : p.getDiscounts()) {

                        switch (d.getDiscountName()) {

                            case TWENTY_PERCENT_OFF:
                                totalPriceAfterDiscount = totalPriceAfterDiscount + (p.getPrice() * 0.8);
                                break;

                            case FIFTY_PERCENT_OFF:
                                totalPriceAfterDiscount = totalPriceAfterDiscount + (p.getPrice() * 0.5);
                                break;

                            case BUY_ONE_GET_OTHER_50_PERCENT:

                                if (!productCountMap.containsKey(p.getProductId())) {
                                    totalPriceAfterDiscount = totalPriceAfterDiscount + p.getPrice();

                                } else {
                                    if (productCountMap.get(p.getProductId()) % 2 != 0) {
                                        totalPriceAfterDiscount = totalPriceAfterDiscount + (p.getPrice() * 0.5);
                                    } else {
                                        totalPriceAfterDiscount = totalPriceAfterDiscount + p.getPrice();
                                    }
                                }
                                break;

                            default:
                                break;
                        }

                    }

                    if (productCountMap.containsKey(p.getProductId())) {
                        productCountMap.replace(p.getProductId(), productCountMap.get(p.getProductId()) + 1);
                    } else {
                        productCountMap.put(p.getProductId(), 1);
                    }
                }
            }

        }

        response.setProducts(products);
        response.setPriceAfterDiscount(totalPriceAfterDiscount);
        response.setResponseCode("200");

        return response;

    }

    private Boolean isProductValid(BigInteger productId) {

        return productRepository.findById(productId).isPresent();
    }

    private Boolean isCustomerValid(BigInteger customerId) {

        return customerRepository.findById(customerId).isPresent();
    }

    //THis method will return a session ID , if sesionID is not present then it will create one session
    private BigInteger getSessionId (BigInteger customerId) {

        //Check if a session already exists

        ShoppingSessionEntity shoppingSession = sessionRepository.findByCustomerIdAndActiveFlag(
                customerId, 'Y'
        );

        if (shoppingSession != null && shoppingSession.getSessionId() != null) {
            return shoppingSession.getSessionId();
        } else {

            ShoppingSessionEntity sessionEntity = new ShoppingSessionEntity();

            sessionEntity.setCustomerId(customerId);
            sessionEntity.setActiveFlag('Y');

           return sessionRepository.save(sessionEntity).getSessionId();
        }
    }
}

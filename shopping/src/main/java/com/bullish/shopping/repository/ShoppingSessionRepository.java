package com.bullish.shopping.repository;

import com.bullish.shopping.entity.hibernate.ProductEntity;
import com.bullish.shopping.entity.hibernate.ShoppingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ShoppingSessionRepository extends JpaRepository<ShoppingSessionEntity, BigInteger> {

    ShoppingSessionEntity findByCustomerIdAndActiveFlag(BigInteger customerId, Character activeFlag);

}

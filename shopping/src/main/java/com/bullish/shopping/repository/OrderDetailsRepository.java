package com.bullish.shopping.repository;

import com.bullish.shopping.entity.hibernate.OrderDetailsEntity;
import com.bullish.shopping.entity.hibernate.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, BigInteger> {

}

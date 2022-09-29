package com.bullish.shopping.repository;

import com.bullish.shopping.entity.hibernate.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, BigInteger> {

    List<CartEntity> findBySessionIdAndProductId (BigInteger sessionId, BigInteger productId);

    List<CartEntity> findBySessionId (BigInteger sessionId);

}

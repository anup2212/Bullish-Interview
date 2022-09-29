package com.bullish.shopping.repository;

import com.bullish.shopping.entity.hibernate.CustomerEntity;
import com.bullish.shopping.entity.hibernate.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, BigInteger> {

}

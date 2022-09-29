package com.bullish.shopping.entity.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_DETAILS")
public class OrderDetailsEntity {

    @Id
    @Column(name ="ORDER_ID")
    private BigInteger ORDER_ID; // Same as session Id

    @Column(name = "CUSTOMER_ID")
    private BigInteger customerId;

    @Column(name = "TOTAL")
    private BigInteger total;

    /**
     * TODO : Add this if more details on payments is required.

    @Column(name = "PAYMENT_ID")
    private BigInteger paymentId;

     */

    //More Details like timestamps can be added
}

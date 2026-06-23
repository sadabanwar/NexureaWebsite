package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {

    private Long id;

    private Long userId;

    private Long packageId;

    private String orderId;

    private String paymentId;

    private BigDecimal amount;

    private String paymentStatus;

    private String referralCode;

    private LocalDateTime purchaseDate;
}

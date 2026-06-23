package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionDto {

    private Long id;

    private Long affiliateId;

    private Long purchaseId;

    private BigDecimal amount;

    private Double commissionRate;

    private String status;

    private LocalDateTime createdAt;
}

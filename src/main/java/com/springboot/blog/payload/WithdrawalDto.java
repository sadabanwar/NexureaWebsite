package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalDto {

    private Long id;

    private Long userId;

    private BigDecimal amount;

    private String status;

    private String accountNumber;

    private String ifscCode;

    private String accountHolderName;

    private String upiId;

    private String transactionId;

    private String remarks;

    private LocalDateTime requestDate;

    private LocalDateTime processedDate;
}

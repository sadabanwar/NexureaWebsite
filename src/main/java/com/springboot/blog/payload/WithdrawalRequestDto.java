package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequestDto {

    @NotNull(message = "Amount should not be null")
    @Positive(message = "Amount should be positive")
    private BigDecimal amount;

    private String accountNumber;

    private String ifscCode;

    private String accountHolderName;

    private String upiId;
}

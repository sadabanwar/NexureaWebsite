package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    @NotNull(message = "Package ID should not be null")
    private Long packageId;

    private String referralCode;
}

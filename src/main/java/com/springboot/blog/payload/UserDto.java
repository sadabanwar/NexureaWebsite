package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String username;

    private String email;

    private String phone;

    private String referralCode;

    private BigDecimal totalEarnings;

    private BigDecimal availableBalance;

    private Integer totalReferrals;

    private LocalDateTime createdAt;
}

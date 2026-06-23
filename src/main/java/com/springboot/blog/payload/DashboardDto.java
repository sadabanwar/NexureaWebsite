package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {

    private BigDecimal totalSales;

    private BigDecimal totalCommission;

    private Integer referralCount;

    private BigDecimal availableBalance;

    private BigDecimal withdrawnAmount;

    private List<CommissionDto> recentCommissions;
}

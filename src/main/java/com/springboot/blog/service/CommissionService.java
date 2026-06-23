package com.springboot.blog.service;

import com.springboot.blog.entity.Purchase;
import com.springboot.blog.payload.CommissionDto;

import java.util.List;

public interface CommissionService {

    CommissionDto processCommission(Purchase purchase);

    List<CommissionDto> getAffiliateCommissions(Long affiliateId);

    Double getTotalEarnings(Long affiliateId);

    Double calculateCommission(Double amount, Double rate);
}

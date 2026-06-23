package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Commission;
import com.springboot.blog.entity.Purchase;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommissionDto;
import com.springboot.blog.repository.CommissionRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.CommissionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommissionServiceImpl implements CommissionService {

    private static final Logger logger = LoggerFactory.getLogger(CommissionServiceImpl.class);

    private final CommissionRepository commissionRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public CommissionServiceImpl(CommissionRepository commissionRepository,
                                  UserRepository userRepository,
                                  ModelMapper mapper) {
        this.commissionRepository = commissionRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CommissionDto processCommission(Purchase purchase) {
        logger.info("Processing commission for purchase ID: {}", purchase.getId());

        // Check if commission already processed
        if (purchase.getCommissionProcessed()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Commission already processed for this purchase");
        }

        // Check if referral code exists
        if (purchase.getReferralCode() == null || purchase.getReferralCode().isEmpty()) {
            logger.info("No referral code found for purchase ID: {}", purchase.getId());
            return null;
        }

        // Find affiliate user by referral code
        User affiliate = userRepository.findByReferralCode(purchase.getReferralCode())
                .orElseThrow(() -> new ResourceNotFoundException("User", "referralCode", 0));

        // Don't give commission if user referred themselves
        if (affiliate.getId().equals(purchase.getUser().getId())) {
            logger.warn("User tried to use their own referral code. Purchase ID: {}", purchase.getId());
            return null;
        }

        // Calculate commission
        Double commissionRate = purchase.getCoursePackage().getCommissionRate();
        Double commissionAmount = calculateCommission(purchase.getAmount(), commissionRate);

        // Create commission record
        Commission commission = new Commission();
        commission.setAffiliate(affiliate);
        commission.setPurchase(purchase);
        commission.setAmount(commissionAmount);
        commission.setCommissionRate(commissionRate);
        commission.setStatus("APPROVED");
        commission.setCreatedAt(LocalDateTime.now());

        Commission savedCommission = commissionRepository.save(commission);

        // Update affiliate earnings
        affiliate.setTotalEarnings(affiliate.getTotalEarnings() + commissionAmount);
        affiliate.setAvailableBalance(affiliate.getAvailableBalance() + commissionAmount);
        userRepository.save(affiliate);

        // Mark commission as processed
        purchase.setCommissionProcessed(true);

        logger.info("Commission processed successfully. Amount: {} for affiliate ID: {}",
                    commissionAmount, affiliate.getId());

        return mapToDto(savedCommission);
    }

    @Override
    public List<CommissionDto> getAffiliateCommissions(Long affiliateId) {
        logger.info("Fetching commissions for affiliate ID: {}", affiliateId);

        User affiliate = userRepository.findById(affiliateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", affiliateId));

        List<Commission> commissions = commissionRepository.findByAffiliateId(affiliateId);
        return commissions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalEarnings(Long affiliateId) {
        logger.info("Calculating total earnings for affiliate ID: {}", affiliateId);

        User affiliate = userRepository.findById(affiliateId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", affiliateId));

        Double totalEarnings = commissionRepository.getTotalEarningsByAffiliate(affiliateId);
        return totalEarnings != null ? totalEarnings : 0.0;
    }

    @Override
    public Double calculateCommission(Double amount, Double rate) {
        if (amount == null || rate == null) {
            return 0.0;
        }
        return (amount * rate) / 100.0;
    }

    private CommissionDto mapToDto(Commission commission) {
        CommissionDto dto = new CommissionDto();
        dto.setId(commission.getId());
        dto.setAffiliateId(commission.getAffiliate().getId());
        dto.setPurchaseId(commission.getPurchase().getId());
        dto.setAmount(BigDecimal.valueOf(commission.getAmount()));
        dto.setCommissionRate(commission.getCommissionRate());
        dto.setStatus(commission.getStatus());
        dto.setCreatedAt(commission.getCreatedAt());
        return dto;
    }
}

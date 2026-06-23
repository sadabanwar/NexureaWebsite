package com.springboot.blog.service.impl;

import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommissionRepository;
import com.springboot.blog.repository.PurchaseRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.ReferralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReferralServiceImpl implements ReferralService {

    private static final Logger logger = LoggerFactory.getLogger(ReferralServiceImpl.class);

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final CommissionRepository commissionRepository;

    public ReferralServiceImpl(UserRepository userRepository,
                                PurchaseRepository purchaseRepository,
                                CommissionRepository commissionRepository) {
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.commissionRepository = commissionRepository;
    }

    @Override
    public String generateReferralCode(String username) {
        logger.info("Generating referral code for username: {}", username);

        String baseCode = username.toUpperCase().replaceAll("[^A-Z0-9]", "");
        if (baseCode.length() > 8) {
            baseCode = baseCode.substring(0, 8);
        }

        String referralCode = baseCode;
        int counter = 1;

        while (userRepository.existsByReferralCode(referralCode)) {
            referralCode = baseCode + counter;
            counter++;
        }

        logger.info("Generated referral code: {}", referralCode);
        return referralCode;
    }

    @Override
    public boolean validateReferralCode(String code) {
        logger.info("Validating referral code: {}", code);

        if (code == null || code.trim().isEmpty()) {
            return false;
        }

        boolean exists = userRepository.existsByReferralCode(code);
        logger.info("Referral code validation result: {}", exists);

        return exists;
    }

    @Override
    public Map<String, Object> getReferralStats(Long userId) {
        logger.info("Fetching referral stats for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Map<String, Object> stats = new HashMap<>();

        // Referral code
        stats.put("referralCode", user.getReferralCode());

        // Total referrals (successful purchases using this code)
        Integer totalReferrals = purchaseRepository.countSuccessfulReferrals(user.getReferralCode());
        stats.put("totalReferrals", totalReferrals != null ? totalReferrals : 0);

        // Total sales from referrals
        Double totalSales = purchaseRepository.getTotalSalesByReferralCode(user.getReferralCode());
        stats.put("totalSales", totalSales != null ? totalSales : 0.0);

        // Total commission count
        Integer commissionCount = commissionRepository.getTotalCommissionCount(userId);
        stats.put("totalCommissions", commissionCount != null ? commissionCount : 0);

        // Total earnings
        Double totalEarnings = commissionRepository.getTotalEarningsByAffiliate(userId);
        stats.put("totalEarnings", totalEarnings != null ? totalEarnings : 0.0);

        // Available balance
        stats.put("availableBalance", user.getAvailableBalance());

        // Withdrawn amount
        stats.put("withdrawnAmount", user.getWithdrawnAmount());

        // Referred by
        stats.put("referredBy", user.getReferredBy());

        logger.info("Referral stats fetched successfully for user ID: {}", userId);

        return stats;
    }
}

package com.springboot.blog.service.impl;

import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommissionDto;
import com.springboot.blog.payload.DashboardDto;
import com.springboot.blog.repository.CommissionRepository;
import com.springboot.blog.repository.CoursePackageRepository;
import com.springboot.blog.repository.PurchaseRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.repository.WithdrawalRepository;
import com.springboot.blog.service.CommissionService;
import com.springboot.blog.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final CommissionRepository commissionRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final CoursePackageRepository coursePackageRepository;
    private final CommissionService commissionService;

    public DashboardServiceImpl(UserRepository userRepository,
                                 PurchaseRepository purchaseRepository,
                                 CommissionRepository commissionRepository,
                                 WithdrawalRepository withdrawalRepository,
                                 CoursePackageRepository coursePackageRepository,
                                 CommissionService commissionService) {
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.commissionRepository = commissionRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.coursePackageRepository = coursePackageRepository;
        this.commissionService = commissionService;
    }

    @Override
    public DashboardDto getAffiliateDashboard(Long userId) {
        logger.info("Fetching affiliate dashboard for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        DashboardDto dashboard = new DashboardDto();

        // Get total sales from referrals
        Double totalSales = purchaseRepository.getTotalSalesByReferralCode(user.getReferralCode());
        dashboard.setTotalSales(BigDecimal.valueOf(totalSales != null ? totalSales : 0.0));

        // Get total commission
        Double totalCommission = commissionRepository.getTotalEarningsByAffiliate(userId);
        dashboard.setTotalCommission(BigDecimal.valueOf(totalCommission != null ? totalCommission : 0.0));

        // Get referral count
        Integer referralCount = purchaseRepository.countSuccessfulReferrals(user.getReferralCode());
        dashboard.setReferralCount(referralCount != null ? referralCount : 0);

        // Get available balance
        dashboard.setAvailableBalance(BigDecimal.valueOf(user.getAvailableBalance()));

        // Get withdrawn amount
        dashboard.setWithdrawnAmount(BigDecimal.valueOf(user.getWithdrawnAmount()));

        // Get recent commissions
        List<CommissionDto> recentCommissions = commissionService.getAffiliateCommissions(userId);
        if (recentCommissions.size() > 10) {
            recentCommissions = recentCommissions.subList(0, 10);
        }
        dashboard.setRecentCommissions(recentCommissions);

        logger.info("Affiliate dashboard fetched successfully for user ID: {}", userId);

        return dashboard;
    }

    @Override
    public Map<String, Object> getAdminDashboard() {
        logger.info("Fetching admin dashboard");

        Map<String, Object> dashboard = new HashMap<>();

        // Total users
        long totalUsers = userRepository.count();
        dashboard.put("totalUsers", totalUsers);

        // Total active packages
        long totalPackages = coursePackageRepository.count();
        long activePackages = coursePackageRepository.findByActiveTrue().size();
        dashboard.put("totalPackages", totalPackages);
        dashboard.put("activePackages", activePackages);

        // Total purchases
        long totalPurchases = purchaseRepository.count();
        dashboard.put("totalPurchases", totalPurchases);

        // Total successful purchases
        long successfulPurchases = purchaseRepository.findAll().stream()
                .filter(p -> "SUCCESS".equals(p.getPaymentStatus()))
                .count();
        dashboard.put("successfulPurchases", successfulPurchases);

        // Total revenue (sum of all successful purchases)
        Double totalRevenue = purchaseRepository.findAll().stream()
                .filter(p -> "SUCCESS".equals(p.getPaymentStatus()))
                .mapToDouble(p -> p.getAmount())
                .sum();
        dashboard.put("totalRevenue", totalRevenue);

        // Total commissions paid
        Double totalCommissionsPaid = commissionRepository.getTotalEarningsByAffiliate(null);
        dashboard.put("totalCommissionsPaid", totalCommissionsPaid != null ? totalCommissionsPaid : 0.0);

        // Pending withdrawals
        long pendingWithdrawals = withdrawalRepository.findByStatus("PENDING").size();
        dashboard.put("pendingWithdrawals", pendingWithdrawals);

        // Total withdrawals
        long totalWithdrawals = withdrawalRepository.count();
        dashboard.put("totalWithdrawals", totalWithdrawals);

        // Total amount withdrawn
        Double totalWithdrawn = withdrawalRepository.findAll().stream()
                .filter(w -> "APPROVED".equals(w.getStatus()))
                .mapToDouble(w -> w.getAmount())
                .sum();
        dashboard.put("totalWithdrawn", totalWithdrawn);

        logger.info("Admin dashboard fetched successfully");

        return dashboard;
    }
}

package com.springboot.blog.repository;

import com.springboot.blog.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByOrderId(String orderId);
    List<Purchase> findByUserId(Long userId);
    List<Purchase> findByUserIdAndPaymentStatus(Long userId, String paymentStatus);
    List<Purchase> findByReferralCode(String referralCode);

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.referralCode = :referralCode AND p.paymentStatus = 'SUCCESS'")
    Integer countSuccessfulReferrals(String referralCode);

    @Query("SELECT SUM(p.amount) FROM Purchase p WHERE p.referralCode = :referralCode AND p.paymentStatus = 'SUCCESS'")
    Double getTotalSalesByReferralCode(String referralCode);

    boolean existsByUserIdAndCoursePackageIdAndPaymentStatus(Long userId, Long coursePackageId, String paymentStatus);
}

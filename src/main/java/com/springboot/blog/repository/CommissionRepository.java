package com.springboot.blog.repository;

import com.springboot.blog.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long> {
    List<Commission> findByAffiliateId(Long affiliateId);
    List<Commission> findByAffiliateIdAndStatus(Long affiliateId, String status);

    @Query("SELECT SUM(c.amount) FROM Commission c WHERE c.affiliate.id = :affiliateId AND c.status = 'APPROVED'")
    Double getTotalEarningsByAffiliate(Long affiliateId);

    @Query("SELECT COUNT(c) FROM Commission c WHERE c.affiliate.id = :affiliateId")
    Integer getTotalCommissionCount(Long affiliateId);
}

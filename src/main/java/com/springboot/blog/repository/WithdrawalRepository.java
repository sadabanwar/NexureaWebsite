package com.springboot.blog.repository;

import com.springboot.blog.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUserId(Long userId);
    List<Withdrawal> findByUserIdOrderByRequestDateDesc(Long userId);
    List<Withdrawal> findByStatus(String status);
    List<Withdrawal> findByStatusOrderByRequestDateDesc(String status);

    @Query("SELECT SUM(w.amount) FROM Withdrawal w WHERE w.user.id = :userId AND w.status = 'APPROVED'")
    Double getTotalWithdrawnByUser(Long userId);
}

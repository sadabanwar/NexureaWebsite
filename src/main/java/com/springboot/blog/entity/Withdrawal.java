package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String ifscCode;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private String upiId;

    private String transactionId;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime requestDate = LocalDateTime.now();

    private LocalDateTime processedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private User processedBy;
}

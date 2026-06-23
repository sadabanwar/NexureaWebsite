package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"referralCode"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String referralCode;

    private String referredBy;

    @Column(columnDefinition = "decimal(10,2) default 0.00")
    private Double totalEarnings = 0.0;

    @Column(columnDefinition = "decimal(10,2) default 0.00")
    private Double availableBalance = 0.0;

    @Column(columnDefinition = "decimal(10,2) default 0.00")
    private Double withdrawnAmount = 0.0;

    @Column(columnDefinition = "int default 0")
    private Integer totalReferrals = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Purchase> purchases = new HashSet<>();

    @OneToMany(mappedBy = "affiliate", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Commission> commissions = new HashSet<>();
}

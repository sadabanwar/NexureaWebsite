package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_packages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double commissionRate;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(columnDefinition = "TEXT")
    private String features;

    private String thumbnailUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "coursePackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseVideo> videos = new HashSet<>();

    @OneToMany(mappedBy = "coursePackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Purchase> purchases = new HashSet<>();
}

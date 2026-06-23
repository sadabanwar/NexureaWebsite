package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_videos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String videoUrl;

    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private CoursePackage coursePackage;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

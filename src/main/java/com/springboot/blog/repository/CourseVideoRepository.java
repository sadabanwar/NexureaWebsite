package com.springboot.blog.repository;

import com.springboot.blog.entity.CourseVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseVideoRepository extends JpaRepository<CourseVideo, Long> {
    List<CourseVideo> findByCoursePackageIdAndActiveTrueOrderByOrderIndexAsc(Long packageId);
    List<CourseVideo> findByCoursePackageIdOrderByOrderIndexAsc(Long packageId);
}

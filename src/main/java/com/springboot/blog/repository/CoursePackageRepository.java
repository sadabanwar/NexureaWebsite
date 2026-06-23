package com.springboot.blog.repository;

import com.springboot.blog.entity.CoursePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePackageRepository extends JpaRepository<CoursePackage, Long> {
    List<CoursePackage> findByActiveTrue();
    List<CoursePackage> findByActiveTrueOrderByPriceAsc();
}

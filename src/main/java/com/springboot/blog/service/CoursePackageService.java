package com.springboot.blog.service;

import com.springboot.blog.payload.CoursePackageDto;

import java.util.List;

public interface CoursePackageService {

    CoursePackageDto createPackage(CoursePackageDto dto);

    CoursePackageDto updatePackage(Long id, CoursePackageDto dto);

    CoursePackageDto getPackageById(Long id);

    List<CoursePackageDto> getAllActivePackages();

    List<CoursePackageDto> getAllPackages();

    void deletePackage(Long id);

    CoursePackageDto togglePackageStatus(Long id);
}

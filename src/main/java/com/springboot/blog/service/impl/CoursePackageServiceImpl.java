package com.springboot.blog.service.impl;

import com.springboot.blog.entity.CoursePackage;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CoursePackageDto;
import com.springboot.blog.repository.CoursePackageRepository;
import com.springboot.blog.service.CoursePackageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursePackageServiceImpl implements CoursePackageService {

    private static final Logger logger = LoggerFactory.getLogger(CoursePackageServiceImpl.class);

    private final CoursePackageRepository coursePackageRepository;
    private final ModelMapper mapper;

    public CoursePackageServiceImpl(CoursePackageRepository coursePackageRepository, ModelMapper mapper) {
        this.coursePackageRepository = coursePackageRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CoursePackageDto createPackage(CoursePackageDto dto) {
        logger.info("Creating new course package: {}", dto.getName());

        CoursePackage coursePackage = new CoursePackage();
        coursePackage.setName(dto.getName());
        coursePackage.setDescription(dto.getDescription());
        coursePackage.setPrice(dto.getPrice().doubleValue());
        coursePackage.setCommissionRate(dto.getCommissionRate());
        coursePackage.setActive(dto.getActive() != null ? dto.getActive() : true);
        coursePackage.setThumbnailUrl(dto.getThumbnailUrl());

        if (dto.getFeatures() != null && !dto.getFeatures().isEmpty()) {
            coursePackage.setFeatures(String.join("||", dto.getFeatures()));
        }

        coursePackage.setCreatedAt(LocalDateTime.now());

        CoursePackage savedPackage = coursePackageRepository.save(coursePackage);
        logger.info("Course package created successfully with ID: {}", savedPackage.getId());

        return mapToDto(savedPackage);
    }

    @Override
    @Transactional
    public CoursePackageDto updatePackage(Long id, CoursePackageDto dto) {
        logger.info("Updating course package with ID: {}", id);

        CoursePackage coursePackage = coursePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", id));

        coursePackage.setName(dto.getName());
        coursePackage.setDescription(dto.getDescription());
        coursePackage.setPrice(dto.getPrice().doubleValue());
        coursePackage.setCommissionRate(dto.getCommissionRate());
        coursePackage.setThumbnailUrl(dto.getThumbnailUrl());

        if (dto.getFeatures() != null && !dto.getFeatures().isEmpty()) {
            coursePackage.setFeatures(String.join("||", dto.getFeatures()));
        }

        if (dto.getActive() != null) {
            coursePackage.setActive(dto.getActive());
        }

        CoursePackage updatedPackage = coursePackageRepository.save(coursePackage);
        logger.info("Course package updated successfully with ID: {}", updatedPackage.getId());

        return mapToDto(updatedPackage);
    }

    @Override
    public CoursePackageDto getPackageById(Long id) {
        logger.info("Fetching course package with ID: {}", id);

        CoursePackage coursePackage = coursePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", id));

        return mapToDto(coursePackage);
    }

    @Override
    public List<CoursePackageDto> getAllActivePackages() {
        logger.info("Fetching all active course packages");

        List<CoursePackage> packages = coursePackageRepository.findByActiveTrue();
        return packages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoursePackageDto> getAllPackages() {
        logger.info("Fetching all course packages");

        List<CoursePackage> packages = coursePackageRepository.findAll();
        return packages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePackage(Long id) {
        logger.info("Deleting course package with ID: {}", id);

        CoursePackage coursePackage = coursePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", id));

        coursePackageRepository.delete(coursePackage);
        logger.info("Course package deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional
    public CoursePackageDto togglePackageStatus(Long id) {
        logger.info("Toggling status for course package with ID: {}", id);

        CoursePackage coursePackage = coursePackageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", id));

        coursePackage.setActive(!coursePackage.getActive());
        CoursePackage updatedPackage = coursePackageRepository.save(coursePackage);

        logger.info("Course package status toggled to {} for ID: {}", updatedPackage.getActive(), id);

        return mapToDto(updatedPackage);
    }

    private CoursePackageDto mapToDto(CoursePackage coursePackage) {
        CoursePackageDto dto = new CoursePackageDto();
        dto.setId(coursePackage.getId());
        dto.setName(coursePackage.getName());
        dto.setDescription(coursePackage.getDescription());
        dto.setPrice(BigDecimal.valueOf(coursePackage.getPrice()));
        dto.setCommissionRate(coursePackage.getCommissionRate());
        dto.setActive(coursePackage.getActive());
        dto.setThumbnailUrl(coursePackage.getThumbnailUrl());
        dto.setCreatedAt(coursePackage.getCreatedAt());

        if (coursePackage.getFeatures() != null && !coursePackage.getFeatures().isEmpty()) {
            dto.setFeatures(Arrays.asList(coursePackage.getFeatures().split("\\|\\|")));
        }

        return dto;
    }
}

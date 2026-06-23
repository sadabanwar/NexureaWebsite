package com.springboot.blog.service.impl;

import com.springboot.blog.entity.CoursePackage;
import com.springboot.blog.entity.CourseVideo;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CourseVideoDto;
import com.springboot.blog.repository.CoursePackageRepository;
import com.springboot.blog.repository.CourseVideoRepository;
import com.springboot.blog.service.CourseVideoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseVideoServiceImpl implements CourseVideoService {

    private static final Logger logger = LoggerFactory.getLogger(CourseVideoServiceImpl.class);

    private final CourseVideoRepository courseVideoRepository;
    private final CoursePackageRepository coursePackageRepository;
    private final ModelMapper mapper;

    public CourseVideoServiceImpl(CourseVideoRepository courseVideoRepository,
                                   CoursePackageRepository coursePackageRepository,
                                   ModelMapper mapper) {
        this.courseVideoRepository = courseVideoRepository;
        this.coursePackageRepository = coursePackageRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CourseVideoDto createVideo(CourseVideoDto dto) {
        logger.info("Creating new course video: {}", dto.getTitle());

        CoursePackage coursePackage = coursePackageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", dto.getPackageId()));

        CourseVideo video = new CourseVideo();
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setVideoUrl(dto.getVideoUrl());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setOrderIndex(dto.getOrderIndex());
        video.setDurationMinutes(dto.getDurationMinutes());
        video.setCoursePackage(coursePackage);
        video.setActive(true);
        video.setCreatedAt(LocalDateTime.now());

        CourseVideo savedVideo = courseVideoRepository.save(video);
        logger.info("Course video created successfully with ID: {}", savedVideo.getId());

        return mapToDto(savedVideo);
    }

    @Override
    @Transactional
    public CourseVideoDto updateVideo(Long id, CourseVideoDto dto) {
        logger.info("Updating course video with ID: {}", id);

        CourseVideo video = courseVideoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseVideo", "id", id));

        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setVideoUrl(dto.getVideoUrl());
        video.setThumbnailUrl(dto.getThumbnailUrl());
        video.setOrderIndex(dto.getOrderIndex());
        video.setDurationMinutes(dto.getDurationMinutes());

        if (dto.getPackageId() != null && !dto.getPackageId().equals(video.getCoursePackage().getId())) {
            CoursePackage coursePackage = coursePackageRepository.findById(dto.getPackageId())
                    .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", dto.getPackageId()));
            video.setCoursePackage(coursePackage);
        }

        CourseVideo updatedVideo = courseVideoRepository.save(video);
        logger.info("Course video updated successfully with ID: {}", updatedVideo.getId());

        return mapToDto(updatedVideo);
    }

    @Override
    public CourseVideoDto getVideoById(Long id) {
        logger.info("Fetching course video with ID: {}", id);

        CourseVideo video = courseVideoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseVideo", "id", id));

        return mapToDto(video);
    }

    @Override
    public List<CourseVideoDto> getVideosByPackageId(Long packageId) {
        logger.info("Fetching all videos for package ID: {}", packageId);

        CoursePackage coursePackage = coursePackageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", packageId));

        List<CourseVideo> videos = courseVideoRepository.findByCoursePackageIdOrderByOrderIndexAsc(packageId);
        return videos.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        logger.info("Deleting course video with ID: {}", id);

        CourseVideo video = courseVideoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseVideo", "id", id));

        courseVideoRepository.delete(video);
        logger.info("Course video deleted successfully with ID: {}", id);
    }

    private CourseVideoDto mapToDto(CourseVideo video) {
        CourseVideoDto dto = new CourseVideoDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setVideoUrl(video.getVideoUrl());
        dto.setThumbnailUrl(video.getThumbnailUrl());
        dto.setOrderIndex(video.getOrderIndex());
        dto.setDurationMinutes(video.getDurationMinutes());
        dto.setPackageId(video.getCoursePackage().getId());
        return dto;
    }
}

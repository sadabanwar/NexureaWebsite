package com.springboot.blog.service;

import com.springboot.blog.payload.CourseVideoDto;

import java.util.List;

public interface CourseVideoService {

    CourseVideoDto createVideo(CourseVideoDto dto);

    CourseVideoDto updateVideo(Long id, CourseVideoDto dto);

    CourseVideoDto getVideoById(Long id);

    List<CourseVideoDto> getVideosByPackageId(Long packageId);

    void deleteVideo(Long id);
}

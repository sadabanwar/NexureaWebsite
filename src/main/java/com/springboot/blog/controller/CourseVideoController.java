package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.CourseVideoDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.CourseVideoService;
import com.springboot.blog.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Course Video REST API")
@RestController
@RequestMapping("/api/videos")
public class CourseVideoController {

    @Autowired
    private CourseVideoService courseVideoService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Create Course Video - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createVideo(@Valid @RequestBody CourseVideoDto videoDto) {
        try {
            CourseVideoDto createdVideo = courseVideoService.createVideo(videoDto);
            return new ResponseEntity<>(
                    new ApiResponse(true, "Video created successfully", createdVideo),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to create video: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Update Course Video - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateVideo(
            @PathVariable Long id,
            @Valid @RequestBody CourseVideoDto videoDto) {
        try {
            CourseVideoDto updatedVideo = courseVideoService.updateVideo(id, videoDto);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Video updated successfully", updatedVideo)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to update video: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Video by ID - USER (checks purchase)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVideoById(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            CourseVideoDto videoDto = courseVideoService.getVideoById(id);

            // Check if user has purchased the package
            boolean hasPurchased = purchaseService.hasUserPurchasedPackage(user.getId(), videoDto.getPackageId());

            if (!hasPurchased) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "You need to purchase this package to access the video"),
                        HttpStatus.FORBIDDEN
                );
            }

            return ResponseEntity.ok(
                    new ApiResponse(true, "Video retrieved successfully", videoDto)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve video: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Videos by Package ID - USER (checks purchase)")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/package/{packageId}")
    public ResponseEntity<ApiResponse> getVideosByPackage(@PathVariable Long packageId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if user has purchased the package
            boolean hasPurchased = purchaseService.hasUserPurchasedPackage(user.getId(), packageId);

            if (!hasPurchased) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "You need to purchase this package to access the videos"),
                        HttpStatus.FORBIDDEN
                );
            }

            List<CourseVideoDto> videos = courseVideoService.getVideosByPackageId(packageId);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Videos retrieved successfully", videos)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve videos: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Delete Course Video - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteVideo(@PathVariable Long id) {
        try {
            courseVideoService.deleteVideo(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Video deleted successfully")
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to delete video: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

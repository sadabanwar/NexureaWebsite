package com.springboot.blog.controller;

import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.CoursePackageDto;
import com.springboot.blog.service.CoursePackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Course Package REST API")
@RestController
@RequestMapping("/api/packages")
public class CoursePackageController {

    @Autowired
    private CoursePackageService coursePackageService;

    @ApiOperation(value = "Create Course Package - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> createPackage(@Valid @RequestBody CoursePackageDto packageDto) {
        try {
            CoursePackageDto createdPackage = coursePackageService.createPackage(packageDto);
            return new ResponseEntity<>(
                    new ApiResponse(true, "Package created successfully", createdPackage),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to create package: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Update Course Package - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody CoursePackageDto packageDto) {
        try {
            CoursePackageDto updatedPackage = coursePackageService.updatePackage(id, packageDto);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Package updated successfully", updatedPackage)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to update package: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Course Package by ID - PUBLIC")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPackageById(@PathVariable Long id) {
        try {
            CoursePackageDto packageDto = coursePackageService.getPackageById(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Package retrieved successfully", packageDto)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Package not found: " + e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @ApiOperation(value = "Get All Active Packages - PUBLIC")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllActivePackages() {
        try {
            List<CoursePackageDto> packages = coursePackageService.getAllActivePackages();
            return ResponseEntity.ok(
                    new ApiResponse(true, "Active packages retrieved successfully", packages)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve packages: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @ApiOperation(value = "Get All Packages including inactive - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPackages() {
        try {
            List<CoursePackageDto> packages = coursePackageService.getAllPackages();
            return ResponseEntity.ok(
                    new ApiResponse(true, "All packages retrieved successfully", packages)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve packages: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @ApiOperation(value = "Delete Course Package - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePackage(@PathVariable Long id) {
        try {
            coursePackageService.deletePackage(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Package deleted successfully")
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to delete package: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Toggle Package Status - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse> togglePackageStatus(@PathVariable Long id) {
        try {
            CoursePackageDto updatedPackage = coursePackageService.togglePackageStatus(id);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Package status toggled successfully", updatedPackage)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to toggle package status: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

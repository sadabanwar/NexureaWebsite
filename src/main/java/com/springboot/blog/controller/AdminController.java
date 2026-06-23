package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "Admin REST API")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get All Users - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            // Remove sensitive data before sending
            users.forEach(user -> user.setPassword(null));

            return ResponseEntity.ok(
                    new ApiResponse(true, "Users retrieved successfully", users)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve users: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @ApiOperation(value = "Get User by ID - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            // Remove sensitive data
            user.setPassword(null);

            return ResponseEntity.ok(
                    new ApiResponse(true, "User retrieved successfully", user)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "User not found: " + e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @ApiOperation(value = "Toggle User Active Status - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{id}/toggle")
    public ResponseEntity<ApiResponse> toggleUserStatus(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            user.setActive(!user.getActive());
            User updatedUser = userRepository.save(user);

            // Remove sensitive data
            updatedUser.setPassword(null);

            return ResponseEntity.ok(
                    new ApiResponse(true, "User status toggled successfully", updatedUser)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to toggle user status: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Sales Report - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales-report")
    public ResponseEntity<ApiResponse> getSalesReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            // This would typically integrate with a dedicated service
            // For now, providing a basic implementation
            Map<String, Object> salesReport = new HashMap<>();
            salesReport.put("message", "Sales report endpoint - to be implemented with detailed sales analytics");
            salesReport.put("startDate", startDate);
            salesReport.put("endDate", endDate);

            // TODO: Implement detailed sales reporting logic
            // This should include:
            // - Total sales by date range
            // - Top selling packages
            // - Revenue breakdown
            // - Commission paid
            // - Active users
            // - Conversion rates

            return ResponseEntity.ok(
                    new ApiResponse(true, "Sales report retrieved successfully", salesReport)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve sales report: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

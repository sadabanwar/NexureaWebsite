package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.DashboardDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "Dashboard REST API")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get Affiliate Dashboard - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/affiliate")
    public ResponseEntity<ApiResponse> getAffiliateDashboard() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            DashboardDto dashboard = dashboardService.getAffiliateDashboard(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Affiliate dashboard retrieved successfully", dashboard)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve dashboard: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Admin Dashboard - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse> getAdminDashboard() {
        try {
            Map<String, Object> dashboard = dashboardService.getAdminDashboard();
            return ResponseEntity.ok(
                    new ApiResponse(true, "Admin dashboard retrieved successfully", dashboard)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve admin dashboard: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

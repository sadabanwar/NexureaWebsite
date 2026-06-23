package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.CommissionDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.CommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "Commission REST API")
@RestController
@RequestMapping("/api/commissions")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get Current User's Commissions - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-commissions")
    public ResponseEntity<ApiResponse> getMyCommissions() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<CommissionDto> commissions = commissionService.getAffiliateCommissions(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Commissions retrieved successfully", commissions)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve commissions: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Total Earnings - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/earnings")
    public ResponseEntity<ApiResponse> getTotalEarnings() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Double totalEarnings = commissionService.getTotalEarnings(user.getId());

            Map<String, Object> earningsData = new HashMap<>();
            earningsData.put("totalEarnings", totalEarnings);
            earningsData.put("availableBalance", user.getAvailableBalance());
            earningsData.put("withdrawnAmount", user.getWithdrawnAmount());

            return ResponseEntity.ok(
                    new ApiResponse(true, "Earnings retrieved successfully", earningsData)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve earnings: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

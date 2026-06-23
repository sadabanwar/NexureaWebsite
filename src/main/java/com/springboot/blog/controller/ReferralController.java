package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.ReferralService;
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
import java.util.Map;

@Api(value = "Referral REST API")
@RestController
@RequestMapping("/api/referral")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get User's Referral Code - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-code")
    public ResponseEntity<ApiResponse> getMyReferralCode() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> referralData = new HashMap<>();
            referralData.put("referralCode", user.getReferralCode());
            referralData.put("totalReferrals", user.getTotalReferrals());

            return ResponseEntity.ok(
                    new ApiResponse(true, "Referral code retrieved successfully", referralData)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve referral code: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Validate Referral Code - PUBLIC")
    @GetMapping("/validate/{code}")
    public ResponseEntity<ApiResponse> validateReferralCode(@PathVariable String code) {
        try {
            boolean isValid = referralService.validateReferralCode(code);

            Map<String, Object> validationData = new HashMap<>();
            validationData.put("valid", isValid);
            validationData.put("code", code);

            if (isValid) {
                return ResponseEntity.ok(
                        new ApiResponse(true, "Referral code is valid", validationData)
                );
            } else {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Invalid referral code", validationData),
                        HttpStatus.BAD_REQUEST
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to validate referral code: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Referral Statistics - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getReferralStats() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> stats = referralService.getReferralStats(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Referral statistics retrieved successfully", stats)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve referral statistics: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

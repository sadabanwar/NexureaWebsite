package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.WithdrawalDto;
import com.springboot.blog.payload.WithdrawalRequestDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.WithdrawalService;
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
import java.util.Map;

@Api(value = "Withdrawal REST API")
@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Create Withdrawal Request - USER")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> createWithdrawal(@Valid @RequestBody WithdrawalRequestDto request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if user has sufficient balance
            if (user.getAvailableBalance() < request.getAmount().doubleValue()) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Insufficient balance for withdrawal"),
                        HttpStatus.BAD_REQUEST
                );
            }

            WithdrawalDto withdrawal = withdrawalService.createWithdrawalRequest(user.getId(), request);
            return new ResponseEntity<>(
                    new ApiResponse(true, "Withdrawal request created successfully", withdrawal),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to create withdrawal request: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get User's Withdrawal Requests - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-withdrawals")
    public ResponseEntity<ApiResponse> getMyWithdrawals() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<WithdrawalDto> withdrawals = withdrawalService.getUserWithdrawals(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Withdrawals retrieved successfully", withdrawals)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve withdrawals: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Pending Withdrawals - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse> getPendingWithdrawals() {
        try {
            List<WithdrawalDto> withdrawals = withdrawalService.getPendingWithdrawals();
            return ResponseEntity.ok(
                    new ApiResponse(true, "Pending withdrawals retrieved successfully", withdrawals)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve pending withdrawals: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @ApiOperation(value = "Approve Withdrawal - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse> approveWithdrawal(
            @PathVariable Long id,
            @RequestBody Map<String, String> data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User admin = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            String transactionId = data.get("transactionId");
            if (transactionId == null || transactionId.trim().isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Transaction ID is required"),
                        HttpStatus.BAD_REQUEST
                );
            }

            WithdrawalDto withdrawal = withdrawalService.approveWithdrawal(id, admin.getId(), transactionId);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Withdrawal approved successfully", withdrawal)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to approve withdrawal: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Reject Withdrawal - ADMIN only")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse> rejectWithdrawal(
            @PathVariable Long id,
            @RequestBody Map<String, String> data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User admin = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            String remarks = data.get("remarks");
            if (remarks == null || remarks.trim().isEmpty()) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Rejection remarks are required"),
                        HttpStatus.BAD_REQUEST
                );
            }

            WithdrawalDto withdrawal = withdrawalService.rejectWithdrawal(id, admin.getId(), remarks);
            return ResponseEntity.ok(
                    new ApiResponse(true, "Withdrawal rejected successfully", withdrawal)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to reject withdrawal: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.PurchaseDto;
import com.springboot.blog.repository.UserRepository;
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

import java.util.List;

@Api(value = "Purchase REST API")
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get Current User's Purchases - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-purchases")
    public ResponseEntity<ApiResponse> getMyPurchases() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<PurchaseDto> purchases = purchaseService.getUserPurchases(user.getId());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Purchases retrieved successfully", purchases)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to retrieve purchases: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Get Purchase by Order ID - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getPurchaseByOrderId(@PathVariable String orderId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            PurchaseDto purchase = purchaseService.getPurchaseByOrderId(orderId);

            // Verify the purchase belongs to the current user
            if (!purchase.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Unauthorized access to purchase"),
                        HttpStatus.FORBIDDEN
                );
            }

            return ResponseEntity.ok(
                    new ApiResponse(true, "Purchase retrieved successfully", purchase)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Purchase not found: " + e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @ApiOperation(value = "Check if User Purchased Package - USER")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/check/{packageId}")
    public ResponseEntity<ApiResponse> checkPurchase(@PathVariable Long packageId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean hasPurchased = purchaseService.hasUserPurchasedPackage(user.getId(), packageId);

            return ResponseEntity.ok(
                    new ApiResponse(true, "Purchase check completed", hasPurchased)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to check purchase: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.ApiResponse;
import com.springboot.blog.payload.PaymentRequestDto;
import com.springboot.blog.payload.PaymentResponseDto;
import com.springboot.blog.payload.PurchaseDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.PaymentService;
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
import java.util.HashMap;
import java.util.Map;

@Api(value = "Payment REST API")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Create Razorpay Order - USER")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse> createOrder(@Valid @RequestBody PaymentRequestDto request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            PaymentResponseDto response = paymentService.createOrder(user.getId(), request);
            return new ResponseEntity<>(
                    new ApiResponse(true, "Order created successfully", response),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Failed to create order: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiOperation(value = "Verify Payment and Complete Purchase - USER")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyPayment(@RequestBody Map<String, String> paymentData) {
        try {
            String orderId = paymentData.get("orderId");
            String paymentId = paymentData.get("paymentId");
            String signature = paymentData.get("signature");

            if (orderId == null || paymentId == null || signature == null) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Missing payment verification data"),
                        HttpStatus.BAD_REQUEST
                );
            }

            // Verify payment signature
            boolean isValid = paymentService.verifyPayment(orderId, paymentId, signature);

            if (!isValid) {
                return new ResponseEntity<>(
                        new ApiResponse(false, "Invalid payment signature"),
                        HttpStatus.BAD_REQUEST
                );
            }

            // Process successful payment
            PurchaseDto purchase = paymentService.processSuccessfulPayment(orderId, paymentId);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("purchase", purchase);
            responseData.put("verified", true);

            return ResponseEntity.ok(
                    new ApiResponse(true, "Payment verified and purchase completed successfully", responseData)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "Payment verification failed: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}

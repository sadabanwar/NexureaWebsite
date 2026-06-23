package com.springboot.blog.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.springboot.blog.entity.CoursePackage;
import com.springboot.blog.entity.Purchase;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PaymentRequestDto;
import com.springboot.blog.payload.PaymentResponseDto;
import com.springboot.blog.payload.PurchaseDto;
import com.springboot.blog.repository.CoursePackageRepository;
import com.springboot.blog.repository.PurchaseRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.CommissionService;
import com.springboot.blog.service.PaymentService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CoursePackageRepository coursePackageRepository;
    private final CommissionService commissionService;
    private final ModelMapper mapper;

    public PaymentServiceImpl(PurchaseRepository purchaseRepository,
                              UserRepository userRepository,
                              CoursePackageRepository coursePackageRepository,
                              CommissionService commissionService,
                              ModelMapper mapper) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.coursePackageRepository = coursePackageRepository;
        this.commissionService = commissionService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PaymentResponseDto createOrder(Long userId, PaymentRequestDto request) {
        logger.info("Creating Razorpay order for user ID: {}", userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

            CoursePackage coursePackage = coursePackageRepository.findById(request.getPackageId())
                    .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", request.getPackageId()));

            // Check if package is active
            if (!coursePackage.getActive()) {
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Course package is not available");
            }

            // Check if user already purchased this package
            boolean alreadyPurchased = purchaseRepository.existsByUserIdAndCoursePackageIdAndPaymentStatus(
                    userId, request.getPackageId(), "SUCCESS"
            );
            if (alreadyPurchased) {
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "You have already purchased this package");
            }

            // Initialize Razorpay client
            RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            // Create order request
            JSONObject orderRequest = new JSONObject();
            int amountInPaise = (int) (coursePackage.getPrice() * 100);
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "ORDER_" + System.currentTimeMillis());

            // Create order
            Order order = razorpayClient.orders.create(orderRequest);
            String orderId = order.get("id");

            logger.info("Razorpay order created successfully: {}", orderId);

            // Create pending purchase record
            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setCoursePackage(coursePackage);
            purchase.setOrderId(orderId);
            purchase.setPaymentId("");
            purchase.setAmount(coursePackage.getPrice());
            purchase.setPaymentStatus("PENDING");
            purchase.setReferralCode(request.getReferralCode());
            purchase.setPurchaseDate(LocalDateTime.now());
            purchase.setCommissionProcessed(false);

            purchaseRepository.save(purchase);

            // Prepare response
            PaymentResponseDto response = new PaymentResponseDto();
            response.setOrderId(orderId);
            response.setAmount(BigDecimal.valueOf(coursePackage.getPrice()));
            response.setCurrency("INR");
            response.setKey(razorpayKeyId);

            return response;

        } catch (RazorpayException e) {
            logger.error("Error creating Razorpay order: {}", e.getMessage());
            throw new BlogAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Payment gateway error: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        logger.info("Verifying payment for order ID: {}", orderId);

        try {
            String payload = orderId + "|" + paymentId;
            String generatedSignature = calculateHmacSHA256(payload, razorpayKeySecret);

            boolean isValid = generatedSignature.equals(signature);
            logger.info("Payment signature verification result: {}", isValid);

            return isValid;

        } catch (Exception e) {
            logger.error("Error verifying payment: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public PurchaseDto processSuccessfulPayment(String orderId, String paymentId) {
        logger.info("Processing successful payment for order ID: {}", orderId);

        Purchase purchase = purchaseRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "orderId", 0));

        // Update payment status
        purchase.setPaymentId(paymentId);
        purchase.setPaymentStatus("SUCCESS");

        Purchase updatedPurchase = purchaseRepository.save(purchase);

        // Process commission if referral code exists
        if (purchase.getReferralCode() != null && !purchase.getReferralCode().isEmpty()) {
            try {
                commissionService.processCommission(purchase);
                logger.info("Commission processed successfully for order ID: {}", orderId);
            } catch (Exception e) {
                logger.error("Error processing commission: {}", e.getMessage());
            }
        }

        logger.info("Payment processed successfully for order ID: {}", orderId);

        return mapToDto(updatedPurchase);
    }

    private String calculateHmacSHA256(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            logger.error("Error calculating HMAC SHA256: {}", e.getMessage());
            throw new BlogAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Error verifying payment signature");
        }
    }

    private PurchaseDto mapToDto(Purchase purchase) {
        PurchaseDto dto = new PurchaseDto();
        dto.setId(purchase.getId());
        dto.setUserId(purchase.getUser().getId());
        dto.setPackageId(purchase.getCoursePackage().getId());
        dto.setOrderId(purchase.getOrderId());
        dto.setPaymentId(purchase.getPaymentId());
        dto.setAmount(BigDecimal.valueOf(purchase.getAmount()));
        dto.setPaymentStatus(purchase.getPaymentStatus());
        dto.setReferralCode(purchase.getReferralCode());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        return dto;
    }
}

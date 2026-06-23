package com.springboot.blog.service.impl;

import com.springboot.blog.entity.CoursePackage;
import com.springboot.blog.entity.Purchase;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PurchaseDto;
import com.springboot.blog.repository.CoursePackageRepository;
import com.springboot.blog.repository.PurchaseRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.PurchaseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final CoursePackageRepository coursePackageRepository;
    private final ModelMapper mapper;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                                UserRepository userRepository,
                                CoursePackageRepository coursePackageRepository,
                                ModelMapper mapper) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.coursePackageRepository = coursePackageRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PurchaseDto createPurchase(PurchaseDto dto) {
        logger.info("Creating new purchase for user ID: {}", dto.getUserId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));

        CoursePackage coursePackage = coursePackageRepository.findById(dto.getPackageId())
                .orElseThrow(() -> new ResourceNotFoundException("CoursePackage", "id", dto.getPackageId()));

        // Check if user already purchased this package
        if (hasUserPurchasedPackage(dto.getUserId(), dto.getPackageId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User has already purchased this package");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setCoursePackage(coursePackage);
        purchase.setOrderId(dto.getOrderId());
        purchase.setPaymentId(dto.getPaymentId() != null ? dto.getPaymentId() : "");
        purchase.setAmount(dto.getAmount().doubleValue());
        purchase.setPaymentStatus(dto.getPaymentStatus());
        purchase.setReferralCode(dto.getReferralCode());
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setCommissionProcessed(false);

        Purchase savedPurchase = purchaseRepository.save(purchase);
        logger.info("Purchase created successfully with ID: {}", savedPurchase.getId());

        return mapToDto(savedPurchase);
    }

    @Override
    public PurchaseDto getPurchaseByOrderId(String orderId) {
        logger.info("Fetching purchase with order ID: {}", orderId);

        Purchase purchase = purchaseRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "orderId", 0));

        return mapToDto(purchase);
    }

    @Override
    public List<PurchaseDto> getUserPurchases(Long userId) {
        logger.info("Fetching all purchases for user ID: {}", userId);

        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        return purchases.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PurchaseDto updatePaymentStatus(String orderId, String paymentId, String status) {
        logger.info("Updating payment status for order ID: {} to {}", orderId, status);

        Purchase purchase = purchaseRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "orderId", 0));

        purchase.setPaymentId(paymentId);
        purchase.setPaymentStatus(status);

        Purchase updatedPurchase = purchaseRepository.save(purchase);
        logger.info("Payment status updated successfully for order ID: {}", orderId);

        return mapToDto(updatedPurchase);
    }

    @Override
    public boolean hasUserPurchasedPackage(Long userId, Long packageId) {
        logger.info("Checking if user ID: {} has purchased package ID: {}", userId, packageId);

        return purchaseRepository.existsByUserIdAndCoursePackageIdAndPaymentStatus(userId, packageId, "SUCCESS");
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

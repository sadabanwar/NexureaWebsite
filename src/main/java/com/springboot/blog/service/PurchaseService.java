package com.springboot.blog.service;

import com.springboot.blog.payload.PurchaseDto;

import java.util.List;

public interface PurchaseService {

    PurchaseDto createPurchase(PurchaseDto dto);

    PurchaseDto getPurchaseByOrderId(String orderId);

    List<PurchaseDto> getUserPurchases(Long userId);

    PurchaseDto updatePaymentStatus(String orderId, String paymentId, String status);

    boolean hasUserPurchasedPackage(Long userId, Long packageId);
}

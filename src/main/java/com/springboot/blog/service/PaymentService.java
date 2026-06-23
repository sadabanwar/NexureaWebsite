package com.springboot.blog.service;

import com.springboot.blog.payload.PaymentRequestDto;
import com.springboot.blog.payload.PaymentResponseDto;
import com.springboot.blog.payload.PurchaseDto;

public interface PaymentService {

    PaymentResponseDto createOrder(Long userId, PaymentRequestDto request);

    boolean verifyPayment(String orderId, String paymentId, String signature);

    PurchaseDto processSuccessfulPayment(String orderId, String paymentId);
}

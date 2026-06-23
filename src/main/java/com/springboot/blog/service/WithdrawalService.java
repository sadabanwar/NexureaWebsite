package com.springboot.blog.service;

import com.springboot.blog.payload.WithdrawalDto;
import com.springboot.blog.payload.WithdrawalRequestDto;

import java.util.List;

public interface WithdrawalService {

    WithdrawalDto createWithdrawalRequest(Long userId, WithdrawalRequestDto dto);

    List<WithdrawalDto> getUserWithdrawals(Long userId);

    List<WithdrawalDto> getPendingWithdrawals();

    WithdrawalDto approveWithdrawal(Long id, Long adminId, String transactionId);

    WithdrawalDto rejectWithdrawal(Long id, Long adminId, String remarks);
}

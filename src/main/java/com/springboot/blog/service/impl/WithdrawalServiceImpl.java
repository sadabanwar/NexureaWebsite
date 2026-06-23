package com.springboot.blog.service.impl;

import com.springboot.blog.entity.User;
import com.springboot.blog.entity.Withdrawal;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.WithdrawalDto;
import com.springboot.blog.payload.WithdrawalRequestDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.repository.WithdrawalRepository;
import com.springboot.blog.service.WithdrawalService;
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
public class WithdrawalServiceImpl implements WithdrawalService {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawalServiceImpl.class);

    private final WithdrawalRepository withdrawalRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository,
                                  UserRepository userRepository,
                                  ModelMapper mapper) {
        this.withdrawalRepository = withdrawalRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public WithdrawalDto createWithdrawalRequest(Long userId, WithdrawalRequestDto dto) {
        logger.info("Creating withdrawal request for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Check if user has sufficient balance
        if (user.getAvailableBalance() < dto.getAmount().doubleValue()) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Insufficient balance for withdrawal");
        }

        // Check minimum withdrawal amount
        if (dto.getAmount().doubleValue() < 100.0) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Minimum withdrawal amount is 100");
        }

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setUser(user);
        withdrawal.setAmount(dto.getAmount().doubleValue());
        withdrawal.setStatus("PENDING");
        withdrawal.setAccountNumber(dto.getAccountNumber());
        withdrawal.setIfscCode(dto.getIfscCode());
        withdrawal.setAccountHolderName(dto.getAccountHolderName());
        withdrawal.setUpiId(dto.getUpiId());
        withdrawal.setRequestDate(LocalDateTime.now());

        Withdrawal savedWithdrawal = withdrawalRepository.save(withdrawal);

        // Deduct from available balance
        user.setAvailableBalance(user.getAvailableBalance() - dto.getAmount().doubleValue());
        userRepository.save(user);

        logger.info("Withdrawal request created successfully with ID: {}", savedWithdrawal.getId());

        return mapToDto(savedWithdrawal);
    }

    @Override
    public List<WithdrawalDto> getUserWithdrawals(Long userId) {
        logger.info("Fetching withdrawals for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Withdrawal> withdrawals = withdrawalRepository.findByUserId(userId);
        return withdrawals.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<WithdrawalDto> getPendingWithdrawals() {
        logger.info("Fetching all pending withdrawals");

        List<Withdrawal> withdrawals = withdrawalRepository.findByStatus("PENDING");
        return withdrawals.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WithdrawalDto approveWithdrawal(Long id, Long adminId, String transactionId) {
        logger.info("Approving withdrawal ID: {} by admin ID: {}", id, adminId);

        Withdrawal withdrawal = withdrawalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Withdrawal", "id", id));

        if (!"PENDING".equals(withdrawal.getStatus())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Only pending withdrawals can be approved");
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

        withdrawal.setStatus("APPROVED");
        withdrawal.setTransactionId(transactionId);
        withdrawal.setProcessedDate(LocalDateTime.now());
        withdrawal.setProcessedBy(admin);

        Withdrawal updatedWithdrawal = withdrawalRepository.save(withdrawal);

        // Update user's withdrawn amount
        User user = withdrawal.getUser();
        user.setWithdrawnAmount(user.getWithdrawnAmount() + withdrawal.getAmount());
        userRepository.save(user);

        logger.info("Withdrawal approved successfully with ID: {}", id);

        return mapToDto(updatedWithdrawal);
    }

    @Override
    @Transactional
    public WithdrawalDto rejectWithdrawal(Long id, Long adminId, String remarks) {
        logger.info("Rejecting withdrawal ID: {} by admin ID: {}", id, adminId);

        Withdrawal withdrawal = withdrawalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Withdrawal", "id", id));

        if (!"PENDING".equals(withdrawal.getStatus())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Only pending withdrawals can be rejected");
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", adminId));

        withdrawal.setStatus("REJECTED");
        withdrawal.setRemarks(remarks);
        withdrawal.setProcessedDate(LocalDateTime.now());
        withdrawal.setProcessedBy(admin);

        Withdrawal updatedWithdrawal = withdrawalRepository.save(withdrawal);

        // Refund the amount back to user's available balance
        User user = withdrawal.getUser();
        user.setAvailableBalance(user.getAvailableBalance() + withdrawal.getAmount());
        userRepository.save(user);

        logger.info("Withdrawal rejected successfully with ID: {}", id);

        return mapToDto(updatedWithdrawal);
    }

    private WithdrawalDto mapToDto(Withdrawal withdrawal) {
        WithdrawalDto dto = new WithdrawalDto();
        dto.setId(withdrawal.getId());
        dto.setUserId(withdrawal.getUser().getId());
        dto.setAmount(BigDecimal.valueOf(withdrawal.getAmount()));
        dto.setStatus(withdrawal.getStatus());
        dto.setAccountNumber(withdrawal.getAccountNumber());
        dto.setIfscCode(withdrawal.getIfscCode());
        dto.setAccountHolderName(withdrawal.getAccountHolderName());
        dto.setUpiId(withdrawal.getUpiId());
        dto.setTransactionId(withdrawal.getTransactionId());
        dto.setRemarks(withdrawal.getRemarks());
        dto.setRequestDate(withdrawal.getRequestDate());
        dto.setProcessedDate(withdrawal.getProcessedDate());
        return dto;
    }
}

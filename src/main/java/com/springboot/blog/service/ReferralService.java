package com.springboot.blog.service;

import java.util.Map;

public interface ReferralService {

    String generateReferralCode(String username);

    boolean validateReferralCode(String code);

    Map<String, Object> getReferralStats(Long userId);
}

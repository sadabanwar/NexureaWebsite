package com.springboot.blog.service;

import com.springboot.blog.payload.DashboardDto;

import java.util.Map;

public interface DashboardService {

    DashboardDto getAffiliateDashboard(Long userId);

    Map<String, Object> getAdminDashboard();
}

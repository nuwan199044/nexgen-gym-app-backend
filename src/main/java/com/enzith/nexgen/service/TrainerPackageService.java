package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.TrainerPackageRequest;
import com.enzith.nexgen.dto.response.TrainerPackageResponse;

import java.util.Map;

public interface TrainerPackageService {
    String createTrainerPackage(TrainerPackageRequest trainerPackageRequest);
    String updateTrainerPackage(TrainerPackageRequest trainerPackageRequest);
    TrainerPackageResponse findTrainerPackageById(Long trainerPackageId);
    Map<String, Object> findAllTrainerPackages(Long trainerId, Integer currentPage, Integer pageSize);
}

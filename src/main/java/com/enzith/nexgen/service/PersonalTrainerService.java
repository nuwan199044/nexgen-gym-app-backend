package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.TrainerRequest;
import com.enzith.nexgen.dto.response.TrainerResponse;

import java.util.Map;

public interface PersonalTrainerService {
    String createTrainer(TrainerRequest trainerRequest);
    String updateTrainer(TrainerRequest trainerRequest);
    TrainerResponse findTrainerById(Long trainerId);
    Map<String, Object> findAllTrainers(String firstName, String phoneNo1, Integer currentPage, Integer pageSize);
}

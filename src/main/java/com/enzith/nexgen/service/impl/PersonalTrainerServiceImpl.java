package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.criteria.TrainerCriteria;
import com.enzith.nexgen.dto.request.TrainerRequest;
import com.enzith.nexgen.dto.response.TrainerResponse;
import com.enzith.nexgen.entity.Trainer;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.TrainerException;
import com.enzith.nexgen.repository.PersonalTrainerRepository;
import com.enzith.nexgen.service.PersonalTrainerService;
import com.enzith.nexgen.specification.TrainerSpecification;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalTrainerServiceImpl implements PersonalTrainerService {

    private final PersonalTrainerRepository personalTrainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public String createTrainer(TrainerRequest trainerRequest) {
        validatePhoneNo1(trainerRequest.getPhoneNo1());

        Trainer trainer = modelMapper.map(trainerRequest, Trainer.class);
        trainer = personalTrainerRepository.save(trainer);
        return trainer.getTrainerNo();
    }

    @Override
    public String updateTrainer(TrainerRequest trainerRequest) {
        Trainer existingTrainer = personalTrainerRepository.findById(trainerRequest.getTrainerId())
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));

        validateUpdatedPhoneNo1(existingTrainer, trainerRequest.getPhoneNo1());

        modelMapper.map(trainerRequest, existingTrainer);
        personalTrainerRepository.save(existingTrainer);
        return existingTrainer.getTrainerNo();
    }

    @Override
    public TrainerResponse findTrainerById(Long trainerId) {
        return modelMapper.map(personalTrainerRepository.findById(trainerId), TrainerResponse.class);
    }

    @Override
    public Map<String, Object> findAllTrainers(String firstName, String phoneNo1, Integer currentPage, Integer pageSize) {
        TrainerCriteria criteria = TrainerCriteria.builder()
                .firstName(firstName)
                .phoneNo1(phoneNo1)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<Trainer> trainers = personalTrainerRepository.findAll(new TrainerSpecification(criteria), pageable);
        return PaginationUtils.convertToPagination(trainers.map(trainer -> modelMapper.map(trainer, TrainerResponse.class)));
    }

    private void validateUpdatedPhoneNo1(Trainer existingTrainer, String newPhoneNo) {
        if (!existingTrainer.getPhoneNo1().equals(newPhoneNo)) {
            validatePhoneNo1(newPhoneNo);
        }
    }

    private void validatePhoneNo1(String phoneNo1) {
        personalTrainerRepository.findByPhoneNo1(phoneNo1)
                .ifPresent(trainer -> {
                    throw new TrainerException(ResponseCode.TRAINER_PHONE_NO_1_IS_ALREADY_EXIST);
                });
    }
}

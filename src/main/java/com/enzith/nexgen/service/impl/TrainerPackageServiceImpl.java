package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.criteria.TrainerPackageCriteria;
import com.enzith.nexgen.dto.request.TrainerPackageRequest;
import com.enzith.nexgen.dto.response.TrainerPackageResponse;
import com.enzith.nexgen.entity.Trainer;
import com.enzith.nexgen.entity.TrainerPackage;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.TrainerException;
import com.enzith.nexgen.exception.TrainerPackageException;
import com.enzith.nexgen.repository.TrainerPackageRepository;
import com.enzith.nexgen.repository.TrainerRepository;
import com.enzith.nexgen.service.TrainerPackageService;
import com.enzith.nexgen.specification.TrainerPackageSpecification;
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
public class TrainerPackageServiceImpl implements TrainerPackageService {

    private final TrainerPackageRepository trainerPackageRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public String createTrainerPackage(TrainerPackageRequest trainerPackageRequest) {
        Trainer trainer = trainerRepository.findById(trainerPackageRequest.getTrainerId())
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));
        TrainerPackage trainerPackage = modelMapper.map(trainerPackageRequest, TrainerPackage.class);
        trainerPackage.setTrainer(trainer);
        trainerPackage = trainerPackageRepository.save(trainerPackage);
        return trainerPackage.getPackageName();
    }

    @Override
    public String updateTrainerPackage(TrainerPackageRequest trainerPackageRequest) {
        TrainerPackage existingTrainerPackage = trainerPackageRepository.findById(trainerPackageRequest.getPackageId())
                .orElseThrow(() -> new TrainerPackageException(ResponseCode.TRAINER_PACKAGE_NOT_FOUND));

        trainerRepository.findById(trainerPackageRequest.getTrainerId())
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));

        modelMapper.map(trainerPackageRequest, existingTrainerPackage);
        trainerPackageRepository.save(existingTrainerPackage);
        return existingTrainerPackage.getPackageName();
    }

    @Override
    public TrainerPackageResponse findTrainerPackageById(Long trainerPackageId) {
        return modelMapper.map(trainerPackageRepository.findById(trainerPackageId), TrainerPackageResponse.class);
    }

    @Override
    public Map<String, Object> findAllTrainerPackages(Long trainerId, Integer currentPage, Integer pageSize) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));

        TrainerPackageCriteria criteria = TrainerPackageCriteria.builder()
                .trainerId(trainer)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<TrainerPackage> trainerPackages = trainerPackageRepository.findAll(new TrainerPackageSpecification(criteria), pageable);
        return PaginationUtils.convertToPagination(trainerPackages.map(trainerPackage -> modelMapper.map(trainerPackage, TrainerPackageResponse.class)));
    }
}

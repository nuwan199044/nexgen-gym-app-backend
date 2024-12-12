package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.TrainerPackageRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.TrainerPackageResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.TrainerPackageService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/trainer-package")
@RequiredArgsConstructor
public class TrainerPackageController {

    private final TrainerPackageService trainerPackageService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> createTrainerPackage(@RequestBody TrainerPackageRequest trainerPackageRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.TRAINER_PACKAGE_CREATED_SUCCESS,
                        trainerPackageService.createTrainerPackage(trainerPackageRequest)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateTrainerPackage(@RequestBody TrainerPackageRequest trainerPackageRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.TRAINER_PACKAGE_UPDATED_SUCCESS,
                        trainerPackageService.updateTrainerPackage(trainerPackageRequest)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllTrainerPackages(
            @RequestParam(value = "trainer_id") Long trainerId,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(trainerPackageService.findAllTrainerPackages(trainerId, currentPage, pageSize), HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<APIResponse<TrainerPackageResponse>> findTrainerPackageById(@RequestParam(value = "package_id") Long packageId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.TRAINER_PACKAGE_FIND_SUCCESS,
                        trainerPackageService.findTrainerPackageById(packageId)
                ), HttpStatus.OK);
    }

}

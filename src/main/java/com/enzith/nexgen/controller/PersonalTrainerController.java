package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.TrainerRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.TrainerResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.PersonalTrainerService;
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
@RequestMapping("/api/personal-trainer")
@RequiredArgsConstructor
public class PersonalTrainerController {

    private final PersonalTrainerService personalTrainerService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> createTrainer(@RequestBody TrainerRequest trainerRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.TRAINER_CREATED_SUCCESS,
                        personalTrainerService.createTrainer(trainerRequest)
                ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateTrainer(@RequestBody TrainerRequest trainerRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.TRAINER_UPDATED_SUCCESS,
                        personalTrainerService.updateTrainer(trainerRequest)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllTrainers(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "phone_no_1", required = false) String phoneNo1,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        return new ResponseEntity<>(personalTrainerService.findAllTrainers(firstName, phoneNo1, currentPage, pageSize), HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<APIResponse<TrainerResponse>> findTrainerById(@RequestParam(value = "trainer_id") Long trainerId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.TRAINER_FIND_SUCCESS,
                        personalTrainerService.findTrainerById(trainerId)
                ), HttpStatus.OK);
    }
}

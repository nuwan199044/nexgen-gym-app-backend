package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.PersonalTrainingRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.PersonalTrainingResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.PersonalTrainingService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personal-training")
@RequiredArgsConstructor
public class PersonalTrainingController {

    private final PersonalTrainingService personalTrainingService;

    @PutMapping
    public ResponseEntity<APIResponse<PersonalTrainingResponse>> updatePersonalTraining(@RequestBody PersonalTrainingRequest personalTrainingRequest) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.PERSONAL_TRAINING_SESSION_UPDATED_SUCCESS,
                        personalTrainingService.updatePersonalTrainingSession(personalTrainingRequest)
                ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonalTrainingResponse>> findPersonalTrainingsByMemberTrainerSession(
            @RequestParam(value = "member_trainer_session_id") Long memberTrainerSessionId) {
        return new ResponseEntity<>(personalTrainingService.findPersonalTrainingsByMemberTrainerSession(memberTrainerSessionId), HttpStatus.OK);
    }
}

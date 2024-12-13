package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.request.PersonalTrainingRequest;
import com.enzith.nexgen.dto.response.PersonalTrainingResponse;

import java.util.List;

public interface PersonalTrainingService {
    PersonalTrainingResponse updatePersonalTrainingSession(PersonalTrainingRequest personalTrainingRequest);

    List<PersonalTrainingResponse> findPersonalTrainingsByMemberTrainerSession(Long memberTrainerSessionId);
}

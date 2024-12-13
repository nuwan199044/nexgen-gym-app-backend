package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.dto.request.PersonalTrainingRequest;
import com.enzith.nexgen.dto.response.PersonalTrainingResponse;
import com.enzith.nexgen.entity.MemberTrainerSession;
import com.enzith.nexgen.entity.PersonalTraining;
import com.enzith.nexgen.enums.CompleteStatus;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.MemberTrainerSessionException;
import com.enzith.nexgen.exception.PersonalTrainingException;
import com.enzith.nexgen.repository.MemberTrainerSessionRepository;
import com.enzith.nexgen.repository.PersonalTrainingRepository;
import com.enzith.nexgen.service.PersonalTrainingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalTrainingServiceImpl implements PersonalTrainingService {

    private final PersonalTrainingRepository personalTrainingRepository;
    private final MemberTrainerSessionRepository memberTrainerSessionRepository;
    private final ModelMapper modelMapper;

    @Override
    public PersonalTrainingResponse updatePersonalTrainingSession(PersonalTrainingRequest personalTrainingRequest) {
        PersonalTraining personalTraining = personalTrainingRepository.findById(personalTrainingRequest.getPersonalTrainingId())
                .orElseThrow(() -> new PersonalTrainingException(ResponseCode.PERSONAL_TRAINING_NOT_FOUND));
        personalTraining.setCompleteStatus(CompleteStatus.COMPLETED);
        personalTraining.setFromTime(personalTrainingRequest.getFromTime());
        personalTraining.setToTime(personalTrainingRequest.getToTime());
        return modelMapper.map(personalTrainingRepository.save(personalTraining), PersonalTrainingResponse.class);
    }

    @Override
    public List<PersonalTrainingResponse> findPersonalTrainingsByMemberTrainerSession(Long memberTrainerSessionId) {
        MemberTrainerSession memberTrainerSession = memberTrainerSessionRepository.findById(memberTrainerSessionId)
                .orElseThrow(() -> new MemberTrainerSessionException(ResponseCode.MEMBER_TRAINING_SESSION_NOT_FOUND));
        return personalTrainingRepository.findByMemberTrainerSession(memberTrainerSession).stream()
                .map(personalTraining -> modelMapper.map(personalTraining, PersonalTrainingResponse.class))
                .toList();
    }
}

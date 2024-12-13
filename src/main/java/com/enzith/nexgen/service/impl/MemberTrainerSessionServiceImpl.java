package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.MemberTrainerSessionCriteria;
import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.request.MemberTrainerSessionRequest;
import com.enzith.nexgen.dto.response.MemberTrainerSessionResponse;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberTrainerSession;
import com.enzith.nexgen.entity.PersonalTraining;
import com.enzith.nexgen.entity.Trainer;
import com.enzith.nexgen.entity.TrainerPackage;
import com.enzith.nexgen.enums.CompleteStatus;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.enums.Status;
import com.enzith.nexgen.exception.MemberException;
import com.enzith.nexgen.exception.MemberTrainerSessionException;
import com.enzith.nexgen.exception.TrainerException;
import com.enzith.nexgen.exception.TrainerPackageException;
import com.enzith.nexgen.repository.MemberRepository;
import com.enzith.nexgen.repository.MemberTrainerSessionRepository;
import com.enzith.nexgen.repository.PersonalTrainingRepository;
import com.enzith.nexgen.repository.TrainerPackageRepository;
import com.enzith.nexgen.repository.TrainerRepository;
import com.enzith.nexgen.service.MemberTrainerSessionService;
import com.enzith.nexgen.specification.MemberTrainerSessionSpecification;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberTrainerSessionServiceImpl implements MemberTrainerSessionService {

    private final MemberTrainerSessionRepository memberTrainerSessionRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;
    private final TrainerPackageRepository trainerPackageRepository;
    private final PersonalTrainingRepository personalTrainingRepository;
    private final ModelMapper modelMapper;

    @Override
    public MemberTrainerSessionResponse createMemberTrainerSession(MemberTrainerSessionRequest memberTrainerSessionRequest) {
        Trainer trainer = trainerRepository.findById(memberTrainerSessionRequest.getTrainerId())
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));

        Member member = memberRepository.findById(memberTrainerSessionRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));

        TrainerPackage trainerPackage = trainerPackageRepository.findById(memberTrainerSessionRequest.getPackageId())
                .orElseThrow(() -> new TrainerPackageException(ResponseCode.TRAINER_PACKAGE_NOT_FOUND));

        MemberTrainerSession memberTrainerSession = modelMapper.map(memberTrainerSessionRequest, MemberTrainerSession.class);
        memberTrainerSession.setTrainer(trainer);
        memberTrainerSession.setMember(member);
        memberTrainerSession.setTrainerPackage(trainerPackage);
        memberTrainerSession.setExpiryDate(memberTrainerSessionRequest.getPurchaseDate().plusDays(30));
        memberTrainerSession.setRemainingSessions(trainerPackage.getSessionCount());
        memberTrainerSession.setPaymentStatus(PaymentStatus.UNPAID);

        memberTrainerSession = memberTrainerSessionRepository.save(memberTrainerSession);

        personalTrainingRepository.saveAll(generateTrainingSessions(member, trainer, trainerPackage, memberTrainerSession));

        return modelMapper.map(memberTrainerSession, MemberTrainerSessionResponse.class);
    }

    @Transactional
    @Override
    public MemberTrainerSessionResponse updateMemberTrainerSession(MemberTrainerSessionRequest memberTrainerSessionRequest) {
        Trainer trainer = trainerRepository.findById(memberTrainerSessionRequest.getTrainerId())
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));

        Member member = memberRepository.findById(memberTrainerSessionRequest.getMemberId())
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));

        TrainerPackage trainerPackage = trainerPackageRepository.findById(memberTrainerSessionRequest.getPackageId())
                .orElseThrow(() -> new TrainerPackageException(ResponseCode.TRAINER_PACKAGE_NOT_FOUND));

        MemberTrainerSession memberTrainerSession = memberTrainerSessionRepository.findById(memberTrainerSessionRequest.getMemberTrainerSessionId())
                .orElseThrow(() -> new MemberTrainerSessionException(ResponseCode.MEMBER_TRAINING_SESSION_NOT_FOUND));

        if (PaymentStatus.PAID.equals(memberTrainerSession.getPaymentStatus())) {
            throw new MemberException(ResponseCode.MEMBER_ALREADY_MAKE_A_PAYMENT_FOR_TRAINING);
        }

        deactivatePersonalTrainings(memberTrainerSession);

        memberTrainerSession.setTrainer(trainer);
        memberTrainerSession.setMember(member);
        memberTrainerSession.setTrainerPackage(trainerPackage);
        map(memberTrainerSession, memberTrainerSessionRequest);
        personalTrainingRepository.saveAll(generateTrainingSessions(member, trainer, trainerPackage, memberTrainerSession));
        return modelMapper.map(memberTrainerSessionRepository.save(memberTrainerSession), MemberTrainerSessionResponse.class);
    }

    @Override
    public MemberTrainerSessionResponse findMemberTrainerSessionById(Long memberTrainerSessionId) {
        return modelMapper.map(memberTrainerSessionRepository.findById(memberTrainerSessionId), MemberTrainerSessionResponse.class);
    }

    @Override
    public Map<String, Object> findAllMemberTrainerSessions(Long trainerId, Long memberId, Integer currentPage, Integer pageSize) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ResponseCode.MEMBER_NOT_FOUND));

        MemberTrainerSessionCriteria criteria = MemberTrainerSessionCriteria.builder()
                .trainerId(trainer)
                .memberId(member)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<MemberTrainerSession> memberTrainerSessions = memberTrainerSessionRepository.findAll(new MemberTrainerSessionSpecification(criteria), pageable);
        return PaginationUtils.convertToPagination(memberTrainerSessions.map(memberTrainerSession -> modelMapper.map(memberTrainerSession, MemberTrainerSessionResponse.class)));
    }

    private void map(MemberTrainerSession memberTrainerSession, MemberTrainerSessionRequest memberTrainerSessionRequest) {
        memberTrainerSession.setRemainingSessions(memberTrainerSessionRequest.getRemainingSessions());
        memberTrainerSession.setDiscount(memberTrainerSessionRequest.getDiscount());
        memberTrainerSession.setTotalAmount(memberTrainerSessionRequest.getTotalAmount());
        memberTrainerSession.setNetAmount(memberTrainerSessionRequest.getNetAmount());
        memberTrainerSession.setPurchaseDate(memberTrainerSessionRequest.getPurchaseDate());
        memberTrainerSession.setPaymentStatus(PaymentStatus.valueOf(memberTrainerSessionRequest.getPaymentStatus()));
        memberTrainerSession.setExpiryDate(memberTrainerSessionRequest.getPurchaseDate().plusDays(30));
    }

    private List<PersonalTraining> generateTrainingSessions(
            Member member,
            Trainer trainer,
            TrainerPackage trainerPackage,
            MemberTrainerSession memberTrainerSession) {
        int sessionCount = trainerPackage.getSessionCount();

        List<PersonalTraining> personalTraining = new ArrayList<>();
        for (int i = 0; i < sessionCount; i++) {
            personalTraining.add(PersonalTraining.builder()
                    .member(member)
                    .trainer(trainer)
                    .trainerPackage(trainerPackage)
                    .memberTrainerSession(memberTrainerSession)
                    .sessionNo(i + 1)
                    .status(Status.ACTIVE)
                    .completeStatus(CompleteStatus.INCOMPLETE)
                    .build());
        }
        return personalTraining;
    }

    private void deactivatePersonalTrainings(MemberTrainerSession memberTrainerSession) {
        List<PersonalTraining> personalTrainings = personalTrainingRepository
                .findByMemberAndTrainerAndTrainerPackageAndStatus(
                        memberTrainerSession.getMember(),
                        memberTrainerSession.getTrainer(),
                        memberTrainerSession.getTrainerPackage(),
                        Status.ACTIVE
                );

        if (personalTrainings.stream().anyMatch(personalTraining -> CompleteStatus.COMPLETED.equals(personalTraining.getCompleteStatus()))) {
            throw new MemberException(ResponseCode.MEMBER_ALREADY_COMPLETED_A_SESSION);
        }

        personalTrainings.forEach(personalTraining -> personalTraining.setStatus(Status.INACTIVE));
        personalTrainingRepository.saveAll(personalTrainings);
    }
}

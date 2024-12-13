package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberTrainerSession;
import com.enzith.nexgen.entity.PersonalTraining;
import com.enzith.nexgen.entity.Trainer;
import com.enzith.nexgen.entity.TrainerPackage;
import com.enzith.nexgen.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonalTrainingRepository extends JpaSpecificationExecutor<PersonalTraining>,
        PagingAndSortingRepository<PersonalTraining, Long>, JpaRepository<PersonalTraining, Long> {
    List<PersonalTraining> findByMemberAndTrainerAndTrainerPackageAndStatus(Member member, Trainer trainer, TrainerPackage trainerPackage, Status status);
    List<PersonalTraining> findByMemberTrainerSession(MemberTrainerSession memberTrainerSession);
}

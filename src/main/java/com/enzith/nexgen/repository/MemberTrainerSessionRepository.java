package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.MemberTrainerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberTrainerSessionRepository extends JpaSpecificationExecutor<MemberTrainerSession>,
        PagingAndSortingRepository<MemberTrainerSession, Long>, JpaRepository<MemberTrainerSession, Long> {
}

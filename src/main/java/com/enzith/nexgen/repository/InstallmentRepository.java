package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.Installment;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    List<Installment> findByMemberAndMemberMembership(Member member, MemberMembership memberMembership);
    List<Installment> findByMemberMembership(MemberMembership memberMembership);
}

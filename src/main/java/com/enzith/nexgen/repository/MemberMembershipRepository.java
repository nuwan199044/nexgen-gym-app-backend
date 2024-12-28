package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberMembership;
import com.enzith.nexgen.enums.MembershipStatus;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberMembershipRepository extends JpaRepository<MemberMembership, Long> {
    Optional<MemberMembership> findByMemberMembershipIdAndStatus(Long memberMembershipId, Status status);
    Optional<MemberMembership> findByMemberMembershipIdAndMemberAndStatusAndPaymentStatus(Long memberMembershipId, Member member, MembershipStatus status, PaymentStatus paymentStatus);
    Page<MemberMembership> findByMemberAndStatusNot(Member member, MembershipStatus status, Pageable pageable);

    @Query("SELECT mm FROM MemberMembership mm WHERE mm.endDate < :currentDate AND mm.status = :status")
    List<MemberMembership> findMembershipsToExpire(@Param("currentDate") LocalDate currentDate, @Param("status") MembershipStatus membershipStatus);
}

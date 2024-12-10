package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.MemberMembership;
import com.enzith.nexgen.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberMembershipRepository extends JpaRepository<MemberMembership, Long> {
    Optional<MemberMembership> findByMemberMembershipIdAndStatus(Long memberMembershipId, Status status);
}

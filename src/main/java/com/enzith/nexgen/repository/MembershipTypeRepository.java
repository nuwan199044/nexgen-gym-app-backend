package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MembershipTypeRepository extends JpaSpecificationExecutor<MembershipType>,
        PagingAndSortingRepository<MembershipType, Long>, JpaRepository<MembershipType, Long> {
    Optional<MembershipType> findMembershipTypeByMembershipTypeName(String membershipTypeName);
}

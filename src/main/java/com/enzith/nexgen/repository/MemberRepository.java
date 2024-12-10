package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MemberRepository extends JpaSpecificationExecutor<Member>,
        PagingAndSortingRepository<Member, Long>, JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

}

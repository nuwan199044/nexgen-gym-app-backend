package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaSpecificationExecutor<UserProfile>,
        PagingAndSortingRepository<UserProfile, Long>, JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findUserByEmail(String email);
}

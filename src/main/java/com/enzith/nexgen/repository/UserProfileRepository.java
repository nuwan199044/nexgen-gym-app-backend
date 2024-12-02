package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findUserByEmail(String email);
}

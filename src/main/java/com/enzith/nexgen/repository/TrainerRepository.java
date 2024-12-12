package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaSpecificationExecutor<Trainer>,
        PagingAndSortingRepository<Trainer, Long>, JpaRepository<Trainer, Long> {
    Optional<Trainer> findByPhoneNo1(String phoneNo1);
}

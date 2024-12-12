package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.TrainerPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainerPackageRepository extends JpaSpecificationExecutor<TrainerPackage>,
        PagingAndSortingRepository<TrainerPackage, Long>, JpaRepository<TrainerPackage, Long> {
}

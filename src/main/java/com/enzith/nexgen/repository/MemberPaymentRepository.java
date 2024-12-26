package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.MemberPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberPaymentRepository extends JpaSpecificationExecutor<MemberPayment>,
        PagingAndSortingRepository<MemberPayment, Long>, JpaRepository<MemberPayment, Long> {

}

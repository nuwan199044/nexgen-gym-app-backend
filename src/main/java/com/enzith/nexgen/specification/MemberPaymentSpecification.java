package com.enzith.nexgen.specification;

import com.enzith.nexgen.criteria.MemberPaymentCriteria;
import com.enzith.nexgen.entity.Member;
import com.enzith.nexgen.entity.MemberPayment;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MemberPaymentSpecification implements Specification<MemberPayment> {

    private final MemberPaymentCriteria criteria;

    @Override
    public Predicate toPredicate(Root<MemberPayment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        query.distinct(true);
        List<Predicate> andPredicates = new ArrayList<>();

        if(StringUtils.isNotBlank(criteria.getFirstName())) {
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("member").get("firstName"), "%"+criteria.getFirstName()+"%");
            andPredicates.add(firstNamePredicate);
        }
        if(StringUtils.isNotBlank(criteria.getPhoneNo())) {
            Predicate phoneNoPredicate = criteriaBuilder.like(root.get("member").get("phoneNo"), "%"+criteria.getPhoneNo()+"%");
            andPredicates.add(phoneNoPredicate);
        }
        if (andPredicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.or(andPredicates.toArray(new Predicate[0]));
    }
}

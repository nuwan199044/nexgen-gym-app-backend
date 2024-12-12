package com.enzith.nexgen.specification;

import com.enzith.nexgen.criteria.MemberCriteria;
import com.enzith.nexgen.entity.Member;
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
public class MemberSpecification implements Specification<Member> {

    private final MemberCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        query.distinct(true);
        List<Predicate> andPredicates = new ArrayList<>();

        if(StringUtils.isNotBlank(criteria.getFirstName())) {
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%"+criteria.getFirstName()+"%");
            andPredicates.add(firstNamePredicate);
        }
        if(StringUtils.isNotBlank(criteria.getPhoneNo())) {
            Predicate phoneNoPredicate = criteriaBuilder.like(root.get("phoneNo"), "%"+criteria.getPhoneNo()+"%");
            andPredicates.add(phoneNoPredicate);
        }
        if (andPredicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.or(andPredicates.toArray(new Predicate[0]));
    }
}

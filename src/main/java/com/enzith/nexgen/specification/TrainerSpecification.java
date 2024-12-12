package com.enzith.nexgen.specification;

import com.enzith.nexgen.criteria.TrainerCriteria;
import com.enzith.nexgen.entity.Trainer;
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
public class TrainerSpecification implements Specification<Trainer> {

    private final TrainerCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Trainer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        query.distinct(true);
        List<Predicate> andPredicates = new ArrayList<>();

        if(StringUtils.isNotBlank(criteria.getFirstName())) {
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%"+criteria.getFirstName()+"%");
            andPredicates.add(firstNamePredicate);
        }
        if(StringUtils.isNotBlank(criteria.getPhoneNo1())) {
            Predicate phoneNo1Predicate = criteriaBuilder.like(root.get("phoneNo1"), "%"+criteria.getPhoneNo1()+"%");
            andPredicates.add(phoneNo1Predicate);
        }
        if (andPredicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.or(andPredicates.toArray(new Predicate[0]));
    }
}

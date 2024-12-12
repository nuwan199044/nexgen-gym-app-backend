package com.enzith.nexgen.specification;

import com.enzith.nexgen.criteria.TrainerPackageCriteria;
import com.enzith.nexgen.entity.TrainerPackage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TrainerPackageSpecification implements Specification<TrainerPackage> {

    private final TrainerPackageCriteria criteria;

    @Override
    public Predicate toPredicate(Root<TrainerPackage> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        query.distinct(true);
        List<Predicate> andPredicates = new ArrayList<>();

        if(!ObjectUtils.isEmpty(criteria.getTrainerId())) {
            Predicate trainerIdPredicate = criteriaBuilder.equal(root.get("trainer"), criteria.getTrainerId());
            andPredicates.add(trainerIdPredicate);
        }
        if (andPredicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.or(andPredicates.toArray(new Predicate[0]));
    }
}

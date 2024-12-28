package com.enzith.nexgen.specification;

import com.enzith.nexgen.criteria.NotificationCriteria;
import com.enzith.nexgen.entity.Notification;
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
public class NotificationSpecification implements Specification<Notification> {

    private final NotificationCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        query.distinct(true);
        List<Predicate> andPredicates = new ArrayList<>();

        if(StringUtils.isNotBlank(criteria.getStatus())) {
            Predicate statuePredicate = criteriaBuilder.equal(root.get("status"), criteria.getStatus());
            andPredicates.add(statuePredicate);
        }
        if (andPredicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.or(andPredicates.toArray(new Predicate[0]));
    }
}

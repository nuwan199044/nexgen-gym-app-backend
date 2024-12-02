package com.enzith.nexgen.utility;

import com.enzith.nexgen.criteria.PaginationCriteria;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static <T> Map<String, Object> convertToPagination(Page<T> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("pageItems", page.getContent());
        response.put("itemsPerPage", page.getPageable().getPageSize());
        return response;
    }

    public static Pageable getPage(PaginationCriteria paginationCriteria) {
        return PageRequest.of(paginationCriteria.getCurrentPage(), paginationCriteria.getPageSize());
    }
}

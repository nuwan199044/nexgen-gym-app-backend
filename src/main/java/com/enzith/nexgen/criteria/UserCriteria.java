package com.enzith.nexgen.criteria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserCriteria {
    private String firstName;
    private String email;
}

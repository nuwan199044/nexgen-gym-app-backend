package com.enzith.nexgen.criteria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberPaymentCriteria {
    private String firstName;
    private String phoneNo;
}

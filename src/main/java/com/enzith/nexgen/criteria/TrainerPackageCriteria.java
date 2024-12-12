package com.enzith.nexgen.criteria;

import com.enzith.nexgen.entity.Trainer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TrainerPackageCriteria {
    private Trainer trainerId;
}

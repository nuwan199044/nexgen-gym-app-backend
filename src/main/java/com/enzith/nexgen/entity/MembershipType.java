package com.enzith.nexgen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "membership_types")
public class MembershipType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_type_id")
    private Long membershipTypeId;

    @Column(name = "membership_type_name", length = 45)
    private String membershipTypeName;

    @Column(name = "is_installments_allowed")
    private Boolean isInstallmentsAllowed;

    @Column(name = "membership_fee")
    private Double membershipFee;

    @Column(name = "duration_in_days")
    private Integer durationInDays;

}

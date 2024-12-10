package com.enzith.nexgen.entity;

import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "installments")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "installment_id")
    private Long installmentId;

    @Column(name = "installment_amount")
    private Double installmentAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "installment_no")
    private Integer installmentNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 7)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 7)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "member_membership_id", referencedColumnName = "member_membership_id")
    private MemberMembership memberMembership;

}

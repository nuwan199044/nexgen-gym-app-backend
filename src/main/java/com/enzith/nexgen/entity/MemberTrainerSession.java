package com.enzith.nexgen.entity;

import com.enzith.nexgen.enums.PaymentStatus;
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
@Table(name = "member_trainer_sessions")
public class MemberTrainerSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_trainer_session_id")
    private Long memberTrainerSessionId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "package_id")
    private TrainerPackage trainerPackage;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "remaining_sessions")
    private Integer remainingSessions;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "net_amount")
    private Double netAmount;

    @Column(name = "discount")
    private Double discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 6)
    private PaymentStatus paymentStatus;

}

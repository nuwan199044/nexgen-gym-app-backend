package com.enzith.nexgen.entity;

import com.enzith.nexgen.enums.PaymentMode;
import com.enzith.nexgen.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_payments")
public class MemberPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_no", length = 45)
    private String paymentNo;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 20)
    private PaymentType paymentType;

    @Column(name = "payment_amount", length = 45)
    private Double paymentAmount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "installment_id", referencedColumnName = "installment_id")
    private Installment installment;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", length = 12, nullable = false)
    private PaymentMode paymentMode;

    @ManyToOne
    @JoinColumn(name = "member_membership_id", referencedColumnName = "member_membership_id")
    private MemberMembership memberMembership;

    @ManyToOne
    @JoinColumn(name = "member_trainer_session_id", referencedColumnName = "member_trainer_session_id")
    private MemberTrainerSession memberTrainerSession;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 45)
    private String createdBy;

    @PrePersist
    private void generatePaymentNo() {
        if (paymentNo == null) {
            LocalDateTime now = LocalDateTime.now(); // Capture the current date and time
            String year = String.valueOf(now.getYear());
            String month = String.format("%02d", now.getMonthValue()); // Ensuring two digits for the month
            String day = String.format("%02d", now.getDayOfMonth()); // Ensuring two digits for the day
            String hour = String.format("%02d", now.getHour()); // Ensuring two digits for the hour
            String minute = String.format("%02d", now.getMinute()); // Ensuring two digits for the minute
            String second = String.format("%02d", now.getSecond()); // Ensuring two digits for the second

            // Format membership number as YEARMMDDHHMMSS-memberId
            paymentNo = String.format("INV-%s%s%s%s%s%s",
                    year, month, day, hour, minute, second); // Using memberId as part of the number if available
        }
    }

}

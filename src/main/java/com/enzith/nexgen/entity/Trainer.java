package com.enzith.nexgen.entity;

import com.enzith.nexgen.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;

    @Column(name = "trainer_no", length = 45)
    private String trainerNo;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_no_1", length = 45)
    private String phoneNo1;

    @Column(name = "phone_no_2", length = 45)
    private String phoneNo2;

    @Column(name = "permanent_address", length = 150)
    private String permanentAddress;

    @Column(name = "temporary_address", length = 150)
    private String temporaryAddress;

    @Column(name = "photo", length = 150)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 8)
    private Status status;

    @Column(name = "created_at", length = 45)
    private String createdAt;

    @Column(name = "created_by", length = 45)
    private String createdBy;

    @Column(name = "is_basic_pay")
    private Boolean isBasicPay;

    @PrePersist
    private void generateTrainerNo() {
        if (trainerNo == null) {
            LocalDateTime now = LocalDateTime.now(); // Capture the current date and time
            String year = String.valueOf(now.getYear());
            String month = String.format("%02d", now.getMonthValue()); // Ensuring two digits for the month
            String day = String.format("%02d", now.getDayOfMonth()); // Ensuring two digits for the day
            String hour = String.format("%02d", now.getHour()); // Ensuring two digits for the hour
            String minute = String.format("%02d", now.getMinute()); // Ensuring two digits for the minute
            String second = String.format("%02d", now.getSecond()); // Ensuring two digits for the second

            // Format membership number as YEARMMDDHHMMSS-memberId
            trainerNo = String.format("TRA-%s%s%s%s%s%s",
                    year, month, day, hour, minute, second); // Using memberId as part of the number if available
        }
    }

}

package com.enzith.nexgen.entity;

import com.enzith.nexgen.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "membership_no", length = 45)
    private String membershipNo;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone_no", length = 45)
    private String phoneNo;

    @Column(name = "nic", length = 45)
    private String nic;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "email", length = 45)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 8)
    private Status status;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 45)
    private String createdBy;

    @OneToMany(mappedBy = "member")
    @Where(clause = "status = 'ACTIVE'")
    List<MemberMembership> memberMemberships;

    @OneToMany(mappedBy = "member")
    @Where(clause = "status = 'ACTIVE'")
    List<Installment> installments;

    @PrePersist
    private void generateMembershipNo() {
        if (membershipNo == null) {
            LocalDateTime now = LocalDateTime.now(); // Capture the current date and time
            String year = String.valueOf(now.getYear());
            String month = String.format("%02d", now.getMonthValue()); // Ensuring two digits for the month
            String day = String.format("%02d", now.getDayOfMonth()); // Ensuring two digits for the day
            String hour = String.format("%02d", now.getHour()); // Ensuring two digits for the hour
            String minute = String.format("%02d", now.getMinute()); // Ensuring two digits for the minute
            String second = String.format("%02d", now.getSecond()); // Ensuring two digits for the second

            // Format membership number as YEARMMDDHHMMSS-memberId
            membershipNo = String.format("MEM-%s%s%s%s%s%s",
                    year, month, day, hour, minute, second); // Using memberId as part of the number if available
        }
    }

}

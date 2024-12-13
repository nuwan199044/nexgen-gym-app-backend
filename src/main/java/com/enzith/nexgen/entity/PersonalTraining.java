package com.enzith.nexgen.entity;

import com.enzith.nexgen.enums.CompleteStatus;
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

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personal_trainings")
public class PersonalTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_training_id")
    private Long personalTrainingId;

    @Column(name = "session_no")
    private Integer sessionNo;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "package_id", nullable = false)
    private TrainerPackage trainerPackage;

    @ManyToOne
    @JoinColumn(name = "member_trainer_session_id", referencedColumnName = "member_trainer_session_id", nullable = false)
    private MemberTrainerSession memberTrainerSession;

    @Column(name = "from_time")
    private LocalDateTime fromTime;

    @Column(name = "to_time")
    private LocalDateTime toTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 8)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "complete_status", length = 8)
    private CompleteStatus completeStatus;

}

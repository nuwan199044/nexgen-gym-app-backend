package com.enzith.nexgen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trainer_packages")
public class TrainerPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long packageId;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "trainer_id")
    private Trainer trainer;

    @Column(name = "session_count")
    private Integer sessionCount;

    @Column(name = "price")
    private Double price;

    @Column(name = "package_name", length = 45)
    private String packageName;

}

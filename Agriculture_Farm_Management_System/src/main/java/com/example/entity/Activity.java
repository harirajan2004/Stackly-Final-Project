package com.example.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.enums.ActivityType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "activity")
public class Activity extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    
    private LocalDate activityDate;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(
            mappedBy = "activity",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Expense> expenses = new ArrayList<>();
}
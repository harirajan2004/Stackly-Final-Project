package com.example.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.enums.CropStatus;

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
@Table(name = "crop")
public class Crop extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cropId;

    private String cropName;

    private LocalDate plantingDate;

    private LocalDate expectedHarvestDate;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @OneToMany(
            mappedBy = "crop",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Harvest> harvests = new ArrayList<>();
}
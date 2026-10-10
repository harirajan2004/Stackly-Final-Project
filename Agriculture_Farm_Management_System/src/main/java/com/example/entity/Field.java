package com.example.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "field")
public class Field extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    private String fieldName;

    private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @OneToMany(
            mappedBy = "field",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Crop> crops = new ArrayList<>();

    @OneToMany(
            mappedBy = "field",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Activity> activities = new ArrayList<>();
}
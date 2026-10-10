package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "farmer")
public class Farmer extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer farmerId;

    private String farmerName;

    private String email;

    private String phoneNumber;
    

    @OneToMany(
            mappedBy = "farmer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Farm> farms = new ArrayList<>();
}
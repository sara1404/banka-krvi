package com.isa.bloodbank.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BloodBank extends BaseEntity {
    @Column
    String name;
    @Column
    Address address;
    @Column
    String description;
    @Column
    double averageGrade;
}

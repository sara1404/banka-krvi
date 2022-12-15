package com.isa.bloodbank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class WorkingHours extends BaseEntity {
    @Column
    LocalDateTime startTime;
    @Column
    LocalDateTime endTime;
}

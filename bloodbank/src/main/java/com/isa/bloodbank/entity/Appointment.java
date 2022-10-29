package com.isa.bloodbank.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Appointment extends BaseEntity{
    @Column
    Long bloodBankId;
    @Column
    LocalDateTime startTime;
    @Column
    LocalDateTime endTime;
    @Column
    List<Long> medicalStaffIds;
    @Column
    boolean available;
    @Column
    AppointmentInfo appointmentInfo;
    //@Column
    //boolean finished;
}

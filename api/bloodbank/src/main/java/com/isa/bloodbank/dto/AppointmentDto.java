package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDto {
    @Id
    Long id;
    Long bloodBankId;
    @NotNull
    LocalDateTime startTime;
    @NotNull
    double duration;
    boolean available;
}

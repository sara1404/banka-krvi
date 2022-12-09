package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class AppointmentDto {
    @NotNull
    LocalDateTime startTime;
    @NotNull
    double duration;
}

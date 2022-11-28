package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDto {
    @NotNull
    LocalDateTime startTime;
    @NotNull
    double duration;
}

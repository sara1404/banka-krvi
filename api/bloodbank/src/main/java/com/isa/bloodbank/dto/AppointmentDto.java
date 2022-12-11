package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class AppointmentDto {
    @NotNull
    LocalDateTime startTime;
    @NotNull
    double duration;
    BloodBankDto bloodBank;
    @Id
    Long id;
}

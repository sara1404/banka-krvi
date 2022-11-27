package com.isa.bloodbank.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

public class WorkingHoursDto {
    LocalDateTime startTime;
    LocalDateTime endTime;
}

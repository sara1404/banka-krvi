package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.WorkingHoursDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.WorkingHours;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingHoursMapper {
	WorkingHours workingHoursDtoToWorkingHours(WorkingHoursDto workingHoursDto);
	WorkingHoursDto workingHoursToWorkingHoursDto(WorkingHours workingHours);
}

package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.entity.Appointment;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAppointmentMapper {
	List<Appointment> userAppointmentDtosToAppointment(List<UserAppointmentDto> appointments);
	List<Appointment> appointmentsToUserAppointmentDto(List<Appointment> appointments);
}

package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.entity.Appointment;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
	Appointment freeAppointmentDtoToFreeAppointment(FreeAppointmentDto appointment);

	FreeAppointmentDto appointmentToFreeAppointmentDto(Appointment appointment);

	List<FreeAppointmentDto> appointmentsToFreeAppointmentDtos(List<Appointment> appointments);

	List<Appointment> freeAppointmentDtosToAppointments(List<FreeAppointmentDto> appointments);

	List<Appointment> userAppointmentDtosToAppointment(List<UserAppointmentDto> appointments);

	List<UserAppointmentDto> appointmentsToUserAppointmentDto(List<Appointment> appointments);

	Appointment appointmentDtoToAppointment(AppointmentDto appointment);

	AppointmentDto appointmentToAppointmentDto(Appointment appointment);

	List<AppointmentDto> appointmentsToAppointmentDtos(List<Appointment> ret);
}

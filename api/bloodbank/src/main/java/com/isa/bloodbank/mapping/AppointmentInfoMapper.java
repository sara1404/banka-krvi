package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.AppointmentInfoDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.AppointmentInfo;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentInfoMapper {
	AppointmentInfo appointmentDtoToAppointment(AppointmentInfoDto appointment);
	AppointmentInfoDto appointmentToAppointmentInfoDto(Appointment appointment);
}

package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.EquipmentDto;
import com.isa.bloodbank.service.EquipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
	@Autowired
	private EquipmentService equipmentService;

	@PostMapping("/used")
	public Boolean removeEquipment(@RequestBody EquipmentDto equipmentDto){
		return equipmentService.removeEquipment(equipmentDto);
	}
}

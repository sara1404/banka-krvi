package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.LocationDto;
import com.isa.bloodbank.dto.MessageDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.security.JwtUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/start")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<?> startDelivery(@RequestBody final LocationDto locationDto, @RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		final List<Double> endLocation = new ArrayList<>();
		endLocation.add(loggedUser.getBloodBank().getAddress().getLongitude());
		endLocation.add(loggedUser.getBloodBank().getAddress().getLatitude());
		locationDto.setStartLocation(endLocation);
		System.out.println(locationDto.getStartLocation());
		System.out.println(locationDto.getEndLocation());
		if (sendRequestToSimulator(locationDto)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	private boolean sendRequestToSimulator(final LocationDto loc) {
		final RestTemplate restTemplate = new RestTemplate();
		final String url = "http://localhost:8081/start";
		final ResponseEntity<Object> response = restTemplate.postForEntity(url, loc, Object.class);
		final HttpStatus status = response.getStatusCode();
		if (status.is2xxSuccessful()) {
			return true;
		}
		return false;
	}

	@RabbitListener(queues = "coordinates")
	public void consumeMessageFromQueue(final MessageDto message) {
		System.out.println("Message recieved from queue: " + message.getMessage());
		simpMessagingTemplate.convertAndSend("/socket-publisher", message.getMessage());
	}
}

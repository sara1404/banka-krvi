package com.isa.bloodbank.controller;

import com.isa.bloodbank.socket.LocationMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public String send(final LocationMessage message) throws Exception {
		for (int i = 0; i < 13; i++) {
			
			Thread.sleep(1000);
		}
		final String time = new SimpleDateFormat("HH:mm").format(new Date());
		return message.getFrom() + " " + message.getMessage() + " " + time;
	}
}

package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.MonthlyTransfer;
import com.isa.bloodbank.service.HospitalService;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HospitalController {
	String api = "[880DA5360C3DE5960B9168CEFDE5C803]";

	@Autowired
	private HospitalService hospitalService;

	@PostMapping(value = "/monthlyTransfer", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String monthlyTransfer() throws IOException, TimeoutException {
		System.out.println("Monhtly Transfer Service worked.");
		final MonthlyTransfer mt = hospitalService.monthlyTransfer();
		if (mt != null) {
			final var factory = new ConnectionFactory();
			factory.setHost("localhost");

			final var connection = factory.newConnection();
			final var channel = connection.createChannel();

			channel.queueDeclare("monthly", false, false, false, null);
			final var message =
				"A_PLUS:" + mt.APlus + "-" + "A_MINUS:" + mt.AMinus + "-" + "B_PLUS:" + mt.BPlus + "-" + "B_MINUS:" + mt.BMinus + "-" + "AB_PLUS:" + mt.ABPlus +
					"-" + "AB_MINUS:" + mt.ABMinus + "-" + "O_PLUS:" + mt.OPlus + "-" + "O_MINUS:" + mt.OMinus;
			final byte[] body = message.getBytes(StandardCharsets.UTF_8);

			channel.basicPublish("", "monthly", null, body);
			System.out.println(mt);
		}

		return "BLA";
	}
}

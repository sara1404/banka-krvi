package com.isa.bloodbank.thread;

import com.isa.bloodbank.controller.HospitalController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewThread extends Thread {
	@Autowired
	private HospitalController hospitalController;

	public void run() {
		final long startTime = System.currentTimeMillis();
		while (true) {
			System.out.println("radi");
			try {
				hospitalController.monthlyTransfer();
				//Wait for one sec so it doesn't print too fast
				Thread.sleep(1000); //86 400 000
			} catch (final IOException | InterruptedException | TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}

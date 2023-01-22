package com.isa.bloodbank.thread;

import com.isa.bloodbank.controller.HospitalController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	@Autowired
	private HospitalController hospitalController;

	@Override
	public void run(final String... args) throws Exception {
		//hospitalController.monthlyTransfer();
		final NewThread thread = new NewThread(hospitalController);
		thread.start();
		System.out.println("radi command");
	}
}

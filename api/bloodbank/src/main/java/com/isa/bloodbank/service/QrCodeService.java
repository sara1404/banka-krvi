package com.isa.bloodbank.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.isa.bloodbank.entity.Appointment;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import org.springframework.stereotype.Service;

@Service
public class QrCodeService {

	public void generateAppointmentQrCode(final Appointment appointment) {
		final QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			final String text = "id: " + appointment.getId() + ", user: " + appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName()
				+ ", blood bank: " + appointment.getBloodBank().getName() + ", start time: " + appointment.getStartTime() + ", duration: " +
				appointment.getDuration() + "min";
			final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
			final ClassLoader classLoader = getClass().getClassLoader();
			final File file = new File(classLoader.getResource(".").getFile() + "/" + appointment.getId() + ".png");
			//FileSystems.getDefault().getPath("../qrcodes/" + appointment.getId() + ".png")
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(appointment.getId() + ".png"));
			System.out.println(file.toPath());
		} catch (final WriterException | IOException e) {
			e.printStackTrace();
		}
	}
}

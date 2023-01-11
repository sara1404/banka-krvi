package com.isa.bloodbank.mailer;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.isa.bloodbank.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;

@Component
public class MailService {
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private MailTemplateService templateService;

	public boolean registerUserEmail(final String to) {
		try {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			final String emailText = templateService.registerUserTemplate().replace("EMAIL_GOES_HERE", to);
			helper.setFrom("isa.banka.krvi@gmail.com");
			helper.setTo(to);
			helper.setSubject("Confirm user registration - ISA blood bank");
			helper.setText(emailText, true);
			emailSender.send(mimeMessage);
			return true;
		} catch (final MessagingException e) {
			return false;
		} catch (final Exception e) {
			return false;
		}
	}

	public boolean sendEmailWithQrCode(final String to, Appointment appointment){
		try {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			//final String emailText = templateService.appointmentConfirmationTemplate().replace("EMAIL_GOES_HERE", to);
			helper.setFrom("isa.banka.krvi@gmail.com");
			helper.setTo(to);
			helper.setSubject("Appointment Confirmation - ISA blood bank");
			//helper.setText(emailText, true);
			MimeBodyPart msgPart = new MimeBodyPart();
			msgPart.setContent("Thank you for your willigness to donate blood! :)", "text/html");
			MimeBodyPart attachPart = new MimeBodyPart();
			attachPart.attachFile("../qrcodes/" + appointment.getId() + ".png");
			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(msgPart);
			multiPart.addBodyPart(attachPart);
			mimeMessage.setContent(multiPart);
			emailSender.send(mimeMessage);
			return true;
		} catch (final MessagingException e) {
			return false;
		} catch (final Exception e) {
			return false;
		}
	}
}

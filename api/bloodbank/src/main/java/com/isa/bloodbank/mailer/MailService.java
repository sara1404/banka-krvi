package com.isa.bloodbank.mailer;

import com.isa.bloodbank.entity.Appointment;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

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

	public boolean sendEmailWithQrCode(final String to, final Appointment appointment) {
		try {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			//final String emailText = templateService.appointmentConfirmationTemplate().replace("EMAIL_GOES_HERE", to);
			helper.setFrom("isa.banka.krvi@gmail.com");
			helper.setTo(to);
			helper.setSubject("Appointment Confirmation - ISA blood bank");
			//helper.setText(emailText, true);
			final MimeBodyPart msgPart = new MimeBodyPart();
			msgPart.setContent("Thank you for your willigness to donate blood! :)", "text/html");
			final MimeBodyPart attachPart = new MimeBodyPart();
			final ClassLoader classLoader = getClass().getClassLoader();
			final File file = new File(classLoader.getResource(".").getFile() + "/" + appointment.getId() + ".png");
			//"../qrcodes/" +appointment.getId() + ".png"
			attachPart.attachFile(file);
			final MimeMultipart multiPart = new MimeMultipart();
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

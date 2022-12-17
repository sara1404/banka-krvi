package com.isa.bloodbank.mailer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
}

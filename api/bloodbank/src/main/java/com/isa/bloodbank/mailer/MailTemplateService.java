package com.isa.bloodbank.mailer;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MailTemplateService {
	@Value("classpath:register_user_template.html")
	Resource registerUserTemplateResourceFile;

	private String resourceFileToString(final Resource resource) throws IOException {
		return new String(Files.readAllBytes(resource.getFile().toPath()));
	}

	public String registerUserTemplate() {
		try {
			return resourceFileToString(registerUserTemplateResourceFile);
		} catch (final IOException e) {
			return null;
		}
	}
}

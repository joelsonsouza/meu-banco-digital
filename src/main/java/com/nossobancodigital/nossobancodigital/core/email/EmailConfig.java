package com.nossobancodigital.nossobancodigital.core.email;

import com.nossobancodigital.nossobancodigital.domain.service.EnvioEmailService;
import com.nossobancodigital.nossobancodigital.infrastructure.service.FakeEnvioEmailService;
import com.nossobancodigital.nossobancodigital.infrastructure.service.SandboxEnvioEmailService;
import com.nossobancodigital.nossobancodigital.infrastructure.service.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public EnvioEmailService envioEmailService() {
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEnvioEmailService();
			case SMTP:
				return new SmtpEnvioEmailService();
			case SANDBOX:
				return new SandboxEnvioEmailService();
			default:
				return null;
		}
	}

}
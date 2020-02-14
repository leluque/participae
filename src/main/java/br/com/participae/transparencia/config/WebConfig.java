package br.com.participae.transparencia.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@ComponentScan
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new SpringSecurityDialect());

		return templateEngine;
	}

	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/erro/404"),
				new ErrorPage(HttpStatus.FORBIDDEN, "/erro/403"),
				new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/erro/500")));
	}

}

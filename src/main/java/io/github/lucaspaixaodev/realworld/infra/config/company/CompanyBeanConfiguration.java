package io.github.lucaspaixaodev.realworld.infra.config.company;

import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.company.service.CreateCompanyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyBeanConfiguration {

	@Bean
	public CreateCompanyService createCompanyService(CompanyRepository companyRepository) {
		return new CreateCompanyService(companyRepository);
	}
}

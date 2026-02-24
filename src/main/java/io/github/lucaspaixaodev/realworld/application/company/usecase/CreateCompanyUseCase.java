package io.github.lucaspaixaodev.realworld.application.company.usecase;

import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.github.lucaspaixaodev.realworld.domain.company.service.CreateCompanyService;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyUseCase {
	private final CreateCompanyService createCompanyService;

	public CreateCompanyUseCase(CreateCompanyService createCompanyService) {
		this.createCompanyService = createCompanyService;
	}

	public CreateCompanyOutput execute(CreateCompanyInput input) {
		return createCompanyService.execute(input);
	}
}

package io.github.lucaspaixaodev.realworld.infra.input.rest.company;

import io.github.lucaspaixaodev.realworld.application.company.usecase.CreateCompanyUseCase;
import io.github.lucaspaixaodev.realworld.infra.input.rest.company.api.CreateCompanyApi;
import io.github.lucaspaixaodev.realworld.infra.input.rest.company.request.CreateCompanyRequest;
import io.github.lucaspaixaodev.realworld.infra.input.rest.company.response.CreateCompanyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCompanyController implements CreateCompanyApi {
	private static final Logger log = LoggerFactory.getLogger(CreateCompanyController.class);

	private final CreateCompanyUseCase createCompanyUseCase;

	public CreateCompanyController(CreateCompanyUseCase createCompanyUseCase) {
		this.createCompanyUseCase = createCompanyUseCase;
	}

	@Override
	public ResponseEntity<CreateCompanyResponse> createCompany(CreateCompanyRequest request) {
		log.info("Received create company request. request={}", request);

		var output = createCompanyUseCase.execute(request.toInput());
		var response = CreateCompanyResponse.from(output);

		log.info("Create company request processed successfully. response={}", response);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}

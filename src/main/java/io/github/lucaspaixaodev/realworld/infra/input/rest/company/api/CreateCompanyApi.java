package io.github.lucaspaixaodev.realworld.infra.input.rest.company.api;

import io.github.lucaspaixaodev.realworld.infra.input.rest.company.request.CreateCompanyRequest;
import io.github.lucaspaixaodev.realworld.infra.input.rest.company.response.CreateCompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Companies", description = "Company management endpoints")
public interface CreateCompanyApi {

	@PostMapping("/companies")
	@Operation(summary = "Create company", description = "Creates a new company")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			description = "Company payload",
			content = @Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = CreateCompanyRequest.class)))
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Company created",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = CreateCompanyResponse.class))),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid request or validation error",
					content = @Content(
							mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class))),
			@ApiResponse(
					responseCode = "422",
					description = "Business rule error",
					content = @Content(
							mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
							schema = @Schema(implementation = ProblemDetail.class)))})
	ResponseEntity<CreateCompanyResponse> createCompany(@Valid @RequestBody CreateCompanyRequest request);
}

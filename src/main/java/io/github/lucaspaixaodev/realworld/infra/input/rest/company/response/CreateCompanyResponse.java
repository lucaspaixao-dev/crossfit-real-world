package io.github.lucaspaixaodev.realworld.infra.input.rest.company.response;

import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateCompanyResponse", description = "Result of company creation")
public record CreateCompanyResponse(
        @Schema(description = "Created company identifier",
                example = "b3cc4cfb-7e4f-4c3f-a93d-7da3c55dd1d4") String id) {
    public static CreateCompanyResponse from(CreateCompanyOutput output) {
        return new CreateCompanyResponse(output.id());
    }
}

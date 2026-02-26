package io.github.lucaspaixaodev.realworld.infra.input.rest.company.request;

import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.infra.input.rest.shared.request.AddressRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CreateCompanyRequest", description = "Payload to create a company")
public record CreateCompanyRequest(
        @Schema(description = "Legal company name", example = "CrossFit Real World LTDA") @NotBlank String legalName,
        @Schema(description = "Trade name", example = "CrossFit Real World") @NotBlank String tradeName,
        @Schema(description = "Company tax identifier (digits only)",
                example = "12345678000199") @NotBlank String taxId,
        @Schema(description = "Company type", example = "BOX") @NotBlank String companyType,
        @Schema(description = "Company address") @NotNull @Valid AddressRequest address,
        @Schema(description = "Company email", example = "contato@crossfitrealworld.com") @NotBlank @Email String email,
        @Schema(description = "Phone number (digits only)", example = "1133334444") @NotBlank String phone,
        @Schema(description = "Cellphone number (digits only)", example = "11999998888") @NotBlank String cellphone) {
    public CreateCompanyInput toInput() {
        return new CreateCompanyInput(legalName, tradeName, taxId, companyType, address.toInput(), email, phone,
                cellphone);
    }
}

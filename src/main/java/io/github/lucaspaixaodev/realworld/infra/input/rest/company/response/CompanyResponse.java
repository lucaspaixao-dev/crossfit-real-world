package io.github.lucaspaixaodev.realworld.infra.input.rest.company.response;

import io.github.lucaspaixaodev.realworld.domain.company.output.GetCompanyByIdOutput;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CompanyResponse", description = "Company details")
public record CompanyResponse(String id, String legalName, String tradeName, String taxId, String companyType,
        AddressResponse address, String email, String phone, String cellphone, boolean active) {

    public static CompanyResponse from(GetCompanyByIdOutput output) {
        return new CompanyResponse(output.id(), output.legalName(), output.tradeName(), output.taxId(),
                output.companyType(), AddressResponse.from(output.address()), output.email(), output.phone(),
                output.cellphone(), output.active());
    }

    @Schema(name = "CompanyAddressREsponse", description = "Company address details")
    public record AddressResponse(String street, String number, String complement, String neighborhood, String city,
            String state, String postalCode, String country) {
        private static AddressResponse from(GetCompanyByIdOutput.AddressOutput output) {
            return new AddressResponse(output.street(), output.number(), output.complement(), output.neighborhood(),
                    output.city(), output.state(), output.postalCode(), output.country());
        }
    }
}

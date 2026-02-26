package io.github.lucaspaixaodev.realworld.domain.company.output;

public record GetCompanyByIdOutput(String id, String legalName, String tradeName, String taxId, String companyType,
        AddressOutput address, String email, String phone, String cellphone, boolean active) {

    public record AddressOutput(String street, String number, String complement, String neighborhood, String city,
            String state, String postalCode, String country) {
    }
}

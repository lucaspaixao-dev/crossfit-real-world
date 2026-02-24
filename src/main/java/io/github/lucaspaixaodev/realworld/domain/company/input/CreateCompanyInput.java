package io.github.lucaspaixaodev.realworld.domain.company.input;

import io.github.lucaspaixaodev.realworld.domain.shared.input.AddressInput;

public record CreateCompanyInput(String legalName, String tradeName, String taxId, String companyType,
		AddressInput address, String email, String phone, String cellphone) {
}

package io.github.lucaspaixaodev.realworld.domain.company.service;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.CompanyType;
import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.shared.*;
import io.github.lucaspaixaodev.realworld.domain.shared.input.AddressInput;

public class CreateCompanyService {
    private final CompanyRepository companyRepository;

    public CreateCompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CreateCompanyOutput execute(CreateCompanyInput input) {
        Company company = Company.create(input.legalName(), input.tradeName(), input.taxId(),
                CompanyType.valueOf(input.companyType().toUpperCase()), toAddress(input.address()),
                new Email(input.email()), new Phone(input.phone()), new Cellphone(input.cellphone()));

        companyRepository.save(company);

        return new CreateCompanyOutput(company.getId().toString());
    }

    private Address toAddress(AddressInput input) {
        return new Address(input.street(), input.number(), input.complement(), input.neighborhood(), input.city(),
                State.valueOf(input.state().trim().toUpperCase()), input.postalCode(), input.country());
    }
}

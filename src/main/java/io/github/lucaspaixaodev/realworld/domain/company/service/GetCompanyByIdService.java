package io.github.lucaspaixaodev.realworld.domain.company.service;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.output.GetCompanyByIdOutput;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.exception.NotFoundException;
import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import io.github.lucaspaixaodev.realworld.domain.shared.Address;
import io.github.lucaspaixaodev.realworld.domain.shared.ID;

public class GetCompanyByIdService {
    private final CompanyRepository companyRepository;

    public GetCompanyByIdService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public GetCompanyByIdOutput execute(String id) {
        ID companyId = parseId(id);
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("company not found"));

        return toOutput(company);
    }

    private ID parseId(String id) {
        try {
            return ID.from(id);
        } catch (IllegalArgumentException exception) {
            throw new ValidationException("id must be a valid UUID");
        }
    }

    private GetCompanyByIdOutput toOutput(Company company) {
        return new GetCompanyByIdOutput(company.getId().toString(), company.getLegalName(), company.getTradeName(),
                company.getTaxId(), company.getCompanyType().name(), toAddressOutput(company.getAddress()),
                company.getEmail().value(), company.getPhone() != null ? company.getPhone().value() : null,
                company.getCellphone().value(), company.isActive());
    }

    private GetCompanyByIdOutput.AddressOutput toAddressOutput(Address address) {
        return new GetCompanyByIdOutput.AddressOutput(address.street(), address.number(), address.complement(),
                address.neighborhood(), address.city(), address.state().name(), address.postalCode(),
                address.country());
    }
}

package io.github.lucaspaixaodev.realworld.domain.company.repository;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.shared.ID;

import java.util.Optional;

public interface CompanyRepository {

    void save(Company company);

    Optional<Company> findById(ID id);
}

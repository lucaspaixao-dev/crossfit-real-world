package io.github.lucaspaixaodev.realworld.infra.output.database.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCompanyJpaRepository extends JpaRepository<CompanyEntity, UUID> {
    boolean existsByTaxId(String taxId);

    boolean existsByLegalName(String legalName);

    boolean existsByEmail(String email);
}

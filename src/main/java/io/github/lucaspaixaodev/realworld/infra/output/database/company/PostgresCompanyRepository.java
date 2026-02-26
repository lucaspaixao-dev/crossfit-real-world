package io.github.lucaspaixaodev.realworld.infra.output.database.company;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.shared.Address;
import io.github.lucaspaixaodev.realworld.domain.shared.Cellphone;
import io.github.lucaspaixaodev.realworld.domain.shared.Email;
import io.github.lucaspaixaodev.realworld.domain.shared.ID;
import io.github.lucaspaixaodev.realworld.domain.shared.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class PostgresCompanyRepository implements CompanyRepository {
    private static final Logger log = LoggerFactory.getLogger(PostgresCompanyRepository.class);

    private final SpringDataCompanyJpaRepository companyJpaRepository;

    public PostgresCompanyRepository(SpringDataCompanyJpaRepository companyJpaRepository) {
        this.companyJpaRepository = companyJpaRepository;
    }

    @Override
    @Transactional
    public void save(Company company) {
        log.info("Saving company. id={}, taxId={}", company.getId(), company.getTaxId());

        companyJpaRepository.save(new CompanyEntity(company));

        log.info("Company saved successfully. id={}", company.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findById(ID id) {
        log.info("Finding company by id. id={}", id);

        return companyJpaRepository.findById(id.value()).map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTaxId(String taxId) {
        return companyJpaRepository.existsByTaxId(taxId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLegalName(String legalName) {
        return companyJpaRepository.existsByLegalName(legalName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return companyJpaRepository.existsByEmail(email);
    }

    private Company toDomain(CompanyEntity entity) {
        CompanyAddressEntity addressEntity = entity.getAddress();
        Address address = new Address(addressEntity.getStreet(), addressEntity.getNumber(),
                addressEntity.getComplement(),
                addressEntity.getNeighborhood(), addressEntity.getCity(), addressEntity.getState(),
                addressEntity.getPostalCode(), addressEntity.getCountry());

        return Company.restore(ID.from(entity.getId()), entity.getLegalName(), entity.getTradeName(), entity.getTaxId(),
                entity.getCompanyType(), address, new Email(entity.getEmail()),
                entity.getPhone() != null ? new Phone(entity.getPhone()) : null, new Cellphone(entity.getCellphone()),
                entity.isActive());
    }
}

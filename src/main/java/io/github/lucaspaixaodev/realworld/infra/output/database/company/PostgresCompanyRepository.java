package io.github.lucaspaixaodev.realworld.infra.output.database.company;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}

package io.github.lucaspaixaodev.realworld.infra.output.database.company;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.CompanyType;
import io.github.lucaspaixaodev.realworld.infra.output.database.shared.AddressEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "company")
public class CompanyEntity {
	@Id
	private UUID id;

	@Column(name = "legal_name", nullable = false)
	private String legalName;

	@Column(name = "trade_name", nullable = false)
	private String tradeName;

	@Column(name = "tax_id", nullable = false, length = 14, unique = true)
	private String taxId;

	@Enumerated(EnumType.STRING)
	@Column(name = "company_type", nullable = false, length = 30)
	private CompanyType companyType;

	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL, optional = false)
	private AddressEntity address;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "phone", nullable = true, length = 10)
	private String phone;

	@Column(name = "cellphone", nullable = false, length = 11)
	private String cellphone;

	@Column(name = "active", nullable = false)
	private boolean active;

	protected CompanyEntity() {
	}

	public CompanyEntity(Company company) {
		this.id = company.getId().value();
		this.legalName = company.getLegalName();
		this.tradeName = company.getTradeName();
		this.taxId = company.getTaxId();
		this.companyType = company.getCompanyType();
		this.email = company.getEmail().value();
		this.phone = company.getPhone().value();
		this.cellphone = company.getCellphone().value();
		this.active = company.isActive();

		attachAddress(new AddressEntity(company.getAddress()));
	}

	public UUID getId() {
		return id;
	}

	private void attachAddress(AddressEntity address) {
		this.address = address;
		if (address != null) {
			address.attachCompany(this);
		}
	}
}

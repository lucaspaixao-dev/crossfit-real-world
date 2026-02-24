package io.github.lucaspaixaodev.realworld.domain.company;

import io.github.lucaspaixaodev.realworld.domain.shared.*;

import static io.github.lucaspaixaodev.realworld.domain.validation.Validation.*;

public class Company {
	private static final int LEGAL_NAME_MIN_LENGTH = 6;
	private static final int TRADE_NAME_MIN_LENGTH = 3;
	private static final int TAX_ID_LENGTH = 14;

	private final ID id;
	private final String legalName;
	private final String tradeName;
	private final String taxId;
	private final CompanyType companyType;
	private final Address address;
	private final Email email;
	private final Phone phone;
	private final Cellphone cellphone;
	private final boolean active;

	private Company(ID id, String legalName, String tradeName, String taxId, CompanyType companyType, Address address,
			Email email, Phone phone, Cellphone cellphone, boolean active) {
		String normalizedLegalName = requireNotBlank(legalName, "legalName");
		String normalizedTradeName = requireNotBlank(tradeName, "tradeName");
		String normalizedTaxId = requireNotBlank(taxId, "taxId");

		this.id = id;
		this.legalName = requireMinLength(normalizedLegalName, "legalName", LEGAL_NAME_MIN_LENGTH);
		this.tradeName = requireMinLength(normalizedTradeName, "tradeName", TRADE_NAME_MIN_LENGTH);
		this.taxId = requireValidTaxId(requireExactLength(normalizedTaxId, "taxId", TAX_ID_LENGTH), "taxId");
		this.companyType = companyType;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.cellphone = cellphone;
		this.active = active;
	}

	public static Company create(String legalName, String tradeName, String taxId, CompanyType companyType,
			Address address, Email email, Phone phone, Cellphone cellphone) {
		return new Company(ID.random(), legalName, tradeName, taxId, companyType, address, email, phone, cellphone,
				true);
	}

	public ID getId() {
		return id;
	}

	public String getLegalName() {
		return legalName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public String getTaxId() {
		return taxId;
	}

	public CompanyType getCompanyType() {
		return companyType;
	}

	public Address getAddress() {
		return address;
	}

	public Email getEmail() {
		return email;
	}

	public Phone getPhone() {
		return phone;
	}

	public Cellphone getCellphone() {
		return cellphone;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Company company)) {
			return false;
		}
		return id.equals(company.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}

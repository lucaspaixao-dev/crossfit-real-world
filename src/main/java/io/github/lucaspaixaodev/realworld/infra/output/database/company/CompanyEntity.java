package io.github.lucaspaixaodev.realworld.infra.output.database.company;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.CompanyType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    private UUID id;

    @Column(name = "legal_name", nullable = false, unique = true)
    private String legalName;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "tax_id", nullable = false, length = 14, unique = true)
    private String taxId;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false, length = 30)
    private CompanyType companyType;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, optional = false)
    private CompanyAddressEntity address;

    @Column(name = "email", nullable = false, unique = true)
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

        attachAddress(new CompanyAddressEntity(company.getAddress()));
    }

    public UUID getId() {
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

    public CompanyAddressEntity getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public boolean isActive() {
        return active;
    }

    private void attachAddress(CompanyAddressEntity address) {
        this.address = address;
        if (address != null) {
            address.attachCompany(this);
        }
    }
}

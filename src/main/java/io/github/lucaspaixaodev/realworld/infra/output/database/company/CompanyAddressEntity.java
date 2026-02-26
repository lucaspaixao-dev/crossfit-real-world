package io.github.lucaspaixaodev.realworld.infra.output.database.company;

import io.github.lucaspaixaodev.realworld.domain.shared.Address;
import io.github.lucaspaixaodev.realworld.domain.shared.State;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "company_address")
public class CompanyAddressEntity {
    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private CompanyEntity company;

    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Column(name = "number", length = 50)
    private String number;

    @Column(name = "complement", length = 255)
    private String complement;

    @Column(name = "neighborhood", nullable = false, length = 255)
    private String neighborhood;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 2)
    private State state;

    @Column(name = "postal_code", nullable = false, length = 8)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    protected CompanyAddressEntity() {
    }

    public CompanyAddressEntity(String street, String number, String complement, String neighborhood, String city,
            State state, String postalCode, String country) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    public CompanyAddressEntity(Address address) {
        this(address.street(), address.number(), address.complement(), address.neighborhood(), address.city(),
                address.state(), address.postalCode(), address.country());
    }

    public void attachCompany(CompanyEntity company) {
        this.company = company;
        this.id = company != null ? company.getId() : null;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }
}

package io.github.lucaspaixaodev.realworld.domain.company;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import io.github.lucaspaixaodev.realworld.domain.shared.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void createShouldBuildActiveCompanyWithValidatedFields() {
        Address address = new Address("Rua A", "10", null, "Centro", "Sao Paulo", State.SP, "01001000", "Brasil");
        Email email = new Email("contato@empresa.com");
        Phone phone = new Phone("1133445566");
        Cellphone cellphone = new Cellphone("11987654321");

        Company company = Company.create("  Empresa Legal  ", "  Marca  ", " 11222333000181 ", CompanyType.LTDA,
                address, email, phone, cellphone);

        assertNotNull(company.getId());
        assertEquals("Empresa Legal", company.getLegalName());
        assertEquals("Marca", company.getTradeName());
        assertEquals("11222333000181", company.getTaxId());
        assertEquals(CompanyType.LTDA, company.getCompanyType());
        assertSame(address, company.getAddress());
        assertSame(email, company.getEmail());
        assertSame(phone, company.getPhone());
        assertSame(cellphone, company.getCellphone());
        assertTrue(company.isActive());
    }

    @Test
    void createShouldThrowWhenLegalNameIsTooShort() {
        ValidationException ex = assertThrows(ValidationException.class, () -> Company.create("abc", "Marca",
                "11222333000181", CompanyType.LTDA, validAddress(), validEmail(), validPhone(), validCellphone()));

        assertEquals("legalName must have at least 6 characters", ex.getMessage());
    }

    @Test
    void createShouldThrowWhenTradeNameIsTooShort() {
        ValidationException ex = assertThrows(ValidationException.class, () -> Company.create("Empresa", "AB",
                "11222333000181", CompanyType.LTDA, validAddress(), validEmail(), validPhone(), validCellphone()));

        assertEquals("tradeName must have at least 3 characters", ex.getMessage());
    }

    @Test
    void createShouldThrowWhenTaxIdIsInvalid() {
        ValidationException ex = assertThrows(ValidationException.class, () -> Company.create("Empresa", "Marca",
                "11222333000182", CompanyType.LTDA, validAddress(), validEmail(), validPhone(), validCellphone()));

        assertEquals("taxId must be a valid Tax id", ex.getMessage());
    }

    private static Address validAddress() {
        return new Address("Rua A", "10", null, "Centro", "Sao Paulo", State.SP, "01001000", "Brasil");
    }

    private static Email validEmail() {
        return new Email("contato@empresa.com");
    }

    private static Phone validPhone() {
        return new Phone("1133445566");
    }

    private static Cellphone validCellphone() {
        return new Cellphone("11987654321");
    }
}

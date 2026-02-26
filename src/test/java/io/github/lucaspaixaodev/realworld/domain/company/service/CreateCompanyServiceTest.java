package io.github.lucaspaixaodev.realworld.domain.company.service;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.CompanyType;
import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.exception.AlreadyExistsException;
import io.github.lucaspaixaodev.realworld.domain.shared.State;
import io.github.lucaspaixaodev.realworld.domain.shared.input.AddressInput;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCompanyServiceTest {

    @Test
    void executeShouldMapInputPersistCompanyAndReturnId() {
        CompanyRepository repository = mock(CompanyRepository.class);
        CreateCompanyService service = new CreateCompanyService(repository);
        CreateCompanyInput input = new CreateCompanyInput("Empresa Legal", "Marca", "11222333000181", "ltda",
                new AddressInput("Rua A", "10", "Sala 1", "Centro", "Sao Paulo", " sp ", "01001000", "Brasil"),
                "contato@empresa.com", "1133445566", "11987654321");

        CreateCompanyOutput output = service.execute(input);

        ArgumentCaptor<Company> companyCaptor = ArgumentCaptor.forClass(Company.class);
        verify(repository).save(companyCaptor.capture());

        Company savedCompany = companyCaptor.getValue();
        assertNotNull(savedCompany.getId());
        assertEquals(savedCompany.getId().toString(), output.id());
        assertEquals("Empresa Legal", savedCompany.getLegalName());
        assertEquals("Marca", savedCompany.getTradeName());
        assertEquals("11222333000181", savedCompany.getTaxId());
        assertEquals(CompanyType.LTDA, savedCompany.getCompanyType());
        assertEquals(State.SP, savedCompany.getAddress().state());
        assertEquals("Rua A", savedCompany.getAddress().street());
        assertEquals("01001000", savedCompany.getAddress().postalCode());
        assertEquals("contato@empresa.com", savedCompany.getEmail().value());
        assertEquals("1133445566", savedCompany.getPhone().value());
        assertEquals("11987654321", savedCompany.getCellphone().value());
    }

    @Test
    void executeShouldThrowWhenTaxIdAlreadyExists() {
        CompanyRepository repository = mock(CompanyRepository.class);
        CreateCompanyService service = new CreateCompanyService(repository);
        CreateCompanyInput input = validInput();
        when(repository.existsByTaxId("11222333000181")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> service.execute(input));

        assertEquals("taxId already exists", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void executeShouldThrowWhenLegalNameAlreadyExists() {
        CompanyRepository repository = mock(CompanyRepository.class);
        CreateCompanyService service = new CreateCompanyService(repository);
        CreateCompanyInput input = validInput();
        when(repository.existsByLegalName("Empresa Legal")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> service.execute(input));

        assertEquals("legalName already exists", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void executeShouldThrowWhenEmailAlreadyExists() {
        CompanyRepository repository = mock(CompanyRepository.class);
        CreateCompanyService service = new CreateCompanyService(repository);
        CreateCompanyInput input = validInput();
        when(repository.existsByEmail("contato@empresa.com")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> service.execute(input));

        assertEquals("email already exists", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void executeShouldThrowWhenCompanyTypeIsInvalid() {
        CompanyRepository repository = mock(CompanyRepository.class);
        CreateCompanyService service = new CreateCompanyService(repository);
        CreateCompanyInput input = new CreateCompanyInput("Empresa Legal", "Marca", "11222333000181", "invalid",
                validAddressInput(), "contato@empresa.com", "1133445566", "11987654321");

        assertThrows(IllegalArgumentException.class, () -> service.execute(input));
        verifyNoInteractions(repository);
    }

    @Test
    void executeShouldThrowWhenStateIsInvalid() {
        CompanyRepository repository = mock(CompanyRepository.class);
        CreateCompanyService service = new CreateCompanyService(repository);
        CreateCompanyInput input = new CreateCompanyInput("Empresa Legal", "Marca", "11222333000181", "LTDA",
                new AddressInput("Rua A", "10", null, "Centro", "Sao Paulo", "XX", "01001000", "Brasil"),
                "contato@empresa.com", "1133445566", "11987654321");

        assertThrows(IllegalArgumentException.class, () -> service.execute(input));
        verifyNoInteractions(repository);
    }

    private static AddressInput validAddressInput() {
        return new AddressInput("Rua A", "10", null, "Centro", "Sao Paulo", "SP", "01001000", "Brasil");
    }

    private static CreateCompanyInput validInput() {
        return new CreateCompanyInput("Empresa Legal", "Marca", "11222333000181", "LTDA", validAddressInput(),
                "contato@empresa.com", "1133445566", "11987654321");
    }
}

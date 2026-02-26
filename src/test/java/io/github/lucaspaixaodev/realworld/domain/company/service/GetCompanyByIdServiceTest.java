package io.github.lucaspaixaodev.realworld.domain.company.service;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.CompanyType;
import io.github.lucaspaixaodev.realworld.domain.company.output.GetCompanyByIdOutput;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.exception.NotFoundException;
import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import io.github.lucaspaixaodev.realworld.domain.shared.Address;
import io.github.lucaspaixaodev.realworld.domain.shared.Cellphone;
import io.github.lucaspaixaodev.realworld.domain.shared.Email;
import io.github.lucaspaixaodev.realworld.domain.shared.ID;
import io.github.lucaspaixaodev.realworld.domain.shared.Phone;
import io.github.lucaspaixaodev.realworld.domain.shared.State;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCompanyByIdServiceTest {

    @Test
    void executeShouldReturnMappedOutputWhenCompanyExists() {
        CompanyRepository repository = mock(CompanyRepository.class);
        GetCompanyByIdService service = new GetCompanyByIdService(repository);
        UUID uuid = UUID.randomUUID();
        ID id = ID.from(uuid);
        Company company = Company.restore(id, "Empresa Legal", "Marca", "11222333000181", CompanyType.LTDA,
                new Address("Rua A", "10", "Sala 1", "Centro", "Sao Paulo", State.SP, "01001000", "Brasil"),
                new Email("contato@empresa.com"), new Phone("1133445566"), new Cellphone("11987654321"), true);

        when(repository.findById(id)).thenReturn(Optional.of(company));

        GetCompanyByIdOutput output = service.execute(uuid.toString());

        assertEquals(uuid.toString(), output.id());
        assertEquals("Empresa Legal", output.legalName());
        assertEquals("Marca", output.tradeName());
        assertEquals("11222333000181", output.taxId());
        assertEquals("LTDA", output.companyType());
        assertEquals("contato@empresa.com", output.email());
        assertEquals("1133445566", output.phone());
        assertEquals("11987654321", output.cellphone());
        assertTrue(output.active());
        assertNotNull(output.address());
        assertEquals("Rua A", output.address().street());
        assertEquals("SP", output.address().state());
    }

    @Test
    void executeShouldThrowValidationExceptionWhenIdIsInvalid() {
        CompanyRepository repository = mock(CompanyRepository.class);
        GetCompanyByIdService service = new GetCompanyByIdService(repository);

        ValidationException exception = assertThrows(ValidationException.class, () -> service.execute("invalid-id"));

        assertEquals("id must be a valid UUID", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void executeShouldThrowNotFoundExceptionWhenCompanyDoesNotExist() {
        CompanyRepository repository = mock(CompanyRepository.class);
        GetCompanyByIdService service = new GetCompanyByIdService(repository);
        UUID uuid = UUID.randomUUID();
        ID id = ID.from(uuid);

        when(repository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.execute(uuid.toString()));

        assertEquals("company not found", exception.getMessage());
        verify(repository).findById(id);
    }
}

package io.github.lucaspaixaodev.realworld.application.company.usecase;

import io.github.lucaspaixaodev.realworld.domain.company.output.GetCompanyByIdOutput;
import io.github.lucaspaixaodev.realworld.domain.company.service.GetCompanyByIdService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class GetCompanyByIdUseCaseTest {

    @Test
    void executeShouldDelegateToServiceAndReturnOutput() {
        GetCompanyByIdService service = mock(GetCompanyByIdService.class);
        GetCompanyByIdUseCase useCase = new GetCompanyByIdUseCase(service);
        GetCompanyByIdOutput expectedOutput = new GetCompanyByIdOutput("id", "Empresa Legal", "Marca",
                "11222333000181", "LTDA",
                new GetCompanyByIdOutput.AddressOutput("Rua A", "10", null, "Centro", "Sao Paulo", "SP",
                        "01001000", "Brasil"),
                "contato@empresa.com", "1133445566", "11987654321", true);

        when(service.execute("company-id")).thenReturn(expectedOutput);

        GetCompanyByIdOutput result = useCase.execute("company-id");

        assertSame(expectedOutput, result);
        verify(service).execute("company-id");
        verifyNoMoreInteractions(service);
    }
}

package io.github.lucaspaixaodev.realworld.application.company.usecase;

import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.github.lucaspaixaodev.realworld.domain.company.service.CreateCompanyService;
import io.github.lucaspaixaodev.realworld.domain.notification.input.PublishNewCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.notification.service.PublishNewCompanyService;
import io.github.lucaspaixaodev.realworld.domain.shared.input.AddressInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CreateCompanyUseCaseTest {

    private final CreateCompanyService createCompanyService = mock(CreateCompanyService.class);
    private final PublishNewCompanyService publishNewCompanyService = mock(PublishNewCompanyService.class);
    private final String queueName = "new-companies.fifo";
    private final CreateCompanyUseCase useCase = new CreateCompanyUseCase(createCompanyService,
            publishNewCompanyService, queueName);

    @Test
    void executeShouldDelegateToServiceAndPublishNotification() {
        CreateCompanyInput input = new CreateCompanyInput("Empresa Legal", "Marca", "11222333000181", "LTDA",
                new AddressInput("Rua A", "10", null, "Centro", "Sao Paulo", "SP", "01001000", "Brasil"),
                "contato@empresa.com", "1133445566", "11987654321");
        CreateCompanyOutput expectedOutput = new CreateCompanyOutput("company-id");

        when(createCompanyService.execute(input)).thenReturn(expectedOutput);

        CreateCompanyOutput result = useCase.execute(input);

        assertSame(expectedOutput, result);
        verify(createCompanyService).execute(input);
        verify(publishNewCompanyService).execute(new PublishNewCompanyInput("company-id", queueName));
        verifyNoMoreInteractions(createCompanyService, publishNewCompanyService);
    }
}

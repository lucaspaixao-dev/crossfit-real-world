package io.github.lucaspaixaodev.realworld.application.company.usecase;

import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.github.lucaspaixaodev.realworld.domain.company.service.CreateCompanyService;
import io.github.lucaspaixaodev.realworld.domain.shared.input.AddressInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class CreateCompanyUseCaseTest {

	@Test
	void executeShouldDelegateToServiceAndReturnOutput() {
		CreateCompanyService service = mock(CreateCompanyService.class);
		CreateCompanyUseCase useCase = new CreateCompanyUseCase(service);
		CreateCompanyInput input = new CreateCompanyInput("Empresa Legal", "Marca", "11222333000181", "LTDA",
				new AddressInput("Rua A", "10", null, "Centro", "Sao Paulo", "SP", "01001000", "Brasil"),
				"contato@empresa.com", "1133445566", "11987654321");
		CreateCompanyOutput expectedOutput = new CreateCompanyOutput("company-id");

		when(service.execute(input)).thenReturn(expectedOutput);

		CreateCompanyOutput result = useCase.execute(input);

		assertSame(expectedOutput, result);
		verify(service).execute(input);
		verifyNoMoreInteractions(service);
	}
}

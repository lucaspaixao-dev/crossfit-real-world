package io.github.lucaspaixaodev.realworld.domain.notification.service;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.exception.NotFoundException;
import io.github.lucaspaixaodev.realworld.domain.notification.NewCompanyNotification;
import io.github.lucaspaixaodev.realworld.domain.notification.input.PublishNewCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.notification.publisher.NotificationPublisher;
import io.github.lucaspaixaodev.realworld.domain.shared.*;
import io.github.lucaspaixaodev.realworld.domain.company.CompanyType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublishNewCompanyServiceTest {

    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private final NotificationPublisher notificationPublisher = mock(NotificationPublisher.class);
    private final PublishNewCompanyService service = new PublishNewCompanyService(companyRepository,
            notificationPublisher);

    @Test
    void executeShouldPublishNotificationWhenCompanyExists() {
        ID companyId = ID.random();
        Company company = Company.restore(companyId, "Crossfit Real World LTDA", "Crossfit Real World",
                "11222333000181", CompanyType.LTDA,
                new Address("Rua A", "10", null, "Centro", "Sao Paulo", State.SP, "01001000", "Brasil"),
                new Email("contato@crossfit.com"), new Phone("1133445566"), new Cellphone("11999998888"),
                LocalDate.of(2026, 1, 1), true);

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        service.execute(new PublishNewCompanyInput(companyId.toString(), "new-companies.fifo"));

        ArgumentCaptor<NewCompanyNotification> notificationCaptor = ArgumentCaptor
                .forClass(NewCompanyNotification.class);
        verify(notificationPublisher).publish(notificationCaptor.capture(), eq("new-companies.fifo"),
                eq(companyId.toString()));

        NewCompanyNotification notification = notificationCaptor.getValue();
        assertEquals(companyId.toString(), notification.getMessage().content());
    }

    @Test
    void executeShouldThrowNotFoundExceptionWhenCompanyDoesNotExist() {
        ID companyId = ID.random();
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        PublishNewCompanyInput input = new PublishNewCompanyInput(companyId.toString(), "new-companies.fifo");
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.execute(input));

        assertEquals("Company not found", exception.getMessage());
        verifyNoInteractions(notificationPublisher);
    }
}

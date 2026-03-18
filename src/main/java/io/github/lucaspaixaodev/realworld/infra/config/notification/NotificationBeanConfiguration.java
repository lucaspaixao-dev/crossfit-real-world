package io.github.lucaspaixaodev.realworld.infra.config.notification;

import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.notification.publisher.NotificationPublisher;
import io.github.lucaspaixaodev.realworld.domain.notification.service.PublishNewCompanyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationBeanConfiguration {

    @Bean
    public PublishNewCompanyService publishNewCompanyService(CompanyRepository companyRepository,
            NotificationPublisher notificationPublisher) {
        return new PublishNewCompanyService(companyRepository, notificationPublisher);
    }
}

package io.github.lucaspaixaodev.realworld.domain.notification.service;

import io.github.lucaspaixaodev.realworld.domain.company.Company;
import io.github.lucaspaixaodev.realworld.domain.company.repository.CompanyRepository;
import io.github.lucaspaixaodev.realworld.domain.exception.NotFoundException;
import io.github.lucaspaixaodev.realworld.domain.notification.NewCompanyNotification;
import io.github.lucaspaixaodev.realworld.domain.notification.publisher.NotificationPublisher;
import io.github.lucaspaixaodev.realworld.domain.shared.ID;

public class PublishNewCompanyService {

    private final CompanyRepository companyRepository;
    private final NotificationPublisher notificationPublisher;

    public PublishNewCompanyService(CompanyRepository companyRepository, NotificationPublisher notificationPublisher) {
        this.companyRepository = companyRepository;
        this.notificationPublisher = notificationPublisher;
    }

    public void execute(String companyId, String queueName) {
        Company company = companyRepository.findById(ID.from(companyId))
                .orElseThrow(() -> new NotFoundException("Company not found"));

        var notification = new NewCompanyNotification(company.getId().toString(), company.getLegalName());
        notificationPublisher.publish(notification, queueName, companyId);
    }
}

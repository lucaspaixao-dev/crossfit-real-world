package io.github.lucaspaixaodev.realworld.application.company.usecase;

import io.github.lucaspaixaodev.realworld.domain.company.input.CreateCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.company.output.CreateCompanyOutput;
import io.github.lucaspaixaodev.realworld.domain.company.service.CreateCompanyService;
import io.github.lucaspaixaodev.realworld.domain.notification.input.PublishNewCompanyInput;
import io.github.lucaspaixaodev.realworld.domain.notification.service.PublishNewCompanyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyUseCase {

    private final CreateCompanyService createCompanyService;
    private final PublishNewCompanyService publishNewCompanyService;
    private final String newCompanyQueueName;

    public CreateCompanyUseCase(CreateCompanyService createCompanyService,
            PublishNewCompanyService publishNewCompanyService,
            @Value("${aws.sqs.queues.new-company}") String newCompanyQueueName) {
        this.createCompanyService = createCompanyService;
        this.publishNewCompanyService = publishNewCompanyService;
        this.newCompanyQueueName = newCompanyQueueName;
    }

    public CreateCompanyOutput execute(CreateCompanyInput input) {
        CreateCompanyOutput output = createCompanyService.execute(input);
        publishNewCompanyService.execute(new PublishNewCompanyInput(output.id(), newCompanyQueueName));
        return output;
    }
}

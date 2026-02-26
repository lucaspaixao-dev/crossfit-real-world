package io.github.lucaspaixaodev.realworld.application.company.usecase;

import io.github.lucaspaixaodev.realworld.domain.company.output.GetCompanyByIdOutput;
import io.github.lucaspaixaodev.realworld.domain.company.service.GetCompanyByIdService;
import org.springframework.stereotype.Component;

@Component
public class GetCompanyByIdUseCase {
    private final GetCompanyByIdService getCompanyByIdService;

    public GetCompanyByIdUseCase(GetCompanyByIdService getCompanyByIdService) {
        this.getCompanyByIdService = getCompanyByIdService;
    }

    public GetCompanyByIdOutput execute(String id) {
        return getCompanyByIdService.execute(id);
    }
}

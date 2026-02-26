package io.github.lucaspaixaodev.realworld.infra.input.rest.company;

import io.github.lucaspaixaodev.realworld.application.company.usecase.GetCompanyByIdUseCase;
import io.github.lucaspaixaodev.realworld.infra.input.rest.company.api.GetCompanyByIdApi;
import io.github.lucaspaixaodev.realworld.infra.input.rest.company.response.CompanyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCompanyByIdController implements GetCompanyByIdApi {
    private static final Logger log = LoggerFactory.getLogger(GetCompanyByIdController.class);

    private final GetCompanyByIdUseCase getCompanyByIdUseCase;

    public GetCompanyByIdController(GetCompanyByIdUseCase getCompanyByIdUseCase) {
        this.getCompanyByIdUseCase = getCompanyByIdUseCase;
    }

    @Override
    public ResponseEntity<CompanyResponse> getCompanyById(String id) {
        log.info("Received get company by id request. id={}", id);

        var output = getCompanyByIdUseCase.execute(id);
        var response = CompanyResponse.from(output);

        log.info("Get company by id request processed successfully. id={}", id);

        return ResponseEntity.ok(response);
    }
}

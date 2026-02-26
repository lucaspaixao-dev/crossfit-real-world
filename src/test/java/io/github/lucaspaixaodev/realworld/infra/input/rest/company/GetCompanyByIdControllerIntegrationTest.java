package io.github.lucaspaixaodev.realworld.infra.input.rest.company;

import io.github.lucaspaixaodev.realworld.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class GetCompanyByIdControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanDatabase() {
        jdbcTemplate.update("DELETE FROM company_address");
        jdbcTemplate.update("DELETE FROM company");
    }

    @Test
    void shouldReturnCompanyById() throws Exception {
        UUID id = UUID.randomUUID();
        insertCompany(id);
        insertCompanyAddress(id);

        mockMvc.perform(get("/company/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.legalName").value("Crossfit Real World LTDA"))
                .andExpect(jsonPath("$.tradeName").value("Crossfit Real World"))
                .andExpect(jsonPath("$.taxId").value("11222333000181"))
                .andExpect(jsonPath("$.companyType").value("LTDA"))
                .andExpect(jsonPath("$.email").value("contato@crossfitrealworld.com"))
                .andExpect(jsonPath("$.phone").value("1133334444"))
                .andExpect(jsonPath("$.cellphone").value("11999998888"))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.address.street").value("Av. Paulista"))
                .andExpect(jsonPath("$.address.number").value("1000"))
                .andExpect(jsonPath("$.address.complement").value("Sala 12"))
                .andExpect(jsonPath("$.address.neighborhood").value("Bela Vista"))
                .andExpect(jsonPath("$.address.city").value("Sao Paulo"))
                .andExpect(jsonPath("$.address.state").value("SP"))
                .andExpect(jsonPath("$.address.postalCode").value("01310100"))
                .andExpect(jsonPath("$.address.country").value("Brasil"));
    }

    @Test
    void shouldReturnBadRequestWhenIdIsInvalid() throws Exception {
        mockMvc.perform(get("/company/{id}", "invalid-id"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Validation error"))
                .andExpect(jsonPath("$.detail").value("id must be a valid UUID"))
                .andExpect(jsonPath("$.instance").value("/company/invalid-id"));
    }

    @Test
    void shouldReturnNotFoundWhenCompanyDoesNotExist() throws Exception {
        UUID missingId = UUID.randomUUID();

        mockMvc.perform(get("/company/{id}", missingId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Business error"))
                .andExpect(jsonPath("$.detail").value("company not found"))
                .andExpect(jsonPath("$.instance").value("/company/" + missingId));
    }

    private void insertCompany(UUID id) {
        jdbcTemplate.update(
                "INSERT INTO company (id, legal_name, trade_name, tax_id, company_type, email, phone, cellphone, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id,
                "Crossfit Real World LTDA",
                "Crossfit Real World",
                "11222333000181",
                "LTDA",
                "contato@crossfitrealworld.com",
                "1133334444",
                "11999998888",
                true);
    }

    private void insertCompanyAddress(UUID id) {
        jdbcTemplate.update(
                "INSERT INTO company_address (id, street, number, complement, neighborhood, city, state, postal_code, country) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id,
                "Av. Paulista",
                "1000",
                "Sala 12",
                "Bela Vista",
                "Sao Paulo",
                "SP",
                "01310100",
                "Brasil");
    }
}

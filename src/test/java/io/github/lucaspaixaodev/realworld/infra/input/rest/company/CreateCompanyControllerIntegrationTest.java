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
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class CreateCompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        jdbcTemplate.update("DELETE FROM company_address");
        jdbcTemplate.update("DELETE FROM company");
    }

    @Test
    void shouldCreateCompanyAndPersistCompanyAndAddress() throws Exception {
        String requestBody = """
                {
                  "legalName": "Crossfit Real World LTDA",
                  "tradeName": "Crossfit Real World",
                  "taxId": "11222333000181",
                  "companyType": "ltda",
                  "address": {
                    "street": "Av. Paulista",
                    "number": "1000",
                    "complement": "Sala 12",
                    "neighborhood": "Bela Vista",
                    "city": "Sao Paulo",
                    "state": "sp",
                    "postalCode": "01310100",
                    "country": "Brasil"
                  },
                  "email": "contato@crossfitrealworld.com",
                  "phone": "1133334444",
                  "cellphone": "11999998888"
                }
                """;

        MvcResult result = mockMvc
                .perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isString()).andReturn();

        JsonNode responseJson = objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        JsonNode idNode = Objects.requireNonNull(responseJson.get("id"), "response id must be present");
        String id = Objects.requireNonNull(idNode.stringValue(), "response id must be textual");
        UUID companyId = UUID.fromString(id);

        Map<String, Object> companyRow = jdbcTemplate.queryForMap("SELECT * FROM company WHERE id = ?", companyId);
        assertEquals(companyId, companyRow.get("id"));
        assertEquals("Crossfit Real World LTDA", companyRow.get("legal_name"));
        assertEquals("Crossfit Real World", companyRow.get("trade_name"));
        assertEquals("11222333000181", companyRow.get("tax_id"));
        assertEquals("LTDA", companyRow.get("company_type"));
        assertEquals("contato@crossfitrealworld.com", companyRow.get("email"));
        assertEquals("1133334444", companyRow.get("phone"));
        assertEquals("11999998888", companyRow.get("cellphone"));
        assertEquals(Boolean.TRUE, companyRow.get("active"));

        Map<String, Object> addressRow = jdbcTemplate.queryForMap("SELECT * FROM company_address WHERE id = ?",
                companyId);
        assertEquals(companyId, addressRow.get("id"));
        assertEquals("Av. Paulista", addressRow.get("street"));
        assertEquals("1000", addressRow.get("number"));
        assertEquals("Sala 12", addressRow.get("complement"));
        assertEquals("Bela Vista", addressRow.get("neighborhood"));
        assertEquals("Sao Paulo", addressRow.get("city"));
        assertEquals("SP", addressRow.get("state"));
        assertEquals("01310100", addressRow.get("postal_code"));
        assertEquals("Brasil", addressRow.get("country"));
    }

    @Test
    void shouldCreateUniqueIndexesForCompanyTable() {
        List<String> indexNames = jdbcTemplate.queryForList(
                "SELECT indexname FROM pg_indexes WHERE schemaname = current_schema() AND tablename = 'company'",
                String.class);

        assertTrue(indexNames.contains("ux_company_legal_name"));
        assertTrue(indexNames.contains("ux_company_tax_id"));
        assertTrue(indexNames.contains("ux_company_email"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestBodyValidationFails() throws Exception {
        String requestBody = """
                {
                  "legalName": "Crossfit Real World LTDA",
                  "tradeName": "Crossfit Real World",
                  "taxId": "11222333000181",
                  "companyType": "LTDA",
                  "address": {
                    "street": "Av. Paulista",
                    "number": "1000",
                    "complement": "Sala 12",
                    "neighborhood": "Bela Vista",
                    "city": "Sao Paulo",
                    "state": "SP",
                    "postalCode": "01310100",
                    "country": "Brasil"
                  },
                  "email": "invalid-email",
                  "phone": "1133334444",
                  "cellphone": "11999998888"
                }
                """;

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Invalid request body"))
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.instance").value("/companies"))
                .andExpect(jsonPath("$.errors[*].field", hasItem("email")));

        assertEquals(0, countRows("company"));
        assertEquals(0, countRows("company_address"));
    }

    @Test
    void shouldReturnBadRequestWhenDomainValidationFails() throws Exception {
        String requestBody = """
                {
                  "legalName": "Crossfit Real World LTDA",
                  "tradeName": "Crossfit Real World",
                  "taxId": "11222333000182",
                  "companyType": "LTDA",
                  "address": {
                    "street": "Av. Paulista",
                    "number": "1000",
                    "complement": "Sala 12",
                    "neighborhood": "Bela Vista",
                    "city": "Sao Paulo",
                    "state": "SP",
                    "postalCode": "01310100",
                    "country": "Brasil"
                  },
                  "email": "contato@crossfitrealworld.com",
                  "phone": "1133334444",
                  "cellphone": "11999998888"
                }
                """;

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Validation error"))
                .andExpect(jsonPath("$.detail").value("taxId must be a valid Tax id"))
                .andExpect(jsonPath("$.instance").value("/companies"));

        assertEquals(0, countRows("company"));
        assertEquals(0, countRows("company_address"));
    }

    @Test
    void shouldReturnUnprocessableContentWhenTaxIdAlreadyExists() throws Exception {
        insertCompanyGraph(UUID.randomUUID(), "Existing Legal Name", "Existing Trade", "11222333000181",
                "existing@company.com");

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(validCreateRequestBody(
                "New Legal Name LTDA", "New Trade", "11222333000181", "new@company.com")))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Business error"))
                .andExpect(jsonPath("$.detail").value("taxId already exists"))
                .andExpect(jsonPath("$.instance").value("/companies"));

        assertEquals(1, countRows("company"));
        assertEquals(1, countRows("company_address"));
    }

    @Test
    void shouldReturnUnprocessableContentWhenLegalNameAlreadyExists() throws Exception {
        insertCompanyGraph(UUID.randomUUID(), "Crossfit Real World LTDA", "Existing Trade", "99888777000166",
                "existing@company.com");

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(validCreateRequestBody(
                "Crossfit Real World LTDA", "New Trade", "11222333000181", "new@company.com")))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Business error"))
                .andExpect(jsonPath("$.detail").value("legalName already exists"))
                .andExpect(jsonPath("$.instance").value("/companies"));

        assertEquals(1, countRows("company"));
        assertEquals(1, countRows("company_address"));
    }

    @Test
    void shouldReturnUnprocessableContentWhenEmailAlreadyExists() throws Exception {
        insertCompanyGraph(UUID.randomUUID(), "Existing Legal Name", "Existing Trade", "99888777000166",
                "contato@crossfitrealworld.com");

        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(validCreateRequestBody(
                "New Legal Name LTDA", "New Trade", "11222333000181", "contato@crossfitrealworld.com")))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.parseMediaType(
                        MediaType.APPLICATION_PROBLEM_JSON_VALUE)))
                .andExpect(jsonPath("$.title").value("Business error"))
                .andExpect(jsonPath("$.detail").value("email already exists"))
                .andExpect(jsonPath("$.instance").value("/companies"));

        assertEquals(1, countRows("company"));
        assertEquals(1, countRows("company_address"));
    }

    private int countRows(String tableName) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
        return Objects.requireNonNull(count, "row count must not be null");
    }

    private void insertCompanyGraph(UUID id, String legalName, String tradeName, String taxId, String email) {
        jdbcTemplate.update(
                "INSERT INTO company (id, legal_name, trade_name, tax_id, company_type, email, phone, cellphone, active) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, legalName, tradeName, taxId, "LTDA", email, "1133334444", "11999998888", true);
        jdbcTemplate.update(
                "INSERT INTO company_address (id, street, number, complement, neighborhood, city, state, postal_code, country) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id, "Av. Paulista", "1000", "Sala 12", "Bela Vista", "Sao Paulo", "SP", "01310100", "Brasil");
    }

    private String validCreateRequestBody(String legalName, String tradeName, String taxId, String email) {
        return """
                {
                  "legalName": "%s",
                  "tradeName": "%s",
                  "taxId": "%s",
                  "companyType": "LTDA",
                  "address": {
                    "street": "Av. Paulista",
                    "number": "1000",
                    "complement": "Sala 12",
                    "neighborhood": "Bela Vista",
                    "city": "Sao Paulo",
                    "state": "SP",
                    "postalCode": "01310100",
                    "country": "Brasil"
                  },
                  "email": "%s",
                  "phone": "1133334444",
                  "cellphone": "11999998888"
                }
                """.formatted(legalName, tradeName, taxId, email);
    }
}

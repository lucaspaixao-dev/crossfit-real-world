# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
./gradlew build          # Full build + tests
./gradlew test           # Run all tests
./gradlew test --tests "fully.qualified.TestClass.methodName"  # Single test
./gradlew bootRun        # Run the Spring Boot app (needs PostgreSQL running)
./gradlew format         # Apply Spotless formatting (Eclipse formatter)
./gradlew lint           # Run checkstyle + spotless verification
./gradlew check          # Full CI check (spotlessCheck + checkstyle + JaCoCo coverage)
./gradlew jacocoTestReport  # Generate coverage report
```

Docker for local PostgreSQL: `docker compose up -d` (port 5432, db: crossfit_real_world, user/pass: crossfit/crossfit).

## Architecture

Hexagonal / Clean Architecture with three layers:

- **Domain** (`domain/`) — Pure business logic, no framework dependencies. Contains aggregates, value objects, domain services, repository interfaces (ports), and domain exceptions. Immutable domain objects with `create()` (new) and `restore()` (from DB) factory methods.
- **Application** (`application/*/usecase/`) — Thin use case orchestrators (`@Component`) that delegate to domain services.
- **Infrastructure** (`infra/`) — Framework adapters. `infra/input/rest/` for REST controllers (implementing API interfaces with OpenAPI annotations), `infra/output/database/` for JPA repository implementations, `infra/config/` for Spring bean wiring of domain services, `infra/output/aws/sqs` for SQS integration, application producers are here, `infra/input/aws/sqs` for SQS integration, application consumers are here..

**Data flow for database:** Controller → UseCase → DomainService → Repository interface → PostgresRepository (JPA) → PostgreSQL

**Exception mapping** in `GlobalExceptionHandler`: `ValidationException` → 400, `NotFoundException` → 404, `AlreadyExistsException` → 409, other `BaseException` → 422.

## Testing

- **Unit tests** for domain models, value objects, domain services, and use cases (mocked repositories).
- **Integration tests** for REST controllers using `@SpringBootTest` + `@AutoConfigureMockMvc` + Testcontainers (PostgreSQL:16-alpine). Import `TestcontainersConfiguration` class. Database cleanup via `JdbcTemplate` in `@BeforeEach`.
- JaCoCo enforces **90% line coverage minimum** on the domain layer.

## Code Quality

- **Java 25** toolchain, **Spring Boot 4.0.3**
- Checkstyle config: `config/checkstyle/checkstyle.xml`
- Formatter: `config/formatter/eclipse-java-formatter.xml` (applied via Spotless)
- Always run `./gradlew format` before committing — CI will reject unformatted code.

## Conventions

- **Commits:** Conventional Commits format — `type(scope): description` (e.g., `feat(company): add endpoint`).
- **Database migrations:** Flyway in `src/main/resources/db/migration/`, naming: `V{YYYYMMDDHHmmss}__{description}.sql`. Hibernate DDL set to `validate` only.
- **Domain layer** must remain free of Spring/Jakarta framework imports.
- Repository interfaces live in the domain; implementations live in infrastructure.
- REST API interfaces (in `infra/input/rest/*/api/`) define OpenAPI docs; controllers implement them.

package io.github.lucaspaixaodev.realworld.infra.input.rest.exception;

import io.github.lucaspaixaodev.realworld.domain.exception.AlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    @Test
    void handleBaseExceptionShouldReturnConflictForAlreadyExistsException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/companies");

        ResponseEntity<ProblemDetail> response = handler.handleBaseException(
                new AlreadyExistsException("email already exists"), request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Business error", response.getBody().getTitle());
        assertEquals("email already exists", response.getBody().getDetail());
        assertEquals(URI.create("/companies"), response.getBody().getInstance());
    }
}

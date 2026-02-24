package io.github.lucaspaixaodev.realworld.domain.shared;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

	@Test
	void shouldTrimAndStoreEmailValue() {
		Email email = new Email("  user@example.com  ");

		assertEquals("user@example.com", email.value());
	}

	@Test
	void shouldThrowWhenEmailIsInvalid() {
		ValidationException ex = assertThrows(ValidationException.class, () -> new Email("bad"));

		assertEquals("email must have at least 6 characters", ex.getMessage());
	}
}

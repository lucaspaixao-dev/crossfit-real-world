package io.github.lucaspaixaodev.realworld.domain.shared;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

	@Test
	void shouldTrimAndStorePhoneValue() {
		Phone phone = new Phone(" 1133445566 ");

		assertEquals("1133445566", phone.value());
	}

	@Test
	void shouldThrowWhenPhoneHasNonDigits() {
		ValidationException ex = assertThrows(ValidationException.class, () -> new Phone("11334A5566"));

		assertEquals("phone must contain only digits", ex.getMessage());
	}
}

package io.github.lucaspaixaodev.realworld.domain.shared;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

	@Test
	void shouldNormalizeAndValidateAddressFields() {
		Address address = new Address("  Rua A  ", "  10 ", "   ", "  Centro  ", "  Sao Paulo  ", State.SP,
				" 01001000 ", "  Brasil  ");

		assertEquals("Rua A", address.street());
		assertEquals("10", address.number());
		assertNull(address.complement());
		assertEquals("Centro", address.neighborhood());
		assertEquals("Sao Paulo", address.city());
		assertEquals(State.SP, address.state());
		assertEquals("01001000", address.postalCode());
		assertEquals("Brasil", address.country());
	}

	@Test
	void shouldKeepNullOptionalFieldsAsNull() {
		Address address = new Address("Rua A", null, null, "Centro", "Sao Paulo", State.SP, "01001000", "Brasil");

		assertNull(address.number());
		assertNull(address.complement());
	}

	@Test
	void shouldThrowWhenStateIsNull() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> new Address("Rua A", "10", null, "Centro", "Sao Paulo", null, "01001000", "Brasil"));

		assertEquals("state must not be null", ex.getMessage());
	}

	@Test
	void shouldThrowWhenPostalCodeContainsNonDigits() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> new Address("Rua A", "10", null, "Centro", "Sao Paulo", State.SP, "01001A00", "Brasil"));

		assertEquals("postalCode must contain only digits", ex.getMessage());
	}
}

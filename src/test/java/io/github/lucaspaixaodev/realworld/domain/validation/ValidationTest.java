package io.github.lucaspaixaodev.realworld.domain.validation;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

	@Test
	void requireNotNullShouldReturnValueWhenPresent() {
		String value = Validation.requireNotNull("abc", "field");

		assertEquals("abc", value);
	}

	@Test
	void requireNotNullShouldThrowWhenNull() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireNotNull(null, "field"));

		assertEquals("field must not be null", ex.getMessage());
	}

	@Test
	void requireNotBlankShouldTrimValue() {
		String value = Validation.requireNotBlank("  abc  ", "field");

		assertEquals("abc", value);
	}

	@Test
	void requireNotBlankShouldThrowWhenBlank() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireNotBlank("   ", "field"));

		assertEquals("field must not be blank", ex.getMessage());
	}

	@Test
	void requireLengthBetweenShouldAcceptBoundaryValues() {
		assertEquals("ab", Validation.requireLengthBetween("ab", "field", 2, 4));
		assertEquals("abcd", Validation.requireLengthBetween("abcd", "field", 2, 4));
	}

	@Test
	void requireLengthBetweenShouldThrowWhenOutsideBounds() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireLengthBetween("a", "field", 2, 4));

		assertEquals("field must have between 2 and 4 characters", ex.getMessage());
	}

	@Test
	void requireMinLengthShouldThrowWhenTooShort() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireMinLength("ab", "field", 3));

		assertEquals("field must have at least 3 characters", ex.getMessage());
	}

	@Test
	void requireExactLengthShouldThrowWhenDifferentLength() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireExactLength("abc", "field", 4));

		assertEquals("field must have 4 characters", ex.getMessage());
	}

	@Test
	void requireDigitsOnlyShouldThrowWhenContainsNonDigits() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireDigitsOnly("12a3", "field"));

		assertEquals("field must contain only digits", ex.getMessage());
	}

	@Test
	void requireValidEmailShouldAcceptValidEmail() {
		assertEquals("user@example.com", Validation.requireValidEmail("user@example.com", "email"));
	}

	@Test
	void requireValidEmailShouldThrowWhenInvalid() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireValidEmail("invalid@", "email"));

		assertEquals("email must be a valid email", ex.getMessage());
	}

	@Test
	void requireValidTaxIdShouldAcceptValidTaxId() {
		assertEquals("11222333000181", Validation.requireValidTaxId("11222333000181", "taxId"));
	}

	@Test
	void requireValidTaxIdShouldThrowWhenAllDigitsAreEqual() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireValidTaxId("11111111111111", "taxId"));

		assertEquals("taxId must be a valid Tax id", ex.getMessage());
	}

	@Test
	void requireValidTaxIdShouldThrowWhenCheckDigitsAreInvalid() {
		ValidationException ex = assertThrows(ValidationException.class,
				() -> Validation.requireValidTaxId("11222333000182", "taxId"));

		assertEquals("taxId must be a valid Tax id", ex.getMessage());
	}
}

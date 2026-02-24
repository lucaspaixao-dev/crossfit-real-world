package io.github.lucaspaixaodev.realworld.domain.shared;

import static io.github.lucaspaixaodev.realworld.domain.validation.Validation.*;

public record Email(String value) {
	private static final int EMAIL_MIN_LENGTH = 6;

	public Email {
		value = requireValidEmail(requireMinLength(requireNotBlank(value, "email"), "email", EMAIL_MIN_LENGTH),
				"email");
	}
}

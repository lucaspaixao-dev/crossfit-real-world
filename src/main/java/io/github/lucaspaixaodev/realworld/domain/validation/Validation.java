package io.github.lucaspaixaodev.realworld.domain.validation;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;

import java.util.regex.Pattern;

public final class Validation {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	private Validation() {
	}

	public static <T> T requireNotNull(T value, String fieldName) {
		if (value == null) {
			throw new ValidationException(fieldName + " must not be null");
		}
		return value;
	}

	public static String requireNotBlank(String value, String fieldName) {
		if (value == null || value.isBlank()) {
			throw new ValidationException(fieldName + " must not be blank");
		}
		return value.trim();
	}

	public static String requireLengthBetween(String value, String fieldName, int minLength, int maxLength) {
		int length = value.length();
		if (length < minLength || length > maxLength) {
			throw new ValidationException(
					fieldName + " must have between " + minLength + " and " + maxLength + " characters");
		}
		return value;
	}

	public static String requireMinLength(String value, String fieldName, int minLength) {
		int length = value.length();
		if (length < minLength) {
			throw new ValidationException(fieldName + " must have at least " + minLength + " characters");
		}
		return value;
	}

	public static String requireExactLength(String value, String fieldName, int expectedLength) {
		int length = value.length();
		if (length != expectedLength) {
			throw new ValidationException(fieldName + " must have " + expectedLength + " characters");
		}
		return value;
	}

	public static String requireDigitsOnly(String value, String fieldName) {
		if (!value.matches("\\d+")) {
			throw new ValidationException(fieldName + " must contain only digits");
		}
		return value;
	}

	public static String requireValidEmail(String value, String fieldName) {
		if (!EMAIL_PATTERN.matcher(value).matches()) {
			throw new ValidationException(fieldName + " must be a valid email");
		}
		return value;
	}

	public static String requireValidTaxId(String value, String fieldName) {
		if (!value.matches("\\d{14}")) {
			throw new ValidationException(fieldName + " must be a valid Tax id");
		}
		if (hasAllDigitsEqual(value)) {
			throw new ValidationException(fieldName + " must be a valid Tax id");
		}
		if (!hasValidTaxIdCheckDigits(value)) {
			throw new ValidationException(fieldName + " must be a valid Tax id");
		}
		return value;
	}

	private static boolean hasAllDigitsEqual(String taxId) {
		char firstDigit = taxId.charAt(0);
		for (int i = 1; i < taxId.length(); i++) {
			if (taxId.charAt(i) != firstDigit) {
				return false;
			}
		}
		return true;
	}

	private static boolean hasValidTaxIdCheckDigits(String taxId) {
		int firstCheckDigit = calculateCheckDigit(taxId, 12);
		int secondCheckDigit = calculateCheckDigit(taxId, 13);
		return firstCheckDigit == Character.getNumericValue(taxId.charAt(12))
				&& secondCheckDigit == Character.getNumericValue(taxId.charAt(13));
	}

	private static int calculateCheckDigit(String taxId, int baseLength) {
		int[] weights = baseLength == 12
				? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
				: new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

		int sum = 0;
		for (int i = 0; i < baseLength; i++) {
			sum += Character.getNumericValue(taxId.charAt(i)) * weights[i];
		}

		int remainder = sum % 11;
		return remainder < 2 ? 0 : 11 - remainder;
	}
}

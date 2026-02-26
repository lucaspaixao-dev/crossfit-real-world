package io.github.lucaspaixaodev.realworld.domain.shared;

import static io.github.lucaspaixaodev.realworld.domain.validation.Validation.*;

public record Address(String street, String number, String complement, String neighborhood, String city, State state,
        String postalCode, String country) {
    private static final int TEXT_MIN_LENGTH = 3;
    private static final int POSTAL_CODE_LENGTH = 8;

    public Address {
        street = requireMinLength(requireNotBlank(street, "street"), "street", TEXT_MIN_LENGTH);
        number = normalizeOptional(number);
        complement = normalizeOptional(complement);
        neighborhood = requireMinLength(requireNotBlank(neighborhood, "neighborhood"), "neighborhood", TEXT_MIN_LENGTH);
        city = requireMinLength(requireNotBlank(city, "city"), "city", TEXT_MIN_LENGTH);
        requireNotNull(state, "state");
        postalCode = requireDigitsOnly(
                requireExactLength(requireNotBlank(postalCode, "postalCode"), "postalCode", POSTAL_CODE_LENGTH),
                "postalCode");
        country = requireMinLength(requireNotBlank(country, "country"), "country", TEXT_MIN_LENGTH);
    }

    private static String normalizeOptional(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}

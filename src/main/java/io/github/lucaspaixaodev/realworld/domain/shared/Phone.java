package io.github.lucaspaixaodev.realworld.domain.shared;

import static io.github.lucaspaixaodev.realworld.domain.validation.Validation.*;

public record Phone(String value) {
    private static final int PHONE_LENGTH = 10;

    public Phone {
        value = requireDigitsOnly(requireExactLength(requireNotBlank(value, "phone"), "phone", PHONE_LENGTH), "phone");
    }
}

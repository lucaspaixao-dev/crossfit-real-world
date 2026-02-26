package io.github.lucaspaixaodev.realworld.domain.shared;

import static io.github.lucaspaixaodev.realworld.domain.validation.Validation.*;

public record Cellphone(String value) {
    private static final int CELLPHONE_LENGTH = 11;

    public Cellphone {
        value = requireDigitsOnly(
                requireExactLength(requireNotBlank(value, "cellphone"), "cellphone", CELLPHONE_LENGTH), "cellphone");
    }
}

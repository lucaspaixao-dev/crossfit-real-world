package io.github.lucaspaixaodev.realworld.domain.shared;

import io.github.lucaspaixaodev.realworld.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellphoneTest {

    @Test
    void shouldTrimAndStoreCellphoneValue() {
        Cellphone cellphone = new Cellphone(" 11987654321 ");

        assertEquals("11987654321", cellphone.value());
    }

    @Test
    void shouldThrowWhenCellphoneHasWrongLength() {
        ValidationException ex = assertThrows(ValidationException.class, () -> new Cellphone("1198765432"));

        assertEquals("cellphone must have 11 characters", ex.getMessage());
    }
}

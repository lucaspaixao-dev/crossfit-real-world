package io.github.lucaspaixaodev.realworld.domain.shared;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IDTest {

	@Test
	void shouldCreateFromUuidAndStringWithSameIdentity() {
		UUID uuid = UUID.randomUUID();

		ID fromUuid = ID.from(uuid);
		ID fromString = ID.from(uuid.toString());

		assertEquals(fromUuid, fromString);
		assertEquals(fromUuid.hashCode(), fromString.hashCode());
		assertEquals(uuid, fromUuid.value());
		assertEquals(uuid.toString(), fromUuid.toString());
	}

	@Test
	void randomShouldGenerateNonNullValue() {
		ID id = ID.random();

		assertNotNull(id.value());
		assertDoesNotThrow(() -> UUID.fromString(id.toString()));
	}

	@Test
	void fromUuidShouldThrowWhenNull() {
		NullPointerException ex = assertThrows(NullPointerException.class, () -> ID.from((UUID) null));

		assertEquals("id must not be null", ex.getMessage());
	}

	@Test
	void fromStringShouldThrowWhenInvalidUuid() {
		assertThrows(IllegalArgumentException.class, () -> ID.from("invalid-uuid"));
	}
}

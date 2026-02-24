package io.github.lucaspaixaodev.realworld.domain.shared;

import java.util.Objects;
import java.util.UUID;

public final class ID {
	private final UUID value;

	private ID(UUID value) {
		this.value = Objects.requireNonNull(value, "id must not be null");
	}

	public static ID from(UUID value) {
		return new ID(value);
	}

	public static ID from(String value) {
		return new ID(UUID.fromString(value));
	}

	public static ID random() {
		return new ID(UUID.randomUUID());
	}

	public UUID value() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ID id)) {
			return false;
		}
		return value.equals(id.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return value.toString();
	}
}

package io.github.lucaspaixaodev.realworld.domain.shared.input;

public record AddressInput(String street, String number, String complement, String neighborhood, String city,
		String state, String postalCode, String country) {
}

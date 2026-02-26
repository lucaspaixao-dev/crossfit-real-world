package io.github.lucaspaixaodev.realworld.infra.input.rest.shared.request;

import io.github.lucaspaixaodev.realworld.domain.shared.input.AddressInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "AddressRequest", description = "Address payload")
public record AddressRequest(@Schema(description = "Street", example = "Av. Paulista") @NotBlank String street,
        @Schema(description = "Number", example = "1000") @NotBlank String number,
        @Schema(description = "Complement", example = "Unit 12") String complement,
        @Schema(description = "Neighborhood", example = "Bela Vista") @NotBlank String neighborhood,
        @Schema(description = "City", example = "Sao Paulo") @NotBlank String city,
        @Schema(description = "State (UF)", example = "SP") @NotBlank String state,
        @Schema(description = "Postal code (digits only)", example = "01310100") @NotBlank String postalCode,
        @Schema(description = "Country", example = "Brazil") @NotBlank String country) {
    public AddressInput toInput() {
        return new AddressInput(street, number, complement, neighborhood, city, state, postalCode, country);
    }
}

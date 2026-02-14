package io.github.theisson.ecommerce.models.types;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;

@Embeddable
public final class Address {

    @Column(name = "state", nullable = false, length = 2)
    private final String state;

    @Column(name = "city", nullable = false)
    private final String city;

    @Column(name = "street", nullable = false)
    private final String street;

    @Column(name = "number")
    private final String number;

    @Column(name = "neighborhood", nullable = false)
    private final String neighborhood;

    @Column(name = "complement")
    private final String complement;

    @Embedded
    private final ZipCode zipCode;

    protected Address() {
        this.state = null;
        this.city = null;
        this.street = null;
        this.number = null;
        this.neighborhood = null;
        this.complement = null;
        this.zipCode = null;
    }

    public Address(String state, String city, String street, String number, String neighborhood, String complement, ZipCode zipCode) {
        validate(state, city, street, neighborhood, zipCode);
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.zipCode = zipCode;
    }

    private void validate(String state, String city, String street, String neighborhood, ZipCode zipCode) {
        if (state == null || state.length() != 2) throw new IllegalArgumentException("Estado inválido.");
        if (city == null || city.trim().isEmpty()) throw new IllegalArgumentException("Cidade obrigatória.");
        if (street == null || street.trim().isEmpty()) throw new IllegalArgumentException("Rua obrigatória.");
        if (neighborhood == null || neighborhood.trim().isEmpty()) throw new IllegalArgumentException("Bairro obrigatório.");
        if (zipCode == null) throw new IllegalArgumentException("CEP é obrigatório.");
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public String getZipCode() {
        return zipCode.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(state, address.state) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(number, address.number) &&
                Objects.equals(neighborhood, address.neighborhood) &&
                Objects.equals(complement, address.complement) &&
                Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, city, street, number, neighborhood, complement, getZipCode());
    }
}

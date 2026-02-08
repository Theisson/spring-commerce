package io.github.theisson.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public final class ZipCode {

    @Column(name = "zip_code", nullable = false, length = 8)
    private final String zipCode;

    protected ZipCode() {
        this.zipCode = null;
    }

    public ZipCode(String zipCode) {
        validate(zipCode);
        this.zipCode = zipCode.replaceAll("\\D", "");
    }

    private void validate(String zipCode) {
        if (zipCode == null || !zipCode.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP inválido.");
        }    
    }

    public String getValue() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZipCode other = (ZipCode) o;
        return Objects.equals(zipCode, other.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode);
    }
}

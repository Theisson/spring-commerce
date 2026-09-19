package io.github.theisson.ecommerce.models.types;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode 
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

    public String getValue() { return zipCode; }
}

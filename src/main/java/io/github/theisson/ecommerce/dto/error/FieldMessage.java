package io.github.theisson.ecommerce.dto.error;

public record FieldMessage(
    String fieldName,
    String message
) {}

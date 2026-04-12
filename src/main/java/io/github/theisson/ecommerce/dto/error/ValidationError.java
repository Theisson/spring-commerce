package io.github.theisson.ecommerce.dto.error;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path, List<FieldMessage> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }
}

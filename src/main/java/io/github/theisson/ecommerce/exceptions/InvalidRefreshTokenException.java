package io.github.theisson.ecommerce.exceptions;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String msg) {
        super(msg);
    }
    
}

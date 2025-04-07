package uk.lset.exception;

public class QuantityBadRequestException extends RuntimeException {
    public QuantityBadRequestException(String message) {
        super(message);
    }
}

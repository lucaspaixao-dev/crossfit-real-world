package io.github.lucaspaixaodev.realworld.domain.exception;

public abstract class BaseException extends RuntimeException {
    private final String message;

    protected BaseException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

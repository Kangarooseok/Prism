package prism.infra.exception;

public class DuplicateGroupNameException extends RuntimeException {
    public DuplicateGroupNameException(String message) {
        super(message);
    }
}

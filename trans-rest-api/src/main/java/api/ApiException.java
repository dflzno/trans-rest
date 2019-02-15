package api;

public class ApiException extends Exception {

    public ApiException() {
    }

    public ApiException(final String message) {
        super(message);
    }
}

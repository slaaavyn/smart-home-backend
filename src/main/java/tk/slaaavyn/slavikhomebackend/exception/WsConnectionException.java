package tk.slaaavyn.slavikhomebackend.exception;

public class WsConnectionException extends RuntimeException {
    public WsConnectionException(String message) {
        super(message);
    }

    public WsConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

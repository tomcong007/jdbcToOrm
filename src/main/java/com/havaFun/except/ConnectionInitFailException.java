package com.havaFun.except;

public class ConnectionInitFailException  extends RuntimeException {
    public ConnectionInitFailException() {
    }

    public ConnectionInitFailException(String message) {
        super(message);
    }

    public ConnectionInitFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionInitFailException(Throwable cause) {
        super(cause);
    }
}

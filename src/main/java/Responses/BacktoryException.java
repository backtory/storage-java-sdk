package Responses;

public class BacktoryException {
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

    public BacktoryException() {
    }

    public int status() {
        return status;
    }

    public String error() {
        return error;
    }

    public String exception() {
        return exception;
    }

    public String message() {
        return message;
    }

    public String path() {
        return path;
    }
}

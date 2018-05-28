package Responses;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Properties;

public class BacktoryException {
    private int statusCode;
    private String error;
    private String message;

    public BacktoryException(int statusCode, String body) throws IOException {
        setFields(statusCode, body);
    }

    private void setFields(int statusCode, String body) throws IOException {
        Gson gson = new Gson();
        Properties data = gson.fromJson(body, Properties.class);
        this.statusCode = statusCode;
        error = data.getProperty("error");
        message = data.getProperty("message");
    }

    public String getExceptionString() {
        return (new Gson()).toJson(this);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}

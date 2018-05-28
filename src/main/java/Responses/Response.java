package Responses;

import com.google.gson.Gson;
import okhttp3.ResponseBody;

import java.io.IOException;

public class Response {
    private int statusCode;
    private String body;

    public Response(ResponseBody responseBody, int statusCode) {
        this.statusCode = statusCode;
        try {
            body = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponseString() {
        return (new Gson()).toJson(this);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body.substring(body.indexOf("[") + 2 , body.indexOf("\"]"));
    }

    public String get() {
        return body;
    }
}

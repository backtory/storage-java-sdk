package Responses;

import com.google.gson.Gson;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Properties;

public class BacktoryLoginMasterResponse {
    private int statusCode;
    private ResponseBody body;
    private String access_token;
    private String token_type;
    private int expires_in;

    public BacktoryLoginMasterResponse(int statusCode, ResponseBody body) throws IOException {
        this.statusCode = statusCode;
        this.body = body;
        setFields();
    }

    private void setFields() throws IOException {
        Gson gson = new Gson();
        Properties data = gson.fromJson(body.string(), Properties.class);
        access_token = data.getProperty("access_token");
        token_type = data.getProperty("token_type");
        expires_in = Integer.parseInt(data.getProperty("expires_in"));
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

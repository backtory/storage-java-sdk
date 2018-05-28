package Internal;

import Responses.BacktoryLoginMasterResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;

public class TokenProvider {
    private String masterAccessToken;
    private final String X_Backtory_Authentication_Id;
    private final String X_Backtory_Authentication_Key;
    private final OkHttpClient client = new OkHttpClient();
    private BacktoryLoginMasterResponse backtoryLoginMasterResponse;
    private Date lastLogin;

    public TokenProvider(String x_Backtory_Authentication_Id, String x_Backtory_Authentication_Key) {
        X_Backtory_Authentication_Id = x_Backtory_Authentication_Id;
        X_Backtory_Authentication_Key = x_Backtory_Authentication_Key;
    }

    public String getToken() throws IOException {
        if (backtoryLoginMasterResponse == null)
            if (!LoginMaster())
                throw new RuntimeException("Unsuccessful Login Master");
            else if (backtoryLoginMasterResponse.getExpires_in() < new Date().getTime() - lastLogin.getTime())
                if (!LoginMaster())
                    throw new RuntimeException("Unsuccessful Login Master");
        return masterAccessToken;
    }

    private boolean LoginMaster() throws IOException {
        RequestBody body = RequestBody.create(null, new byte[0]);
        int loginMasterTimes = 3;
        while (loginMasterTimes > 0) {
            Request request = new Request.Builder()
                    .addHeader("X-Backtory-Authentication-Id", X_Backtory_Authentication_Id)
                    .addHeader("X-Backtory-Authentication-Key", X_Backtory_Authentication_Key)
                    .url("http://api.backtory.com/auth/login")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 417)
                throw new RuntimeException("Either Authentication Id or Authentication key is not correct");
            else if (response.code() == 200) {
                backtoryLoginMasterResponse = new BacktoryLoginMasterResponse(response.code(), response.body());
                masterAccessToken = backtoryLoginMasterResponse.getAccess_token();
                lastLogin = new Date();
                return true;
            }
            loginMasterTimes -= 1;
        }
        return false;
    }

    public BacktoryLoginMasterResponse getBacktoryLoginMasterResponse() {
        return backtoryLoginMasterResponse;
    }
}

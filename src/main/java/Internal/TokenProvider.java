package Internal;

import Responses.BacktoryLoginMasterResponse;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class TokenProvider {
    private String masterAccessToken;
    private final String xBacktoryAuthenticationId;
    private final String xBacktoryAuthenticationKey;
    private final OkHttpClient client = new OkHttpClient();
    private BacktoryLoginMasterResponse backtoryLoginMasterResponse;
    private Date lastLogin;
    private File tokenProviderFile;

    public TokenProvider(String xBacktoryAuthenticationId, String xBacktoryAuthenticationKey) {
        this.xBacktoryAuthenticationId = xBacktoryAuthenticationId;
        this.xBacktoryAuthenticationKey = xBacktoryAuthenticationKey;
    }

    public TokenProvider(String xBacktoryAuthenticationId, String xBacktoryAuthenticationKey, File tokenProviderFile) {
        this.xBacktoryAuthenticationId = xBacktoryAuthenticationId;
        this.xBacktoryAuthenticationKey = xBacktoryAuthenticationKey;
        this.tokenProviderFile = tokenProviderFile;
    }

    public String getToken() throws IOException {
        if (tokenProviderFile != null) {
            String data = "";
            data = new String(Files.readAllBytes(Paths.get(tokenProviderFile.getAbsolutePath())));
            BacktoryLoginMasterResponse response = new Gson().fromJson(data, BacktoryLoginMasterResponse.class);
            if (response.getExpires_in() < new Date().getTime() - lastLogin.getTime()) {
                if (backtoryLoginMasterResponse == null) {
                    if (!loginMaster()) {
                        throw new RuntimeException("Unsuccessful Login Master");
                    }
                } else if (backtoryLoginMasterResponse.getExpires_in() < new Date().getTime() - lastLogin.getTime())
                    if (!loginMaster())
                        throw new RuntimeException("Unsuccessful Login Master");
            } else {
                backtoryLoginMasterResponse = response;
                masterAccessToken = backtoryLoginMasterResponse.getAccess_token();
            }
            return masterAccessToken;
        } else {
            if (backtoryLoginMasterResponse == null)
                if (!loginMaster())
                    throw new RuntimeException("Unsuccessful Login Master");
            else if (backtoryLoginMasterResponse.getExpires_in() < new Date().getTime() - lastLogin.getTime())
                if (!loginMaster())
                    throw new RuntimeException("Unsuccessful Login Master");
            return masterAccessToken;
        }

    }

    private boolean loginMaster() throws IOException {
        RequestBody body = RequestBody.create(null, new byte[0]);
        int loginMasterTimes = 3;
        while (loginMasterTimes > 0) {
            Request request = new Request.Builder()
                    .addHeader("X-Backtory-Authentication-Id", xBacktoryAuthenticationId)
                    .addHeader("X-Backtory-Authentication-Key", xBacktoryAuthenticationKey)
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

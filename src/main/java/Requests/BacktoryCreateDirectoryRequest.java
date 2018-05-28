package Requests;

import Responses.BacktoryResponse;
import Structure.CreateDirectoryInfo;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class BacktoryCreateDirectoryRequest implements BacktoryRequest {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private CreateDirectoryInfo createDirectoryInfo;
    private final String xBacktoryStorageId;
    private final String masterAccessToken;

    public BacktoryCreateDirectoryRequest(CreateDirectoryInfo createDirectoryInfo, String xBacktoryStorageId, String masterAccessToken) {
        this.createDirectoryInfo = createDirectoryInfo;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
    }

    @Override
    public BacktoryResponse perform() throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(createDirectoryInfo);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url + "directories")
                .addHeader("Authorization", "Bearer" + masterAccessToken)
                .addHeader("X-Backtory-Storage-Id", xBacktoryStorageId)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        BacktoryResponse backtoryResponse = new BacktoryResponse(response.code(), response.body());
        return backtoryResponse;
    }
}

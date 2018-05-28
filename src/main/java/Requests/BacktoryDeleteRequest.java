package Requests;

import Internal.BacktoryFileStrorageService;
import Responses.BacktoryResponse;
import Structure.DeleteInfo;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BacktoryDeleteRequest implements BacktoryRequest {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final BacktoryFileStrorageService backtoryFileStrorageService;
    private DeleteInfo deleteInfo;
    private final String masterAccessToken;
    private final String xBacktoryStorageId;
    private BacktoryResponse<String> backtoryResponse;

    public BacktoryDeleteRequest(DeleteInfo deleteInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStrorageService backtoryFileStrorageService) {
        this.deleteInfo = deleteInfo;
        this.masterAccessToken = masterAccessToken;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.backtoryFileStrorageService = backtoryFileStrorageService;
    }

    @Override
    public BacktoryResponse<String> perform() throws IOException {
        Call call =  backtoryFileStrorageService.delete(
                "Bearer" + masterAccessToken,
                xBacktoryStorageId,
                deleteInfo
        );
        Response response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), "");
        else
            backtoryResponse = new BacktoryResponse<>(response.code(), response.errorBody().string());

        return backtoryResponse;
//        Gson gson = new Gson();
//        String json = gson.toJson(deleteInfo);
//        RequestBody requestBody = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .addHeader("Authorization", "Bearer " + masterAccessToken)
//                .addHeader("X-Backtory-Storage-Id", xBacktoryStorageId)
//                .url("http://storage.backtory.com/files/delete")
//                .post(requestBody)
//                .build();
//        Response response = BacktoryRequest.client.newCall(request).execute();
//        return new BacktoryResponse<String>(response.code(), response.body().string());
    }
}

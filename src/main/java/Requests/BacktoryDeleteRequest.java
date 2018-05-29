package Requests;

import Internal.BacktoryFileStorageService;
import Responses.BacktoryResponse;
import Structure.DeleteInfo;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class BacktoryDeleteRequest implements BacktoryRequest {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final BacktoryFileStorageService backtoryFileStorageService;
    private DeleteInfo deleteInfo;
    private final String masterAccessToken;
    private final String xBacktoryStorageId;
    private BacktoryResponse<String> backtoryResponse;

    public BacktoryDeleteRequest(DeleteInfo deleteInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.deleteInfo = deleteInfo;
        this.masterAccessToken = masterAccessToken;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    @Override
    public BacktoryResponse<String> perform() throws IOException {
        Call call =  backtoryFileStorageService.delete(
                "Bearer " + masterAccessToken,
                xBacktoryStorageId,
                deleteInfo
        );
        Response response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), new ArrayList<String>());
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

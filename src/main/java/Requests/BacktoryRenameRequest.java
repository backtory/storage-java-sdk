package Requests;

import Internal.BacktoryFileStrorageService;
import Responses.BacktoryResponse;
import Structure.RenameInfo;
import Structure.RenameItemsInfo;
import Structure.RenameResponse;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class BacktoryRenameRequest implements BacktoryRequest<String> {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String xBacktoryStorageId;
    private final String masterAccessToken;
    private final BacktoryFileStrorageService backtoryFileStrorageService;
    private RenameItemsInfo renameItemsInfo;
    private BacktoryResponse<String> backtoryResponse;

    public BacktoryRenameRequest(RenameItemsInfo renameItemsInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStrorageService backtoryFileStrorageService) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        this.renameItemsInfo = renameItemsInfo;
        this.backtoryFileStrorageService = backtoryFileStrorageService;
    }

    public BacktoryRenameRequest(RenameInfo renameInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStrorageService backtoryFileStrorageService) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        ArrayList<RenameInfo> renameInfoArrayList = new ArrayList<>();
        renameInfoArrayList.add(renameInfo);
        this.renameItemsInfo = new RenameItemsInfo(renameInfoArrayList);
        this.backtoryFileStrorageService = backtoryFileStrorageService;
    }

    @Override
    public BacktoryResponse<String> perform() throws IOException {
        Call<RenameResponse> call = backtoryFileStrorageService.rename(
                "Bearer" + masterAccessToken,
                xBacktoryStorageId,
                renameItemsInfo
        );

        Response<RenameResponse> response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), response.body().getPaths());
        else
            backtoryResponse = new BacktoryResponse<>(response.code(), response.errorBody().string());

        return backtoryResponse;
//        Gson gson = new Gson();
//        String json = gson.toJson(renameItemsInfo);
//        RequestBody requestBody = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .addHeader("Authorization", "Bearer " + masterAccessToken)
//                .addHeader("X-Backtory-Storage-Id", xBacktoryStorageId)
//                .url(url + "files/rename")
//                .post(requestBody)
//                .build();
//
//        Response response = BacktoryRequest.client.newCall(request).execute();
//        String responseBody = response.body().string();
//        String[] responseArray = null;
//        List<String> responseList = new ArrayList<>();
//        String deserializedBody = responseBody;
//        if (response.code() == 200) {
//            deserializedBody = responseBody.substring(responseBody.indexOf('[') + 1, responseBody.indexOf(']'));
//            responseArray = deserializedBody.split(",");
//            for (String str : responseArray)
//                responseList.add(str);
//
//        }
//
//        return new BacktoryResponse<>(response.code(), responseBody, responseList);
    }
}
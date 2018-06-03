package Requests;

import Internal.BacktoryFileStorageService;
import Internal.ErrorUtils;
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
    private final BacktoryFileStorageService backtoryFileStorageService;
    private RenameItemsInfo renameItemsInfo;
    private BacktoryResponse<String> backtoryResponse;

    public BacktoryRenameRequest(RenameItemsInfo renameItemsInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        this.renameItemsInfo = renameItemsInfo;
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    public BacktoryRenameRequest(RenameInfo renameInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        ArrayList<RenameInfo> renameInfoArrayList = new ArrayList<>();
        renameInfoArrayList.add(renameInfo);
        this.renameItemsInfo = new RenameItemsInfo(renameInfoArrayList);
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    @Override
    public BacktoryResponse<String> perform() throws IOException {
        Call<RenameResponse> call = backtoryFileStorageService.rename(
                "Bearer" + masterAccessToken,
                xBacktoryStorageId,
                renameItemsInfo
        );
        Response<RenameResponse> response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), response.body().getNewPaths(), null);
        else
            backtoryResponse = new BacktoryResponse<>(response.code(), null, ErrorUtils.parseError(response));
        return backtoryResponse;
    }
}

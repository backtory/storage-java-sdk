package Requests;

import Internal.BacktoryFileStorageService;
import Internal.ErrorUtils;
import Responses.BacktoryResponse;
import Structure.DeleteInfo;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BacktoryDeleteRequest implements BacktoryRequest {
    private final BacktoryFileStorageService backtoryFileStorageService;
    private DeleteInfo deleteInfo;
    private final String masterAccessToken;
    private final String xBacktoryStorageId;
    private BacktoryResponse<Void> backtoryResponse;

    public BacktoryDeleteRequest(DeleteInfo deleteInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.deleteInfo = deleteInfo;
        this.masterAccessToken = masterAccessToken;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    @Override
    public BacktoryResponse<Void> perform() throws IOException {
        Call call =  backtoryFileStorageService.delete(
                "Bearer " + masterAccessToken,
                xBacktoryStorageId,
                deleteInfo
        );
        Response response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), null, null);
        else
            backtoryResponse = new BacktoryResponse<>(response.code(), null, ErrorUtils.parseError(response));
        return backtoryResponse;
    }
}

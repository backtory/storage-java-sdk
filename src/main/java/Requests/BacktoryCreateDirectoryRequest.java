package Requests;

import Internal.BacktoryFileStorageService;
import Internal.ErrorUtils;
import Responses.BacktoryResponse;
import Structure.CreateDirectoryInfo;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BacktoryCreateDirectoryRequest implements BacktoryRequest {
    private CreateDirectoryInfo createDirectoryInfo;
    private final String xBacktoryStorageId;
    private final String masterAccessToken;
    private final BacktoryFileStorageService backtoryFileStorageService;
    private BacktoryResponse<Void> backtoryResponse;

    public BacktoryCreateDirectoryRequest(CreateDirectoryInfo createDirectoryInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.createDirectoryInfo = createDirectoryInfo;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    @Override
    public BacktoryResponse<Void> perform() throws IOException {
        Call call = backtoryFileStorageService.createDirectory(
                "Bearer " + masterAccessToken,
                xBacktoryStorageId,
                createDirectoryInfo
        );
        Response response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), null, null);
        else
            backtoryResponse = new BacktoryResponse<>(response.code(), null, ErrorUtils.parseError(response));
        return backtoryResponse;
    }
}

package Requests;

import Internal.BacktoryFileStorageService;
import Internal.ErrorUtils;
import Responses.BacktoryResponse;
import Structure.DirListReponse;
import Structure.DirectoryListingInfo;
import Structure.DirectoryListingJsonObject;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BacktoryDirectoryListingRequest implements BacktoryRequest{
    private DirectoryListingInfo directoryListingInfo;
    private final String xBacktoryStorageId;
    private final String masterAccessToken;
    private BacktoryFileStorageService backtoryFileStorageService;
    private BacktoryResponse<DirectoryListingJsonObject> backtoryResponse;

    public BacktoryDirectoryListingRequest(DirectoryListingInfo directoryListingInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.directoryListingInfo = directoryListingInfo;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    @Override
    public BacktoryResponse<DirectoryListingJsonObject> perform() throws IOException {
        Call<DirListReponse> call = backtoryFileStorageService.directoryListing(
                "Bearer " + masterAccessToken,
                xBacktoryStorageId,
                directoryListingInfo
        );
        Response<DirListReponse> response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<>(response.code(), response.body().getFiles(), null);
        else
            backtoryResponse = new BacktoryResponse<>(response.code(), null, ErrorUtils.parseError(response));
        return backtoryResponse;
    }
}

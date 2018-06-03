package Requests;

import Internal.BacktoryFileStorageService;
import Internal.ErrorUtils;
import Responses.BacktoryResponse;
import Structure.UploadInfo;
import Structure.UploadItemsInfo;
import Structure.UploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BacktoryUploadRequest implements BacktoryRequest<String> {
    private static final MediaType MEDIATYPE_MULTIPART = MediaType.parse("multipart/form-data");
    private UploadItemsInfo uploadInfoItems;
    private final String xBacktoryStorageId;
    private final String masterAccessToken;
    private BacktoryResponse<String> backtoryResponse;
    private BacktoryFileStorageService backtoryFileStorageService;

    public BacktoryUploadRequest(UploadItemsInfo uploadInfoItems, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.uploadInfoItems = uploadInfoItems;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    public BacktoryUploadRequest(UploadInfo uploadInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStorageService backtoryFileStorageService) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        ArrayList<UploadInfo> list = new ArrayList<>();
        list.add(uploadInfo);
        this.uploadInfoItems = new UploadItemsInfo(list);
        this.backtoryFileStorageService = backtoryFileStorageService;
    }

    @Override
    public BacktoryResponse<String> perform() throws IOException {
        List<MultipartBody.Part> uploadListItems = new ArrayList<>();
        List<UploadInfo> list = uploadInfoItems.getItems();
        for (int i = 0; i < list.size(); i++) {
            UploadInfo uploadInfo = list.get(i);
            uploadListItems.add(
                    MultipartBody.Part.createFormData(
                            String.format("fileItems[%d].path", i),
                            uploadInfo.getPath()
                    )
            );
            File file = uploadInfo.getFileToUpload();
            RequestBody requestBody = RequestBody.create(MEDIATYPE_MULTIPART, file);
            uploadListItems.add(
                    MultipartBody.Part.createFormData(
                            String.format("fileItems[%d].fileToUpload", i),
                            file.getName(),
                            requestBody
                    )
            );
            uploadListItems.add(
                    MultipartBody.Part.createFormData(
                            String.format("fileItems[%d].replacing", i),
                            Boolean.toString(uploadInfo.getReplacing())
                    )
            );
        }

        Call<UploadResponse> call = backtoryFileStorageService.upload(
                "Bearer" + masterAccessToken,
                xBacktoryStorageId,
                uploadListItems
        );
        Response<UploadResponse> response = call.execute();
        if (response.isSuccessful())
            backtoryResponse = new BacktoryResponse<String>(response.code(), response.body().getSavedFilesUrls(), null);
        else
            backtoryResponse = new BacktoryResponse<String>(response.code(), null, ErrorUtils.parseError(response));
//        backtoryResponse = new BacktoryResponse<String>(response.code(), response.raw().body().string(), response.body().getSavedFilesUrls());
        return backtoryResponse;
    }
}

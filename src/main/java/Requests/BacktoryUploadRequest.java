package Requests;

import Internal.BacktoryFileStrorageService;
import Responses.BacktoryResponse;
import Structure.UploadInfo;
import Structure.UploadItemsInfo;
import Structure.UploadResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BacktoryUploadRequest implements BacktoryRequest<String> {
    private OkHttpClient.Builder client = new OkHttpClient.Builder();
    private static final MediaType MEDIATYPE_MULTIPART = MediaType.parse("multipart/form-data");
    private UploadItemsInfo uploadInfoItems;
    private final String xBacktoryStorageId;
    private final String masterAccessToken;
    private List<String> responseList;
    private BacktoryResponse<String> backtoryResponse;
    private BacktoryFileStrorageService backtoryFileStrorageService;

    public BacktoryUploadRequest(UploadItemsInfo uploadInfoItems, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStrorageService backtoryFileStrorageService) {
        this.uploadInfoItems = uploadInfoItems;
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        this.backtoryFileStrorageService = backtoryFileStrorageService;
    }

    public BacktoryUploadRequest(UploadInfo uploadInfo, String xBacktoryStorageId, String masterAccessToken, BacktoryFileStrorageService backtoryFileStrorageService) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        this.masterAccessToken = masterAccessToken;
        ArrayList<UploadInfo> list = new ArrayList<>();
        list.add(uploadInfo);
        this.uploadInfoItems = new UploadItemsInfo(list);
        this.backtoryFileStrorageService = backtoryFileStrorageService;
    }

    @Override
    public BacktoryResponse<String> perform() throws IOException {
        List<MultipartBody.Part> uploadListItems = new ArrayList<>();
        List<UploadInfo> list = uploadInfoItems.getItems();
        for(int i = 0; i < list.size(); i++) {
            UploadInfo uploadInfo = list.get(i);
            uploadListItems.add(
                    MultipartBody.Part.createFormData(
                        String.format("fileItems[%d].path", i),
                        uploadInfo.getPath()
                    )
            );
            RequestBody reqiestBody = RequestBody.create(MEDIATYPE_MULTIPART, uploadInfo.getFileToUpload());
            uploadListItems.add(
                    MultipartBody.Part.createFormData(
                        String.format("fileItems[%d].fileToUpload", i),
                        uploadInfo.getFileToUpload().getName(),
                        reqiestBody
                    )
            );
            uploadListItems.add(
                    MultipartBody.Part.createFormData(
                        String.format("fileItems[%d].replacing", i),
                        Boolean.toString(uploadInfo.getReplacing())
                    )
            );
        }

        Call<UploadResponse> call = backtoryFileStrorageService.upload(
                "Bearer" + masterAccessToken,
                xBacktoryStorageId,
                uploadListItems
        );
        System.out.println(call.execute().body().getSavedFileUrls());
        Response<UploadResponse> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("MotherFucker Successful :)");
            System.out.println(response.body().getSavedFileUrls());
            backtoryResponse = new BacktoryResponse<String>(response.code(), response.body().getSavedFileUrls());
        } else {
            System.out.println("Fucking Unsuccessful :(");
            backtoryResponse = new BacktoryResponse<String>(response.code(), response.errorBody().string());
        }

        return backtoryResponse;

//        for (int i = 0; i < list.size(); i++) {
//            multipart.addFormDataPart("fileItems[" + i + "].path", list.get(i).getPath())
//                    .addFormDataPart("fileItems[" + i + "].fileToUpload", list.get(i).getFileToUpload().getPath(), RequestBody.create(MEDIATYPE_MULTIPART, list.get(i).getFileToUpload()))
//                    .addFormDataPart("fileItems[" + i + "].replacing", Boolean.toString(list.get(i).getReplacing()));
//        }
//        RequestBody requestBody = multipart.build();
//        Request request = new Request.Builder()
//                .url(url + "files")
//                .addHeader("Authorization", "Bearer " + masterAccessToken)
//                .addHeader("X-Backtory-Storage-Id", xBacktoryStorageId)
//                .post(requestBody)
//                .build();
//
//        Response response = BacktoryRequest.client.newCall(request).execute();
//        String responseBody = response.body().string();
//        List<String> responseList = new ArrayList<>();
//        String deserializedBody = responseBody;
//        if (response.code() >= 200 || response.code() < 300) {
//            deserializedBody = responseBody.substring(responseBody.indexOf('[') + 1, responseBody.indexOf(']'));
//            String[] responseArray = deserializedBody.split(",");
//            for (String str : responseArray)
//                responseList.add(str);
//        }
//
//        BacktoryResponse<String> backtoryResponse = new BacktoryResponse<String>(response.code(), responseBody, responseList);
//        new Gson().fromJson(responseBody, BacktoryUploadRequest.class);
//        return backtoryResponse;
    }
}
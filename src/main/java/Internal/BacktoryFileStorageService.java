package Internal;

import Structure.*;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BacktoryFileStorageService {

    @POST ("files/")
    @Multipart
    Call<UploadResponse> upload (
            @Header("Authorization") String authorization,
            @Header("X-Backtory-Storage-Id") String xBacktoryStorageId,
            @Part List<MultipartBody.Part> file
    );

    @POST ("files/rename/")
    Call<RenameResponse> rename (
            @Header("Authorization") String authorization,
            @Header("X-Backtory-Storage-Id") String xBacktoryStorageId,
            @Body RenameItemsInfo renameItemsInfo
    );

    @POST ("files/delete/")
    Call<Void> delete (
            @Header("Authorization") String authorization,
            @Header("X-Backtory-Storage-Id") String xBacktoryStorageId,
            @Body DeleteInfo deleteInfo
    );

    @POST ("directories/")
    Call<Void> createDirectory (
            @Header("Authorization") String authorization,
            @Header("X-Backtory-Storage-Id") String xBacktoryStorageId,
            @Body CreateDirectoryInfo createDirectoryInfo
    );

    @POST ("files/directoryInfo/")
    Call<DirListReponse> directoryListing (
            @Header("Authorization") String authorization,
            @Header("X-Backtory-Storage-Id") String xBacktoryStorageId,
            @Body DirectoryListingInfo directoryListingInfo
    );

}

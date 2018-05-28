package Internal;

import Structure.DeleteInfo;
import Structure.RenameItemsInfo;
import Structure.RenameResponse;
import Structure.UploadResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BacktoryFileStrorageService {

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

    @POST ("files/delete")
    Call<Void> delete (
            @Header("Authorization") String authorization,
            @Header("X-Backtory-Strorage-Id") String xBacktoryStorageId,
            @Body DeleteInfo deleteInfo
    );

}

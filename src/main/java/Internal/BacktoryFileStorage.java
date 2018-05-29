package Internal;

import Requests.BacktoryCreateDirectoryRequest;
import Requests.BacktoryDeleteRequest;
import Requests.BacktoryRenameRequest;
import Requests.BacktoryUploadRequest;
import Responses.BacktoryResponse;
import Structure.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BacktoryFileStorage {
    private BacktoryFileStorageService backtoryFileStorageService = ServiceGenerator.createService(BacktoryFileStorageService.class);
    private String xBacktoryStorageId;
    private String masterAccessToken;
    private TokenProvider tokenProvider;

    public void init(String xBacktoryAuthenticationId, String xBacktoryAuthenticationKey, String xBacktoryStorageId) {
        this.xBacktoryStorageId = xBacktoryStorageId;
        tokenProvider = new TokenProvider(xBacktoryAuthenticationId, xBacktoryAuthenticationKey);
    }

    public BacktoryResponse<String> rename(String pathToRename, String newFileName) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        RenameInfo renameInfo = new RenameInfo(pathToRename, newFileName);
        BacktoryRenameRequest backtoryRenameRequest = new BacktoryRenameRequest(renameInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
        return backtoryRenameRequest.perform();
    }

    public BacktoryResponse renameFilesSingleRequest(ArrayList<String> pathToRename, ArrayList<String> newFileName) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        RenameInfo renameInfo;
        ArrayList<RenameInfo> list = new ArrayList<>();
        for (int i = 0; i < pathToRename.size(); i++) {
            renameInfo = new RenameInfo(pathToRename.get(i), newFileName.get(i));
            list.add(renameInfo);
        }
        RenameItemsInfo renameItemsInfo = new RenameItemsInfo(list);
        BacktoryRenameRequest backtoryRenameRequest = new BacktoryRenameRequest(renameItemsInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
        return backtoryRenameRequest.perform();
    }

    public List<BacktoryResponse> renameFilesMultipleRequest(ArrayList<String> pathToRename, ArrayList<String> newFileName) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        RenameInfo renameInfo;
        BacktoryRenameRequest backtoryRenameRequest;
        ArrayList<BacktoryResponse> responses = new ArrayList<>();
        for (int i = 0; i < pathToRename.size(); i++) {
            renameInfo = new RenameInfo(pathToRename.get(i), newFileName.get(i));
            backtoryRenameRequest = new BacktoryRenameRequest(renameInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
            responses.add(backtoryRenameRequest.perform());
        }
        return responses;
    }

    public BacktoryResponse<String> upload(String fileToUpload, String path) throws IOException {
        return upload(fileToUpload, path, false);
    }

    public BacktoryResponse<String> upload(String fileToUpload, String path, boolean replacing) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        UploadInfo uploadInfo = new UploadInfo(fileToUpload, path, replacing);
        BacktoryUploadRequest backtoryUploadRequest = new BacktoryUploadRequest(uploadInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
        return backtoryUploadRequest.perform();
    }

    public BacktoryResponse<String> uploadFilesSingleRequest(ArrayList<String> fileToUpload, ArrayList<String> path) throws IOException {
        ArrayList<Boolean> replacing = new ArrayList<>();
        for (int i = 0; i < fileToUpload.size(); i++)
            replacing.add(false);
        return uploadFilesSingleRequest(fileToUpload, path, replacing);
    }

    public BacktoryResponse<String> uploadFilesSingleRequest(ArrayList<String> fileToUpload, ArrayList<String> path, ArrayList<Boolean> replacing) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        UploadInfo uploadInfo;
        ArrayList<UploadInfo> list = new ArrayList<>();
        for (int i = 0; i < fileToUpload.size(); i++) {
            uploadInfo = new UploadInfo(fileToUpload.get(i), path.get(i), replacing.get(i));
            list.add(uploadInfo);
        }
        UploadItemsInfo uploadItemsInfo = new UploadItemsInfo(list);
        BacktoryUploadRequest backtoryUploadRequest = new BacktoryUploadRequest(uploadItemsInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
        return backtoryUploadRequest.perform();
    }

    public List<BacktoryResponse<String>> uploadFilesMultipleRequests(ArrayList<String> fileToUpload, ArrayList<String> path) throws IOException {
        ArrayList<Boolean> replacing = new ArrayList<>();
        for (int i = 0; i < fileToUpload.size(); i++)
            replacing.add(false);
        return uploadFilesMultipleRequests(fileToUpload, path, replacing);
    }

    public List<BacktoryResponse<String>> uploadFilesMultipleRequests(ArrayList<String> fileToUpload, ArrayList<String> path, ArrayList<Boolean> replacing) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        UploadInfo uploadInfo;
        ArrayList<BacktoryResponse<String>> responses = new ArrayList<>();
        BacktoryUploadRequest backtoryUploadRequest;
        for (int i = 0; i < fileToUpload.size(); i++) {
            uploadInfo = new UploadInfo(fileToUpload.get(i), path.get(i), replacing.get(i));
            backtoryUploadRequest = new BacktoryUploadRequest(uploadInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
            BacktoryResponse<String> response = backtoryUploadRequest.perform();
            responses.add(response);
        }
        return responses;
    }

    public List<BacktoryResponse<String>> uploadDirectory(String directoryToUpload, String path) throws IOException {
        checkDirectoryExistence(directoryToUpload);
        masterAccessToken = tokenProvider.getToken();
        UploadItemsInfo uploadItemsInfo = uploadDirectoryListGenerator(directoryToUpload, path);
        BacktoryUploadRequest backtoryUploadRequest;
        ArrayList<BacktoryResponse<String>> responses = new ArrayList<>();
        for (UploadInfo uploadInfo : uploadItemsInfo.getItems()) {
            if (uploadInfo.getFileToUpload().isDirectory()) {
                uploadDirectory(uploadInfo.getFileToUpload().getPath(), uploadInfo.getPath());
            } else {
                backtoryUploadRequest = new BacktoryUploadRequest(uploadInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
                responses.add(backtoryUploadRequest.perform());
            }
        }
        return responses;
    }

    public List<List<BacktoryResponse<String>>> uploadDirectories(ArrayList<String> directoriesToUpload, ArrayList<String> paths) throws IOException {
        checkDirectoriesExistence(directoriesToUpload);
        masterAccessToken = tokenProvider.getToken();
        ArrayList<List<BacktoryResponse<String>>> responses = new ArrayList<>();
        for (int i = 0; i < directoriesToUpload.size(); i++) {
            System.out.println(directoriesToUpload.get(i) + " in : " + paths.get(i));
            responses.add(uploadDirectory(directoriesToUpload.get(i), paths.get(i)));
        }
        return responses;
    }

    private UploadItemsInfo uploadDirectoryListGenerator(String directoryToUpload, String path) {
        File directory = new File(directoryToUpload);
        File[] files = directory.listFiles((dir, name) -> !name.equals(".DS_Store"));
        ArrayList<UploadInfo> list = new ArrayList<>();
        for (File file : files) { // TODO (should be handled not null file)
            list.add(new UploadInfo(file.getPath(), path + "/" + file.getParent()));
        }
        return new UploadItemsInfo(list);
    }

    public BacktoryResponse<String> delete(String url, boolean forced) throws IOException {
        if (url.indexOf("/") > 0)
            url = url.substring(url.indexOf("/"));
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<Boolean> forcedList = new ArrayList<>();
        urls.add(url);
        forcedList.add(forced);
        return deleteFiles(urls, forcedList);
    }

    public BacktoryResponse<String> deleteFiles(ArrayList<String> urls, ArrayList<Boolean> forced) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        DeleteInfo deleteInfo = new DeleteInfo(urls, forced);
        BacktoryDeleteRequest backtoryDeleteRequest = new BacktoryDeleteRequest(deleteInfo, xBacktoryStorageId, masterAccessToken, backtoryFileStorageService);
        return backtoryDeleteRequest.perform();
    }

    public BacktoryResponse<String> createDirectory(String path) throws IOException {
        masterAccessToken = tokenProvider.getToken();
        CreateDirectoryInfo createDirectoryInfo = new CreateDirectoryInfo(path);
        BacktoryCreateDirectoryRequest backtoryCreateDirectoryRequest = new BacktoryCreateDirectoryRequest(createDirectoryInfo, xBacktoryStorageId, masterAccessToken);
        return backtoryCreateDirectoryRequest.perform();
    }

    private void checkFilesExistence(ArrayList<String> fileToUpload) throws FileNotFoundException {
        for (int i = 0; i < fileToUpload.size(); i++) {
            if (!new File(fileToUpload.get(i)).exists())
                throw new FileNotFoundException("File " + (i + 1) + " of " + fileToUpload.size() + " does not exist");
        }
    }

    private void checkDirectoryExistence(String directoryToUpload) throws FileNotFoundException {
        ArrayList<String> directoriesToUpload = new ArrayList<>();
        directoriesToUpload.add(directoryToUpload);
        checkDirectoriesExistence(directoriesToUpload);
    }

    private void checkDirectoriesExistence(ArrayList<String> directoriesToUpload) throws FileNotFoundException {
        for (String aDirectoriesToUpload : directoriesToUpload) {
            if (!new File(aDirectoriesToUpload).exists())
                throw new FileNotFoundException("Directory " + aDirectoriesToUpload + " does not exist");
        }
    }
}

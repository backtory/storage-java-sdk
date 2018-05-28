//import Internal.BacktoryFileStorage;
//import Responses.BacktoryResponse;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class BacktoryFileStorageOut {
//    private ArrayList<BacktoryResponse> responses;
//    private ArrayList<ArrayList<BacktoryResponse>> uploadDirectoriesResponse;
//    private BacktoryFileStorage backtoryFileStorage;
//
//    public void init(String xBacktoryAuthenticationId, String xBacktoryAuthenticationKey, String xBacktoryStorageId) {
//        backtoryFileStorage = new BacktoryFileStorage();
//        backtoryFileStorage.init(xBacktoryAuthenticationId, xBacktoryAuthenticationKey, xBacktoryStorageId);
//    }
//
//    public void rename(String pathToRename, String newFileName) throws IOException {
//        responses = backtoryFileStorage.rename(pathToRename, newFileName);
//    }
//
//    public void renameFiles(ArrayList<String> pathToRename, ArrayList<String> newFileName, boolean isSingleRequest) throws IOException {
//        responses = backtoryFileStorage.renameFiles(pathToRename, newFileName, isSingleRequest);
//    }
//
//    public void upload(String fileToUplaod, String path) throws IOException {
//        responses = backtoryFileStorage.upload(fileToUplaod, path);
//    }
//
//    public void upload(String fileToUpload, String path, String replacing) throws IOException {
//        responses = backtoryFileStorage.upload(fileToUpload, path, replacing);
//    }
//
//    public void uploadFiles(ArrayList<String> fileToUpload, ArrayList<String> path, boolean isSingleRequest) throws IOException {
//        responses = backtoryFileStorage.uploadFiles(fileToUpload, path, isSingleRequest);
//    }
//
//    public void uploadFiles(ArrayList<String> fileToUpload, ArrayList<String> path, ArrayList<String> replacing, boolean isSingleRequest) throws IOException {
//        responses = backtoryFileStorage.uploadFiles(fileToUpload, path, replacing, isSingleRequest);
//    }
//
//    public void uploadDirectory(String directoryToUpload, String path) throws IOException {
//        responses = backtoryFileStorage.uploadDirectory(directoryToUpload, path);
//    }
//
//    public void uploadDirecotories(ArrayList<String> directoriesToUpload, ArrayList<String> paths) throws IOException {
//        uploadDirectoriesResponses = backtoryFileStorage.uploadDirectories(directoriesToUpload, paths);
//    }
//
//    public void delete(String url, boolean forced) throws IOException {
//        responses = backtoryFileStorage.delete(url, forced);
//    }
//
//    public void deleteFiles(ArrayList<String> urls, ArrayList<Boolean> forced) throws IOException {
//        responses = backtoryFileStorage.deleteFiles(urls, forced);
//    }
//
//    public void createDirectory(String path) throws IOException {
//        responses = backtoryFileStorage.createDirectory(path);
//    }
//
//    public ArrayList<BacktoryResponse> getResponses() {
//        return responses;
//    }
//
//    public void setResponses(ArrayList<BacktoryResponse> responses) {
//        this.responses = responses;
//    }
//
//    public ArrayList<ArrayList<BacktoryResponse>> getUploadDirectoriesResponses() {
//        return uploadDirectoriesResponses;
//    }
//
//    public void setUploadDirectoriesResponses(ArrayList<ArrayList<BacktoryResponse>> uploadDirectoriesResponses) {
//        this.uploadDirectoriesResponses = uploadDirectoriesResponses;
//    }
//}

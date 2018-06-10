import Internal.BacktoryFileStorage;
import Responses.BacktoryResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BacktoryFileStorageTest {
    private final BacktoryFileStorage backtoryFileStorage = new BacktoryFileStorage();

    @Before
    public void initializingBacktoryFileStorage(){
        backtoryFileStorage.init(
                "5a658f91e4b0f52492b4979c",
                "43cb8ec1a1884913a28ea5f3",
                "5a683cb9e4b060507b572358");
    }

    /* ------------------------ Rename Test ------------------------ */

    @Test
    public void returnOKStatusCodeOnRenameCorrectCall() throws IOException {
        Assert.assertEquals(BacktoryResponse.OK_CREATED, backtoryFileStorage.upload("directory/delete.txt", "/").getStatusCode());
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.rename("/delete.txt", "mohsen.txt").getStatusCode());
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.delete("/mohsen.txt", true).getStatusCode());
    }

    @Test
    public void returnNotFoundErrorCodeWhenInvalidAddressFileGivenToRename() throws IOException {
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.rename("lajsdlkf", "/UploadExample").getStatusCode());
    }

    @Test
    public void returnNotFoundErrorCodeForTheSpecificMissingFileWhenInvalidAddressFileGivenToRenameFilesMultipleRequests() throws IOException {
        ArrayList<String> files = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        files.add("directory/delete.txt");
        files.add("directory/rename.txt");
        paths.add("/");
        paths.add("/");
        List<BacktoryResponse<String>> uploadResponse = backtoryFileStorage.uploadFilesMultipleRequests(files, paths);
        Assert.assertEquals(BacktoryResponse.OK_CREATED, uploadResponse.get(0).getStatusCode());
        Assert.assertEquals(BacktoryResponse.OK_CREATED, uploadResponse.get(1).getStatusCode());
        ArrayList<String> pathToRename = new ArrayList<>();
        ArrayList<String> newFileName = new ArrayList<>();
        pathToRename.add(uploadResponse.get(0).getResponseList().get(0));
        pathToRename.add("ladjflajd");
        newFileName.add("newFile.txt");
        newFileName.add("123x");
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.renameFilesMultipleRequest(pathToRename, newFileName).get(1).getStatusCode());
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/newFile.txt");
        urls.add("/rename.txt");
        ArrayList<Boolean> forced = new ArrayList<>();
        forced.add(true);
        forced.add(true);
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.deleteFiles(urls, forced).getStatusCode());
    }

    @Test
    public void returnNotFoundErrorCodeForTheSpecificMissingFileWhenInvalidAddressFileGivenToRenameFilesSingleRequests() throws IOException {
        ArrayList<String> fileToUpload = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        fileToUpload.add("directory/delete.txt");
        fileToUpload.add("directory/rename.txt");
        path.add("/");
        path.add("/");
        BacktoryResponse uploadResponse = backtoryFileStorage.uploadFilesSingleRequest(fileToUpload, path);
        Assert.assertEquals(BacktoryResponse.OK_CREATED, uploadResponse.getStatusCode());
        ArrayList<String> pathToRename = new ArrayList<>();
        ArrayList<String> newFileName = new ArrayList<>();
        pathToRename.add((String) uploadResponse.getResponseList().get(0));
        pathToRename.add("ladjflajd");
        newFileName.add("newFile.txt");
        newFileName.add("123x");
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.renameFilesSingleRequest(pathToRename, newFileName).getStatusCode());
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/delete.txt");
        urls.add("/rename.txt");
        ArrayList<Boolean> forced = new ArrayList<>();
        forced.add(true);
        forced.add(true);
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.deleteFiles(urls, forced).getStatusCode());
    }

    @Test
    public void returnEmptyListInBacktoryResponseWhenUnsuccessful() throws IOException {
        Assert.assertNotNull(backtoryFileStorage.rename("lkasjdfl", "hello").getResponseList());
    }

    @Test
    public void returnEXCEPTION_FAILEDStatusCodeWhenNewFileNameAlreadyExistInRename() throws IOException {
        backtoryFileStorage.upload("directory/delete.txt", "/");
        backtoryFileStorage.upload("directory/rename.txt", "/");
        Assert.assertEquals(BacktoryResponse.EXCEPTION_FAILED, backtoryFileStorage.rename("/delete.txt", "rename.txt").getStatusCode());
        backtoryFileStorage.delete("/delete.txt", true);
        backtoryFileStorage.delete("/rename.txt", true);
    }

    @Test
    public void returnEXCEPTION_FAILEDStatusCodeWhenNewFileNameAlreadyExistInRenameFilesSingleRequest() throws IOException {
        backtoryFileStorage.upload("directory/delete.txt", "/");
        backtoryFileStorage.upload("directory/rename.txt", "/");
        backtoryFileStorage.upload("directory/mohsen.txt", "/");
        ArrayList<String> pathToRename = new ArrayList<>();
        ArrayList<String> newFileName = new ArrayList<>();
        pathToRename.add("/delete.txt");
        pathToRename.add("/rename.txt");
        newFileName.add("mohsen.txt");
        newFileName.add("correct.txt");
        Assert.assertEquals(BacktoryResponse.EXCEPTION_FAILED, backtoryFileStorage.renameFilesSingleRequest(pathToRename, newFileName).getStatusCode());
        backtoryFileStorage.delete("/delete.txt", true);
        backtoryFileStorage.delete("/rename.txt", true);
        backtoryFileStorage.delete("/mohsen.txt", true);
    }

    @Test
    public void returnEXCEPTION_FAILEDStatusCodeForCorrectFileWhenNewFileNameAlreadyExistInRenameFilesMultipleRequests() throws IOException {
        backtoryFileStorage.upload("directory/delete.txt", "/");
        backtoryFileStorage.upload("directory/rename.txt", "/");
        backtoryFileStorage.upload("directory/mohsen.txt", "/");
        ArrayList<String> pathToRename = new ArrayList<>();
        ArrayList<String> newFileName = new ArrayList<>();
        pathToRename.add("/delete.txt");
        pathToRename.add("/rename.txt");
        newFileName.add("correct.txt");
        newFileName.add("mohsen.txt");
        Assert.assertEquals(BacktoryResponse.EXCEPTION_FAILED, backtoryFileStorage.renameFilesMultipleRequest(pathToRename, newFileName).get(1).getStatusCode());
        backtoryFileStorage.delete("/correct.txt", true);
        backtoryFileStorage.delete("/rename.txt", true);
        backtoryFileStorage.delete("/mohsen.txt", true);
    }

    /* ------------------------ Upload Test ------------------------ */

    @Test
    public void returnOKStatusCodeOnUploadCorrectCall() throws IOException {
        Assert.assertEquals(BacktoryResponse.OK_CREATED ,backtoryFileStorage.upload("directory/delete.txt", "/").getStatusCode());
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.delete("/delete.txt", true).getStatusCode());
    }

    @Test(expected = FileNotFoundException.class)
    public void throwFileNotFoundExceptionWhenInvalidFileGiven() throws IOException {
        backtoryFileStorage.upload("lkasjdflk", "/");
    }

    @Test(expected = FileNotFoundException.class)
    public void throwFileNotFoundExceptionWithSpecificFileIndexWhenOneFileDoesNotExistInUploadFiles() throws IOException {
        ArrayList<String> fileToUpload = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        fileToUpload.add("UploadDir/delete.txt");
        fileToUpload.add("lajsdfl");
        path.add("/");
        path.add("/");
        backtoryFileStorage.uploadFilesSingleRequest(fileToUpload, path);
    }

    @Test(expected = FileNotFoundException.class)
    public void throwFileNotFoundExceptionWhenDirectoryNotExistsInUploadDirectory() throws IOException {
        backtoryFileStorage.uploadDirectory("upload", "/");
    }

    @Test (expected = FileNotFoundException.class)
    public void throwFileNotFoundExceptionWhenDirectoryDoesNotExistInUploadDirectories() throws IOException {
        ArrayList<String> directoriesToUpload = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        directoriesToUpload.add("UploadDir");
        directoriesToUpload.add("lajdsflk");
        paths.add("/");
        paths.add("/");
        backtoryFileStorage.uploadDirectories(directoriesToUpload, paths);
    }

    @Test
    public void getOK_CREATEDStatusCodeWhenPathNotExistsInBucketInUpload() throws IOException {
        Assert.assertEquals(BacktoryResponse.OK_CREATED, backtoryFileStorage.upload("directory/delete.txt", "/123").getStatusCode());
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.delete("/123/", true).getStatusCode());
    }

    @Test
    public void getOK_CREATEDStatusCodeWhenPathNotExistsInBucketInUploadFiles() throws IOException {
        ArrayList<String> fileToUpload = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        fileToUpload.add("directory/delete.txt");
        fileToUpload.add("directory/delete.txt");
        path.add("/");
        path.add("/1234");
        Assert.assertEquals(BacktoryResponse.OK_CREATED, backtoryFileStorage.uploadFilesMultipleRequests(fileToUpload, path).get(1).getStatusCode());
        backtoryFileStorage.delete("/delete.txt", true);
        backtoryFileStorage.delete("/1234/", true);
    }

    /* ------------------------ Delete Test ------------------------ */

    @Test
    public void returnNotFoundErrorCodeWhenInvalidFileAddressGiven() throws IOException {
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.delete("klsjdfklas", false).getStatusCode());
    }

    @Test
    public void returnNotFoundErrorForTheSpecificFileInDeleteFilesWhenInvalidFileAddressGiven() throws IOException {
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<Boolean> forced = new ArrayList<>();
        backtoryFileStorage.upload("directory/delete.txt", "/");
        urls.add("/delete.txt");
        urls.add("/ali.png");
        forced.add(true);
        forced.add(true);
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.deleteFiles(urls, forced).getStatusCode());
    }

    /* ------------------------ CreateDirectory Test ------------------------ */

    @Test
    public void returnOKStatusCodeOnCreateDirectoryCorrectCall() throws IOException {
        Assert.assertEquals(BacktoryResponse.OK_CREATED, backtoryFileStorage.createDirectory("mohsen").getStatusCode());
        Assert.assertEquals(BacktoryResponse.OK, backtoryFileStorage.delete("/mohsen/", true).getStatusCode());
    }

}

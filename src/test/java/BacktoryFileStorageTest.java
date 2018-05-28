import Internal.BacktoryFileStorage;
import Responses.BacktoryResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BacktoryFileStorageTest {
    BacktoryFileStorage backtoryFileStorage = new BacktoryFileStorage();

    @Before
    public void initializingBacktoryFileStorage(){
        backtoryFileStorage.init(
                "5a658f91e4b0f52492b4979c",
                "43cb8ec1a1884913a28ea5f3",
                "5a683cb9e4b060507b572358");
    }

    /* ------------------------ Rename Test ------------------------ */

    @Test
    public void returnNotFoundErrorCodeWhenInvalidAddressFileGivenToRename() throws IOException {
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.rename("lajsdlkf", "/UploadExample").get(0).getBacktoryException().getStatusCode());
    }

    @Test
    public void returnNotFoundErrorCodeForTheSpecificMissingFileWhenInvalidAddressFileGivenToRenameFilesMultipleRequests() throws IOException {
        ArrayList<String> pathToRename = new ArrayList<>();
        ArrayList<String> newFileName = new ArrayList<>();
        pathToRename.add("alsjdflk");
        pathToRename.add("newfile.txt");
        pathToRename.add("1234.png");
        newFileName.add("1234");
        newFileName.add("new.txt");
        newFileName.add("123.png");
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.renameFiles(pathToRename, newFileName, false).get(1).getBacktoryException().getStatusCode());
        ArrayList<String>  correctPathToRename = new ArrayList<>();
        ArrayList<String> correctNewFileName = new ArrayList<>();
        correctPathToRename.add("new.txt");
        correctPathToRename.add("123.png");
        correctNewFileName.add("newfile.txt");
        correctNewFileName.add("1234.png");
        backtoryFileStorage.renameFiles(correctPathToRename, correctNewFileName, false);
    }

    @Test
    public void returnNotFoundErrorCodeForTheSpecificMissingFileWhenInvalidAddressFileGivenToRenameFilesSingleRequests() throws IOException {
        ArrayList<String> pathToRename = new ArrayList<>();
        ArrayList<String> newFileName = new ArrayList<>();
        pathToRename.add("newfile.txt");
        pathToRename.add("alsjdflk");
        pathToRename.add("1234.png");
        newFileName.add("new.txt");
        newFileName.add("1234");
        newFileName.add("123.png");
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.renameFiles(pathToRename, newFileName, false).get(0).getBacktoryException().getStatusCode());
        ArrayList<String>  correctPathToRename = new ArrayList<>();
        ArrayList<String> correctNewFileName = new ArrayList<>();
        correctPathToRename.add("new.txt");
        correctPathToRename.add("123.png");
        correctNewFileName.add("newfile.txt");
        correctNewFileName.add("1234.png");
        backtoryFileStorage.renameFiles(correctPathToRename, correctNewFileName, false);
    }

    /* ------------------------ Upload Test ------------------------ */

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
        backtoryFileStorage.uploadFiles(fileToUpload, path, false);
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
        Assert.assertEquals(BacktoryResponse.OK_CREATED, backtoryFileStorage.upload("delete.txt", "/123").get(0).getStatusCode());
        backtoryFileStorage.delete("/123/", true);
    }

    @Test
    public void getOK_CREATEDStatusCodeWhenPathNotExistsInBucketInUploadFiles() throws IOException {
        ArrayList<String> fileToUpload = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        fileToUpload.add("delete.txt");
        fileToUpload.add("delete.txt");
        path.add("/");
        path.add("/123");
        Assert.assertEquals(BacktoryResponse.OK_CREATED, backtoryFileStorage.uploadFiles(fileToUpload, path, false).get(1).getStatusCode());
        backtoryFileStorage.delete("/delete.txt", true);
        backtoryFileStorage.delete("/123/delete.txt", true);
    }

    /* ------------------------ Delete Test ------------------------ */

    @Test
    public void returnNotFoundErrorCodeWhenInvalidFileAddressGiven() throws IOException {
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.delete("klsjdfklas", false).get(0).getBacktoryException().getStatusCode());
    }

    @Test
    public void returnNotFoundErrorForTheSpecificFileInDeleteFilesWhenInvalidFileAddressGiven() throws IOException {
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<Boolean> forced = new ArrayList<>();
        urls.add("/delete.txt");
        urls.add("/ali.png");
        forced.add(true);
        forced.add(true);
        Assert.assertEquals(BacktoryResponse.NOT_FOUND, backtoryFileStorage.deleteFiles(urls, forced).get(0).getStatusCode());
        backtoryFileStorage.upload("delete.txt", "/");
    }

}

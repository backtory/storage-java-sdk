package Structure;

import java.io.File;

public class UploadInfo {
    File fileToUpload;
    String path;
    boolean replacing;

    public UploadInfo(String fileToUpload, String path, boolean replacing) {
        this.fileToUpload = new File(fileToUpload);
        this.path = path;
        this.replacing = replacing;
    }

    public UploadInfo(String fileToUpload, String path) {
        this(fileToUpload, path, false);
    }

    public File getFileToUpload() {
        return fileToUpload;
    }

    public String getPath() {
        return path;
    }

    public boolean getReplacing() {
        return replacing;
    }
}


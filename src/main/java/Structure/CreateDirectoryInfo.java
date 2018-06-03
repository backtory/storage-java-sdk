package Structure;

public class CreateDirectoryInfo {
    String path;

    public CreateDirectoryInfo(String path) {
        this.path = path.charAt(0) == '/' ? path : "/" + path;
    }
}

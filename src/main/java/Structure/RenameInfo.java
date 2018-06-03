package Structure;

public class RenameInfo {
    String pathToRename;
    String newFileName;

    public RenameInfo(String pathToRename, String newFileName) {
        this.pathToRename = pathToRename.charAt(0) == '/' ? pathToRename : "/" + pathToRename;
        this.newFileName = newFileName;
    }
}

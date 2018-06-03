package Structure;

import com.google.gson.JsonObject;

import java.util.List;

public class DirectoryListingResponse {
    List<JsonObject> files;

    public List<JsonObject> getFiles() {
        return files;
    }
}

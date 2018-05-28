package Structure;

import java.util.List;

public class UploadItemsInfo {
    List<UploadInfo> items;

    public UploadItemsInfo(List<UploadInfo> items) {
        this.items = items;
    }

    public List<UploadInfo> getItems() {
        return items;
    }
}

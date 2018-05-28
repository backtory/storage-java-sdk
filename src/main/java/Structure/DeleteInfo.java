package Structure;

import java.util.List;

public class DeleteInfo {
    List<String> urls;
    List<Boolean> forced;

    public DeleteInfo(List<String> urls, List<Boolean> forced) {
        this.urls = urls;
        this.forced = forced;
    }
}

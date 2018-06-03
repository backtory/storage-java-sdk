package Structure;

public class DirectoryListingInfo {
    String url;
    int pageNumber;
    int pageSize;
    String sortingType;

    public DirectoryListingInfo(String url, int pageNumber, int pageSize, String sortingType) {
        this.url = url;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortingType = sortingType;
    }
}

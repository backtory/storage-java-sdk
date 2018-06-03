package Structure;

public class DirectoryListingJsonObject {
    private String guid;
    private String url;
    private String ownerId;
    private float creationDate;
    private float lastModificationDate;
    private int level;
    private int fileSizeInBytes;
    private int realFileSizeInBytes;
    private int numberOfDirectChildren;
    private int numberOfTotalChildren;
    private String md5Hash;
    private String crcHash;
    private String thumbnails;
    private String defaultThumbnail;
    private String metaData;
    private boolean directory;
    private boolean isDirectory;

    public String getUrl() {
        return url;
    }

    public float getCreationDate() {
        return creationDate;
    }

    public float getLastModificationDate() {
        return lastModificationDate;
    }

    public int getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public int getRealFileSizeInBytes() {
        return realFileSizeInBytes;
    }

    public int getNumberOfDirectChildren() {
        return numberOfDirectChildren;
    }

    public int getNumberOfTotalChildren() {
        return numberOfTotalChildren;
    }

    public boolean isDirectory() {
        return directory;
    }
}

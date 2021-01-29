package pl.edu.pjwstk.Request;

public class PhotoRequest {
    private String photoName;
    private int photoPosition;

    public PhotoRequest() {
    }

    public PhotoRequest(String photoName, int photoPosition) {
        this.photoName = photoName;
        this.photoPosition = photoPosition;
    }

    public String getPhotoName() {
        return photoName;
    }

    public int getPhotoPosition() {
        return photoPosition;
    }
}

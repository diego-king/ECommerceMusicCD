package ca.edu.uottawa.csi5380.exception.response;

public class ExceptionJsonInfo {

    private final String url;
    private final String message;

    public ExceptionJsonInfo(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ExceptionJsonInfo{" +
                "url='" + url + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

import java.util.Date;

public class SimpleHttpResponseHeader {
    private String httpVersion;
    private int statusCode;
    private String description;
    private String contentType;
    private int contentLength;
    private Date date;
    private String server;

    public int getStatusCode() {
        return statusCode;
    }

    public SimpleHttpResponseHeader() {
    }

    public SimpleHttpResponseHeader httpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public SimpleHttpResponseHeader statusCode(int statusCode) {
        this.statusCode = statusCode;
        statusCodeToDescription();
        return this;
    }

    public SimpleHttpResponseHeader contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public SimpleHttpResponseHeader contentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public SimpleHttpResponseHeader date(Date date) {
        this.date = date;
        return this;
    }

    public SimpleHttpResponseHeader server(String server) {
        this.server = server;
        return this;
    }

    private void statusCodeToDescription() {
        switch (this.statusCode) {
            case 200: {
                this.description = "OK";
                break;
            }
            case 400: {
                this.description = "Bad Request";
                break;
            }
            case 403: {
                this.description = "Forbidden";
                break;
            }
            case 404: {
                this.description = "Not Found";
                break;
            }
            default: this.description = "Unknown";
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/").append(httpVersion).append(" ");
        sb.append(statusCode).append(" ").append(description).append("\n");
        sb.append("Server: ").append(server).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Content-type: ").append(contentType).append("\n");
        sb.append("Content-length: ").append(contentLength).append("\n");
        sb.append("\n");
        return sb.toString();
    }
}

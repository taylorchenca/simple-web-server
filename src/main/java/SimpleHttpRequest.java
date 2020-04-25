import java.util.StringTokenizer;

public class SimpleHttpRequest {
    private String method;
    private String endPoint = "";
    private String httpVersion;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public SimpleHttpRequest(String method, String endPoint, String httpVersion) {
        this.method = method;
        this.endPoint = endPoint;
        this.httpVersion = httpVersion;
    }

    public SimpleHttpRequest(String request) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(request);
        this.method = tokenizer.nextToken();
        if (!method.equals("GET")) {
            throw new Exception("Invalid method");
        }
        this.endPoint = tokenizer.nextToken();
        this.httpVersion = tokenizer.nextToken();
        if (!httpVersion.equals("HTTP/1.0") && !httpVersion.equals("HTTP/1.1")) {
            throw new Exception("Invalid http version");
        }
    }
}

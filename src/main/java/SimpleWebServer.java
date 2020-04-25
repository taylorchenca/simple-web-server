import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleWebServer implements Runnable {
    static final String SERVER_NAME = "Simple server";
    private Socket socket;
    public SimpleWebServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            String request;
            while ((request = bufferedReader.readLine()) != null) {
                System.out.println("Request: " + request);
                while (!bufferedReader.readLine().equals("")) {
                    //Discard the rest of the Http request as we only want the first line
                }
                SimpleHttpRequest httpRequest = new SimpleHttpRequest(request);
                SimpleFileHandler fileHandler = new SimpleFileHandler(
                        Main.documentRoot, httpRequest.getEndPoint());

                SimpleHttpResponseHeader header = new SimpleHttpResponseHeader()
                        .httpVersion("1.1").statusCode(200).server(SERVER_NAME).date(new Date())
                        .contentType(fileHandler.getContentType())
                        .contentLength(fileHandler.getLength());

                if (!httpRequest.isValid()) header.statusCode(400);
                else if (fileHandler.getFileStatus() == FileStatus.Status.NOT_FOUND)
                    header.statusCode(404);//TODO: implement and test 404 case
                else if (fileHandler.getFileStatus() == FileStatus.Status.DENIED)
                    header.statusCode(403);

                printWriter.print(header);
                printWriter.flush();

                if (fileHandler.getFileStatus() == FileStatus.Status.SUCCESS) {
                    bufferedOutputStream.write(fileHandler.getByteArray(), 0, fileHandler.getLength());
                    bufferedOutputStream.flush();
                    System.out.println("File " + httpRequest.getEndPoint() + " of type " + fileHandler.getContentType() + " returned");
                }
            }
        } catch (IOException ioe) {
            System.err.println("Server error : " + ioe);
        } finally {
            try {
                bufferedReader.close();
                printWriter.close();
                bufferedOutputStream.close();
                socket.close();
            } catch (Exception e) {
                System.err.println("Error closing stream : " + e.getMessage());
            }
//            System.out.println("Connection closed.\n");
        }
    }
}
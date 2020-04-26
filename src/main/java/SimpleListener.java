import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleListener {
    private ServerSocket serverSocket;
    private int counter = 0;
    private final int defaultTimeOut = 5000; //If there is only one connection, set timeout to 5 seconds
    public static int activeConnections = 0;
    public SimpleListener(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            activeConnections++;
            int timeOut = defaultTimeOut / activeConnections;
            socket.setSoTimeout(timeOut);
            SimpleWebServer simpleWebServer = new SimpleWebServer(socket);

            System.out.println("Connection" + counter++ + " opened. (" + new Date() + ") with timeout " + timeOut + "ms");
            Thread thread = new Thread(simpleWebServer);
            thread.start();
        }
    }
}

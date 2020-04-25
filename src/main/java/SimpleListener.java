import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleListener {
    private ServerSocket serverSocket;

    public SimpleListener(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while (true) {
//            serverSocket.setSoTimeout(60000);
//            SimpleWebServer simpleWebServer = new SimpleWebServer(serverSocket.accept());

            Socket socket = serverSocket.accept();
//            socket.setKeepAlive(true);
//            socket.setSoTimeout(5000);
//            socket.setSoTimeout(50000);
//            socket.setTcpNoDelay(true);
            SimpleWebServer simpleWebServer = new SimpleWebServer(socket);

            System.out.println("Connection opened. (" + new Date() + ")");
            Thread thread = new Thread(simpleWebServer);
            System.out.println(thread.toString() + " created");
            thread.start();
        }
    }
}

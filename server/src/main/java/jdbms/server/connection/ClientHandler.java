package jdbms.server.connection;

import jdbms.server.user.User;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 17:33
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private User user;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        Logger.Log(Importance.DEBUG, "Client Thread Entered");
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            Logger.Log(Importance.DEBUG, "Client Thread Prepared");
            do {
                bytesRead = inputStream.read(buffer);
                Logger.Log(Importance.DEBUG, "Client Thread Receiving");
                String message = new String(buffer, 0, bytesRead);
                System.out.println("Received message from " + clientSocket.getInetAddress().getHostName() + ": " + message);
                outputStream.write(("Echo: " + message).getBytes());
            } while (bytesRead != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

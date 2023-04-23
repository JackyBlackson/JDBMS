package jdbms.server.connection;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 17:25
 */
import jdbms.server.config.SystemConfig;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientTCPServer {
    private static final int PORT = 8080;
    private static final int MAX_THREADS = 10;
    private static final int ACCEPT_TIMEOUT_MS = 1000;

    public static void main(String[] args) {
        try {
            new MultiClientTCPServer().StartMultiThread();
        } catch (Exception e) {
            Logger.LogException(Importance.SEVERE, e);
        }
    }

    public void StartSingleThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SystemConfig.ServerPort);
            System.out.println("Server started on port " + SystemConfig.ServerPort);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostName());
                executorService.submit(new ClientHandler(clientSocket));
                Logger.Log(Importance.DEBUG, "Connection Accepted.");
            }
        } catch (IOException e) {
            Logger.LogException(Importance.WARNING, e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    Logger.LogException(Importance.WARNING, e);
                }
            }
        }
    }

    public void StartMultiThread() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SystemConfig.ServerPort);
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

        System.out.println("Server started on port " + SystemConfig.ServerPort);

        // Start a thread to accept client connections
        new Thread(() -> {
            while (true) {
                try {
                    serverSocket.setSoTimeout(ACCEPT_TIMEOUT_MS);
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());
                    threadPool.execute(new ClientHandler(clientSocket));
                } catch (SocketTimeoutException e) {
                    // no new client connections, continue waiting
                } catch (IOException e) {
                    Logger.LogException(Importance.WARNING, e);
                }
            }
        }).start();
    }
}


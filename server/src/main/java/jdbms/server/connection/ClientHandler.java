package jdbms.server.connection;

import jdbms.server.user.User;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
            ByteBuffer buffer = ByteBuffer.allocate(100000000); // 创建大小为140960的ByteBuffer缓冲区
            int bytesRead = 0;
            Logger.Log(Importance.DEBUG, "Client Thread Prepared");
            do {
                int bytesReadeadThisTime = 0;
                byte[] re = new byte[4096];
                bytesReadeadThisTime = inputStream.read(re) + bytesRead;
                System.out.println(bytesRead);
                System.out.println(bytesReadeadThisTime);
                System.out.println(buffer.remaining());
                buffer.position(bytesRead);
                buffer.put(re, 0, bytesReadeadThisTime);
                bytesRead += bytesReadeadThisTime;
                Logger.Log(Importance.DEBUG, "Client Thread Receiving");
                //成功读取
                if (bytesRead > 0) {
                    buffer.position(bytesRead);
                    buffer.flip();
                    byte[] data = new byte[bytesRead];
                    buffer.get(data);
                    String message = new String(data, 0, bytesRead);
                    //遇到消息尾 #
                    if (message.contains("#")) {
                        String fullMessage = message.trim();
                        //检测消息是否是消息头开始
                        if (fullMessage.startsWith("$")) {
                            //此处消息接收完成，交给处理器处理
                            System.out.println("Received message from " + clientSocket.getInetAddress().getHostName() + ": " + fullMessage);
                            outputStream.write(("Echo: " + fullMessage).getBytes());
                        } else {    //消息出现错误，没有检测到消息头：发出警报并忽略这条消息
                            Logger.Log(Importance.WARNING, "收到了不完整的消息，是否网络或客户端出现错误: " + fullMessage);
                        }
                        bytesRead = 0;
                        buffer.clear();
                    }
                }
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

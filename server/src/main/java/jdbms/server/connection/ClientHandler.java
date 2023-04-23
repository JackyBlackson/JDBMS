package jdbms.server.connection;

import jdbms.server.config.SystemConfig;
import jdbms.server.user.User;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 17:33
 */
public class ClientHandler implements Runnable {
    String IpAddress;
    private Socket clientSocket;
    private String userName = "Unknown User";
    private volatile Boolean isLoggedIn = false;
    private volatile Boolean isTimeOut = false;

    public ClientHandler(Socket clientSocket, String ip) {
        this.clientSocket = clientSocket;
        this.IpAddress = ip;
    }

    @Override
    public void run() {
        Logger.Log(Importance.DEBUG, "Client Thread Entered");
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            ByteBuffer buffer = ByteBuffer.allocate(100000000); // 创建大小为140960的ByteBuffer缓冲区
            int bytesRead = 0;
            //Logger.Log(Importance.DEBUG, "Client Thread Prepared");
            do {
                int bytesReadeadThisTime = 0;
                byte[] re = new byte[4096];
                //阻塞方法，阻塞直到有数据可以读取
                bytesReadeadThisTime = inputStream.read(re) + bytesRead;
                buffer.position(bytesRead);
                buffer.put(re, 0, bytesReadeadThisTime);
                bytesRead += bytesReadeadThisTime;
                //Logger.Log(Importance.DEBUG, "Client Thread Receiving");
                DEBUG_COUNT = 0;
                //DebugCounter();  //0
                //成功读取
                if (bytesRead > 0) {
                    //DebugCounter();  //1
                    buffer.position(bytesRead);
                    //DebugCounter();  //2
                    buffer.flip();
                    //DebugCounter();  //3
                    byte[] data = new byte[bytesRead];
                    //DebugCounter();  //4
                    buffer.get(data);
                    //DebugCounter();  //5
                    String message = new String(data, 0, bytesRead, StandardCharsets.UTF_8);
                    //DebugCounter();  //6
                    //遇到消息尾 #
                    if (message.contains("#")) {
                        //DebugCounter();  //7
                        String fullMessage = message.trim();
                        //DebugCounter();  //8
                        //检测消息是否是消息头开始
                        if (fullMessage.startsWith("$")) {
                            //DebugCounter();  //9
                            //此处消息接收完成，交给处理器处理
                            Logger.Log(Importance.INFO, "Received message from \"" + this.userName + "\"@" + this.IpAddress + ": " + fullMessage);
                            outputStream.write(MessageHandler(ProcessMessage(fullMessage)).getBytes(StandardCharsets.UTF_8));
                        } else {    //消息出现错误，没有检测到消息头：发出警报并忽略这条消息
                            Logger.Log(Importance.WARNING, "收到了不完整的消息，是否网络或客户端出现错误: " + fullMessage);
                        }
                        bytesRead = 0;
                        buffer.clear();
                    }
                }
            } while (bytesRead != -1 && (isLoggedIn || !isTimeOut));
        } catch (Exception e) {
            Logger.Log(Importance.INFO, "客户端连接断开，连接关闭");
        } finally {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    Logger.Log(Importance.INFO, "与客户端断开连接失败，连接线程强制退出");
                }
            }
        }
    }

    private void WatchDog() {
        Timer timer = new Timer();
        //新建延时任务项
        TimerTask task = new TimerTask() {
            public void run() {
                // 检查 flag 的值，如果为 false，则强制结束线程
                if (!isLoggedIn) {
                    Logger.Log(Importance.INFO,  "客户端连接超时，结束连接");
                    StopRunning();
                }
            }
        };
        //设置执行超时任务的时间为系统配置中的配置项
        timer.schedule(task, SystemConfig.ClientLoginTimeOut);
    }

    /**
     * 超时之后结束线程运行的回调函数
     */
    private void StopRunning(){
        this.isTimeOut = false;
    }

    public static int DEBUG_COUNT = 0;

    private void DebugCounter(){
        System.out.println(DEBUG_COUNT);
        DEBUG_COUNT++;
    }

    /**
     * 初步处理客户端发送的消息，把多个消息分开，并检查消息完整性
     * @param msg
     * @return
     */
    public ArrayList<String> ProcessMessage(String msg) {
        ArrayList<String> res = new ArrayList<>();
        String[] splited = msg.split("\\$");
        for(String s : splited) {
            //分割是否非空
            if(s.length() > 0) {
                //分割结果非空
                if(s.endsWith("#")) {   //消息格式是否正确包含消息尾
                    //正确包含消息尾，加入作为返回值的Arraylist
                    res.add(s.substring(0, s.length() - 1));
                } else {
                    Logger.Log(Importance.WARNING, "消息没有正确包含消息尾：" + s);
                }
            } else {
                //分割结果是空，DO nothing
            }
        }
        return res;
    }

    private String BoxMessage(String s) {
        return "$" + s + "#";
    }

    public String MessageHandler(ArrayList<String> stripped) {
        String res;
        StringBuilder sb = new StringBuilder();
        for(String s : stripped) {
            System.out.println("Stripped: " + s);
            sb.append(BoxMessage(s));
        }
        return sb.toString();
    }
}

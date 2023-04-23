package jdbms.server.util;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 14:05
 */
import jdbms.server.config.SystemConfig;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Logger {
    private static String datetime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date(System.currentTimeMillis()));

    public static void Log(Importance imp, String text){
        try {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date(System.currentTimeMillis()));
            //System.out.println("Logs\\GOLG_Log_Started_At" + datetime + ".txt: " + "[" + currentTime + "] " + "[" + imp.toString() + "] " + text + "\r\n");
            File file = new File("Logs\\JDBMS_Log_Started_At_" + datetime + ".txt");
            if (!file.exists()) {
                file.getParentFile().mkdir();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(raf.length());
            if (SystemConfig.ShowDebugInfo || imp != Importance.DEBUG) {
                raf.writeBytes("[" + currentTime + "] [" + Thread.currentThread().getName() + "] [" + imp.toString() + "] " + text + "\r\n");
                System.out.println("[" + currentTime + "] [" + Thread.currentThread().getName() + "] [" + imp.toString() + "] " + text);
            }
        } catch (IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public static void LogStackTrace(Importance imp, StackTraceElement[] stackTrace) {
        Log(imp, Arrays.toString(stackTrace));
    }

    public static void LogException(Importance imp, Exception e){
        Logger.Log(imp, e.getLocalizedMessage());
        Logger.LogStackTrace(imp, e.getStackTrace());
    }
}

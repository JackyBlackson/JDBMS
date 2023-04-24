package jdbms.server.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 14:07
 */
public class SystemConfig {
    public static Integer ServerPort = 8080;
    public static boolean ShowDebugInfo = false;
    public static int ClientLoginTimeOut = 64000;
    public static int VarCharMaxLong = 256;

    /**
     * 从文件读取系统配置文件
     * @param path 配置文件的路径，可以是相对路径或者绝对路径
     * @throws IOException
     */
    public static void loadGameInfoFromFile(String path) throws IOException {
        Properties systemProperties = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        systemProperties.load(bufferedReader);

        ////////读取配置文件//////
        ShowDebugInfo       = Boolean.parseBoolean(systemProperties.getProperty("ShowDebugInfo"));
        ServerPort          = Integer.parseInt(systemProperties.getProperty("ServerPort"));
        ClientLoginTimeOut  = Integer.parseInt(systemProperties.getProperty("ClientLoginTimeOut"));
        VarCharMaxLong      = Integer.parseInt(systemProperties.getProperty("VarCharMaxLong"));
        ///////读取  结束////////

        bufferedReader.close();
    }
}

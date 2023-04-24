package jdbms.server.main;

import jdbms.server.config.SystemConfig;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

/**
 * @author Jacky_Blackson
 * @description 实际使用的主类，这里的代码不能删改
 * @date 2023/4/24 16:02
 */
public class Main {
    public static void main(String[] args) {
        //读取配置文件
        try {
            SystemConfig.loadGameInfoFromFile(args[0]);
            System.out.println(SystemConfig.ShowDebugInfo);
        } catch (Exception e) {
            Logger.Log(Importance.SEVERE, "无法读取配置文件，系统退出");
            Logger.LogException(Importance.SEVERE, e);
            return;     //如果读取配置文件失败，则直接退出系统
        }

        //加载用户信息

        //扫描并加载所有数据库

        //开启服务器
    }
}

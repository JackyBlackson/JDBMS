package jdbms.server.threads;

import jdbms.server.config.SystemConfig;
import jdbms.server.connection.MultiClientTCPServer;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 16:16
 */
public class MainThread {
    public static void main(String[] args) {
        try {
            SystemConfig.loadGameInfoFromFile(args[0]);
            System.out.println(SystemConfig.ShowDebugInfo);
        } catch (Exception e) {
            Logger.Log(Importance.SEVERE, e.getLocalizedMessage());
            Logger.Log(Importance.SEVERE, e.getMessage());
        }

        try {
            new MultiClientTCPServer().StartMultiThread();
        } catch (Exception e) {
            Logger.LogException(Importance.SEVERE, e);
        }
    }
}

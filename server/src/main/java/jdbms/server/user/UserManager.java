package jdbms.server.user;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 20:04
 */
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static volatile UserManager instance = null;

    private static Map<String, User> users = new HashMap<>();

    private UserManager() {}

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public static synchronized User getUser(String userName) {
        if (users.containsKey(userName)) {
            return users.get(userName);
        }
        return null;
    }

    public static synchronized void registerUser(String userName, String password) {
        User newUser = new User(userName, password, false);
        users.put(userName, newUser);
    }

    public static synchronized void removeUser(String userName) {
        users.remove(userName);
    }
}


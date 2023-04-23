package jdbms.server.user;

import jdbms.server.user.permissions.Permission;
import jdbms.server.user.permissions.PermissionType;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 11:47
 */
public class User {
    public String UserName;
    public String Password;
    private Permission Permission;
    private Boolean IsDBMSAdministrator;

    public User(String uname, String upw, Boolean isAdmin){
        this.UserName = uname;
        this.Password = upw;
        this.Permission = new Permission();
        this.IsDBMSAdministrator = isAdmin;
    }

    public Boolean GetPermissionOn(String name, PermissionType type){
        return this.Permission.GetPermission(name, type);
    }

    public void SetPermissionOn(String name, PermissionType type, Boolean perm) {
        this.Permission.SetPermission(name, type, perm);
    }
}

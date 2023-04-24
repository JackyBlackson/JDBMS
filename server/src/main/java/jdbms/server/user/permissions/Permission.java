package jdbms.server.user.permissions;

import java.util.LinkedHashMap;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/23 19:40
 */
public class Permission {

    private LinkedHashMap<String, PermissionItem> PermissionList;

    public Permission(){
        this.PermissionList = new LinkedHashMap<>();
    }

    public Permission(LinkedHashMap<String, PermissionItem> list){
        this.PermissionList = list;
    }

    public LinkedHashMap<String, PermissionItem> GetPermissionList(){
        return this.PermissionList;
    }

    /**
     * 获取权限记录的值。如果没有记录，那么会返回null。需要处理NULL
     * @param name 记录的名字，可以是数据库或者表
     * @param type 记录的类别，需要从PermissionType的枚举类中选择
     * @return
     */
    public Boolean GetPermission(String name, PermissionType type) {
        if(this.PermissionList.containsKey(name)){
            //有这个数据库或表的记录
            switch(type) {      //判断记录类别
                case READ: return this.PermissionList.get(name).READ;
                case INSERT: return this.PermissionList.get(name).INSERT;
                case ALTER: return this.PermissionList.get(name).ALTER;
                case DELETE: return this.PermissionList.get(name).DELETE;
                case ADMIN: return this.PermissionList.get(name).ADMIN;
            }
        } else {    //没有这个数据库或者表的权限记录
            return null;
        }
        return null;
    }

    public void SetPermission(String name, PermissionType type, Boolean perm){
        //检查是否有待插入的权限记录
        if(!this.PermissionList.containsKey(name)){
            //没有这个数据库或表的记录
            this.PermissionList.put(name, new PermissionItem());
        }
        //添加记录
        switch(type) {      //判断记录类别
            case READ: this.PermissionList.get(name).READ = perm;
            case INSERT: this.PermissionList.get(name).INSERT = perm;
            case ALTER: this.PermissionList.get(name).ALTER = perm;
            case DELETE: this.PermissionList.get(name).DELETE = perm;
            case ADMIN: this.PermissionList.get(name).ADMIN = perm;
        }
    }
}

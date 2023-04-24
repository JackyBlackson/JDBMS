package jdbms.server.main;

import jdbms.server.config.SystemConfig;
import jdbms.server.connection.MultiClientTCPServer;
import jdbms.server.dataapi.field.Field;
import jdbms.server.dataapi.field.FieldManager;
import jdbms.server.dataapi.value.ValueType;
import jdbms.server.exception.dataexception.fieldexception.FieldNameAlreadyExistsException;
import jdbms.server.exception.dataexception.fieldexception.FieldNameNotFoundException;
import jdbms.server.exception.dataexception.vcharexception.IllegalVCharLengthException;
import jdbms.server.util.Importance;
import jdbms.server.util.Logger;

/**
 * @author Jacky_Blackson
 * @description 用来测试的主类，这里的代码可以随意删改
 * @date 2023/4/23 16:16
 */
public class TestMain {
    public static void main(String[] args) {
        //验证配置问价读取的可靠性
        try {
            SystemConfig.loadGameInfoFromFile(args[0]);
            System.out.println(SystemConfig.ShowDebugInfo);
        } catch (Exception e) {
            Logger.Log(Importance.SEVERE, e.getLocalizedMessage());
            Logger.Log(Importance.SEVERE, e.getMessage());
        }

        //验证Field管理类的可靠性
        FieldManager fm = new FieldManager();
        try {
            fm.addField("114", new Field(ValueType.INT, 0));
            fm.addField("514", new Field(ValueType.BOOL, 0));
            fm.addField("233", new Field(ValueType.VARCHAR, 8));
            fm.addField("1919", new Field(ValueType.VARCHAR, 8));
            System.out.println(fm.toString());
            fm.deleteFieldByName("233");
            System.out.println(fm.toString());
        } catch (FieldNameAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (IllegalVCharLengthException e) {
            throw new RuntimeException(e);
        } catch (FieldNameNotFoundException e) {
            throw new RuntimeException(e);
        }
        //验证TCP服务器的可靠性
        try {
            new MultiClientTCPServer().StartMultiThread();
        } catch (Exception e) {
            Logger.LogException(Importance.SEVERE, e);
        }
    }
}

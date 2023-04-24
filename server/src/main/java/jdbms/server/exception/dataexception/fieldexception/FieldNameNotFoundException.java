package jdbms.server.exception.dataexception.fieldexception;

import jdbms.server.exception.dataexception.DataException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 15:14
 */
public class FieldNameNotFoundException extends DataException {
    public FieldNameNotFoundException(String illegalName) {
        super("新建或修改的字段名称重复：" + illegalName);
    }
}

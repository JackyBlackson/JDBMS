package jdbms.server.exception.dataexception.fieldexception;

import jdbms.server.exception.dataexception.DataException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 15:08
 */
public class FieldNameAlreadyExistsException extends DataException {
    public FieldNameAlreadyExistsException(String illegalName) {
        super("新建或修改的字段名称重复：" + illegalName);
    }
}

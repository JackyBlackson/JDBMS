package jdbms.server.exception.dataexception.fieldexception;

import jdbms.server.exception.dataexception.DataException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 15:02
 */
public class FieldIndexOutOfBoundException extends DataException {
    public FieldIndexOutOfBoundException(int illegalIndex, int maxIndex) {
        super("在访问字段时字段索引越界：应当在[0]到[" + maxIndex + "]，实际访问[" + illegalIndex + "]。");
    }
}

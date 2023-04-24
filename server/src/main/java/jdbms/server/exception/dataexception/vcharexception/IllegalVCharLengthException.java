package jdbms.server.exception.dataexception.vcharexception;

import jdbms.server.exception.dataexception.DataException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 13:27
 */
public class IllegalVCharLengthException extends DataException {
    public IllegalVCharLengthException(String message, Integer length) {
        super(message + "；非法的长度：" + length.toString() + "。");
    }
}

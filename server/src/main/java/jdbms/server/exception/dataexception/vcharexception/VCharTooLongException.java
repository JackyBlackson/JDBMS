package jdbms.server.exception.dataexception.vcharexception;

import jdbms.server.exception.dataexception.DataException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 13:07
 */
public class VCharTooLongException extends DataException {
    String message;
    String vchar;
    Integer bound;
    public VCharTooLongException(String illegalVChar, int bound) {
        super("向VARCHAR字段插入数据超出长度：" + "；字符串：" + illegalVChar + "；最大允许长度：" + bound + "。");
        this.message = message;
        this.vchar = illegalVChar;
        this.bound = bound;
    }

    @Override
    public String getLocalizedMessage() {
        return "向VARCHAR字段插入数据超出长度：" + "；字符串：" + vchar + "；最大允许长度：" + bound.toString() + "。";
    }
}

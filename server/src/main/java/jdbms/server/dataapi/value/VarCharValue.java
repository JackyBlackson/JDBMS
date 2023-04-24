package jdbms.server.dataapi.value;

import jdbms.server.config.SystemConfig;
import jdbms.server.exception.dataexception.vcharexception.IllegalVCharLengthException;
import jdbms.server.exception.dataexception.vcharexception.VCharTooLongException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 13:02
 */
public class VarCharValue implements ValueInterface<String>{
    public int length = 0;
    private String value;

    public VarCharValue(int length, String value) throws IllegalVCharLengthException, VCharTooLongException {
        if(length < 0 || length > SystemConfig.VarCharMaxLong) {
            throw new IllegalVCharLengthException("建立VARCHAR字段时应用错误长度", length);
        } else if(value.length() > length) {
            throw new VCharTooLongException(value, length);
        } else {
            this.value = value;
        }
    }

    @Override
    public String GetValue() {
        return this.value;
    }

    @Override
    public void SetValue(String v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean equals(VarCharValue obj) {
        return value.equals(obj.GetValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int GetLength() {
        return BoolValue.length;
    }

    @Override
    public void SetLength(int length) {
        //BOOL类型的长度不可变，DO NOTHING
    }
}

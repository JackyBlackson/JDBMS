package jdbms.server.dataapi.value;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 13:40
 */
public class DatetimeValue implements ValueInterface<Date>{
    public static final int length = 16;
    private Date value;

    public DatetimeValue(Date value){
        this.value = value;
    }

    @Override
    public Date GetValue() {
        return this.value;
    }

    @Override
    public void SetValue(Date v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean equals(DatetimeValue obj) {
        return value.equals(obj.GetValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int GetLength() {
        return DatetimeValue.length;
    }

    @Override
    public void SetLength(int length) {
        //BOOL类型的长度不可变，DO NOTHING
    }
}

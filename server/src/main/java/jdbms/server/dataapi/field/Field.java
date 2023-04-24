package jdbms.server.dataapi.field;

import jdbms.server.config.SystemConfig;
import jdbms.server.dataapi.value.BoolValue;
import jdbms.server.dataapi.value.DatetimeValue;
import jdbms.server.dataapi.value.IntValue;
import jdbms.server.dataapi.value.ValueType;
import jdbms.server.exception.dataexception.vcharexception.IllegalVCharLengthException;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 13:51
 */
public class Field {
    int length;
    ValueType type;

    public Field(ValueType type, int length) throws IllegalVCharLengthException {
        switch(type) {
            case INT -> {
                this.type = type;
                this.length = IntValue.length;
            }
            case BOOL -> {
                this.type = type;
                this.length = BoolValue.length;
            }
            case VARCHAR -> {
                this.type = type;
                if(0 < length && length <= SystemConfig.VarCharMaxLong){
                    this.type = type;
                    this.length = length;
                } else {
                    throw new IllegalVCharLengthException("字段创建时VARCHAR类型长度超过系统设置的最大值", length);
                }
            }
            case DATETIME -> {
                this.type = type;
                this.length = DatetimeValue.length;
            }
        }
    }
}

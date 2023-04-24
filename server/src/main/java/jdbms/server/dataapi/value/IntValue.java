package jdbms.server.dataapi.value;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 11:47
 */
public class IntValue implements ValueInterface<Integer>{
    public static final int length = 4;
    private Integer value;

    public IntValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer GetValue() {
        return this.value;
    }

    @Override
    public void SetValue(Integer v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean equals(IntValue obj) {
        return value.equals(obj.GetValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int GetLength() {
        return IntValue.length;
    }

    @Override
    public void SetLength(int length) {
        //int类型地长度不可变，直接抛弃，DONOTHING
    }
}

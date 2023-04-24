package jdbms.server.dataapi.value;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 11:56
 */
public class BoolValue implements ValueInterface<Boolean>{
    public static final int length = 2;
    private Boolean value;

    public BoolValue(Boolean value){
        this.value = value;
    }

    @Override
    public Boolean GetValue() {
        return this.value;
    }

    @Override
    public void SetValue(Boolean v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public boolean equals(BoolValue obj) {
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

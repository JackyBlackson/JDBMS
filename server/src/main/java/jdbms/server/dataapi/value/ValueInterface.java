package jdbms.server.dataapi.value;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 11:31
 */
public interface ValueInterface<T> {

    public T GetValue();

    public void SetValue(T v);

    public int GetLength();

    public void SetLength(int length);
}

package jdbms.server.dataapi.field;

import jdbms.server.dataapi.value.ValueType;
import jdbms.server.exception.dataexception.fieldexception.FieldIndexOutOfBoundException;
import jdbms.server.exception.dataexception.fieldexception.FieldNameAlreadyExistsException;
import jdbms.server.exception.dataexception.fieldexception.FieldNameNotFoundException;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Jacky_Blackson
 * @description
 * @date 2023/4/24 13:53
 */
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FieldManager {
    private Map<String, Field> fieldNameMap = new HashMap<>();
    private List<String> nameList = new ArrayList<>();

    public FieldManager() {
        // 初始化 fields 和 fieldsList
    }

    public int length(){
        return this.nameList.size();
    }

    // 添加 Field 对象
    public void addField(String name, Field field) throws FieldNameAlreadyExistsException {
        //判断添加字段的名字是否唯一
        if (!nameList.contains(name)) {     //唯一
            fieldNameMap.put(name, field);  //添加字段
            nameList.add(name);             //名字序列中添加名字
        } else {    //名字不唯一，抛出异常等待上层处理
            throw new FieldNameAlreadyExistsException(name);
        }
    }

    // 删除 Field 对象
    public void removeField(String name) throws FieldNameNotFoundException {
        //判断是否持有这个字段
        if(nameList.contains(name)){        //持有这个字段
            fieldNameMap.remove(name);
            nameList.remove(name);
        } else {
            throw new FieldNameNotFoundException(name);
        }
    }

    // 根据名称查找 Field 对象，返回 null 表示未找到
    public Field findFieldByName(String name) throws FieldNameNotFoundException {
        if(nameList.contains(name)){
            return fieldNameMap.get(name);
        } else {
            throw new FieldNameNotFoundException(name);
        }
    }

    // 根据 Index 随机访问 Field 对象，返回 null 表示未找到
    public Field getFieldByIndex(int index) throws FieldIndexOutOfBoundException {
        if (0 <= index && index < nameList.size()) {   //访问是否越界
            //没有越界
            return fieldNameMap.get(nameList.get(index));
        } else {    //访问越界
            throw new FieldIndexOutOfBoundException(index, nameList.size() - 1);
        }
    }

    // 根据名称和 Index 插入新的 Field 对象，如果已经存在则覆盖
    public void insertFieldByIndex(int index, String name, Field field) throws FieldIndexOutOfBoundException {
        if (0 <= index && index < nameList.size()) {   //访问是否越界
            //没有越界
            nameList.add(index, name);
        } else {    //访问越界
            throw new FieldIndexOutOfBoundException(index, nameList.size() - 1);
        }
    }

    // 根据名称删除 Field 对象，并减少后面的 Field 对象的 Index
    public void deleteFieldByName(String name) throws FieldNameNotFoundException {
        //判断是否含有名字
        if(nameList.contains(name)) {   //含有名字
            nameList.remove(name);
            fieldNameMap.remove(name);
        } else {    //不含有名字，直接抛出异常等待上层处理
            throw new FieldNameNotFoundException(name);
        }
    }

    // 打印所有 Field 对象的内容
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String fieldName : nameList) {
            sb.append(fieldName).append(": ");
            Field f = fieldNameMap.get(fieldName);
            sb.append(f.type).append("(").append(f.length).append(")\n");
        }
        String re = sb.toString();
        return re.substring(0, re.length() - 1);
    }

    // 打印所有 Field 对象的内容，按照 Name 排序
    public void printFieldsSortedByName() {
        for (Map.Entry<String, Field> entry : fieldNameMap.entrySet()) {
            Field field = entry.getValue();
            System.out.println(entry.getKey() + ": " + field);
        }
    }
}


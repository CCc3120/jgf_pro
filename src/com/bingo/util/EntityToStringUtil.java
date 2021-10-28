package com.bingo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class EntityToStringUtil {

    public static String conver(Object obj) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            ClassNotFoundException {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder("{");
        for (Field field : fields) {
            String methodName =
                    "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            if (result instanceof Date) {
                result = ((Date) result).getTime();
            }
            sb.append("\"")
                    .append(field.getName())
                    .append("\"")
                    .append(":")
                    .append("\"")
                    .append(result)
                    .append("\"")
                    .append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }

    public static String conver(Map<String, String> map) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            ClassNotFoundException {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<String, String> entry : map.entrySet()) {
            sb.append("\"")
                    .append(entry.getKey().replace("\\", "\\\\"))
                    .append("\"")
                    .append(":")
                    .append("\"")
                    .append(entry.getValue().replace("\\", "\\\\"))
                    .append("\"")
                    .append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }
}

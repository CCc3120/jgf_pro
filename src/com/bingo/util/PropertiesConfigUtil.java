package com.bingo.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesConfigUtil {

	private volatile static Properties prop;

	private static String path = "resource/config.properties";

	public static Properties getProp() {
		return prop;
	}

	static {
		getInstance();
	}

	// 初始化方法
	private static synchronized void getInstance() {
//		InputStream in = Thread.currentThread().getContextClassLoader()
//				.getResourceAsStream("resource/config.properties");
		// Properties继承自Hashtabel,所以读写是无序的。若对是否有序需要求，下面可直接new一个Properties
		FileInputStream fis = null;
		BufferedReader bufferedReader = null;
		prop = new PropertiesUtil();
		try {
			fis = new FileInputStream(path);
			bufferedReader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			prop.load(bufferedReader);
		} catch (IOException e) {
			prop = null;
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				bufferedReader = null;
				e.printStackTrace();
			}
		}

	}

	// 读方法
	public static String getProperty(String key) {
		if (null == prop) {
			// 使用双重检查锁
			synchronized (PropertiesConfigUtil.class) {
				if (null == prop) {
					getInstance();
				}
			}
		}
		return prop.getProperty(key);
	}

	// 写方法
	public static void updateConfig(String key, String value) {
		if (null == prop) {
			getInstance();
		}
		prop.setProperty(key, value);
		FileOutputStream outputFile = null;
		OutputStreamWriter osw = null;
		try {
			outputFile = new FileOutputStream(path);
			osw = new OutputStreamWriter(outputFile, Charset.forName("UTF-8"));
			prop.store(osw, "modify");
			outputFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
				if (outputFile != null) {
					outputFile.close();
				}
			} catch (IOException e) {
				// 若流关闭时出现异常则将其设为null,保证其不占用资源
//				outputFile = null;
				e.printStackTrace();
			}
		}
	}
}

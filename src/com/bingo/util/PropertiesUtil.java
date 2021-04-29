package com.bingo.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 由于Properties继承自Hashtabel,所以读写是无序的,现改用linkedhashset去维护则可保持顺序
	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

	// 重写stringPropertyNames方法,将key值存入linkedhashset维护
	@Override
	public Set<String> stringPropertyNames() {
		Set<String> set = new LinkedHashSet<String>();
		for (Object key : keys) {
			set.add((String) key);
		}
		return set;
	}

	// 重写keySet方法,返回的是一个linkedhashset
	@Override
	public Set<Object> keySet() {
		return keys;
	}

	// 重写keys方法
	@Override
	public synchronized Enumeration<Object> keys() {
		return Collections.enumeration(keys);
	}

	// 重写put方法
	@Override
	public synchronized Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}

}

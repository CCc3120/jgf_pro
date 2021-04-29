package com.bingo.util;

import com.bingo.main.ParamConstant;

public enum MessageType {

	OP_OPEN("打开APP", "1"), 
	OP_BT_LIKE("点击" + ParamConstant.left_bt_desc + "按钮", "2"), 
	OP_BT_UNLIKE("点击" + ParamConstant.right_bt_desc + "按钮", "3"),
	OP_BT_CLOSE("尝试点击关闭按钮", "4"), 
	OP_CLOSE_FAIL("APP关闭失败", "5"), 
	OP_CLOSE("正常关闭APP", "0");

	private String key;

	private String value;

	MessageType(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}

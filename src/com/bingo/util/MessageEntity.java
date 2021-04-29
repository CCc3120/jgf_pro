package com.bingo.util;

import java.util.Date;

public class MessageEntity {

	private String opType;

	private String operation;

	private Date opTime;

	public MessageEntity(String operation, String opType) {
		super();
		this.opType = opType;
		this.operation = operation;
		this.opTime = new Date();
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

}

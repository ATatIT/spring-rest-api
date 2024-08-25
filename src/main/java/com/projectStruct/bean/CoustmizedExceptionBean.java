package com.projectStruct.bean;

import java.time.LocalDateTime;

public class CoustmizedExceptionBean {
	
	private LocalDateTime timeStamp;
	private String massage;
	private String detail;
	public CoustmizedExceptionBean(LocalDateTime timeStamp, String massage, String detail) {
		super();
		this.timeStamp = timeStamp;
		this.massage = massage;
		this.detail = detail;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public String getMassage() {
		return massage;
	}
	public String getDetail() {
		return detail;
	}
	@Override
	public String toString() {
		return "CoustmizedExceptionBean [timeStamp=" + timeStamp + ", massage=" + massage + ", detail=" + detail + "]";
	}
	
	
}

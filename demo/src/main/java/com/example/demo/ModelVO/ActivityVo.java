package com.example.demo.ModelVO;

import java.sql.Timestamp;

public class ActivityVo {
	
	private int value; // The no of activities
	
	private long currentTime; // Time at whicch activities are saved
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}
	
}

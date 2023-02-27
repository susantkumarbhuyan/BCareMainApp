package com.bcareapp.commonclasses.fitness;

public class SleepSegment {

	private long startDateTime;
	private long endDateTime;
	private int sleepSegmentValue; // Unspecified - 0, Awake - 1, Sleeping - 2, Out of bed - 3, Light sleep - 4,
									// Deep sleep - 5, REM sleep - 6
	private String sleepTypeName;




	public long getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(long startDateTime) {
		this.startDateTime = startDateTime;
	}

	public long getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(long endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getSleepSegmentValue() {
		return sleepSegmentValue;
	}

	public void setSleepSegmentValue(int sleepSegmentValue) {
		this.sleepSegmentValue = sleepSegmentValue;
	}

	public String getSleepTypeName() {
		return sleepTypeName;
	}

	public void setSleepTypeName(String sleepTypeName) {
		this.sleepTypeName = sleepTypeName;
	}

}

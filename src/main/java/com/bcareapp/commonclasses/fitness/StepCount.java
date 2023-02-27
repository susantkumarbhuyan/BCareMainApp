package com.bcareapp.commonclasses.fitness;

public class StepCount {
	private long startMinuteTime;
	private long endMinuteTime;
	private double stepCountValue;

	public long getStartMinuteTime() {
		return startMinuteTime;
	}

	public void setStartMinuteTime(long startMinuteTime) {
		this.startMinuteTime = startMinuteTime;
	}

	public long getEndMinuteTime() {
		return endMinuteTime;
	}

	public void setEndMinuteTime(long endMinuteTime) {
		this.endMinuteTime = endMinuteTime;
	}

	public double getStepCountValue() {
		return stepCountValue;
	}

	public void setStepCountValue(double stepCountValue) {
		this.stepCountValue = stepCountValue;
	}

}

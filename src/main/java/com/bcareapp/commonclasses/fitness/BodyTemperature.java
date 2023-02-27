package com.bcareapp.commonclasses.fitness;

public class BodyTemperature {
	private long startTimestamp;
	private long endtimestamp;
	private double bodyTemperatureValue;

	
	public long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public long getEndtimestamp() {
		return endtimestamp;
	}

	public void setEndtimestamp(long endtimestamp) {
		this.endtimestamp = endtimestamp;
	}

	public double getBodyTemperatureValue() {
		return bodyTemperatureValue;
	}

	public void setBodyTemperatureValue(double bodyTemperatureValue) {
		this.bodyTemperatureValue = bodyTemperatureValue;
	}

}

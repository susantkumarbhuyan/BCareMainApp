package com.bcareapp.commonclasses.fitness;

public class BloodPressure {

	private long startSecondMillis;
	private long endSecondMillis;
	private double systolicValue;
	private double diastolicValue;

	public long getStartSecondMillis() {
		return startSecondMillis;
	}

	public void setStartSecondMillis(long startSecondMillis) {
		this.startSecondMillis = startSecondMillis;
	}

	public long getEndSecondMillis() {
		return endSecondMillis;
	}

	public void setEndSecondMillis(long endSecondMillis) {
		this.endSecondMillis = endSecondMillis;
	}

	public double getSystolicValue() {
		return systolicValue;
	}

	public void setSystolicValue(double systolicValue) {
		this.systolicValue = systolicValue;
	}

	public double getDiastolicValue() {
		return diastolicValue;
	}

	public void setDiastolicValue(double diastolicValue) {
		this.diastolicValue = diastolicValue;
	}


}

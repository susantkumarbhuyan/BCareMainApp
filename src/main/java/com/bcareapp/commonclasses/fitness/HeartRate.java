package com.bcareapp.commonclasses.fitness;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HeartRate {
	private long startSecondMillis;
	private long endSecondMillis;
	private double heartRateValue;
	private double restingHeartRate;

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

	public double getHeartRateValue() {
		return heartRateValue;
	}

	public void setHeartRateValue(double heartRateValue) {
		this.heartRateValue = heartRateValue;
	}

	public double getRestingHeartRate() {
		return restingHeartRate;
	}

	public void setRestingHeartRate(double restingHeartRate) {
		this.restingHeartRate = restingHeartRate;
	}

}

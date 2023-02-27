package com.bcareapp.commonclasses.fitness;

public class OxygenSaturation {

	private long startSecondMillis;
	private long endSecondMillis;
	private double oxygenSaturationValue;
	private double supplementalOxygenFlowRateValue;

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

	public double getOxygenSaturationValue() {
		return oxygenSaturationValue;
	}

	public void setOxygenSaturationValue(double oxygenSaturationValue) {
		this.oxygenSaturationValue = oxygenSaturationValue;
	}

	public double getSupplementalOxygenFlowRateValue() {
		return supplementalOxygenFlowRateValue;
	}

	public void setSupplementalOxygenFlowRateValue(double supplementalOxygenFlowRateValue) {
		this.supplementalOxygenFlowRateValue = supplementalOxygenFlowRateValue;
	}

}

package com.bcareapp.commonclasses.fitness;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GoogleFitData {
	private long startDateMillis;
	private long endDateMillis;
	private long profileId;
	private long stepSummery;
	private double distanceSummery;
	private double bodyTemperatureAvg;
	private double bloodPressureSystolicAvg;
	private double bloodPressureDiastolicAvg;
	private double oxygenSaturationAvg;
	private double heartRateAvg;
	private List<StepCount> stepCount;
	private List<Distance> distance;
	private List<SleepSegment> sleepSegment;
	private List<BodyTemperature> bodyTemperature;
	private List<BloodPressure> bloodPressure;
	private List<OxygenSaturation> oxygenSaturation;
	private List<HeartRate> heartRate;

	public long getStartDateMillis() {
		return startDateMillis;
	}

	public void setStartDateMillis(long startDateMillis) {
		this.startDateMillis = startDateMillis;
	}

	public long getEndDateMillis() {
		return endDateMillis;
	}

	public void setEndDateMillis(long endDateMillis) {
		this.endDateMillis = endDateMillis;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public long getStepSummery() {
		return stepSummery;
	}

	public void setStepSummery(long stepSummery) {
		this.stepSummery = stepSummery;
	}

	public double getDistanceSummery() {
		return distanceSummery;
	}

	public void setDistanceSummery(double distanceSummery) {
		this.distanceSummery = distanceSummery;
	}

	public double getBodyTemperatureAvg() {
		return bodyTemperatureAvg;
	}

	public void setBodyTemperatureAvg(double bodyTemperatureAvg) {
		this.bodyTemperatureAvg = bodyTemperatureAvg;
	}

	public double getBloodPressureSystolicAvg() {
		return bloodPressureSystolicAvg;
	}

	public void setBloodPressureSystolicAvg(double bloodPressureSystolicAvg) {
		this.bloodPressureSystolicAvg = bloodPressureSystolicAvg;
	}

	public double getBloodPressureDiastolicAvg() {
		return bloodPressureDiastolicAvg;
	}

	public void setBloodPressureDiastolicAvg(double bloodPressureDiastolicAvg) {
		this.bloodPressureDiastolicAvg = bloodPressureDiastolicAvg;
	}

	public double getOxygenSaturationAvg() {
		return oxygenSaturationAvg;
	}

	public void setOxygenSaturationAvg(double oxygenSaturationAvg) {
		this.oxygenSaturationAvg = oxygenSaturationAvg;
	}

	public double getHeartRateAvg() {
		return heartRateAvg;
	}

	public void setHeartRateAvg(double heartRateAvg) {
		this.heartRateAvg = heartRateAvg;
	}

	public List<StepCount> getStepCount() {
		return stepCount;
	}

	public void setStepCount(List<StepCount> stepCount) {
		this.stepCount = stepCount;
	}

	public List<Distance> getDistance() {
		return distance;
	}

	public void setDistance(List<Distance> distance) {
		this.distance = distance;
	}

	public List<SleepSegment> getSleepSegment() {
		return sleepSegment;
	}

	public void setSleepSegment(List<SleepSegment> sleepSegment) {
		this.sleepSegment = sleepSegment;
	}

	public List<BodyTemperature> getBodyTemperature() {
		return bodyTemperature;
	}

	public void setBodyTemperature(List<BodyTemperature> bodyTemperature) {
		this.bodyTemperature = bodyTemperature;
	}

	public List<BloodPressure> getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(List<BloodPressure> bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public List<OxygenSaturation> getOxygenSaturation() {
		return oxygenSaturation;
	}

	public void setOxygenSaturation(List<OxygenSaturation> oxygenSaturation) {
		this.oxygenSaturation = oxygenSaturation;
	}

	public List<HeartRate> getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(List<HeartRate> heartRate) {
		this.heartRate = heartRate;
	}

}

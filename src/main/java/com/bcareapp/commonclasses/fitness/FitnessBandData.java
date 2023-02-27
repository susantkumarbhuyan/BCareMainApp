package com.bcareapp.commonclasses.fitness;

import com.bcareapp.commonclasses.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

public class FitnessBandData extends BaseResponse<Object> {

	private long profileId;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private long currentDate;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private long currentUpdatedTime;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private long startDate;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private long endDate;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private long deviceTypeId;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private GoogleFitData googleFitData;
	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public long getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(long currentDate) {
		this.currentDate = currentDate;
	}

	public long getCurrentUpdatedTime() {
		return currentUpdatedTime;
	}

	public void setCurrentUpdatedTime(long currentUpdatedTime) {
		this.currentUpdatedTime = currentUpdatedTime;
	}

	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public GoogleFitData getGoogleFitData() {
		return googleFitData;
	}

	public void setGoogleFitData(GoogleFitData googleFitData) {
		this.googleFitData = googleFitData;
	}

}

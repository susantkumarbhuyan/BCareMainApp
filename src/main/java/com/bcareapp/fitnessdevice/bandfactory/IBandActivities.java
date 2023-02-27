package com.bcareapp.fitnessdevice.bandfactory;

import java.util.List;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.fitness.FitnessBandData;

public interface IBandActivities {
	public BaseResponse<Void> updateFitnessDeviceToken() throws BCException;

	public FitnessBandData getFitnessData(List<Integer> dataTypes, long fromDate, long toDate) throws BCException;
}

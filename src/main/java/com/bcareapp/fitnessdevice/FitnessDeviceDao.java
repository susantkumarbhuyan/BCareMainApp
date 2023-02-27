package com.bcareapp.fitnessdevice;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;

public interface FitnessDeviceDao {
	FitnessDeviceToken getFitnessDeviceToken(long profileId, int deviceTypeId) throws BCException;

	BaseResponse<Void> updateFitnessDeviceToken(FitnessDeviceToken fitnessDeviceToken) throws BCException;
}

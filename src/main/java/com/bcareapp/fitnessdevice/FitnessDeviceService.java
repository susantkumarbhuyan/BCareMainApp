package com.bcareapp.fitnessdevice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.fitness.FitnessBandData;
import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;
import com.bcareapp.fitnessdevice.bandfactory.BandFactory;
import com.bcareapp.fitnessdevice.bandfactory.BandOperator;

@Service("fintessDeviceService")
public class FitnessDeviceService {
	@Autowired
	private FitnessDeviceDao iFitnessDeviceDao;

	@Autowired
	private BandFactory bandFactory;

	public BaseResponse<Void> updateFitnessDeviceToken(FitnessDeviceToken fitnessDeviceToken) throws BCException {
		BandOperator bandOperator = bandFactory.createFactory(fitnessDeviceToken.getDeviceTypeId(), fitnessDeviceToken);
		return bandOperator.updateFitnessDeviceToken();
	}
	
	public FitnessBandData getFitnessData(long profileId, int deviceTypeId, ArrayList<Integer> dataTypes, long fromDate,
			long toDate)
			throws BCException {
		FitnessDeviceToken fitnessDeviceToken = iFitnessDeviceDao.getFitnessDeviceToken(profileId, deviceTypeId);
		BandOperator bandOperator = bandFactory.createFactory(deviceTypeId, fitnessDeviceToken);
		return bandOperator.getFitnessData(dataTypes, fromDate, toDate);
	}
}

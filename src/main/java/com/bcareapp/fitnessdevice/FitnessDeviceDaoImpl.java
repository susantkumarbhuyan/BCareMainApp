package com.bcareapp.fitnessdevice;

import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;
import com.bcareapp.constants.MongoCollectionConstants;
import com.bcareapp.constants.ResponseConstants;
import com.bcareapp.util.MongoDBUtil;

@Repository("fitnessDeviceDao")
public class FitnessDeviceDaoImpl implements FitnessDeviceDao {
	@Override
	public BaseResponse<Void> updateFitnessDeviceToken(FitnessDeviceToken fitnessDeviceToken) throws BCException {
		BaseResponse<Void> baseResponse = new BaseResponse<Void>();
		try {
			new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_FITNESS_DEVICE_TOKEN)
					.update("{profileId:#,deviceTypeId:#}", fitnessDeviceToken.getProfileId(),
							fitnessDeviceToken.getDeviceTypeId())
					.upsert().with(fitnessDeviceToken);

			baseResponse.setStatusCode(ResponseConstants.SUCCESS_CREATED);
			baseResponse.setStatusMessage(ResponseConstants.UPDATE_SUCCESS);
		} catch (Exception e) {
			baseResponse.setStatusMessage(ResponseConstants.INSERTION_FAILED);
			baseResponse.setStatusCode(ResponseConstants.FAILED);
			throw new BCException("Error occured while inserting FitnessDeviceToken data", e);
		}
		return baseResponse;
	}

	@Override
	public FitnessDeviceToken getFitnessDeviceToken(long profileId, int deviceTypeId) throws BCException {
		FitnessDeviceToken fitnessDeviceToken = null;
		try {
			fitnessDeviceToken = new Jongo(MongoDBUtil.getDB())
					.getCollection(MongoCollectionConstants.CN_FITNESS_DEVICE_TOKEN)
					.findOne("{profileId:#,deviceTypeId:#}", profileId, deviceTypeId).as(FitnessDeviceToken.class);
		} catch (Exception e) {
			throw new BCException("Error occured while getting FitnessDeviceToken data", e);
		}
		return fitnessDeviceToken;
	}
}

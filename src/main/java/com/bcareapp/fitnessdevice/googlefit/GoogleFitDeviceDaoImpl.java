package com.bcareapp.fitnessdevice.googlefit;

import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.constants.MongoCollectionConstants;
import com.bcareapp.util.MongoDBUtil;


@Repository("googleFitDeviceDao")
public class GoogleFitDeviceDaoImpl implements IGoolgeFitDeviceDao {

	@Override
	public void insertGoogleFitData(com.bcareapp.commonclasses.fitness.GoogleFitData googleFitData) throws BCException {
		new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_GOOGLEFIT_DUMPDATA)
		.update("{dataTypeId:#}", 4).upsert().with("{$set:#}", googleFitData);

		
	}

}

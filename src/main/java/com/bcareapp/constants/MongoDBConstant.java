package com.bcareapp.constants;

import java.lang.reflect.Method;
import com.mongodb.ServerAddress;

public class MongoDBConstant {
	public static ServerAddress SERVER_ADDRESSES;
	public static String DB_NAME;

	static {
		try {
			Object environmentInstance = GetEnvironmentFactoryConstant.getEnvironmentInstance();

			Method getDBRegion = environmentInstance.getClass().getMethod("getMongoServerAddresses");
			Object result = getDBRegion.invoke(environmentInstance);
			SERVER_ADDRESSES = (ServerAddress) result;

			getDBRegion = environmentInstance.getClass().getMethod("getDBName");
			result = getDBRegion.invoke(environmentInstance);
			DB_NAME = (String) result;

		} catch (Exception e) {
		}

	}

}

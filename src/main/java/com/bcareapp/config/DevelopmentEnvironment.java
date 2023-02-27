package com.bcareapp.config;

import com.bcareapp.constants.BrandConstants;
import com.mongodb.ServerAddress;

public class DevelopmentEnvironment implements Environment {
	private static final String MONGODB_HOST = "localhost";
	private static final int MONGODB_PORT = 27017;
	private static final String MONGODB_DATABASE = "BCareApp";
	private static final long BRAND_ID = BrandConstants.BRAND_ID_BCARE;
//	private static final String EMAIL_USERNAME = "kumarfashionclub@gmail.com";
//	private static final String EMAIL_PASSWORD = "oyhxvqanvsfumuku";
	private static final String EMAIL_USERNAME = "noreplybcare@gmail.com";
	private static final String EMAIL_PASSWORD = "fdrqoophalgyfvnd";

	@Override
	public ServerAddress getMongoServerAddresses() {
		return new ServerAddress(MONGODB_HOST, MONGODB_PORT);

	}

	@Override
	public String getDBName() {
		return MONGODB_DATABASE;
	}

	@Override
	public long getBrandId() {
		return BRAND_ID;
	}

	@Override
	public String getEmailUsername() {
		return EMAIL_USERNAME;
	}

	@Override
	public String getEmailPassword() {
		return EMAIL_PASSWORD;
	}
}

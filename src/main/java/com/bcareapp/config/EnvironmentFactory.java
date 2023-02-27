package com.bcareapp.config;
@SuppressWarnings("unused")
public class EnvironmentFactory {
//	private static final String PROP_FILENAME = "config.properties";
	private static final String DEMO_ENVIRONMENT = "DEMO";

	private static final String PROD_ENVIRONMENT = "PROD";
	private static final String BOOKSTORE_ENVIRONMENT = "BOOK";

	public static final String DEV_ENVIRONMENT = "DEV";
	private static final String ENVIRONMENT = BOOKSTORE_ENVIRONMENT; // manually need to set up for environments
	public static String environmentName = ENVIRONMENT;
	private static Environment environment;

	private EnvironmentFactory() {

	}

	public static synchronized Environment getInstance() {
		if (environment == null) {
			if (environmentName == "DEV") {
				environment = new DevelopmentEnvironment();
			} else if (environmentName == "DEMO") {

			} else if (environmentName == "PROD") {

			} else if (environmentName == "BOOK") {
				environment = new BookStoreEnvironment();
			}
		}
		return environment;

	}
}

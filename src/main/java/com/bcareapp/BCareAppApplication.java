package com.bcareapp;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;

import com.bcareapp.config.EnvironmentFactory;
import com.bcareapp.constants.PlatformConstant;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
@ComponentScan(basePackages = "com.bcareapp")
public class BCareAppApplication {

	private static final Logger logger = Logger.getLogger(BCareAppApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BCareAppApplication.class);
		addInitHooks(application);
	}

	static void addInitHooks(SpringApplication application) {
		try {
			application
					.setDefaultProperties(Collections.singletonMap(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME,
							String.valueOf(EnvironmentFactory.getInstance().getBrandId())));
			application.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
				initializePlatformConfiguration();
				application.setAdditionalProfiles(String.valueOf(EnvironmentFactory.getInstance().getBrandId()));
				System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
						String.valueOf(EnvironmentFactory.getInstance().getBrandId()));
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while addInitHooks the platform constants", e);
		}
	}

	public static void initializePlatformConfiguration() {
		try {
			PlatformConstant.appId = EnvironmentFactory.getInstance().getBrandId();
			PlatformConstant.brandId = EnvironmentFactory.getInstance().getBrandId();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occurred while initializing the platform constants", e);
		}
	}
}

package com.bcareapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.AbstractEnvironment;

import com.bcareapp.config.EnvironmentFactory;
import com.bcareapp.constants.PlatformConstant;

public class ServletInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		try {
			BCareAppApplication.addInitHooks(application.application());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return application.sources(BCareAppApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		if (PlatformConstant.appId != EnvironmentFactory.getInstance().getBrandId()) {
			// MongoDBUtil.closeConnections();
			System.out.println("BCAREMAIN > Mismatch Found in the Brand Configurations");
			System.out.println("BCAREMAIN > Active Profile is set to "
					+ System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
			BCareAppApplication.initializePlatformConfiguration();
			System.out.println("BCAREMAIN > Platform App Id is reset to " + PlatformConstant.appId);
			System.out.println("BCAREMAIN > Environment Brand Id is " + EnvironmentFactory.getInstance().getBrandId());
		}
	}
}

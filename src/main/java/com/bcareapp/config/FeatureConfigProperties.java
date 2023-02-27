package com.bcareapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:config-${spring.profiles.active}.properties", ignoreResourceNotFound = false)
@ConfigurationProperties(prefix = "bcare")
public class FeatureConfigProperties {

	private long brandId;
	private String smallPlatform;

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public String getSmallPlatform() {
		return smallPlatform;
	}

	public void setSmallPlatform(String smallPlatform) {
		this.smallPlatform = smallPlatform;
	}

}

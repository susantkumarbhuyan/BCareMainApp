package com.bcareapp.config;

import com.mongodb.ServerAddress;

public interface Environment {
	public ServerAddress getMongoServerAddresses();

	static final String DEFAULT_SMTP_HOST = "smtp.gmail.com";

	public String getDBName();

	public long getBrandId();

	public String getEmailUsername();

	public String getEmailPassword();

	default String getFromEmail() {
		return null;
	}

	default String getEmailSMTPHost() {
		return DEFAULT_SMTP_HOST;
	}
}

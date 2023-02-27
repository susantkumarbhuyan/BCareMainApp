package com.bcareapp.bookstore.commonclass;

public class JwtUser {


	public static final int USER = 1;
	public static final int CONSUMER = 2;
	public static final int INTERNAL = 3;
	public static final int EXTERNAL = 4;

	private String username;
	private long userId;
	private String roles;
	private long expiry;
	private int userType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public long getExpiry() {
		return expiry;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}


}

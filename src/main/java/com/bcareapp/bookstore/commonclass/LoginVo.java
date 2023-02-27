package com.bcareapp.bookstore.commonclass;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginVo {
	@NotBlank(message = "Invalid email")
	@Email
	private String emailId;
	@NotBlank(message = "Invalid Password")
	@Size(min = 6, max = 40)
	private String password;
	private long empId;
	private long updatedTime;
	private String mobile;
	private int otpNumber;

	public LoginVo() {
		this.updatedTime = System.currentTimeMillis();
	}

	public LoginVo(String emailid, String password, long empid) {
		this.emailId = emailid;
		this.password = password;
		this.empId = empid;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = System.currentTimeMillis();
	}

	public int getOtpNumber() {
		return otpNumber;
	}

	public void setOtpNumber(int otpNumber) {
		this.otpNumber = otpNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}

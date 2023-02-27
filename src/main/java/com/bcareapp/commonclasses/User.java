package com.bcareapp.commonclasses;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
	private long userId;
	private String title;
	@NotBlank
	private String firstName;
	private String lastName;
	private String fullName;
	@NotBlank
	private String gender;
	@Email
	private String emailId;
	private List<String> contactList;
	private List<BaseAddress> addressList;
	@Size(min = 6, max = 40)
	private String password;
	private List<String> roles;
	private List<String> roleString;

	public User() {
		super();
	}

	public User(long userId, String title, String firstName, String lastName, String emailId) {
		super();
		this.setUserId(userId);
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<String> getContactList() {
		return contactList;
	}

	public void setContactList(List<String> contactList) {
		this.contactList = contactList;
	}

	public List<BaseAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<BaseAddress> addressList) {
		this.addressList = addressList;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoleString() {
		return roleString;
	}

	public void setRoleString(List<String> roleString) {
		this.roleString = roleString;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}

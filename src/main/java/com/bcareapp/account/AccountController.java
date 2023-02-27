package com.bcareapp.account;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcareapp.bookstore.commonclass.LoginVo;
import com.bcareapp.bookstore.controller.AdminController;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.User;

@RestController
@RequestMapping("/account")
public class AccountController {
	private static final Logger logger = Logger.getLogger(AdminController.class.getName());
	@Autowired
	private AccountService accountService;

	@PostMapping("/login")
	public BaseResponse<String> loginUsingUserNamePassword(@Valid @RequestBody LoginVo login) {
		BaseResponse<String> response = null;
		try {
			response = accountService.userLogin(login);
		} catch (Exception e) {
			logger.error("Exception occurred in  signinSignupService SignUp", e);
		}
		return response;
	}

	@PostMapping("/signup")
	public BaseResponse<User> registerUser(@Valid @RequestBody User user) {
		BaseResponse<User> response = null;
		try {
			response = accountService.signUp(user);
		} catch (Exception e) {
			logger.error("Exception occurred in  signinSignupService SignUp", e);
		}
		return response;
	}

	/**
	 * This method is used to send OTP to the consumer for login (Latest)
	 */
	@RequestMapping(value = "/loginOTP", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse<Void> sendOTPForLogin(@RequestBody LoginVo login) {
		BaseResponse<Void> customResponse = null;
		try {
			customResponse = accountService.sendOTPForLogin(login);
		} catch (Exception e) {
			logger.error("Exception occurred while sending OTP to customer", e);
			customResponse = new BaseResponse<>();
			customResponse.setStatusCode(201);
			customResponse.setStatusMessage("INVALID_OTP");
		}
		return customResponse;
	}

	/**
	 * This method is used to validate the consumer details via OTP (Latest)
	 */
	@RequestMapping(value = "/loginthroughOTP", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse<String> loginOrRegisterUsingOTP(@RequestBody LoginVo login) {
		BaseResponse<String> response = new BaseResponse<String>();
		try {
			response = accountService.loginOrRegisterUsingOTP(login);
		} catch (Exception e) {
			logger.error("Exception occurred while login through otp", e);
		}
		return response;
	}

}

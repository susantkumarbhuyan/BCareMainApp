package com.bcareapp.bookstore.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcareapp.bookstore.service.UserService;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.User;

@RestController
@RequestMapping("/usercontrol")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public BaseResponse<User> getUserByUserId(long userId) {
		BaseResponse<User> response = null;
		try {
			response = userService.getUserByUserId(userId);
		} catch (Exception e) {
			logger.error("Exception occurred in UserService viewUserById", e);
		}
		return response;
	}
@PostMapping("/updateuser")
	public BaseResponse<String> updateUserDetails(@RequestBody User user) {
		BaseResponse<String> response = null;
		try {
			response = userService.updateUserDetail(user);
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService updateUserDetail", e);
		}
		return response;
	}

}

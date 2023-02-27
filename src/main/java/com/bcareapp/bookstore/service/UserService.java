package com.bcareapp.bookstore.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bcareapp.bookstore.dao.IUserDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.BaseResponseListData;
import com.bcareapp.commonclasses.User;
import com.bcareapp.constants.ResponseConstants;

@Service("userService")
public class UserService {
	private static final Logger logger = Logger.getLogger(UserService.class.getName());
	@Autowired
	private IUserDao userDao;
	@Autowired
	private PasswordEncoder encoder;

	public BaseResponseListData getAllUsersList() throws BCException {
		BaseResponseListData response = new BaseResponseListData();
		try {
			List<User> userList = userDao.getAllUsersList();
			if (userList.size() > 0) {
				response.setData(userList);
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.NOT_FOUND);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService getAllOrders", e);
		}
		return response;
	}

	public BaseResponse<User> getUserByUsername(String username) throws BCException {
		BaseResponse<User> response = new BaseResponse<>();
		try {
			User user = userDao.getUserDetailsByUserName(username);
			if (user != null) {
				response.setData(user);
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.NOT_FOUND);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in UserService viewUserById", e);
		}
		return response;
	}

	public BaseResponse<String> removeUserByUserId(int userId) throws BCException {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			int result = userDao.removeUserByUserId(userId);
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in UserService viewUserById", e);
		}
		return response;
	}

	public BaseResponse<String> removeAllUser() {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			int result = userDao.removeAllUsers();
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in UserService deleteAllUser", e);
		}
		return response;
	}

	public BaseResponse<User> getUserByUserId(long userId) throws BCException {
		BaseResponse<User> response = new BaseResponse<>();
		try {
			User user = userDao.getUserByUserId(userId);
			if (user != null) {
				response.setData(user);
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.NOT_FOUND);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in UserService viewUserById", e);
		}
		return response;
	}

	public BaseResponse<String> updateUserDetail(User user) {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			user.setPassword(encoder.encode(user.getPassword()));
			int result = userDao.insertandupdateUser(user);
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in UserService deleteAllUser", e);
		}
		return response;
	}
}

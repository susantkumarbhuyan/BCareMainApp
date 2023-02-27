package com.bcareapp.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bcareapp.bookstore.commonclass.JwtUser;
import com.bcareapp.bookstore.commonclass.LoginVo;
import com.bcareapp.bookstore.constant.TempConstant;
import com.bcareapp.bookstore.dao.IUserDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.User;
import com.bcareapp.commonservice.OtpService;
import com.bcareapp.constants.ResponseConstants;
import com.bcareapp.security.JwtValidator;
import com.bcareapp.security.UserDetailsServiceImpl;
import com.bcareapp.util.CommonsDataUtil;
import com.bcareapp.util.ServerSideScriptsUtil;

@Service
public class AccountService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtValidator jwtValidator;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private OtpService otpService;

	public BaseResponse<String> userLogin(@Valid LoginVo login) throws BCException {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			User user = userDao.getUserDetailsByUserName(login.getEmailId());
			if (user != null) {
				TempConstant.setUSERID(user.getUserId());
				boolean result = encoder.matches(login.getPassword(), user.getPassword());
				if (result) {
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(login.getEmailId(), login.getPassword()));

					// SecurityContextHolder.getContext().setAuthentication(authentication);
					UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmailId());
					List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
							.collect(Collectors.toList());
					JwtUser jwtUser = new JwtUser();
					jwtUser.setUsername(user.getEmailId());
					jwtUser.setUserId(user.getUserId());
					jwtUser.setUserType(JwtUser.USER);
					jwtUser.setRoles(roles.get(0));
					String token = jwtValidator.generate(jwtUser);
					user.setRoleString(roles);
					response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
					response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
					response.setData(token);
				} else {
					response.setStatusCode(ResponseConstants.WRONG);
					response.setStatusMessage(ResponseConstants.WRONG_PASSWORD);
				}
			} else {
				response.setStatusCode(ResponseConstants.NOT_FOUND);
				response.setStatusMessage(ResponseConstants.USER_NOTFOUND);
			}
		} catch (Exception e) {
			throw new BCException("Exception occured while inserting/updating doctor ", e);
		}
		return response;
	}

	public BaseResponse<User> signUp(@Valid User user) throws BCException {
		BaseResponse<User> response = new BaseResponse<>();
		try {
			User userData = userDao.getUserDetailsByUserName(user.getEmailId());
			if (userData != null) {
				response.setStatusCode(ResponseConstants.WRONG);
				response.setStatusMessage(ResponseConstants.USER_EXIST);
				return response;
			}
			user.setPassword(encoder.encode(user.getPassword()));
			List<String> strRoles = user.getRoleString();
			List<String> roles = new ArrayList<>();
			if (strRoles == null) {
				roles.add("ROLE_USER");
			} else {
				for (String role : strRoles) {
					switch (role) {
					case "admin":
						roles.add("ROLE_ADMIN");
						break;
					case "user":
						roles.add("ROLE_USER");
						break;
					default:
						roles.add("ROLE_USER");
					}
				}
			}
			user.setUserId(ServerSideScriptsUtil.generateUniqueLongVal("userId"));
			user.setRoles(roles);
			int userResult = userDao.insertandupdateUser(user);
			if (userResult == 1) {
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.USER_SUCESS);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.FAILED_MESSAGE);
			}

		} catch (Exception e) {
			throw new BCException("Exception occured while inserting/updating doctor ", e);
		}
		return response;
	}

	public BaseResponse<Void> sendOTPForLogin(LoginVo login) {
		BaseResponse<Void> customResponse = new BaseResponse<>();
		try {
			if (!CommonsDataUtil.isNullOrEmpty(login.getEmailId())) {
				User user = userDao.getUserDetailsByUserName(login.getEmailId());
				if (user != null) {
					boolean isSent = otpService.sendOtp(login.getEmailId());
					if (isSent) {
						customResponse.setStatusCode(200);
						customResponse.setStatusMessage("OTP has been sent successfully");
					}
				}else {
					customResponse.setStatusCode(401);
					customResponse.setStatusMessage("Please Register Your Self");
				}
			} else {
				customResponse.setStatusCode(401);
				customResponse.setStatusMessage("MOBILE_INVALID");
				return customResponse;
			}

//		if (login.getMobile() != null && login.getMobile().length() == DateTimeConstants.mobileLength) {
////TODO : sendOTP METHOD  call
//		} else {
//			customResponse.setStatusCode(401);
//			customResponse.setStatusMessage("MOBILE_INVALID");
//			return customResponse;
//		}
		} catch (BCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customResponse;
	}

	public BaseResponse<String> loginOrRegisterUsingOTP(LoginVo login) {
		BaseResponse<String> customResponse = new BaseResponse<>();
		try {
			if (!CommonsDataUtil.isNullOrEmpty(login.getEmailId())) {
				User user = userDao.getUserDetailsByUserName(login.getEmailId());
				if (user != null) {
					boolean isValid = otpService.validateOTP(login.getEmailId(), login.getOtpNumber());
					if (!isValid) {
						customResponse.setStatusCode(401);
						customResponse.setStatusMessage("Invelid OTP");
						return customResponse;
					} else {
						UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmailId());
						List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
								.collect(Collectors.toList());
						JwtUser jwtUser = new JwtUser();
						jwtUser.setUsername(user.getEmailId());
						jwtUser.setUserId(user.getUserId());
						jwtUser.setUserType(JwtUser.USER);
						jwtUser.setRoles(roles.get(0));
						String token = jwtValidator.generate(jwtUser);
						customResponse.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
						customResponse.setStatusCode(ResponseConstants.SUCCESS_CREATED);
						customResponse.setData(token);
					}
				}
			} else {
				customResponse.setStatusCode(401);
				customResponse.setStatusMessage("MOBILE_INVALID");
				return customResponse;
			}
		} catch (BCException e) {
			e.printStackTrace();
		}
		return customResponse;
	}

}

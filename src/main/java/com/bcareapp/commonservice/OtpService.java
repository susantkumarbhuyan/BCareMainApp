package com.bcareapp.commonservice;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.bcareapp.constants.ResponseConstants;
import com.bcareapp.util.EmailUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Description(value = "Service responsible for handling OTP related functionality.")
@Service
public class OtpService {

	private static final Integer EXPIRE_MIN = 5;
	private LoadingCache<String, Integer> otpCache;

	/**
	 * Method for validating provided OTP
	 *
	 * @param key       - provided key
	 * @param otpNumber - provided OTP number
	 * @return boolean value (true|false)
	 */

	public boolean sendOtp(String emailId) {
		Integer otp = getOPTByKey(emailId);
		if (otp == null) {
			otp = generateOTP(emailId);
		}
		String description = otp + ResponseConstants.OTP_MSG;
		String subject = "BCareApp OTP";
		return EmailUtil.sendMail(subject, description, emailId);
	}

	/**
	 * Constructor configuration.
	 */
	public OtpService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String s) throws Exception {
						return 0;
					}
				});
	}

	/**
	 * Method for generating OTP and put it in cache.
	 *
	 * @param key - cache key
	 * @return cache value (generated OTP number)
	 */
	public Integer generateOTP(String key) {
		Random random = new Random();
		int OTP = 100000 + random.nextInt(900000);
		otpCache.put(key, OTP);

		return OTP;
	}

	/**
	 * Method for getting OTP value by key.
	 *
	 * @param key - target key
	 * @return OTP value
	 */
	public Integer getOPTByKey(String key) {
		return otpCache.getIfPresent(key);
	}

	/**
	 * Method for removing key from cache.
	 *
	 * @param key - target key
	 */
	public void clearOTPFromCache(String key) {
		otpCache.invalidate(key);
	}

	public Boolean validateOTP(String key, Integer otpNumber) {
		// get OTP from cache
		Integer cacheOTP = getOPTByKey(key);
		if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
			clearOTPFromCache(key);
			return true;
		}
		return false;
	}
}

package com.bcareapp.fitnessdevice.googlefit;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;
import com.bcareapp.constants.BandConstant;
import com.bcareapp.util.HttpUtil;

public class GoogleFitBandUri {
	private static final Logger logger = Logger.getLogger(GoogleFitBandUri.class);

	public static String getAccessAndRefreshTokenUsingCode(FitnessDeviceToken fitnessDeviceToken) {
		String jsonInString = "";
		String url = BandConstant.GOOGLEFIT_TOKEN_URL + "?code=" + fitnessDeviceToken.getCode() + "&client_id="
				+ BandConstant.GOOGLEFIT_CLIENT_ID + "&client_secret=" + BandConstant.GOOGLEFIT_SECRETKEY
				+ "&grant_type=authorization_code&redirect_uri=" + BandConstant.GOOGLEFIT_REDIRECT_URL;
		System.out.println("url--> " + url);
		String newAccessRefreshTokenResponse = null;
		try {
			Map<String, String> headerMap = new HashMap<>();
			newAccessRefreshTokenResponse = HttpUtil.performPostRequest(jsonInString, url, headerMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newAccessRefreshTokenResponse;
	}

	public static String getAccessTokenUsingRefreshToken(FitnessDeviceToken fitnessDeviceToken) {
		String newAccessTokenObject = "";
		String refreshToken = fitnessDeviceToken.getRefreshToken();
		String uriForAccessToken = BandConstant.GOOGLEFIT_TOKEN_URL + "?client_id=" + BandConstant.GOOGLEFIT_CLIENT_ID
				+ "&client_secret=" + BandConstant.GOOGLEFIT_SECRETKEY + "&grant_type=refresh_token&refresh_token="
				+ refreshToken;
		String jsonInString = "";
		try {
			Map<String, String> headerMap = new HashMap<>();
			newAccessTokenObject = HttpUtil.performPostRequest(jsonInString, uriForAccessToken, headerMap);
		} catch (BCException e) {
			logger.error(e);
		}
		return newAccessTokenObject;
	}

	public static String getDailyDataInMinute(FitnessDeviceToken fitnessDeviceToken, long startTimeMillis,
			long endTimeMillis) {
//		long startTimeMillis = DateUtil.getTodayDateInMilli();
//		long endTimeMillis = startTimeMillis + DateUtil.ONE_DAY_IN_MILLISCONDS;
		String googleFitData = "";
		String dataUri = BandConstant.GOOGLEFIT_DATA_URL;
		String requestData = " { \"dataTypeName\": \"com.google.step_count.delta\" }, { \"dataTypeName\": \"com.google.distance.delta\" }, { \"dataTypeName\": \"com.google.body.temperature\" },  { \"dataTypeName\": \"com.google.sleep.segment\" },  { \"dataTypeName\": \"com.google.blood_pressure\" },{ \"dataTypeName\": \"com.google.oxygen_saturation\" },{\"dataTypeName\": \"com.google.heart_rate.bpm\"},{\"dataSourceId\": \"derived:com.google.heart_rate.bpm:com.google.android.gms:resting_heart_rate<-merge_heart_rate_bpm\"}";
		String jsonInString = "{\"aggregateBy\": [" + requestData + "],\"startTimeMillis\":" + startTimeMillis
				+ ", \"endTimeMillis\":" + endTimeMillis + "}";
		String Authorization = "Bearer " + fitnessDeviceToken.getAccessToken();
		try {
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("Authorization", Authorization);
			googleFitData = HttpUtil.performPostRequest(jsonInString, dataUri, headerMap);
		} catch (BCException e) {
			e.printStackTrace();
		}
		return googleFitData;
	}

//	public static String getDailyDataInSecond(FitnessDeviceToken fitnessDeviceToken) {
//		long startTimeMillis = DateUtil.getTodayDateInMilli();
//		long endTimeMillis = startTimeMillis + DateUtil.ONE_DAY_IN_MILLISCONDS;
//		String googleFitData = "";
//		String dataUri = BandConstant.GOOGLEFIT_DATA_URL;
//		String requestData = " { \"dataTypeName\": \"com.google.blood_pressure\" },{ \"dataTypeName\": \"com.google.oxygen_saturation\" },{\"dataTypeName\": \"com.google.heart_rate.bpm\"},{\"dataSourceId\": \"derived:com.google.heart_rate.bpm:com.google.android.gms:resting_heart_rate<-merge_heart_rate_bpm\"}";
//		String jsonInString = "{\"aggregateBy\": [" + requestData + "],"
//				+ " \"bucketByTime\": { \"durationMillis\": 1000 }," + "\"startTimeMillis\":" + startTimeMillis
//				+ ", \"endTimeMillis\":" + endTimeMillis + "}";
//		String Authorization = "Bearer " + fitnessDeviceToken.getAccessToken();
//		try {
//			Map<String, String> headerMap = new HashMap<>();
//			headerMap.put("Authorization", Authorization);
//			googleFitData = HttpUtil.performPostRequest(jsonInString, dataUri, headerMap);
//		} catch (BCException e) {
//			e.printStackTrace();
//		}
//		return googleFitData;
//	}

	public static String getProfileDetailUsingToken(String string) {
		String profileUri = "https://oauth2.googleapis.com/tokeninfo?access_token=" + string;
		String jsonInString = "";
		String profileResponse = null;
		Map<String, String> headerMap = new HashMap<>();
		try {
			profileResponse = HttpUtil.performPostRequest(jsonInString, profileUri, headerMap);
		} catch (BCException e) {
			e.printStackTrace();
		}
		return profileResponse;
	}

}

package com.bcareapp.bookstore;

import java.util.HashMap;
import java.util.Map;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.util.HttpUtil;

public class Temp {
	

	public static String getDailyDataInMinute() {
		String newAccessTokenObject = "";
		String sessionid ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3MDc3MzU1MDg1IiwidXNlcklkIjoiMTI4NDYzIiwidXNlclR5cGUiOiIyIiwicm9sZXMiOiIyIiwiZXhwaXJ5IjoiMTY3NzkyMzE0MDI4MiJ9.FGYTLAoC83S2qqNjAcoyTAbzpS5UPCsIG2VuVNdtRIs6SWr9U0UB4Rq80qj0lwjxKXqOmcKYeFEWDPtfbvs3oQ";
		 String url ="https://api-qa.healthsignz.net/QAHSignzAppServices/dashboard/secure/v2/dashboard";
		String jsonInString = "{\"brandId\":0,\"latitude\":12.9143093,\"longitude\":77.6266308,\"pinCode\":\"560102\",\"pocId\":0,\"profileId\":128463,\"service_Type\":0}";
	
		try {
			Map<String, String> headerMap = new HashMap<>();
			headerMap.put("sessionid", sessionid);
			newAccessTokenObject = HttpUtil.performPostRequest(jsonInString, url, headerMap);
		} catch (BCException e) {
			e.printStackTrace();
		}
		return newAccessTokenObject;
	}
	
}

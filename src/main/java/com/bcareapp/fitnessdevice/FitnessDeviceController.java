package com.bcareapp.fitnessdevice;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.fitness.FitnessBandData;
import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;
import com.bcareapp.util.DateUtil;
import com.google.gson.Gson;

@RestController
@RequestMapping("/fitnessdevice")
public class FitnessDeviceController {
	private static final Logger logger = Logger.getLogger(FitnessDeviceController.class);

	@Autowired
	private FitnessDeviceService fintessDeviceService;

	@RequestMapping(value = "/updatedevicetoken", method = RequestMethod.POST, headers = "Accept=application/json")
	public BaseResponse<Void> updateFitnessDeviceToken(@RequestBody FitnessDeviceToken fitnessDeviceToken) {
		logger.debug("Inside updateFitnessDeviceToken. Request Body=>" + new Gson().toJson(fitnessDeviceToken));
		BaseResponse<Void> response = null;
		try {
			logger.debug("Inside updateFitnessDeviceToken controller start =>" + DateUtil.getTimeStamp());
			response = fintessDeviceService.updateFitnessDeviceToken(fitnessDeviceToken);
			logger.debug("Inside updateFitnessDeviceToken controller End =>" + DateUtil.getTimeStamp());
		} catch (BCException e) {
			logger.error("Exception occurred while updating the device token", e);
		}
		return response;
	}

	@RequestMapping(value = "/fitnessdata", method = RequestMethod.GET)
	public FitnessBandData getFitnessData(long profileId,
			@RequestParam(value = "deviceTypeId", required = false) int deviceTypeId,
			@RequestParam(value = "dataTypes", required = false) ArrayList<Integer> dataTypes,
			@RequestParam(value = "fromDate", required = false, defaultValue = "0") long fromDate,
			@RequestParam(value = "toDate", required = false, defaultValue = "0") long toDate) {
		FitnessBandData response = null;
		try {
			logger.debug("Inside getFitnessData controller start =>" + DateUtil.getTimeStamp());
			response = fintessDeviceService.getFitnessData(profileId, deviceTypeId, dataTypes, fromDate, toDate);
			logger.debug("Inside getFitnessData controller end =>" + DateUtil.getTimeStamp());
		} catch (BCException e) {
			logger.error("Exception occurred while getting the fitness device data", e);
		}
		return response;
	}

	@RequestMapping(value = "/getcode", method = RequestMethod.GET)
	public void getCode(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "scope", required = false) String scope) {

		
	}
}

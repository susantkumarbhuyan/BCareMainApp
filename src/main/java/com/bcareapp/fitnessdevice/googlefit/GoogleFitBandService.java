package com.bcareapp.fitnessdevice.googlefit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.fitness.BloodPressure;
import com.bcareapp.commonclasses.fitness.BodyTemperature;
import com.bcareapp.commonclasses.fitness.Distance;
import com.bcareapp.commonclasses.fitness.FitnessBandData;
import com.bcareapp.commonclasses.fitness.GoogleFitData;
import com.bcareapp.commonclasses.fitness.HeartRate;
import com.bcareapp.commonclasses.fitness.OxygenSaturation;
import com.bcareapp.commonclasses.fitness.SleepSegment;
import com.bcareapp.commonclasses.fitness.StepCount;
import com.bcareapp.constants.BandConstant;
import com.bcareapp.constants.ResponseConstants;
import com.bcareapp.fitnessdevice.FitnessDeviceDao;
import com.bcareapp.fitnessdevice.bandfactory.BandOperator;
import com.bcareapp.util.CommonsDataUtil;
import com.bcareapp.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service("googleFitBandService")
public class GoogleFitBandService extends BandOperator {
	private static final Logger logger = Logger.getLogger(GoogleFitBandService.class);
	@Autowired
	private IGoolgeFitDeviceDao goolgeFitDeviceDao;
	@Autowired
	private FitnessDeviceDao fitnessDeviceDao;

	@SuppressWarnings("unchecked")
	@Override
	public BaseResponse<Void> updateFitnessDeviceToken() throws BCException {
		BaseResponse<Void> baseResponse = new BaseResponse<>();
		String accessRefreshTokenResponse = GoogleFitBandUri.getAccessAndRefreshTokenUsingCode(this.fitnessDeviceToken);
		logger.debug("accessRefreshTokenResponse-->" + accessRefreshTokenResponse);
		if (accessRefreshTokenResponse.contains("invalid_grant")) {
			baseResponse.setStatusCode(400);
			baseResponse.setStatusMessage("Authorization code is invalid");
		} else {
			try {
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, Object> json = mapper.readValue(accessRefreshTokenResponse, HashMap.class);
				String profileDetails = GoogleFitBandUri
						.getProfileDetailUsingToken(json.get("access_token").toString());
				HashMap<String, Object> profileResponse = mapper.readValue(profileDetails, HashMap.class);
				fitnessDeviceToken.setAccessToken(json.get("access_token").toString());
				fitnessDeviceToken.setExpiresIn(
						DateUtil.getTimeStamp() + (Long.parseLong(json.get("expires_in").toString()) * 1000));
				if (!CommonsDataUtil.isNullOrEmpty((String) json.get("refresh_token"))) {
					fitnessDeviceToken.setRefreshToken(json.get("refresh_token").toString());
				}
				fitnessDeviceToken.setScope(json.get("scope").toString());
				fitnessDeviceToken.setTokenType(json.get("token_type").toString());
				fitnessDeviceToken.setUserId(profileResponse.get("email").toString());
				fitnessDeviceToken.setClientId(BandConstant.GOOGLEFIT_CLIENT_ID);
				fitnessDeviceToken.setSecretKey(BandConstant.GOOGLEFIT_SECRETKEY);
				fitnessDeviceToken.setProfileId(fitnessDeviceToken.getProfileId());
				fitnessDeviceToken.setCode(fitnessDeviceToken.getCode());
				fitnessDeviceToken.setDeviceTypeId(fitnessDeviceToken.getDeviceTypeId());
				baseResponse = fitnessDeviceDao.updateFitnessDeviceToken(fitnessDeviceToken);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return baseResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FitnessBandData getFitnessData(List<Integer> dataTypes, long fromDate, long toDate) throws BCException {
		FitnessBandData fitnessBandData = new FitnessBandData();
		String minuteResponse = null;
		try {
			if (fromDate == 0 && toDate == 0) {
				fromDate = DateUtil.getTodayDateInMilli();
				toDate = fromDate + DateUtil.ONE_DAY_IN_MILLISCONDS;
			}
			minuteResponse = GoogleFitBandUri.getDailyDataInMinute(fitnessDeviceToken, fromDate, toDate);

			logger.debug("GoogleFit TODAY Minute DATA : " + new Gson().toJson(minuteResponse));
			if (minuteResponse.contains("Invalid Credentials") || minuteResponse.contains("UNAUTHENTICATED")) {
				String newAccessTokenObject = GoogleFitBandUri
						.getAccessTokenUsingRefreshToken(super.fitnessDeviceToken);
				if (newAccessTokenObject.contains("server error") || newAccessTokenObject.contains("invalid_grant")
						|| newAccessTokenObject.contains("400")) {
					fitnessBandData.setStatusCode(ResponseConstants.STATUS400);
					fitnessBandData.setStatusMessage(ResponseConstants.TOKEN_EXPIRED);
					return fitnessBandData;
				} else {
					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, Object> json = null;
					try {
						json = mapper.readValue(newAccessTokenObject, HashMap.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					super.fitnessDeviceToken.setAccessToken(json.get("access_token").toString());
					fitnessDeviceToken.setExpiresIn(
							DateUtil.getTimeStamp() + (Long.parseLong(json.get("expires_in").toString()) * 1000));
					// updating fitnessDeviceToken in DB
					fitnessDeviceDao.updateFitnessDeviceToken(super.fitnessDeviceToken);
					minuteResponse = GoogleFitBandUri.getDailyDataInMinute(fitnessDeviceToken, fromDate, toDate);
				}
			}
			if (minuteResponse.contains("invalidArgument")) {
				fitnessBandData.setStatusCode(ResponseConstants.STATUS400);
				fitnessBandData.setStatusMessage(ResponseConstants.INVALID_ARGUMENT);
				return fitnessBandData;
			}
			fitnessBandData.setStartDate(fromDate);
			fitnessBandData.setEndDate(toDate);
			fitnessBandData.setCurrentDate(fromDate);
			fitnessBandData.setCurrentUpdatedTime(DateUtil.getTimeStamp());
			fitnessBandData.setProfileId(this.fitnessDeviceToken.getProfileId());
			fitnessBandData.setDeviceTypeId(this.fitnessDeviceToken.getDeviceTypeId());
			saveGoogleFitData(minuteResponse, fitnessBandData);
			logger.debug("FitnessBandData _ GoogleFit : " + new Gson().toJson(fitnessBandData));
		} catch (BCException e) {
			throw new BCException("Exception occurred while fetching the googleFit Daily Data", e);
		}
		return fitnessBandData;
	}

	private GoogleFitData saveGoogleFitData(String minuteResponse, FitnessBandData fitnessBandData) throws BCException {
		ObjectMapper mapper = new ObjectMapper();
		GoogleFitData googleFitData = new GoogleFitData();
		try {
			GoogleFitDataResponse minuteDataResponse = mapper.readValue(minuteResponse, GoogleFitDataResponse.class);
			double distanceSummery = 0;
			double totalBodyTemperature = 0;
			double totalBloodPressureSystolic = 0;
			double totalBloodPressureDiastolic = 0;
			double totalOxygenSaturation = 0;
			double totalHeartRate = 0;

			long countBodyTemperature = 0;
			long countBloodPressure = 0;
			long countOxygenSaturation = 0;
			long countHeartRate = 0;
			long stepSummery = 0;
			List<StepCount> stepCountList = new ArrayList<>();
			List<Distance> distanceList = new ArrayList<>();
			List<SleepSegment> sleepSegmentList = new ArrayList<>();
			List<BodyTemperature> bodyTemperatureList = new ArrayList<>();
			List<BloodPressure> bloodPressureList = new ArrayList<>();
			List<OxygenSaturation> oxygenSaturationList = new ArrayList<>();
			List<HeartRate> heartRateList = new ArrayList<>();
			for (Bucket bucket : minuteDataResponse.getBucket()) {
				for (Dataset dataSet : bucket.getDataset()) {
					if (dataSet.getDataSourceId().contains("step_count.delta") && dataSet.getPoint().size() > 0
							&& dataSet.getPoint().get(0).getValue().get(0).getIntVal() > 0) {
						for (Point point : dataSet.getPoint()) {
							StepCount stepCount = new StepCount();
							long startTime = DateUtil.getMilliMinuteFromNanoSec(point.getStartTimeNanos());
							stepCount.setStartMinuteTime(startTime);
							stepCount.setEndMinuteTime(startTime + 60000l);
							stepCount.setStepCountValue(point.getValue().get(0).getIntVal());
							stepSummery += point.getValue().get(0).getIntVal();
							stepCountList.add(stepCount);
						}
					} else if (dataSet.getDataSourceId().contains("distance.delta") && dataSet.getPoint().size() > 0) {
						for (Point point : dataSet.getPoint()) {
							Distance distance = new Distance();
							long startTime = DateUtil.getMilliMinuteFromNanoSec(point.getStartTimeNanos());
							distance.setStartMinuteTime(startTime);
							distance.setEndMinuteTime(startTime + 60000l);
							distance.setDistanceValue(point.getValue().get(0).getFpVal());
							distanceSummery += point.getValue().get(0).getFpVal();
							distanceList.add(distance);
						}
					} else if (dataSet.getDataSourceId().contains("body.temperature")
							&& dataSet.getPoint().size() > 0) {
						for (Point point : dataSet.getPoint()) {
							BodyTemperature bodyTemperature = new BodyTemperature();
							long startTime = DateUtil.getMilliMinuteFromNanoSec(point.getStartTimeNanos());
							bodyTemperature.setStartTimestamp(startTime);
							bodyTemperature.setEndtimestamp(startTime + 60000l);
							bodyTemperature.setBodyTemperatureValue(point.getValue().get(0).getFpVal());
							totalBodyTemperature += point.getValue().get(0).getFpVal();
							countBodyTemperature++;
							bodyTemperatureList.add(bodyTemperature);
						}
					} else if (dataSet.getDataSourceId().contains("sleep.segment") && dataSet.getPoint().size() > 0) {
						for (Point point : dataSet.getPoint()) {
							SleepSegment sleepSegment = new SleepSegment();
							long startTime = DateUtil.getMilliMinuteFromNanoSec(point.getStartTimeNanos());
							sleepSegment.setStartDateTime(startTime);
							sleepSegment.setEndDateTime(startTime + 60000l);
							sleepSegment.setSleepSegmentValue((int) point.getValue().get(0).getIntVal());
							sleepSegment.setSleepTypeName(getSleepTypeName(point.getValue().get(0).getIntVal()));
							sleepSegmentList.add(sleepSegment);
						}
					} else if (dataSet.getDataSourceId().contains("blood_pressure") && dataSet.getPoint().size() > 0) {
						for (Point point : dataSet.getPoint()) {
							BloodPressure bloodPressure = new BloodPressure();
							long startTime = DateUtil.getMilliSecFromNanoSec(point.getStartTimeNanos());
							bloodPressure.setStartSecondMillis(startTime);
							bloodPressure.setEndSecondMillis(startTime + 1000l);
							bloodPressure.setSystolicValue(point.getValue().get(0).getFpVal());
							bloodPressure.setDiastolicValue(point.getValue().get(1).getFpVal());
							totalBloodPressureSystolic += point.getValue().get(0).getFpVal();
							totalBloodPressureDiastolic += point.getValue().get(1).getFpVal();
							countBloodPressure++;
							bloodPressureList.add(bloodPressure);
						}
					} else if (dataSet.getDataSourceId().contains("oxygen_saturation")
							&& dataSet.getPoint().size() > 0) {
						for (Point point : dataSet.getPoint()) {
							long startTime = DateUtil.getMilliSecFromNanoSec(point.getStartTimeNanos());
							OxygenSaturation oxygenSaturation = new OxygenSaturation();
							oxygenSaturation.setStartSecondMillis(startTime);
							oxygenSaturation.setEndSecondMillis(startTime + 1000l);
							oxygenSaturation.setOxygenSaturationValue(point.getValue().get(0).getFpVal());
							oxygenSaturation.setSupplementalOxygenFlowRateValue(point.getValue().get(1).getFpVal());
							totalOxygenSaturation += point.getValue().get(0).getFpVal();
							countOxygenSaturation++;
							oxygenSaturationList.add(oxygenSaturation);
						}
					} else if (dataSet.getDataSourceId().contains("resting_heart_rate")) {
						for (Point point : dataSet.getPoint()) {
							HeartRate heartRate = new HeartRate();
							long startTime = DateUtil.getMilliSecFromNanoSec(point.getStartTimeNanos());
							heartRate.setStartSecondMillis(startTime);
							heartRate.setEndSecondMillis(startTime + 1000l);
							heartRate.setRestingHeartRate((int) point.getValue().get(0).getFpVal());
							heartRateList.add(heartRate);
						}
					} else if (dataSet.getDataSourceId().contains("heart_rate") && dataSet.getPoint().size() > 0) {
						for (Point point : dataSet.getPoint()) {
							long startTime = DateUtil.getMilliSecFromNanoSec(point.getStartTimeNanos());
							HeartRate heartRate = new HeartRate();
							heartRate.setStartSecondMillis(startTime);
							heartRate.setEndSecondMillis(startTime + 1000l);
							heartRate.setHeartRateValue(point.getValue().get(0).getFpVal());
							totalHeartRate += point.getValue().get(0).getFpVal();
							countHeartRate++;
							heartRateList.add(heartRate);
						}
					}
				}

				googleFitData.setStepSummery(stepSummery);
				googleFitData.setDistanceSummery(distanceSummery);
				googleFitData.setBodyTemperatureAvg(getAverageValue(totalBodyTemperature, countBodyTemperature));
				googleFitData
						.setBloodPressureSystolicAvg(getAverageValue(totalBloodPressureSystolic, countBloodPressure));
				googleFitData
						.setBloodPressureDiastolicAvg(getAverageValue(totalBloodPressureDiastolic, countBloodPressure));
				googleFitData.setOxygenSaturationAvg(getAverageValue(totalOxygenSaturation, countOxygenSaturation));
				googleFitData.setHeartRateAvg(getAverageValue(totalHeartRate, countHeartRate));
				googleFitData.setStepCount(stepCountList);
				googleFitData.setDistance(distanceList);
				googleFitData.setBodyTemperature(bodyTemperatureList);
				googleFitData.setSleepSegment(sleepSegmentList);
				googleFitData.setBloodPressure(bloodPressureList);
				googleFitData.setOxygenSaturation(oxygenSaturationList);
				googleFitData.setHeartRate(heartRateList);
				googleFitData.setStartDateMillis(DateUtil.getTodayDateInMilli());
				googleFitData.setEndDateMillis(DateUtil.getTodayDateInMilli() + DateUtil.ONE_DAY_IN_MILLISCONDS);
				googleFitData.setProfileId(this.fitnessDeviceToken.getProfileId());
				goolgeFitDeviceDao.insertGoogleFitData(googleFitData);
			}
		} catch (Exception e) {
			throw new BCException("Exception occurred while updating the googlefit data", e);
		}
		return googleFitData;
	}

	public double getAverageValue(double sum, long numbers) {
		double average = 0;
		if (sum > 0 && numbers > 0) {
			average = sum / numbers;
		}
		return average;
	}

	public String getSleepTypeName(long sleepType) {
		String name = "";
		switch ((int) sleepType) {
		case 0:
			name = "UNSPECIFID";
			break;
		case 1:
			name = "AWAKE";
			break;
		case 2:
			name = "SLEEPING";
			break;
		case 3:
			name = "OUTOFBED";
			break;
		case 4:
			name = "LIGHTSLEEP";
			break;
		case 5:
			name = "DEEPSLEEP";
			break;
		case 6:
			name = "REMSLEEP";
			break;
		}
		return name;
	}

}

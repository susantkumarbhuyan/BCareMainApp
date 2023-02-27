package com.bcareapp.fitnessdevice.bandfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;
import com.bcareapp.fitnessdevice.googlefit.GoogleFitBandService;

@Service("bandFactory")
public class BandFactory {
	enum BandType {
		 GOOGLEFIT(4);

		private int value;

		BandType(int value) {
			this.value = value;
		}
	}
	@Autowired
	private GoogleFitBandService googleFitBandService;

	public BandOperator createFactory(int bandType, FitnessDeviceToken fitnessDeviceToken) throws BCException {
		BandOperator operator = null;
		if (bandType == BandType.GOOGLEFIT.value) {
			operator = googleFitBandService;
		}
		operator.setFitnessDeviceToken(fitnessDeviceToken);
		return operator;
	}
}

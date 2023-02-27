package com.bcareapp.fitnessdevice.bandfactory;

import com.bcareapp.commonclasses.fitness.FitnessDeviceToken;

public abstract class BandOperator  implements IBandActivities {
	protected FitnessDeviceToken fitnessDeviceToken;

	public void setFitnessDeviceToken(FitnessDeviceToken fitnessDeviceToken) {
		this.fitnessDeviceToken = fitnessDeviceToken;
	}

}

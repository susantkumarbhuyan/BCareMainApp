package com.bcareapp.fitnessdevice.googlefit;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.fitness.GoogleFitData;

public interface IGoolgeFitDeviceDao {
	public void insertGoogleFitData(GoogleFitData googleFitData) throws BCException;
}

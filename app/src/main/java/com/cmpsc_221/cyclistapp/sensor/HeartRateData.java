//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp.sensor;

//#========# #========# #========# #========# #========# #========# #========# #========# #========#

/**
 * Created by green_000 on 4/27/2015.
 */
public class HeartRateData {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public constructor

	public HeartRateData(double bpm, double calories) {
		mHeartBpm = bpm;
		mCalories = calories;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public get functions

	public double getHeartRate() {
		return mHeartBpm;
	}

	public double getCalories() {
		return mCalories;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected set functions

	protected void setHeartRate(double bpm) {
		mHeartBpm = bpm;
	}

	protected void setCalories(double calories) {
		mCalories = calories;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	private double mHeartBpm;
	private double mCalories;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
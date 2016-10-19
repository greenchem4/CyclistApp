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
public class CadenceData {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public constructor

	public CadenceData(double tireRpm, double pedalRpm, double distance, double speed) {
		mTireRpm = tireRpm;
		mPedalRpm = pedalRpm;
		mDistance = distance;
		mSpeed = speed;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public get functions

	public double getTireRpm() {
		return mTireRpm;
	}

	public double getPedalRpm() {
		return mPedalRpm;
	}

	public double getDistance() {
		return mDistance;
	}

	public double getSpeed() {
		return mSpeed;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected set functions

	protected void setTireRpm(double tireRpm) {
		mTireRpm = tireRpm;
	}

	protected void setPedalRpm(double pedalRpm) {
		mPedalRpm = pedalRpm;
	}

	protected void setDistance(double distance) {
		mDistance = distance;
	}

	protected void setSpeed(double speed) {
		mSpeed = speed;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	private double mTireRpm;
	private double mPedalRpm;
	private double mDistance;
	private double mSpeed;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
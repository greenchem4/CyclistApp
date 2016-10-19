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
public class GpsData {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public constructor

	public GpsData(double altitude, double longitude, double latitude, double distance, double speed) {
		mAltitude = altitude;
		mLongitude = longitude;
		mLatitude = latitude;
		mDistance = distance;
		mSpeed = speed;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public get functions

	public double getAltitude() {
		return mAltitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getDistance() {
		return mDistance;
	}

	public double getSpeed() {
		return mSpeed;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected set functions

	protected void setAltitude(double altitude) {
		mAltitude = altitude;
	}

	protected void setLongitude(double longitude) {
		mLongitude = longitude;
	}

	protected void setLatitude(double latitude) {
		mLatitude = latitude;
	}

	protected void setDistance(double distance) {
		mDistance = distance;
	}

	protected void setSpeed(double speed) {
		mSpeed = speed;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	private double mAltitude;
	private double mLongitude;
	private double mLatitude;
	private double mDistance;
	private double mSpeed;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
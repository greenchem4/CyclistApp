//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cmpsc_221.cyclistapp.sensor.CadenceData;
import com.cmpsc_221.cyclistapp.sensor.GpsData;
import com.cmpsc_221.cyclistapp.sensor.HeartRateData;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

public class RealTimeDataService extends Service {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static constants

	private static final boolean mDebug = true;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public constructor

	public RealTimeDataService() {
		//TODO: implement constructor
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public lifecycle function

	@Override
	public void onCreate() {
		//TODO: implement on create
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		//TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	private GpsData mGps;
	private CadenceData mCadence;
	private HeartRateData mHeartRate;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
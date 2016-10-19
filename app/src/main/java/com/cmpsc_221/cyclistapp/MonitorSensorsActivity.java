//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.OnDataPointListener;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

public class MonitorSensorsActivity extends GoogleActivity implements
	OnDataPointListener {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static variables

	/**
	 * Identifier for log entries
	 */
	private static final String TAG = "MonitorSensorsActivity";

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected lifecycle functions

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitor_sensors);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		restoreGooglePref();
	}

	@Override
	protected void onStart() {
		//TODO: implement on start
		super.onStart();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		//TODO: implement on restore instance state

		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		//TODO: implement on resume
		super.onResume();
	}

	@Override
	protected void onPause() {
		//TODO: implement on pause
		super.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		//TODO: implement on save instance state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onStop() {
		//TODO: implement on stop
		super.onStop();
	}

	@Override
	protected void onRestart() {
		//TODO: implement on restart
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		//TODO: implement on destroy
		super.onDestroy();
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public override functions

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_monitor_sensors, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Handle action bar item clicks here.
		//The action bar will automatically handle clicks on the Home/Up button,
		//so long as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.action_settings:
				openSettings();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public view functions

	public void displayRealTimeData(View view) {
		Intent intent = new Intent(this, RealTimeDisplayActivity.class);
		startActivity(intent);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public menu functions

	/**
	 * This opens up the setting activity
	 */
	public void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public google play setup functions

	@Override
	public void onConnected(Bundle connectionHint) {
		super.onConnected(connectionHint);
		//Now you can make calls to the Fitness APIs. Put application specific code here.
		restoreGooglePref();
		if(mSessionIdentifier == "null") {
			subscribe();
			Toast.makeText(getApplicationContext(), "Monitoring started", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDataPoint(DataPoint dataPoint) {
		//Do cool stuff that matters.
		Toast.makeText(getApplicationContext(), "Data point", Toast.LENGTH_SHORT).show();
		//TODO: implement on data point
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private restore state function

	private void restoreGooglePref() {
		SharedPreferences save = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
		mSessionIdentifier = save.getString(Constant.PREF_SESSION, "null");
		mTripNum = save.getInt(Constant.PREF_TRIP, 0);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private sensor functions

	private void subscribe() {
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_LOCATION_SAMPLE)
			.setResultCallback(new CheckSubscribe());
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_DISTANCE_DELTA)
			.setResultCallback(new CheckSubscribe());
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_SPEED)
			.setResultCallback(new CheckSubscribe());

		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_CYCLING_PEDALING_CADENCE)
			.setResultCallback(new CheckSubscribe());
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_CYCLING_WHEEL_RPM)
			.setResultCallback(new CheckSubscribe());
		/*
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_DISTANCE_DELTA)
			.setResultCallback(new CheckSubscribe());
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_SPEED)
			.setResultCallback(new CheckSubscribe());
		 */

		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_HEART_RATE_BPM)
			.setResultCallback(new CheckSubscribe());
		Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_CALORIES_EXPENDED)
			.setResultCallback(new CheckSubscribe());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		mSessionIdentifier = Constant.SESSION + mTripNum;
		mTripNum = mTripNum + 1;

		SharedPreferences.Editor editor = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE).edit();
		editor.putInt(Constant.PREF_TRIP, mTripNum);
		editor.putString(Constant.PREF_SESSION, mSessionIdentifier);
		editor.commit();

		Session session = new Session.Builder()
			.setName("Cycling session")
			.setIdentifier(mSessionIdentifier)
			.setDescription(Constant.SESSION_DESCRIPTION)
			.setStartTime(calendar.getTimeInMillis(), TimeUnit.MILLISECONDS)
			.build();

		PendingResult<Status> pendingResult =
			Fitness.SessionsApi.startSession(mGoogleApiClient, session);
	}

	public class CheckSubscribe implements ResultCallback<Status> {
		//#========# #========# #========# #========# #========# #========# #========# #----# #----#
		//Public override functions

		@Override
		public void onResult(Status status) {
			if(status.isSuccess()) {
				if(status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED)
					Log.i(TAG, "Already subscribed");
				else
					Log.i(TAG, "Successfully subscribed");
			}
			else
				Log.i(TAG, "Problem subscribing to sensors");
		}

		//#========# #========# #========# #========# #========# #========# #========# #----# #----#
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	/**
	 * The identifier for the current session
	 */
	private String mSessionIdentifier;

	/**
	 * The trip unique identifier.
	 * This is incremented before each trip
	 */
	private int mTripNum;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
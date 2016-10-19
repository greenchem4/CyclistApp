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
import android.preference.PreferenceManager;
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
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

public class MainActivity extends GoogleActivity {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static variables

	/**
	 * Identifier for log entries
	 */
	private static final String TAG = "MainActivity";

	/**
	 * This is how to formatt the dates
	 */
	private static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected lifecycle functions

	/**
	 * The initial entry point for the app.
	 *
	 * This is called when the activity is created
	 *
	 * @param savedInstanceState The session save data
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PreferenceManager.setDefaultValues(this, R.xml.settings_general, false);
		restoreGooglePref();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		getMenuInflater().inflate(R.menu.menu_main, menu);
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

	/**
	 * Switches to monitoring activity.
	 *
	 * The monitoring activing subscribes to the sensor data when started.
	 *
	 * @param view the calling view (layout)
	 */
	public void startMonitoring(View view) {
		Intent intent = new Intent(this, MonitorSensorsActivity.class);
		startActivity(intent);
	}

	/**
	 * Database part is still a work in progress.
	 * \\
	 * @param view the calling view (layout)
	 */
	public void startDatabase(View view) {
		Toast.makeText(getApplicationContext(), "Database WIP", Toast.LENGTH_SHORT).show();
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

	/**
	 * Called when a successful connection to the google API has been made.
	 *
	 * @param connectionHint passed on to super class
	 */
	@Override
	public void onConnected(Bundle connectionHint) {
		super.onConnected(connectionHint);
		//Now you can make calls to the Fitness APIs. Put application specific code here.
		restoreGooglePref();
		if(!mSessionIdentifier.equals("null")) {
		//Toast.makeText(getApplicationContext(), mSessionIdentifier, Toast.LENGTH_SHORT).show();
			unsubscribe();
			Toast.makeText(getApplicationContext(), "Monitoring stopped", Toast.LENGTH_SHORT).show();
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private restore state function

	/**
	 * This function restores the google preferences which is currently the session identifier.
	 */
	private void restoreGooglePref() {
		SharedPreferences save = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
		mSessionIdentifier = save.getString(Constant.PREF_SESSION, "null");
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private unsubscribe functions

	/**
	 * This method unsubscribes the app from recording sensor data.
	 *
	 * This will be called when main is returned to after monitoring has been stoped.
	 */
	private void unsubscribe() {

		PendingResult<SessionStopResult> pendingResult =
			Fitness.SessionsApi.stopSession(mGoogleApiClient, mSessionIdentifier);


		//getSessionData();

		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_LOCATION_SAMPLE)
			.setResultCallback(new CheckUnsubscribe());
		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_DISTANCE_DELTA)
			.setResultCallback(new CheckUnsubscribe());
		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_SPEED)
			.setResultCallback(new CheckUnsubscribe());

		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_CYCLING_PEDALING_CADENCE)
			.setResultCallback(new CheckUnsubscribe());
		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_CYCLING_WHEEL_RPM)
			.setResultCallback(new CheckUnsubscribe());
		/*
		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_DISTANCE_DELTA)
			.setResultCallback(new CheckUnsubscribe());
		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_SPEED)
			.setResultCallback(new CheckUnsubscribe());
		 */

		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_HEART_RATE_BPM)
			.setResultCallback(new CheckUnsubscribe());
		Fitness.RecordingApi.unsubscribe(mGoogleApiClient, DataType.TYPE_CALORIES_EXPENDED)
			.setResultCallback(new CheckUnsubscribe());

		mSessionIdentifier = "null";
		SharedPreferences.Editor editor = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE).edit();
		editor.putString(Constant.PREF_SESSION, mSessionIdentifier);
		editor.commit();
	}

	/**
	 * The following code was moslty obtained from Google BasicHisorySessions example
	 */
	private void getSessionData() {
		//CRASHES APP

		//Set a start and end time for our query, using a start time of 1 week before this moment.
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		long endTime = cal.getTimeInMillis();
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		long startTime = cal.getTimeInMillis();

		//Build a session read request
		SessionReadRequest readRequest = new SessionReadRequest.Builder()
			.setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
			.read(DataType.TYPE_LOCATION_SAMPLE)
			.read(DataType.TYPE_DISTANCE_DELTA)
			.read(DataType.TYPE_SPEED)
			.read(DataType.TYPE_CYCLING_PEDALING_CADENCE)
			.read(DataType.TYPE_CYCLING_WHEEL_RPM)
			.read(DataType.TYPE_HEART_RATE_BPM)
			.read(DataType.TYPE_CALORIES_EXPENDED)
			.setSessionId(mSessionIdentifier)
			.build();

		//Invoke the Sessions API to fetch the session with the query and wait for the result of the read request.
		SessionReadResult sessionReadResult = Fitness.SessionsApi
			.readSession(mGoogleApiClient, readRequest).await(1, TimeUnit.MINUTES);


		//Get a list of the sessions that match the criteria to check the result.
		Log.i(TAG, "Session read was successful. Number of returned sessions is: "
			+ sessionReadResult.getSessions().size());
		for (Session session : sessionReadResult.getSessions()) {
			//Process the session
			dumpSession(session);

			//Process the data sets for this session
			List<DataSet> dataSets = sessionReadResult.getDataSet(session);
			for (DataSet dataSet : dataSets) {
				dumpDataSet(dataSet);
			}
		}
	}

	/**
	 * Obtained form google api tutorial documentation.
	 */
	public class CheckUnsubscribe implements ResultCallback<Status> {
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

	/**
	 * The following code was obtained from google fit BasicHistorySessions example
	 * @param dataSet the information to dump
	 */
	private void dumpDataSet(DataSet dataSet) {
		Log.i(TAG, "Data returned for Data type: " + dataSet.getDataType().getName());
		for (DataPoint dp : dataSet.getDataPoints()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			Log.i(TAG, "Data point:");
			Log.i(TAG, "\tType: " + dp.getDataType().getName());
			Log.i(TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
			Log.i(TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
			for(Field field : dp.getDataType().getFields()) {
				Log.i(TAG, "\tField: " + field.getName() +
					" Value: " + dp.getValue(field));
			}
		}
	}

	/**
	 * The following code was obtained from google fit BasicHistorySessions example
	 * @param session the sesion to dump
	 */
	private void dumpSession(Session session) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Log.i(TAG, "Data returned for Session: " + session.getName()
			+ "\n\tDescription: " + session.getDescription()
			+ "\n\tStart: " + dateFormat.format(session.getStartTime(TimeUnit.MILLISECONDS))
			+ "\n\tEnd: " + dateFormat.format(session.getEndTime(TimeUnit.MILLISECONDS)));
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	/**
	 * The identifier for the current session
	 */
	private String mSessionIdentifier;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
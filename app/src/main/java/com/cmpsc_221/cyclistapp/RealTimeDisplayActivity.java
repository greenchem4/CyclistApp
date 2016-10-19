//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpsc_221.cyclistapp.sensor.CadenceData;
import com.cmpsc_221.cyclistapp.sensor.GpsData;
import com.cmpsc_221.cyclistapp.sensor.HeartRateData;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

public class RealTimeDisplayActivity extends GoogleActivity {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static variables

	/**
	 * Identifier for log entries
	 */
	private static final String TAG = "RealTimeDisplayActivity";

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected lifecycle functions

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_real_time_display);
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
		getMenuInflater().inflate(R.menu.menu_real_time_display, menu);
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

	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private restore state function

	private void restoreGooglePref() {
		SharedPreferences save = getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
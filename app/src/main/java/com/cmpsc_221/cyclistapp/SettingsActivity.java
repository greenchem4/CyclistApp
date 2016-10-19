//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.app.Activity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.view.MenuItem;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

public class SettingsActivity extends Activity {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public lifecycle functions

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public override functions

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public static preference fragment classes

	public static class SettingsFragment extends PreferenceFragment {
		//#========# #========# #========# #========# #========# #========# #========# #----# #----#
		//Public lifecycle function

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			//Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.settings_general);
		}

		//#========# #========# #========# #========# #========# #========# #========# #----# #----#
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//preference key names

	public static final String PREF_SETTINGS = "key_pref_settings";

	public static final String PREF_GENERAL = "key_pref_general";
	public static final String PREF_DATABASE = "key_pref_database";
	public static final String PREF_DB_URL = "key_pref_db_url";
	public static final String PREF_DB_USER = "key_pref_db_user";
	public static final String PREF_DB_PASSWORD = "key_pref_db_password";

	public static final String PREF_SENSORS = "key_pref_sensors";
	public static final String PREF_GPS = "key_pref_gps";
	public static final String PREF_CADENCE = "key_pref_cadence";
	public static final String PREF_HEART = "key_pref_heart";

	public static final String PREF_GRAPH = "key_pref_graph";

	public static final String PREF_GRAPH_1 = "key_pref_graph_1";
	public static final String PREF_GPS_GRAPH_1 = "key_pref_gps_graph_1";
	public static final String PREF_GPS_ALTITUDE_1 = "key_pref_gps_altitude_1";
	public static final String PREF_GPS_DISTANCE_1 = "key_pref_gps_distance_1";
	public static final String PREF_GPS_SPEED_1 = "key_pref_gps_speed_1";

	public static final String PREF_CADENCE_GRAPH_1 = "key_pref_cadence_graph_1";
	public static final String PREF_CADENCE_PEDAL_1 = "key_pref_cadence_pedal_1";
	public static final String PREF_CADENCE_WHEEL_1 = "key_pref_cadence_wheel_1";
	public static final String PREF_CADENCE_DISTANCE_1 = "key_pref_cadence_distance_1";
	public static final String PREF_CADENCE_SPEED_1 = "key_pref_cadence_speed_1";

	public static final String PREF_HEART_GRAPH_1 = "key_pref_heart_graph_1";
	public static final String PREF_HEART_BPM_1 = "key_pref_heart_bpm_1";
	public static final String PREF_HEART_CALORIES_1 = "key_pref_heart_calories_1";

	public static final String PREF_GRAPH_2 = "key_pref_graph_2";
	public static final String PREF_GPS_GRAPH_2 = "key_pref_gps_graph_2";
	public static final String PREF_GPS_ALTITUDE_2 = "key_pref_gps_altitude_2";
	public static final String PREF_GPS_DISTANCE_2 = "key_pref_gps_distance_2";
	public static final String PREF_GPS_SPEED_2 = "key_pref_gps_speed_2";

	public static final String PREF_CADENCE_GRAPH_2 = "key_pref_cadence_graph_2";
	public static final String PREF_CADENCE_PEDAL_2 = "key_pref_cadence_pedal_2";
	public static final String PREF_CADENCE_WHEEL_2 = "key_pref_cadence_wheel_2";
	public static final String PREF_CADENCE_DISTANCE_2 = "key_pref_cadence_distance_2";
	public static final String PREF_CADENCE_SPEED_2 = "key_pref_cadence_speed_2";

	public static final String PREF_HEART_GRAPH_2 = "key_pref_heart_graph_2";
	public static final String PREF_HEART_BPM_2 = "key_pref_heart_bpm_2";
	public static final String PREF_HEART_CALORIES_2 = "key_pref_heart_calories_2";
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
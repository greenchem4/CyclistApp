//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmpsc_221.cyclistapp.sensor.CadenceData;
import com.cmpsc_221.cyclistapp.sensor.GpsData;
import com.cmpsc_221.cyclistapp.sensor.HeartRateData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

//#========# #========# #========# #========# #========# #========# #========# #========# #========#

/**
 * Created by green_000 on 5/2/2015.
 */
public class GraphFragment extends Fragment {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static variables

	/**
	 * Identifier for log entries
	 */
	private static final String TAG = "GraphFragment";

	private static final boolean mDebug = true;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public lifecycle function

	@Override
	public void onAttach(Activity activity) {
		//TODO: implement on attatch
		super.onAttach(activity);
		restoreState();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//TODO: implement on create
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStance) {
		//TODO: implement on create view
		//super.onCreateView(inflater, container, savedInstanceStance);
		return inflater.inflate(R.layout.fragment_graph_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceStance) {
		//TODO: implement on activity created
		super.onActivityCreated(savedInstanceStance);

		mGraph1 = (GraphView) getActivity().findViewById(R.id.graph);
		mGraph2 = (GraphView) getActivity().findViewById(R.id.graph2);

		mSeries[0] = new LineGraphSeries<>();
		mSeries[0].setTitle(getActivity().getString(R.string.graph_series_gps_altitude));
		//mSeries[0].setDrawDataPoints(true);
		//mSeries[0].setDataPointsRadius(10);
		mSeries[0].setColor(Color.GRAY);
		mSeries[0].setThickness(8);

		mSeries[1] = new LineGraphSeries<>();
		mSeries[1].setTitle(getActivity().getString(R.string.graph_series_gps_distance));
		//mSeries[1].setDrawDataPoints(true);
		//mSeries[1].setDataPointsRadius(10);
		mSeries[1].setColor(Color.CYAN);
		mSeries[1].setThickness(8);

		mSeries[2] = new LineGraphSeries<>();
		mSeries[2].setTitle(getActivity().getString(R.string.graph_series_gps_speed));
		//mSeries[2].setDrawDataPoints(true);
		//mSeries[2].setDataPointsRadius(10);
		mSeries[2].setColor(Color.GREEN);
		mSeries[2].setThickness(8);

		mSeries[3] = new LineGraphSeries<>();
		mSeries[3].setTitle(getActivity().getString(R.string.graph_series_cadence_pedal));
		//mSeries[3].setDrawDataPoints(true);
		//mSeries[3].setDataPointsRadius(10);
		mSeries[3].setColor(Color.MAGENTA);
		mSeries[3].setThickness(8);

		mSeries[4] = new LineGraphSeries<>();
		mSeries[4].setTitle(getActivity().getString(R.string.graph_series_cadence_wheel));
		//mSeries[4].setDrawDataPoints(true);
		//mSeries[4].setDataPointsRadius(10);
		mSeries[4].setColor(Color.BLUE);
		mSeries[4].setThickness(8);

		mSeries[5] = new LineGraphSeries<>();
		mSeries[5].setTitle(getActivity().getString(R.string.graph_series_cadence_distance));
		//mSeries[5].setDrawDataPoints(true);
		//mSeries[5].setDataPointsRadius(10);
		mSeries[5].setColor(Color.CYAN);
		mSeries[5].setThickness(8);

		mSeries[6] = new LineGraphSeries<>();
		mSeries[6].setTitle(getActivity().getString(R.string.graph_series_cadence_speed));
		//mSeries[6].setDrawDataPoints(true);
		//mSeries[6].setDataPointsRadius(10);
		mSeries[6].setColor(Color.GREEN);
		mSeries[6].setThickness(8);

		mSeries[7] = new LineGraphSeries<>();
		mSeries[7].setTitle(getActivity().getString(R.string.graph_series_heart_bpm));
		//mSeries[7].setDrawDataPoints(true);
		//mSeries[7].setDataPointsRadius(10);
		mSeries[7].setColor(Color.RED);
		mSeries[7].setThickness(8);

		mSeries[8] = new LineGraphSeries<>();
		mSeries[8].setTitle(getActivity().getString(R.string.graph_series_heart_calories));
		//mSeries[8].setDrawDataPoints(true);
		//mSeries[8].setDataPointsRadius(10);
		mSeries[8].setColor(Color.BLACK);
		mSeries[8].setThickness(8);

		if(mPlot1[0])
			mGraph1.addSeries(mSeries[0]);
		if(mPlot1[1])
			mGraph1.addSeries(mSeries[1]);
		if(mPlot1[2])
			mGraph1.addSeries(mSeries[2]);

		if(mPlot1[3])
			mGraph1.addSeries(mSeries[3]);
		if(mPlot1[4])
			mGraph1.addSeries(mSeries[4]);
		if(mPlot1[5])
			mGraph1.addSeries(mSeries[5]);
		if(mPlot1[6])
			mGraph1.addSeries(mSeries[6]);

		if(mPlot1[7])
			mGraph1.addSeries(mSeries[7]);
		if(mPlot1[8])
			mGraph1.addSeries(mSeries[8]);

		if(mPlot2[0])
			mGraph2.addSeries(mSeries[0]);
		if(mPlot2[1])
			mGraph2.addSeries(mSeries[1]);
		if(mPlot2[2])
			mGraph2.addSeries(mSeries[2]);

		if(mPlot2[3])
			mGraph2.addSeries(mSeries[3]);
		if(mPlot2[4])
			mGraph2.addSeries(mSeries[4]);
		if(mPlot2[5])
			mGraph2.addSeries(mSeries[5]);
		if(mPlot2[6])
			mGraph2.addSeries(mSeries[6]);

		if(mPlot2[7])
			mGraph2.addSeries(mSeries[7]);
		if(mPlot2[8])
			mGraph2.addSeries(mSeries[8]);

		mGraph1.getViewport().setXAxisBoundsManual(true);
		mGraph1.getViewport().setMinX(0);
		mGraph1.getViewport().setMaxX(60);

		mGraph2.getViewport().setXAxisBoundsManual(true);
		mGraph2.getViewport().setMinX(0);
		mGraph2.getViewport().setMaxX(60);
	}

	@Override
	public void onStart() {
		//TODO: implement on start
		super.onStart();
	}

	@Override
	public void onResume() {
		//TODO: implement on resume
		super.onResume();

		/**
		 * Code based off of GraphView demo (RealtimeUpdates.java)
		 * https://github.com/jjoe64/GraphView-Demos/blob/master/app/src/main/java/com/jjoe64/
		 * graphview_demos/fragments/RealtimeUpdates.java
		 */
		mTimer = new Runnable() {
			@Override
			public void run() {
				randomData();
				++mLastTimeValue;

				if(mPlotted[0])
					mSeries[0].appendData(new DataPoint(mLastTimeValue, mGps.getAltitude()), true, 60);
				if(mPlotted[1])
					mSeries[1].appendData(new DataPoint(mLastTimeValue, mGps.getDistance()), true, 60);
				if(mPlotted[2])
					mSeries[2].appendData(new DataPoint(mLastTimeValue, mGps.getSpeed()), true, 60);

				if(mPlotted[3])
					mSeries[3].appendData(new DataPoint(mLastTimeValue, mCadence.getPedalRpm()), true, 60);
				if(mPlotted[4])
					mSeries[4].appendData(new DataPoint(mLastTimeValue, mCadence.getTireRpm()), true, 60);
				if(mPlotted[5])
					mSeries[5].appendData(new DataPoint(mLastTimeValue, mCadence.getDistance()), true, 60);
				if(mPlotted[6])
					mSeries[6].appendData(new DataPoint(mLastTimeValue, mCadence.getSpeed()), true, 60);

				if(mPlotted[7])
					mSeries[7].appendData(new DataPoint(mLastTimeValue, mHeart.getHeartRate()), true, 60);
				if(mPlotted[8])
					mSeries[8].appendData(new DataPoint(mLastTimeValue, mHeart.getCalories()), true, 60);

				mHandler.postDelayed(this, 1000);
			}
		};
		mHandler.postDelayed(mTimer, 1000);
	}

	@Override
	public void onPause() {
		//TODO: implement on pause
		mHandler.removeCallbacks(mTimer);
		super.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		//TODO: implement on save instance state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onStop() {
		//TODO: implement on stop
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		//TODO: implement on destroy view
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		//TODO: implement on destroy
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		//TODO implement on detach
		super.onDetach();
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private state functions

	private void restoreState() {
		SharedPreferences save = PreferenceManager.getDefaultSharedPreferences(getActivity());

		mPlot1[0] = save.getBoolean(SettingsActivity.PREF_GPS_ALTITUDE_1, false);
		mPlot1[1] = save.getBoolean(SettingsActivity.PREF_GPS_DISTANCE_1, false);
		mPlot1[2] = save.getBoolean(SettingsActivity.PREF_GPS_SPEED_1, false);

		mPlot1[3] = save.getBoolean(SettingsActivity.PREF_CADENCE_PEDAL_1, false);
		mPlot1[4] = save.getBoolean(SettingsActivity.PREF_CADENCE_WHEEL_1, false);
		mPlot1[5] = save.getBoolean(SettingsActivity.PREF_CADENCE_DISTANCE_1, false);
		mPlot1[6] = save.getBoolean(SettingsActivity.PREF_CADENCE_SPEED_1, false);

		mPlot1[7] = save.getBoolean(SettingsActivity.PREF_HEART_BPM_1, false);
		mPlot1[8] = save.getBoolean(SettingsActivity.PREF_HEART_CALORIES_1, false);

		mPlot2[0] = save.getBoolean(SettingsActivity.PREF_GPS_ALTITUDE_2, false);
		mPlot2[1] = save.getBoolean(SettingsActivity.PREF_GPS_DISTANCE_2, false);
		mPlot2[2] = save.getBoolean(SettingsActivity.PREF_GPS_SPEED_2, false);

		mPlot2[3] = save.getBoolean(SettingsActivity.PREF_CADENCE_PEDAL_2, false);
		mPlot2[4] = save.getBoolean(SettingsActivity.PREF_CADENCE_WHEEL_2, false);
		mPlot2[5] = save.getBoolean(SettingsActivity.PREF_CADENCE_DISTANCE_2, false);
		mPlot2[6] = save.getBoolean(SettingsActivity.PREF_CADENCE_SPEED_2, false);

		mPlot2[7] = save.getBoolean(SettingsActivity.PREF_HEART_BPM_2, false);
		mPlot2[8] = save.getBoolean(SettingsActivity.PREF_HEART_CALORIES_2, false);

		for (int i = 0; i < 9; i++) {
			mPlotted[i] = mPlot1[i] || mPlot2[i];
		}
		for (int j = 0; j < 9; j++) {
			mLastRandom[j] = mRandom.nextDouble();
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private testing functions

	private double getRandom(int i) {
		//Range of -.25 to .25 to add to last random value
		return mLastRandom[i] += (mRandom.nextDouble()) - 0.5;
	}

	public void randomData() {
		if(mDebug) {
			mGps = new GpsData(getRandom(0), 1.0, 1.0, getRandom(1), getRandom(2));
			mCadence = new CadenceData(getRandom(3), getRandom(4), getRandom(5), getRandom(6));
			mHeart = new HeartRateData(getRandom(7), getRandom(8));
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	private final Handler mHandler = new Handler();
	private Random mRandom = new Random();
	private double[] mLastRandom = new double[9];

	private double mLastTimeValue = 5d;
	private Runnable mTimer;

	private GpsData mGps;
	private CadenceData mCadence;
	private HeartRateData mHeart;

	private GraphView mGraph1;
	private GraphView mGraph2;

	/**
	 * Series are as follows:
	 * Gps Altitude
	 * Gps Distance
	 * Gps Speed
	 *
	 * Cadence Pedal rpm
	 * Cadence Wheel rpm
	 * Cadence Distance
	 * Cadence Speed
	 *
	 * Heart rate bpm
	 * Heart calories burned
	 */
	private LineGraphSeries<DataPoint>[] mSeries = new LineGraphSeries[9];

	/**
	 * Each element refers to the respective datapoint.
	 * True means display the data set and false means do not display the dataset
	 */
	private boolean[] mPlot1 = new boolean[9];
	private boolean[] mPlot2 = new boolean[9];
	private boolean[] mPlotted = new boolean[9];

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
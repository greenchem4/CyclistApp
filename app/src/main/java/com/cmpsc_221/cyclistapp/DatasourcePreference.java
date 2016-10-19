//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

/**
 * Code also obtained from android settings activity training guide
 * Created by green_000 on 5/3/2015.
 */
public class DatasourcePreference extends DialogPreference {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static function

	private static DataType[] mDataTypeLookup = {DataType.AGGREGATE_ACTIVITY_SUMMARY,
		DataType.TYPE_LOCATION_SAMPLE,
		DataType.TYPE_DISTANCE_DELTA,
		DataType.TYPE_SPEED,
		DataType.TYPE_CYCLING_PEDALING_CADENCE,
		DataType.TYPE_CYCLING_WHEEL_RPM,
		DataType.TYPE_DISTANCE_DELTA,
		DataType.TYPE_SPEED,
		DataType.TYPE_HEART_RATE_BPM,
		DataType.TYPE_CALORIES_EXPENDED
	};

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Public constructor

	public DatasourcePreference(Context context, AttributeSet attributes) {
		//TODO: implement constructor
		super(context, attributes);

		//setDialogLayoutResource(R.xml.preference_datasource);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);

		//mNumberPicker = (EditTextPreference) findPreferenceInHierarchy("key_pref_cadence_b");

		setDialogIcon(null);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected lifecycle functions

	@Override
	protected View onCreateDialogView() {
		//return mLayout;
		mLayout = new RelativeLayout(getContext());

		View view = LayoutInflater.from(getContext()).inflate(R.layout.pref_datasource, mLayout, true);

		mLayout.findViewById(R.id.sensor_type);
		return view;
	}

	@Override
	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);

	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);
		//When the user selects "OK", persist the new value
		if (positiveResult) {
			//mNewValue = 0;//mNumberPicker.getText();
			persistInt(mIdentifier);
			SharedPreferences.Editor editor = getContext().getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE).edit();
			editor.putString((getKey() + "_app_package_name"), mAppPackageName);
			editor.putString((getKey() + "_name"), mName);

			editor.putString((getKey() + "_device_manufacture"), mDeviceManufacture);
			editor.putString((getKey() + "_device_model"), mDeviceModel);
			editor.putString((getKey() + "_device_uid"), mDeviceUid);
			editor.putInt((getKey() + "_device_type"), mDeviceType);

			editor.putString((getKey() + "_stream_name"), mStreamName);
			editor.putInt((getKey() + "_sensor_type"), mType);
			editor.commit();
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected default value functions

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		if (restorePersistedValue) {
			//Restore existing state
			mIdentifier = getPersistedInt(0);
		}
		else {
			//Set default state from the XML attribute
			mIdentifier = (int) defaultValue;
			persistInt(mIdentifier);
		}

		if(0 < mIdentifier && mIdentifier < 10)
			mDatatype = mDataTypeLookup[mIdentifier];

		SharedPreferences save = getContext().getSharedPreferences(Constant.PREF_FILE, Context.MODE_PRIVATE);

		mAppPackageName = save.getString((getKey() + "_app_package_name"), "null");
		mName = save.getString((getKey() + "_name"), "null");

		mDeviceManufacture = save.getString((getKey() + "_device_manufacture"), "null");
		mDeviceModel = save.getString((getKey() + "_device_model"), "null");
		mDeviceUid = save.getString((getKey() + "_device_uid"), "null");
		mDeviceType = save.getInt((getKey() + "_device_type"), 0);

		mStreamName = save.getString((getKey() + "_stream_name"), "null");
		mType = save.getInt((getKey() + "_sensor_type"), 0);

		DataSource source;

		try {
			Device device = new Device(mDeviceManufacture, mDeviceModel, mDeviceUid, mDeviceType);
			source = new DataSource.Builder()
				.setAppPackageName(mAppPackageName)
				.setDataType(mDatatype)
				.setDevice(device)
				.setName(mName)
				.setStreamName(mStreamName)
				.setType(mType)
				.build();
		}
		catch (Exception e) {
			//do nothing no problem
		}
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return 5;
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected save state functions


	@Override
	protected Parcelable onSaveInstanceState() {
		final Parcelable superState = super.onSaveInstanceState();
		//Check whether this Preference is persistent (continually saved)
		if (isPersistent()) {
			//No need to save instance state since it's persistent, use superclass state
			return superState;
		}

		//Create instance of custom BaseSavedState
		final SavedState myState = new SavedState(superState);
		//Set the state's value with the class member that holds current setting value
		//myState.value = mNewValue;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		//Check whether we saved the state in onSaveInstanceState
		if (state == null || !state.getClass().equals(SavedState.class)) {
			//Didn't save the state, so call superclass
			super.onRestoreInstanceState(state);
			return;
		}

		//Cast state to custom BaseSavedState and pass to superclass
		SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());

		//Set this Preference's widget to reflect the restored state
		//mNumberPicker.setText(myState.value);
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static save state class

	private static class SavedState extends BaseSavedState {
		//#========# #========# #========# #========# #========# #========# #========# #----# #----#
		//Public constructors

		//Member that holds the setting's value
		//Change this data type to match the type saved by your Preference
		int value;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		public SavedState(Parcel source) {
			super(source);
			//Get the current preference's value
			value = source.readInt();  //Change this to read the appropriate data type
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			//Write the preference's value
			dest.writeInt(value); //Change this to write the appropriate data type
		}

		//Standard creator object using an instance of this class
		public static final Parcelable.Creator<SavedState> CREATOR =
			new Parcelable.Creator<SavedState>() {

				public SavedState createFromParcel(Parcel in) {
					return new SavedState(in);
				}

				public SavedState[] newArray(int size) {
					return new SavedState[size];
				}
			};
		//#========# #========# #========# #========# #========# #========# #========# #----# #----#
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private variables

	//private int mNewValue;

	private RelativeLayout mLayout;

	private int mIdentifier;

	private String mAppPackageName;
	private DataType mDatatype;
	private String mName;

	//Device strings
	private String mDeviceManufacture;
	private String mDeviceModel;
	private String mDeviceUid;
	private int mDeviceType;

	private String mStreamName;
	private int mType;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
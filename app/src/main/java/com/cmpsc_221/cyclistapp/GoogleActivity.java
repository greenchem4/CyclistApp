//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//File description

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//License and copyright

//#========# #========# #========# #========# #========# #========# #========# #========# #========#
package com.cmpsc_221.cyclistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
//#========# #========# #========# #========# #========# #========# #========# #========# #========#

/**
 * Created by green_000 on 5/2/2015.
 */
public class GoogleActivity extends Activity implements
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener {
	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static flag codes for google api connecting

	/**
	 /**
	 * AUTH_DEFAULT:
	 * The default state of the connection before the user has signed in.
	 * Do not attempt to resolve conflicts yet because user hasn't tried to sign in yet.
	 *
	 * AUTH_CONNECTION
	 * This state indicates that sign in has been attempted but not completed.
	 *
	 * AUTH_ERROR
	 * This state indicates that we are attempting to resolve an error
	 *
	 * AUTH_GOOD
	 * This state indicates that a valid connection has been established
	 */
	private static final int AUTH_DEFAULT = 0;
	private static final int AUTH_GOOD = 1;
	private static final int AUTH_CONNECTING = 2;
	private static final int AUTH_ERROR_IN_PROGRESS = 3;

	private static final int REQUEST_CODE_SIGN_IN = 0;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static storage identifier tags

	private static final String TAG_BASE = "google_connection";

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private static storage identifier tags

	private static final String STATE_CONNECTION = "connection_state";

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected lifecycle functions

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mConnectionState = AUTH_CONNECTING;
		mGoogleApiClient = null;

		if(savedInstanceState != null)
			mConnectionState = savedInstanceState.getInt(STATE_CONNECTION, AUTH_DEFAULT);

		buildFitnessClient();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
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
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onStop() {
		if(mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
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
	//Public google play setup functions

	//Code in this section was obtained from Google fit API documentation
	//specifically the getting started guide and then slightly tweaked
	//Also obtained from google quickstart example

	//https://developers.google.com/fit/android/get-started
	//http://developer.android.com/google/auth/api-client.html
	//https://developers.google.com/+/quickstart/android

	/**
	 *  Build a {@link GoogleApiClient} that will authenticate the user and allow the application
	 *  to connect to Fitness APIs. The scopes included should match the scopes your app needs
	 *  (see documentation for details). Authentication will occasionally fail intentionally,
	 *  and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
	 *  can address. Examples of this include the user never having signed in before, or having
	 *  multiple accounts on the device and needing to specify which account to use, etc.
	 */
	private void buildFitnessClient() {
		//Create the Google API Client
		mGoogleApiClient = new GoogleApiClient.Builder(this)
			.addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
			.addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE))
			.addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
			.addApi(Fitness.SESSIONS_API)
			.addApi(Fitness.RECORDING_API)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this)
			.build();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG_BASE, "Connected");
		mConnectionState = AUTH_GOOD;
	}

	@Override
	public void onConnectionSuspended(int cause) {
		//If your connection to the sensor gets lost at some point,
		//you'll be able to determine the reason and react to it here.

		if(mConnectionState == AUTH_GOOD)
			mConnectionState = AUTH_DEFAULT;

		if (cause == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
			Log.i(TAG_BASE, "Connection lost.  Cause: Network Lost.");
		}
		else if (cause == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
			Log.i(TAG_BASE, "Connection lost.  Reason: Service Disconnected");
		}

		//try to reconnect
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i(TAG_BASE, "Connection failed. Cause: " + result.getErrorCode());

		if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
			Toast.makeText(getApplicationContext(), "API unavailable", Toast.LENGTH_SHORT).show();
			//An API requested for GoogleApiClient is not available.
			//The device's current configuration might not be supported with the requested API
			//or a required component may not be installed, such as the Android Wear application.
			//You may need to use a second GoogleApiClient to manage the application's optional APIs.
			Log.w(TAG_BASE, "API Unavailable.");
		}
		else if (mConnectionState != AUTH_ERROR_IN_PROGRESS) {
			//We do not have an intent in progress so we should store the latest
			//error resolution intent for use when the sign in button is clicked.
			mConnectionIntent = result.getResolution();
			mConnectionError = result.getErrorCode();

			if (mConnectionState == AUTH_CONNECTING) {
				//AUTH_CONNECTING indicates the user already clicked the sign in button
				//so we should continue processing errors until the user is signed in or they click cancel.
				resolveSignInError();
			}
		}
	}

	/**
	 * Starts an appropriate intent or dialog for user interaction to resolve
	 * the current error preventing the user from being signed in.
	 * This could be a dialog allowing the user to select an account,
	 * an activity allowing the user to consent to the permissions being requested by your app,
	 * a setting to enable device networking, etc.
	 */
	private void resolveSignInError() {
		if (mConnectionIntent != null) {
			//We have an intent which will allow our user to sign in or resolve an error.
			//For example if the user needs to select an account to sign in with,
			//or if they need to consent to the permissions your app is requesting.

			try {
				//Send the pending intent that we stored on the most recent OnConnectionFailed callback.
				//This will allow the user to resolve the error currently preventing our connection to Google Play services.
				mConnectionState = AUTH_ERROR_IN_PROGRESS;
				startIntentSenderForResult(mConnectionIntent.getIntentSender(), REQUEST_CODE_SIGN_IN, null, 0, 0, 0);
			}
			catch (IntentSender.SendIntentException e) {
				Log.i(TAG_BASE, "Sign in intent could not be sent: " + e.getLocalizedMessage());
				//The intent was canceled before it was sent.
				//Attempt to connect to get an updated ConnectionResult.
				mConnectionState = AUTH_CONNECTING;
				mGoogleApiClient.connect();
			}
		}
		else {
			//Google Play services wasn't able to provide an intent for some error types,
			//so we show the default Google Play services error dialog
			//which may still start an intent on our behalf if the user can resolve the issue.
			createErrorDialog().show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_SIGN_IN) {
			if (resultCode == RESULT_OK) {
				//If the error resolution was successful we should continue processing errors.
				mConnectionState = AUTH_CONNECTING;
			}
			else {
				//If the error resolution was not successful or the user canceled,
				//we should stop processing errors.
				mConnectionState = AUTH_DEFAULT;
			}

			if (!mGoogleApiClient.isConnecting()) {
				//If Google Play services resolved the issue with a dialog then
				//onStart is not called so we need to re-attempt connection here.
				mGoogleApiClient.connect();
			}
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private Google API error handling function

	private Dialog createErrorDialog() {
		Toast.makeText(getApplicationContext(), "Create dialog", Toast.LENGTH_SHORT).show();
		if (GooglePlayServicesUtil.isUserRecoverableError(mConnectionError)) {
			return GooglePlayServicesUtil.getErrorDialog(mConnectionError, this, REQUEST_CODE_SIGN_IN,
				new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						Log.e(TAG_BASE, "Google Play services resolution cancelled");
						mConnectionState = AUTH_DEFAULT;
					}
				});
		}
		else {
			return new AlertDialog.Builder(this)
				.setMessage(R.string.play_services_error)
				.setPositiveButton(R.string.close,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Log.e(TAG_BASE, "Google Play services error could not be resolved: " + mConnectionError);
							mConnectionState = AUTH_DEFAULT;
						}
					}).create();
		}
	}

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Protected Google API client

	/**
	 * The interface to the google fit API.
	 */
	protected GoogleApiClient mGoogleApiClient;


	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
	//Private Google API variables

	/**
	 * Using mConectionState to track the state of the connection (and user).
	 */
	private int mConnectionState;

	/**
	 * Used to store the PendingIntent most recently returned by Google Play services.
	 */
	private PendingIntent mConnectionIntent;

	/**
	 * Used to store the error code most recently returned by Google Play services.
	 */
	private int mConnectionError;

	//#========# #========# #========# #========# #========# #========# #========# #========# #----#
}
//#========# #========# #========# #========# #========# #========# #========# #========# #========#
//*/ //End
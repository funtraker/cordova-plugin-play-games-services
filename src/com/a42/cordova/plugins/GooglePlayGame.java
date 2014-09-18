/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.a42.cordova.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class GooglePlayGame extends CordovaPlugin {
	
	private static final String LOGTAG = "GooglePlayGame";
	
	private static final String ACTION_AUTH = "auth";
    private static final String ACTION_SUBMIT_SCORE = "submitScore";
    private static final String ACTION_SHOW_LEADERBOARD = "showLeaderboard";
    
    private boolean isGpsAvailable = false;
    
    private GoogleGameService googleGameService = new GoogleGameService();
    
    @Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    	super.initialize(cordova, webView);
    	isGpsAvailable = (GooglePlayServicesUtil.isGooglePlayServicesAvailable(cordova.getActivity()) == ConnectionResult.SUCCESS);
    	Log.w(LOGTAG, String.format("isGooglePlayServicesAvailable: %s",  isGpsAvailable?"true":"false"));
	}
    
	@Override
	public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {
		
		PluginResult result = null;
		
		if (ACTION_AUTH.equals(action)) {
			JSONObject options = inputs.optJSONObject(0);
			result = executeAuth(options, callbackContext);
		} else if (ACTION_SUBMIT_SCORE.equals(action)) {
			JSONObject options = inputs.optJSONObject(0);
			result = executeSubmitScore(options, callbackContext);
		} else if (ACTION_SHOW_LEADERBOARD.equals(action)) {
			JSONObject options = inputs.optJSONObject(0);
			result = executeShowLeaderboard(options, callbackContext);
		}
		
		if(result != null) callbackContext.sendPluginResult( result );
		
        return true;
	}
	
	private PluginResult executeAuth(JSONObject options, final CallbackContext callbackContext) {
		Log.w(LOGTAG, "executeAuth");
		
		cordova.getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {
            	googleGameService.doSignIn();
            	callbackContext.success();
            }
		});
    	return null;
	}
	
	private PluginResult executeSubmitScore(JSONObject options, CallbackContext callbackContext) {
		Log.w(LOGTAG, "executeSubmitScore");
    	callbackContext.success();
    	return null;
	}
	
	private PluginResult executeShowLeaderboard(JSONObject options, CallbackContext callbackContext) {
		Log.w(LOGTAG, "executeShowLeaderboard");
    	callbackContext.success();
    	return null;
	}
}
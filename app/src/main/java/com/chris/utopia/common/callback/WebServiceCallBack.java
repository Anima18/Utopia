package com.chris.utopia.common.callback;

import org.json.JSONException;

import java.text.ParseException;


public interface WebServiceCallBack {
	public void callBack(int statusCode, String result) throws JSONException, IllegalAccessException, IllegalArgumentException,ParseException; 
}

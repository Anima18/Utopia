package com.chris.utopia.common.util;

import android.os.Handler;
import android.os.Message;

import com.chris.utopia.common.callback.WebServiceCallBack;
import com.chris.utopia.common.constant.Constant;

import org.apache.http.client.ResponseHandler;
import org.json.JSONException;

import java.text.ParseException;
import java.util.HashMap;
/**
 * Created by Chris on 2015/9/2.
 */
public class WebServiceUtils {
	public static void callWebAPI(final String serverUrl, final String requestUrl, final int requestCount, final String method, final HashMap<String, String> params, final WebServiceCallBack webServiceCallBack) {
		
		final Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				try {
					webServiceCallBack.callBack(msg.getData().getInt("STATUS"), msg.getData().getString("RESPONSE"));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		};

		final ResponseHandler<String> responseHandler = HTTPRequestHelper.getResponseHandlerInstance(mHandler);

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
            	HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
				String url = serverUrl + requestUrl;
            	if (Constant.METHOD_GET.equals(method)) {
                    helper.performGet(url, null, null, null);
                } else if (Constant.METHOD_POST.equals(method)) {
                    helper.performPost(HTTPRequestHelper.MIME_FORM_ENCODED, url, null, null, null, params);
                }
            }
        }.start();
    }
	
}

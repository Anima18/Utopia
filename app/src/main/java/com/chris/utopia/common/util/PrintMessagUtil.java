package com.chris.utopia.common.util;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PrintMessagUtil {

	public static void showToast(Context context,String value) {
		Toast.makeText(context, value ,Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context,String value,int time,int fontSize) {
		Toast mToast = Toast.makeText(context, value , time);
        LinearLayout toastLayout = (LinearLayout) mToast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(fontSize);
	    mToast.show();
	}
}

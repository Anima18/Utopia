package com.chris.utopia.common.util;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.chris.utopia.R;
import com.chris.utopia.common.callback.ButtonOnclickCallBack;
/**
 * Created by Chris on 2015/9/2.
 */
public class AlertDialogUtil {
	public static void showConfirmDialog(final Context context, String title, String message, 
			final ButtonOnclickCallBack positiveCallBack, final ButtonOnclickCallBack neutralCallBack, 
			final ButtonOnclickCallBack cancelCallBack) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.mipmap.ic_launcher);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(context.getResources().getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				positiveCallBack.callBack();
			}
		});
		builder.setNegativeButton(context.getResources().getString(R.string.dialog_button_negative), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				neutralCallBack.callBack();
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener(){
		    @Override
		    public void onCancel(DialogInterface dialog){
		    	cancelCallBack.callBack();
		    }
		});
		builder.setCancelable(false);
		builder.show();
	}
	
	public static void showMessageDialog(Context context, String title, String message, 
			final ButtonOnclickCallBack positiveCallBack, final ButtonOnclickCallBack cancelCallBack) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.mipmap.ic_launcher);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(context.getResources().getString(R.string.dialog_button_positive), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				positiveCallBack.callBack();
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				cancelCallBack.callBack();
		    }
		});
		builder.setCancelable(false);
		builder.show();
	}


}

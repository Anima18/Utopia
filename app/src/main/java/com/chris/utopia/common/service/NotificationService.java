package com.chris.utopia.common.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.chris.utopia.R;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.system.activity.MainActivity2;

import java.util.Random;


public class NotificationService extends Service {
	private static final String TAG = "NotificationService";

    private NotificationManager mNM;

	//private NotificationCompat.Builder mBuilder;

	//private static final int Notification_ID_BASE = 1;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //startForegroundCompat();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		Thing thing = (Thing)intent.getSerializableExtra("THING");
		Random random = new Random();
		int notificationId = random.nextInt(9999 - 1000) + 1000;

		Intent startIntent = new Intent(this, MainActivity2.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pd = PendingIntent.getActivity(this, 0, startIntent, 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle(thing.getTitle())
				.setContentText(thing.getDescription())
				.setSmallIcon(R.mipmap.ic_launcher)
				.setDefaults(Notification.DEFAULT_ALL)
				.setContentIntent(pd)
				.setAutoCancel(true);
		mNM.notify(notificationId, mBuilder.build());
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//stopForegroundCompat(Notification_ID_BASE);
	}

	private void stopForegroundCompat(){
		mNM.cancelAll();
		stopForeground(false);
	}

}

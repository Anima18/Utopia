package com.chris.utopia.common.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.service.NotificationService;
import com.chris.utopia.entity.Thing;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chris on 2016/2/23.
 */
public class AlarmManagerUtil {

    /*public static void addAlarm(Context context, Thing thing) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("THING", thing);
        Intent intent = new Intent(context, NotificationService.class);
        intent.putExtras(bundle);
        PendingIntent sender = PendingIntent.getService(context, thing.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        String timeStr = (StringUtil.isEmpty(thing.getBeginDate()) ? DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_4) : thing.getBeginDate()) + " " + thing.getBeginTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.toDate(timeStr, Constant.DATETIME_FORMAT_6));

        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    public static void removeAlarm(Context context, Thing thing) {
        Intent intent = new Intent(context, NotificationService.class);
        PendingIntent sender = PendingIntent.getService(context, thing.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        am.cancel(sender);
    }*/

}

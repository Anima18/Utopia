package com.chris.utopia.common.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.chris.utopia.base.MyApplication;

/**
 * Created by Chris on 2015/9/9.
 */
public class CommonUtil {
    public static Context getApplicationContext() {
        return MyApplication.getInstance();
    }

    public static boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * The width of the side navigation is equal to the width of the screen minus the height of the actionbar,
     * 56 dp or away from the right side of the screen edge.Side navigation maximum width
     * is 5 times the standard increment (56 dp on the mobile phone, tablet is 64 dp).
     * @return
     */
    public static int getNavigationViewWidth() {
        return 5*64;
    }

    /**
     * 获取屏幕宽度dp
     * 根据宽度dp，可以判断当前设备属于哪个Value范畴
     * @param context
     * @return
     */
    public static int getSceenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (width / scale + 0.5f);
    }
}

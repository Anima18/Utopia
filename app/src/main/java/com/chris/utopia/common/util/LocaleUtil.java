package com.chris.utopia.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.chris.utopia.common.constant.Constant;

import java.util.Locale;
/**
 * Created by Chris on 2015/9/2.
 */
public class LocaleUtil {

	public static void changeLocal(Context context, String defaultLocale) {
		Resources res = context.getResources();
	    // Change locale settings in the app.
	    DisplayMetrics dm = res.getDisplayMetrics();
	    android.content.res.Configuration conf = res.getConfiguration();

	    if(Constant.LOCALE_ZH_TW.equals(defaultLocale)) {
	    	conf.locale = Locale.TAIWAN;
		}else if(Constant.LOCALE_ZH_CN.equals(defaultLocale)) {
			conf.locale = Locale.SIMPLIFIED_CHINESE;
		}else if(Constant.LOCALE_EN.equals(defaultLocale)) {
			conf.locale = Locale.ENGLISH;
		}
	    
	    res.updateConfiguration(conf, dm);
	}
}

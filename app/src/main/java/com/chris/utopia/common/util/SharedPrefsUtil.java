package com.chris.utopia.common.util;

import android.content.Context;
/**
 * Created by Chris on 2015/9/2.
 */
public class SharedPrefsUtil {
  
	public final static String NAME = "config";

    public static void putLongValue(Context context, String key, long value) {  
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putLong(key, value).commit();  
    }  
  
    public static void putIntValue(Context context, String key, int value) {  
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putInt(key, value).commit();  
    }  
  
   
    public static void putStringValue(Context context, String key, String value) {  
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();  
    }  

    public static void putBooleanValue(Context context, String key,  
            boolean value) {  
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();  
    }  
  
    public static long getLongValue(Context context, String key, long defValue) {  
        return context.getSharedPreferences(NAME,Context.MODE_PRIVATE).getLong(key, defValue);  
    }  
  
    public static int getIntValue(Context context, String key, int defValue) {  
        return context.getSharedPreferences(NAME,Context.MODE_PRIVATE).getInt(key, defValue);  
    }  
  
    public static boolean getBooleanValue(Context context, String key,  
            boolean defValue) {  
        return context.getSharedPreferences(NAME,Context.MODE_PRIVATE).getBoolean(key, defValue);  
    }  
  
    public static String getStringValue(Context context, String key,  
            String defValue) {  
        return context.getSharedPreferences(NAME,Context.MODE_PRIVATE).getString(key, defValue);  
    }  
   
    public static void clear(Context context) {  
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().clear().commit();  
    }  
  
    public static void remove(Context context, String key) {  
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().remove(key).commit();  
    }  
}

package com.chris.utopia.common.constant;

import android.os.Environment;

/**
 * Created by Chris on 2015/9/9.
 */
public interface Constant {

    /**
     * DATETIME_FORMAT
     */
    String DATETIME_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss";
    String DATETIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    String DATETIME_FORMAT_3 = "yyyy-MM-dd";
    String DATETIME_FORMAT_4 = "yyyy/MM/dd";
    String DATETIME_FORMAT_5 = "HH:mm:ss";
    String DATETIME_FORMAT_6 = "yyyy/MM/dd HH:mm:ss";

    /**
     * WebService method type
     */
    String METHOD_GET = "GET";
    String METHOD_POST = "POST";

    //navigationView item id
    int NAV_HOME_ID = 10000;
    int NAV_SETTING_ID = 10001;
    int NAV_BASE_ID = 10002;

    //locale type
    String LOCALE_ZH_TW = "zh_TW";
    String LOCALE_ZH_CN = "zh_CN";
    String LOCALE_EN = "en";

    //SharedPrefs key
    String SP_KEY_LOGIN_USER_ID = "SP_KEY_LOGIN_USER_ID";
    String SP_KEY_LOGIN_USER_NAME = "SP_KEY_LOGIN_USER_NAME";
    String SP_KEY_LOGIN_USER_EMAIL = "SP_KEY_LOGIN_USER_EMAIL";

    //Thing status
    String THING_STATUS_NEW = "NEW";
    String THING_STATUS_IGNORE = "IGNORE";
    String THING_STATUS_DONE = "DONE";

    //Habit status
    String HABIT_STATUS_ACTION = "ACTION";
    String HABIT_STATUS_PAUSE = "PAUSE";

    //plan status
    String Plan_STATUS_NEW = "NEW";

    //add plan thing result code
    int ADD_THING_RESULT_CODE = 0;

    //Thing type
    String THING_TYPE_THING = "THING";
    String THING_TYPE_HABIT = "HABIT";

    String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
}

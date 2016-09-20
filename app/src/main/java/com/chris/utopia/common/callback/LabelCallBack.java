package com.chris.utopia.common.callback;

import com.chris.utopia.entity.ThingClasses;

/**
 * Created by Chris on 2016/2/3.
 */
public interface LabelCallBack {
    void callBack(ThingClasses classes);
    void fail(String message);
}

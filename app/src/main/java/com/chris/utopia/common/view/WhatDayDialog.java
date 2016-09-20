package com.chris.utopia.common.view;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.callback.QuadrantCallBack;

/**
 * Created by Chris on 2016/2/8.
 */
public class WhatDayDialog {
    private static WhatDayDialog single = null;
    private MaterialDialog quadrantDialog;
    private Context mContext;
    private int index = -1;

    private WhatDayDialog() {
    }

    public static WhatDayDialog initInstance() {
        if (single == null) {
            single = new WhatDayDialog();
        }
        return single;
    }

    public void setmContext(Context context) {
        this.mContext = context;
        index = -1;
    }

    public void showWhatDayDialog(final String value, final QuadrantCallBack callBack) {
        String[] whatDayArray = mContext.getResources().getStringArray(R.array.thing_what_day);
        int index = -1;
        for(int i = 0; i < whatDayArray.length; i++) {
            if(whatDayArray[i].equals(value)) {
                index = i;
                break;
            }
        }
        quadrantDialog = new MaterialDialog.Builder(mContext)
                .title("What Day")
                .items(whatDayArray)
                .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if(text != null) {
                            callBack.callBack(text.toString());
                        }
                        return true;
                    }
                })
                .positiveText(R.string.dialog_button_positive)
                .negativeText(R.string.dialog_button_negative)
                .show();

    }
}

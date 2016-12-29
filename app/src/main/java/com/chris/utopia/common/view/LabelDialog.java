package com.chris.utopia.common.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.callback.LabelCallBack;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/2/3.
 */
public class LabelDialog {
    private static LabelDialog single = null;
    private SystemInteractor interactor;
    private MaterialDialog labelDialog;
    private  Context mContext;
    private int index = -1;

    private LabelDialog() {
        Injector inj=  Guice.createInjector(new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(SystemInteractor.class).to(SystemInteractorImpl.class);
            }
        });
        interactor = inj.getInstance(SystemInteractorImpl.class);
    }

    public static LabelDialog initInstance() {
        if (single == null) {
            single = new LabelDialog();
        }
        return single;
    }

    public void setmContext(Context context) {
        this.mContext = context;
        index = -1;
    }

    public void showLableDialog(final String labelId, final LabelCallBack callBack) {
        try {
            //get thingClass data
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            ThingClasses classes = new ThingClasses();
            classes.setUserId(userId);
            final List<ThingClasses> classesList =  interactor.findThingClassess(classes);

            //get default thingClass index
            String[] labelArray = new String[classesList.size()];
            for(int i = 0; i < classesList.size(); i++) {
                ThingClasses thingClasses = classesList.get(i);
                labelArray[i] = thingClasses.getName();
                if(labelId != null && labelId.equals(thingClasses.getId())) {
                    index = i;
                }
            }

            labelDialog = new MaterialDialog.Builder(mContext)
                    .title("Label")
                    .items(labelArray)
                    .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            index = which;
                            if(which != -1) {
                                callBack.callBack(classesList.get(which));
                            }
                            labelDialog.dismiss();
                            labelDialog.cancel();
                            labelDialog = null;
                            return true;
                        }
                    })
                    .positiveText(R.string.dialog_button_positive)
                    .negativeText(R.string.dialog_button_negative)
                    .neutralText("新建类别")
                    .show();

            labelDialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCreateLabelDialog(labelId, callBack);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            callBack.fail("获取数据失败");
        }
    }

    public void showCreateLabelDialog(final String labelId, final LabelCallBack callBack) {
        final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .title("创建类别")
                .customView(R.layout.dialog_create_label, true)
                .positiveText(R.string.dialog_button_positive)
                .negativeText(R.string.dialog_button_negative)
                .show();
        View view = dialog.getCustomView();
        final TextInputLayout titleTi = (TextInputLayout) view.findViewById(R.id.clDialog_title_textInput);
        final TextInputLayout descTi = (TextInputLayout) view.findViewById(R.id.clDialog_desc_textInput);
        final EditText titleEt = (EditText) view.findViewById(R.id.lcDialog_title_et);
        final EditText descEt = (EditText) view.findViewById(R.id.clDialog_desc_et);

        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String desc = descEt.getText().toString();
                boolean flag = true;
                if (StringUtil.isEmpty(title)) {
                    titleTi.setErrorEnabled(true);
                    titleTi.setError("类别名称不能为空");
                    flag = false;
                }
                if (StringUtil.isEmpty(desc)) {
                    descTi.setErrorEnabled(true);
                    descTi.setError("类别名称不能为空");
                    flag = false;
                }
                if (flag) {
                    String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
                    ThingClasses classes = new ThingClasses(title, desc, userId);
                    labelDialog.dismiss();
                    dialog.dismiss();

                    String userName = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_NAME, "");
                    classes.setCreateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_2));
                    classes.setCreateBy(userName);
                    classes.setUpdateAt(DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_2));
                    classes.setUpdateBy(userName);
                    classes.setId(StringUtil.getUUID());

                    try {
                        interactor.addThingClassess(classes);
                        showLableDialog(labelId, callBack);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

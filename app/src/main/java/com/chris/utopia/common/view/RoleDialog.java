package com.chris.utopia.common.view;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chris.utopia.R;
import com.chris.utopia.common.callback.RoleCallBack;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Role;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/2/3.
 */
public class RoleDialog {
    private static RoleDialog single = null;
    private SystemInteractor interactor;
    private MaterialDialog roleDialog;
    private  Context mContext;
    private int index = -1;

    private RoleDialog() {
        Injector inj=  Guice.createInjector(new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(SystemInteractor.class).to(SystemInteractorImpl.class);
            }
        });
        interactor = inj.getInstance(SystemInteractorImpl.class);
    }

    public static RoleDialog initInstance() {
        if (single == null) {
            single = new RoleDialog();
        }
        return single;
    }

    public void setmContext(Context context) {
        this.mContext = context;
        index = -1;
    }

    public void showRoleDialog(final String roleId, final RoleCallBack callBack) {
        try {
            //get thingClass data
            String userId = SharedPrefsUtil.getStringValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, "");
            final Role role = new Role();
            role.setUserId(userId);
            final List<Role> roles = interactor.findRole(role);


            //get default thingClass index
            String[] labelArray = new String[roles.size()];
            for(int i = 0; i < roles.size(); i++) {
                Role role1 = roles.get(i);
                labelArray[i] = role1.getName();
                if(roleId != null && roleId.equals(role1.getId())) {
                    index = i;
                }
            }

            roleDialog = new MaterialDialog.Builder(mContext)
                    .title("Role")
                    .items(labelArray)
                    .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            index = which;
                            if(which != -1) {
                                callBack.callBack(roles.get(which));
                            }
                            roleDialog.dismiss();
                            roleDialog.cancel();
                            roleDialog = null;
                            return true;
                        }
                    })
                    .positiveText(R.string.dialog_button_positive)
                    .negativeText(R.string.dialog_button_negative)
                    .show();
        } catch (SQLException e) {
            e.printStackTrace();
            callBack.fail("获取数据失败");
        }
    }
}

package com.chris.utopia.common.callback;

import com.chris.utopia.entity.Role;

/**
 * Created by Chris on 2016/2/3.
 */
public interface RoleCallBack {
    void callBack(Role role);
    void fail(String message);
}

package com.chris.utopia.module.system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;

/**
 * Created by Chris on 2016/1/17.
 */
public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transmitNextPage();
    }

    private void transmitNextPage() {
        startActivity(getNextPageIntent());
        overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
        IndexActivity.this.finish();
    }

    private Intent getNextPageIntent() {
        int userId = SharedPrefsUtil.getIntValue(IndexActivity.this, Constant.SP_KEY_LOGIN_USER_ID, 0);
        if(userId == 0) {
            return new Intent(IndexActivity.this, LoginActivity.class);
        }else {
            return new Intent(IndexActivity.this, MainActivity.class);
        }
    }

}

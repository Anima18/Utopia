package com.chris.utopia.module.system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.util.StringUtil;
import com.chris.utopia.module.guide.GuideActivity;

/**
 * Created by Chris on 2016/1/17.
 */
public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("IndexActivity", "IndexActivity");
        setContentView(R.layout.activity_index);
        getNextPageIntent();
}

    private void getNextPageIntent() {
        String userId = SharedPrefsUtil.getStringValue(IndexActivity.this, Constant.SP_KEY_LOGIN_USER_ID, "");
        if(StringUtil.isEmpty(userId)) {
            startActivity(new Intent(IndexActivity.this, GuideActivity.class));
            overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
            IndexActivity.this.finish();
        }else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    startActivity(new Intent(IndexActivity.this, MainActivity2.class));
                    overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                    IndexActivity.this.finish();
                }
            }, 1000);
        }

    }

}

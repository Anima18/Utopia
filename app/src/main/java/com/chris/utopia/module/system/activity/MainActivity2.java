package com.chris.utopia.module.system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.chris.utopia.R;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.module.home.activity.HomeFragment;
import com.chris.utopia.module.home.activity.MessageActivity;
import com.chris.utopia.module.home.activity.ProfileActivity;
import com.chris.utopia.module.home.activity.SearchActivity;
import com.chris.utopia.module.idea.activity.IdeaFragment;
import com.chris.utopia.module.plan.activity.PlanFragment;
import com.chris.utopia.module.role.activity.RoleFragment;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by jianjianhong on 2016/12/2.
 */
@ContentView(R.layout.activity_main2)
public class MainActivity2 extends BaseActivity {
    @InjectView(R.id.bottom_nav)
    private BottomNavigationView navigationView;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(fragment1 == null) {
            fragment1 = new HomeFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1).commitAllowingStateLoss();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        if(fragment1 == null) {
                            fragment1 = new HomeFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment1).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_nav_role:
                        if(fragment2 == null) {
                            fragment2 = new RoleFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment2).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_nav_plan:
                        if(fragment3 == null) {
                            fragment3 = new PlanFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment3).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_nav_idea:
                        if(fragment4 == null) {
                            fragment4 = new IdeaFragment();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment4).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_nav_me:
                        if(fragment5 == null) {
                            fragment5 = new ProfileActivity();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment5).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setToolBarTitle() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.mainAct_action_logout:
                SharedPrefsUtil.clear(getContext());
                Intent intent0 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent0);
                overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                return true;
            case R.id.mainAct_action_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                return true;
            case R.id.mainAct_action_message:
                Intent intent2 = new Intent(getContext(), MessageActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

package com.chris.utopia.module.system.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chris.utopia.R;
import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.common.view.BaseActivity;
import com.chris.utopia.module.home.activity.HomeFragment;
import com.chris.utopia.module.home.activity.MessageActivity;
import com.chris.utopia.module.home.activity.ProfileActivity;
import com.chris.utopia.module.home.activity.SearchActivity;
import com.chris.utopia.module.idea.activity.IdeaFragment;
import com.chris.utopia.module.plan.activity.PlanFragment;
import com.chris.utopia.module.role.activity.RoleFragment;
import com.chris.utopia.module.system.presenter.LoginPresenter;
import com.google.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by Admin on 2015/10/1.
 */
@ContentView(R.layout.activtiy_main)
public class MainActivity extends BaseActivity implements View.OnClickListener, LoginActionView {
    @InjectView(R.id.mainAct_drawer_layout)
    private DrawerLayout mDrawerLayout;
    @InjectView(R.id.mainAct_navigationView)
    private NavigationView mNavigationView;
    private CircleImageView userIm;
    private TextView userNameTv;
    private TextView emailTv;

    @Inject
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
        initNavigationView();
    }

    public void initNavigationView() {

        mNavigationView.post(new Runnable() {
            @Override
            public void run() {
                userNameTv = (TextView)mNavigationView.findViewById(R.id.navHeader_userName_tv);
                emailTv = (TextView)mNavigationView.findViewById(R.id.navHeader_email_tv);
                userIm = (CircleImageView)mNavigationView.findViewById(R.id.navHeader_user_im);
                userNameTv.setText(SharedPrefsUtil.getStringValue(getContext(), Constant.SP_KEY_LOGIN_USER_NAME, ""));
                emailTv.setText(SharedPrefsUtil.getStringValue(getContext(), Constant.SP_KEY_LOGIN_USER_EMAIL, ""));
                userIm.setOnClickListener(MainActivity.this);
            }
        });
        setupDrawerContent(mNavigationView);
    }

    private void setupDrawerContent(NavigationView navigationView)  {
        selectItem(navigationView.getMenu().getItem(0));
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectItem(MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = getFragment(menuItem);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainAct_content_frame, fragment).commitAllowingStateLoss();

        // update selected item and title, then close the drawer
        menuItem.setChecked(true);
        toolbar.setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    private Fragment getFragment(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                return new HomeFragment();
            case R.id.nav_role:
                return new RoleFragment();
            case R.id.nav_idea:
                return new IdeaFragment();
            case R.id.nav_plan:
                return new PlanFragment();
            default:
                return new OtherFragment();
        }
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void setToolBarTitle() {
        toolbar.setTitle("Utopia");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navHeader_user_im :
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void toMainPage() {

    }

    @Override
    public void showLoginMessage(String message) {

    }
}

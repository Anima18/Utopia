package com.chris.utopia.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragmentList;
	private List<String> fragmentNameList;
	
	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> fragmentNameList) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragmentList = fragmentList;
		this.fragmentNameList = fragmentNameList;
	}
	
	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		return fragmentList.get(index);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentNameList.get(position);
	}

}

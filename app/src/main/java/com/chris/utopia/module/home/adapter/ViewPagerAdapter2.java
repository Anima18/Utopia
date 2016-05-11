package com.chris.utopia.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter2 extends FragmentPagerAdapter {

	private Fragment fragment;
	private List<String> fragmentNameList;

	public ViewPagerAdapter2(FragmentManager fm, Fragment fragment, List<String> fragmentNameList) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragment = fragment;
		this.fragmentNameList = fragmentNameList;
	}
	
	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentNameList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentNameList.get(position);
	}


}

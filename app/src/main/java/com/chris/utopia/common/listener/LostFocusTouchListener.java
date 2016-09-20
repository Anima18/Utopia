package com.chris.utopia.common.listener;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;

public class LostFocusTouchListener implements OnTouchListener {

	private Context context;
	private View view;
	
	public LostFocusTouchListener(Context context, View view) {
		this.context = context;
		this.view = view;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		return false;
	}		
	
}

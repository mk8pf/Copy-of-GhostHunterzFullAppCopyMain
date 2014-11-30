package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class GameLinearLayout extends LinearLayout {

	public GameLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GameLinearLayout(Context context, int orientation,
			int paramsWidth, int paramsHeight) {
		super(context);
		setOrientation(orientation);
		setLayoutParams(new LinearLayout.LayoutParams(paramsWidth, paramsHeight));
	}
	
	public void addViews(View... views) {
		for (View v : views) {
			addView(v);
		}
	}

}

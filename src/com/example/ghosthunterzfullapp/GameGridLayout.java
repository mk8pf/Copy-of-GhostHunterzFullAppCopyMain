package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;

public class GameGridLayout extends GridLayout {

	public GameGridLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public GameGridLayout(Context context, int orientation, int rowCount,
			int columnCount, int paramWidth, int paramHeight) {
		super(context);
		setOrientation(orientation);
		setRowCount(rowCount);
		setColumnCount(columnCount);
		ViewGroup.LayoutParams gridLayoutParams = new ViewGroup.LayoutParams(paramWidth, paramHeight);
		this.setLayoutParams(gridLayoutParams);
	}
	
	public void addViews(View... views) {
		for (View v : views) {
			addView(v);
		}
	}

}

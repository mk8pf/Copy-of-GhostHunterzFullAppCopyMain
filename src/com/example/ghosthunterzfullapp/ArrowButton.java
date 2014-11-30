package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ArrowButton extends ImageButton {
	private static final int MBUTTON_WIDTH = 90;
	private static final int MBUTTON_HEIGHT = 90;
	ImageView.ScaleType mScaleType = ImageButton.ScaleType.FIT_XY;
	ViewGroup.LayoutParams mLayoutParams = new LayoutParams(convertDpPixels(MBUTTON_WIDTH), convertDpPixels(MBUTTON_HEIGHT));

	public ArrowButton(Context context) {
		super(context);
	}
	
	public ArrowButton(Context context, ImageView.ScaleType scaleType, int imageResource,
			ViewGroup.LayoutParams layoutParams) {
		super(context);
		setScaleType(scaleType);
		setImageResource(imageResource);
		setLayoutParams(layoutParams);
	}
	
	public ArrowButton(Context context, int imageResource,
			ViewGroup.LayoutParams layoutParams) {
		super(context);
		setScaleType(mScaleType);
		setImageResource(imageResource);
		setLayoutParams(layoutParams);
	}
	
	public ArrowButton(Context context, int imageResource) {
		super(context);
		setScaleType(mScaleType);
		setImageResource(imageResource);
		setLayoutParams(mLayoutParams);
	}
	
	private int convertDpPixels(int dp) {
		final float SCALE = this.getResources().getDisplayMetrics().density;
		return (int)(dp * SCALE + 0.5f); //official android formula
	}

}

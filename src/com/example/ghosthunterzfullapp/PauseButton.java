package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;

public class PauseButton extends Button {
	private String mText;
	private float mTextSize;
	private int mTextColor;

	public PauseButton(Context context) {
		super(context);
	}
	
	public PauseButton(Context context, String text, float textSize, int textColor) {
		super(context);
		mText = text;
		mTextSize = textSize;
		mTextColor = textColor;
		setText(mText);
		setTextSize(mTextSize);
		setTextColor(mTextColor);
		setPadding(10, 0, 10, 0);
		setBackgroundColor(Color.BLACK);
		Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Capture_it.ttf");
		setTypeface(myTypeface);
	}

}

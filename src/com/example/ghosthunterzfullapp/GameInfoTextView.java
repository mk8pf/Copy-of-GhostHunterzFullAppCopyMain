package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class GameInfoTextView extends TextView {
	private String mText;
	private float mTextSize;
	private int mTextColor;

	public GameInfoTextView(Context context) {
		super(context);
	}
	
	// sets up the text, text size, text color, also configures text font/ padding
	
	public GameInfoTextView(Context context, String text, float textSize, int textColor) {
		super(context);
		mText = text;
		mTextSize = textSize;
		mTextColor = textColor;
		setText(mText);
		setTextSize(mTextSize);
		setTextColor(mTextColor);
		setPadding(10, 0, 50, 0);
		Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Capture_it.ttf");
		setTypeface(myTypeface);
	}
	
	

}

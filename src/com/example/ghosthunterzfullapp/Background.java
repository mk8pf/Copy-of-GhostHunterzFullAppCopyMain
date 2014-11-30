package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends DrawableObject {
	Bitmap rescaledBackground;

	public Background(Context context, int resourceID) {
		super(context, resourceID);
		rescaledBackground = Bitmap.createScaledBitmap(getBitmap(), 800, 950, true);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(rescaledBackground, 0, 0, null);
	}

}

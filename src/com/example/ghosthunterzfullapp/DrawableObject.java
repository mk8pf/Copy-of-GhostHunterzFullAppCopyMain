package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class DrawableObject {
	protected Bitmap mBitmap;
	protected Rect mRect;
	protected int x, y; //(refers to the top right corner of the image)
	private int xSpeed, ySpeed;
	protected GameScreenView mGhostHunterScreen;
	protected boolean isFrozen = false;
	
	
	public DrawableObject(Context context, int resourceID) {
		this(context, resourceID, 0, 0);
	}
	
	public DrawableObject(DrawableObject drawableObject) {
		mBitmap = drawableObject.mBitmap;
		x = drawableObject.x;
		y = drawableObject.y;
		mRect = drawableObject.mRect;
	}
	
	public DrawableObject(Context context, int resourceID, int x, int y) {
		this.x = x;
		this.y = y;
		mBitmap = BitmapFactory.decodeResource(context.getResources(), resourceID);
		mRect = new Rect(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight());
	}
	
	public DrawableObject(Context context, int resourceID, GameScreenView ghostHunterScreen) {
		this(context, resourceID, 0, 0);
		mGhostHunterScreen = ghostHunterScreen;
	}
	
	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(mBitmap, x - mBitmap.getWidth()/2, y - mBitmap.getHeight()/2, null);
		mRect.set(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight());
	}
	
	public void update() {
		xSpeed = 0;
		ySpeed = 0;
		x += xSpeed;
		y += ySpeed;
	}
	
	public Bitmap getBitmap() {
		return mBitmap;
	}
	
	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		setRect(new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight()));
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Rect getRect() {
		return mRect;
	}
	
	public void setRect(Rect rect) {
		mRect = rect;
	}
	
	public void freeze() {
		isFrozen = !isFrozen;
	}
	
	public boolean isFrozen() {
		return isFrozen;
	}
	
}

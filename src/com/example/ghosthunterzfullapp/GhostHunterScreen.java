package com.example.ghosthunterzfullapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class GhostHunterScreen extends GameScreenView {
	Bitmap background, rescaledBackground;
	Bitmap mainCharacter;
	int pos_x, pos_y;
	float velocity_y;
	DrawableObject d, mc;
	ArrayList<DrawableObject> drawableObjects;
	Sprite sprite;
	Bitmap spriteBitmap;
	private ArrayList<DrawableObject>[] levels;
	private ArrayList<DrawableObject> mCurrentLevel;
	private GameLevelHandler gameLevelHandler;
	

	public GhostHunterScreen(Context context, long fps, int paramWidth, int paramHeight) {
		super(context, fps, paramWidth, paramHeight);
//		d = new Background(context, R.drawable.background_image);
//		background = BitmapFactory.decodeResource(getResources(), R.drawable.background_image);
//		rescaledBackground = Bitmap.createScaledBitmap(background, 800, 950, true);
//		pos_x = pos_y = 100;
//		velocity_y = 10;
//		mc = new MainCharacter(context, R.drawable.ghost);
//		sprite = new Sprite(context, R.drawable.sprite_sheet, this);
//		drawableObjects = new ArrayList<DrawableObject>(Arrays.asList(d, mc, sprite));
		gameLevelHandler = GameLevelHandler.get(context, this);
		mCurrentLevel = gameLevelHandler.getLevel(0);
	
	}

	@Override
	protected void drawCurrentFrame(Canvas frame) {
		for (DrawableObject drawableObject : mCurrentLevel) {
			drawableObject.onDraw(frame);
		}
		
	}
	
	public DrawableObject getDrawableObject(int index) {
		return mCurrentLevel.get(index);
	}
	
	public ArrayList<DrawableObject> getDrawableObjects(int start, int end) {
		ArrayList<DrawableObject> selectObjects = new ArrayList<DrawableObject>();
		if (end > mCurrentLevel.size()) end = selectObjects.size();
		for (int i = start; i < end; ++i) {
			selectObjects.add(getDrawableObject(i));
		}
		return selectObjects;
	}
	
	public ArrayList<DrawableObject> getDrawableObjects(int start) {
		return getDrawableObjects(start, mCurrentLevel.size());
	}
	
	public GameLevelHandler getGameLevelHandler() {
		return gameLevelHandler;
	}
	
	public void freeze() {
		for (DrawableObject drawableObject : mCurrentLevel) {
			drawableObject.freeze();
		}
	}
	

	

	
	

}

package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class MainCharacter extends DrawableObject {
	private static final double RESIZE_FACTOR = 0.64;
	private static final int CENTER_HEIGHT = 475;
	private static final int CENTER_WIDTH = 400;
	private int direction = 0; // 0 means facing right, 1 is facing left
	
	Bitmap resizedMainCharacter;
	Bitmap flippedMainCharacter;
	

	public MainCharacter(Context context, int resourceID) {
		this(context, resourceID, CENTER_WIDTH, CENTER_HEIGHT);
	}

	public MainCharacter(Context context, int resourceID, int pos_x, int pos_y) {
		super(context, resourceID, pos_x, pos_y);
		resizedMainCharacter = resize();
		setBitmap(resizedMainCharacter);
		Matrix matrix = new Matrix();
		matrix.preScale(-1, 1);
		flippedMainCharacter = Bitmap.createBitmap(resizedMainCharacter, 0, 0, 
				resizedMainCharacter.getWidth(), resizedMainCharacter.getHeight(), matrix, false);
	}
	
	public MainCharacter(Context context, int resourceID,
			GameScreenView ghostHunterScreenView) {
		this(context, resourceID);
		mGhostHunterScreen = ghostHunterScreenView;
	}

	private Bitmap resize() {
		return Bitmap.createScaledBitmap(getBitmap(), (int)(getBitmap().getWidth()*RESIZE_FACTOR), 
				(int)(getBitmap().getHeight()*RESIZE_FACTOR), true);
	}
	
	public void moveUp() {
		if (getY() + 0.5 - resizedMainCharacter.getHeight()/2 < 0) {
			setY(getY());
		} else {
			setY(getY() - 20);
		}
	}
	
	public void moveRight() {
		if (getX() + resizedMainCharacter.getWidth()/2 > mGhostHunterScreen.getWidth()) {
			setX(getX());
		} else {
			setX(getX() + 20);
		}
		direction = 0;
		orient(direction);
	}
	
	public void moveDown() {
		if (getY() + resizedMainCharacter.getHeight()/2 > mGhostHunterScreen.getHeight()) {
			setY(getY());
		} else {
			setY(getY() + 20);
		}
	}
	
	public void moveLeft() {
		if (getX() - resizedMainCharacter.getWidth()/2 < 0) {
			setX(getX());
		} else {
			setX(getX() - 20);
		}
		direction = 1;
		orient(direction);
	}
	
	public boolean isIntersecting() {
		DrawableObject enemy = ((GhostHunterScreen)mGhostHunterScreen).getGameLevelHandler().getLevel(0).get(2);
		return this.getRect().intersect(enemy.getRect());
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int d) {
		direction = d;
	}
	
	public void orient(int d) {
		if (d == 1) {
			setBitmap(resizedMainCharacter);
		} else {
			setBitmap(flippedMainCharacter);
		}
	}
}

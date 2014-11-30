package com.example.ghosthunterzfullapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Sprite extends DrawableObject {
	
//	int x, y;
	int x_velocity, y_velocity;
	int height, width;
	Bitmap bitmap;
//	GameScreenView mGameScreenView;
	int currentFrame = 0;
	int direction = 3;
	int velocity = 5;
	
	public Sprite(Context context, int resourceID, GameScreenView gameScreenView, int start_x, int start_y) {
		this(context, resourceID, gameScreenView);
		setX(start_x);
		setY(start_y);
	}

	public Sprite(Context context, int resourceID, GameScreenView gameScreenView) {
		super(context, resourceID, gameScreenView);
		bitmap = getBitmap();
		//mGameScreenView = gameScreenView;
		// 4 rows
		height = bitmap.getHeight() / 4;
		// 4 columns
		width = bitmap.getWidth() / 4;
		setX(0);
		setY(0);
		x_velocity = 5;
		y_velocity = 0;
	}

	public void onDraw(Canvas canvas) {
		// defines what we want to cut out from the sprite sheet
		if (!isFrozen()) {
			update();
		}
		int srcX = currentFrame * width;
		int srcY = direction * height;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect(x, y, x + width, y + height);
		canvas.drawBitmap(bitmap, src, dst, null);
		mRect.set(x, y, x + width, y + height);
	}

	public void update() {
		
		// 0 = down
		// 1 = left
		// 2 = right
		// 3 = up
		// facing down
			if (x > mGhostHunterScreen.getWidth() - width - x_velocity) {
				x_velocity = 0;
				y_velocity = velocity;
				direction = 0;
			}
			// going left
			if (y > mGhostHunterScreen.getHeight() - height - y_velocity) {
				x_velocity = -velocity;
				y_velocity = 0;
				direction = 0;
			}
			// facing up
			if (x + x_velocity < 0) {
				x = 0;
				x_velocity = 0;
				y_velocity = -velocity;
				direction = 3;
			}
			// facing right
			if ( y + y_velocity < 0) {
				y = 0;
				x_velocity = velocity;
				y_velocity = 0;
				direction = 3;
			}
			
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			currentFrame = ++currentFrame % 4;
			x += x_velocity;
			y += y_velocity;
			
		
	}

}

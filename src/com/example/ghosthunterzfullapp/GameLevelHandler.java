package com.example.ghosthunterzfullapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.View;

public class GameLevelHandler {

		
		private static GameLevelHandler sGameLevelHandler;
		private Context mAppContext;
		private GameScreenView mGhostHunterScreenView;
		private ArrayList<ArrayList<DrawableObject>> mLevels = 
				new ArrayList<ArrayList<DrawableObject>>();
		
		private GameLevelHandler(Context appContext, GameScreenView g) {
			mAppContext = appContext;
			mGhostHunterScreenView = g;
			setUpLevels();
		}
		
		public static GameLevelHandler get(Context c, GameScreenView g) {
			if (sGameLevelHandler == null) {
				sGameLevelHandler = new GameLevelHandler(c.getApplicationContext(), g);
			}
			return sGameLevelHandler;
		}
		
		private void setUpLevels() {
			ArrayList<DrawableObject> levelOne = new ArrayList<DrawableObject>(Arrays.asList(
							new Background(mAppContext, R.drawable.background_image),
							new MainCharacter(mAppContext, R.drawable.ghost, mGhostHunterScreenView),
							new Sprite(mAppContext, R.drawable.ghost_sprite_sheet_2, mGhostHunterScreenView, 0, 0),
							new Sprite(mAppContext, R.drawable.ghost_sprite_sheet_2, mGhostHunterScreenView, 
									400, 0)));
			mLevels.add(levelOne);
		}
		
		public ArrayList<DrawableObject> getLevel(int lev) {
			return mLevels.get(lev);
		}
}

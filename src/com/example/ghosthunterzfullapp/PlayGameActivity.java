package com.example.ghosthunterzfullapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlayGameActivity extends Activity {

	private static final String TAG = "MainGame";
	private static final int MBUTTON_WIDTH = 90; //in dp
	private static final int MBUTTON_HEIGHT = 90; //in dp
	private static final int GAMESCREEN_HEIGHT = 900;
	private static final int GAMEINFO_TEXT_SIZE = 25;
	
	private ImageButton mRightButton, mLeftButton, mUpButton, mDownButton;
	private ArrayList<View> gridFiller;
	private LinearLayout gameControlsLayout;
	private GhostHunterScreen gameScreen;
	private GameGridLayout directionButtonLayout;
	private LayoutParams overallLayoutParams, controlsLayoutParams, screenParams, directionButtonLayoutParams,
		mButtonParams, gameInfoLayoutParams;
	float ball_x, ball_y;
	private GameLinearLayout gameInfoLayout, overallLayout;
	public TextView numLivesTextView, healthTextView, pointsTextView;
	private GameLevelHandler sGameLevelHandler;
	private PauseButton pauseButton;
	private PauseButton resumeButton;
	
	int numLives = 3;
	int health = 100;
	int score = 0;
	
	ArrayList<DrawableObject> enemies;
	ArrayList<Rect> enemiesRects;
	MainCharacter mainCharacter;
	Rect mainCharacterRect;
	
	boolean pressed;
	
	private CheckBox mSpeedBoostActivatedCheckBox;
	boolean isSpeedBoostActivated = false;
	private CheckBox mTouchModeActivatedCheckBox;
	boolean isTouchModeActivated = false;
	private CheckBox mShakeBombActivatedCheckBox;
	boolean isShakeBombActivated = false;
	boolean wasSpeedBoostActivated, wasTouchModeActivated, wasShakeBombActivated;
	boolean isKeyFrozen = false;
	boolean frozen;
	int buttonToggle = 0;

	ArrayList<String> gameInfoText = new ArrayList<String>(Arrays.
			asList("Lives: " + numLives,
					"Health: " + health,
					"Score: " + score,
					"Pause"));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createLayout();
		
	}

	private void createLayout() {
		
		//define the overallLayout
		overallLayout = new GameLinearLayout(this, LinearLayout.VERTICAL, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		//define game info that will hold num lives, health, and score text views
		gameInfoLayout = new GameLinearLayout(this, LinearLayout.HORIZONTAL, LayoutParams.MATCH_PARENT, 50);
		
		numLivesTextView = new GameInfoTextView(this, gameInfoText.get(0), GAMEINFO_TEXT_SIZE, 
				getResources().getColor(R.color.Green));
		healthTextView = new GameInfoTextView(this, gameInfoText.get(1), GAMEINFO_TEXT_SIZE,
				getResources().getColor(R.color.Red));
		pointsTextView = new GameInfoTextView(this, gameInfoText.get(2), GAMEINFO_TEXT_SIZE,
				getResources().getColor(R.color.DarkOrange));
		pauseButton = new PauseButton(this, gameInfoText.get(3), GAMEINFO_TEXT_SIZE,
				getResources().getColor(R.color.SlateGray));
		
		//define screen
		gameScreen = new GhostHunterScreen(this, 30, LayoutParams.MATCH_PARENT, GAMESCREEN_HEIGHT);
//		frozen = getMainCharacter().isFrozen();
		gameScreen.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				int motions[] = {MotionEvent.ACTION_DOWN,
						MotionEvent.ACTION_UP,
						MotionEvent.ACTION_MOVE};
					if (!frozen && isTouchModeActivated && (action == motions[0] || action == motions[1] || 
							action == motions[2])) {
						getMainCharacter().setX((int)event.getX());
						getMainCharacter().setY((int)event.getY());
						checkIntersect();
						try {
							Thread.sleep(75);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return true;
					}
					
				return false;
			}
		});
		pauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pauseGame();
			
			}
		});
		
		frozen = getMainCharacter().isFrozen();
		// populate game info layout with the text views
		gameInfoLayout.addViews(numLivesTextView, healthTextView, pointsTextView, pauseButton);
		
		//define controlsLayout
		gameControlsLayout = new GameLinearLayout(this, LinearLayout.HORIZONTAL, 
				LayoutParams.MATCH_PARENT, (1230 - GAMESCREEN_HEIGHT));

		//define directionButtonLayout that will hold the arrow buttons
		directionButtonLayout = new GameGridLayout(this, GridLayout.HORIZONTAL, 2, 4, 
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		//define the movement buttons' layout parameters
		mButtonParams = new LayoutParams(convertDpPixels(MBUTTON_WIDTH), convertDpPixels(MBUTTON_HEIGHT));
		
		//create buttons
		mUpButton = new ArrowButton(this, R.drawable.up);
		mDownButton = new ArrowButton(this, R.drawable.down);
		mLeftButton = new ArrowButton(this, R.drawable.left);
		mRightButton = new ArrowButton(this, R.drawable.right);
			mUpButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isKeyFrozen)
					getMainCharacter().moveUp();	
					checkIntersect();
				}
			});
			mUpButton.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();
					int motions[] = {MotionEvent.ACTION_DOWN,
							MotionEvent.ACTION_UP,
							MotionEvent.ACTION_MOVE};
						if (isSpeedBoostActivated && (action == motions[0] || action == motions[1] || 
								action == motions[2])) {
							getMainCharacter().moveUp();
							checkIntersect();
							return true;
						}
						
					return false;
				}
			});
			mDownButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isKeyFrozen)
					getMainCharacter().moveDown();
					checkIntersect();
				}
			});
			mDownButton.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();
					int motions[] = {MotionEvent.ACTION_DOWN,
							MotionEvent.ACTION_UP,
							MotionEvent.ACTION_MOVE};
						if (isSpeedBoostActivated && (action == motions[0] || action == motions[1] || 
								action == motions[2])) {
							getMainCharacter().moveDown();
							checkIntersect();
							return true;
						}
						
					return false;
				}
			});
			mLeftButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isKeyFrozen)
					getMainCharacter().moveLeft();
					checkIntersect();
				}
			});
			mLeftButton.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();
					int motions[] = {MotionEvent.ACTION_DOWN,
							MotionEvent.ACTION_UP,
							MotionEvent.ACTION_MOVE};
						if (isSpeedBoostActivated && (action == motions[0] || action == motions[1] || 
								action == motions[2])) {
							getMainCharacter().moveLeft();
							checkIntersect();
							return true;
						}
						
					return false;
				}
			});
			mRightButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isKeyFrozen) {
					getMainCharacter().moveRight();
					}
					checkIntersect();
				}
			});
			mRightButton.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				int motions[] = {MotionEvent.ACTION_DOWN,
						MotionEvent.ACTION_UP,
						MotionEvent.ACTION_MOVE};
					if (isSpeedBoostActivated && (action == motions[0] || action == motions[1] || 
							action == motions[2])) {
						getMainCharacter().moveRight();
						checkIntersect();
						return true;
					}
					
				return false;
			}
		});
		
		mSpeedBoostActivatedCheckBox = new CheckBox(this);
		mSpeedBoostActivatedCheckBox.setText("Speed Boost");
		mSpeedBoostActivatedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				isSpeedBoostActivated = !isSpeedBoostActivated;				
			}
		});
		
		mTouchModeActivatedCheckBox = new CheckBox(this);
		mTouchModeActivatedCheckBox.setText("Touch Mode");
		mTouchModeActivatedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				isTouchModeActivated = !isTouchModeActivated;				
			}
		});
		
		// 2 x 3 grid, need to fill six squared with 4 arrow buttons and 2 filler buttons
		gridFiller = new ArrayList<View>();
		for (int i = 0; i < 3; i++) {
			View filler = new View(this);
			filler.setLayoutParams(mButtonParams);
			gridFiller.add(filler);
		}
		
		directionButtonLayout.addViews(gridFiller.get(0), mTouchModeActivatedCheckBox,
				mUpButton, mSpeedBoostActivatedCheckBox, gridFiller.get(1),
				mLeftButton, mDownButton, mRightButton);
		
		//add sub-views to controlsLayout
		gameControlsLayout.addView(directionButtonLayout);
		gameControlsLayout.setHorizontalGravity(Gravity.CENTER);
		gameControlsLayout.setPadding(0, 15, 0, 0);
		
		//add sub-views to overall view
		overallLayout.addViews(gameInfoLayout, gameScreen, gameControlsLayout);
		
		// finished product
		setContentView(overallLayout);
		
	}

	protected void checkIntersect() {
		enemies = getGhostHunterScreen().getDrawableObjects(2);
		enemiesRects = new ArrayList<Rect>();
		for (DrawableObject enemy : enemies) {
			enemiesRects.add(enemy.getRect());
		}
		mainCharacter = getMainCharacter();
		mainCharacterRect = mainCharacter.getRect();
		
		for (Rect enemyRect : enemiesRects) {
			if(mainCharacterRect.intersect(enemyRect)) {
				setHealth(getHealth() - 10);
				if (health == 0) {
					setHealth(100);
					setNumLives(getNumLives() - 1);
					if (numLives < 0) {
						gameOver();
					}
					numLivesTextView.setText("Lives: " + numLives);
				}
				healthTextView.setText("Health: " + health);
			}
		}
	}
	
	public void pauseGame() {
	//	frozen = getMainCharacter().isFrozen();
		isTouchModeActivated = false;
		isSpeedBoostActivated = false;
		isKeyFrozen = true;
		gameScreen.freeze();
	}
	
	public void gameOver() {
		pauseGame();
		Toast toast = Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		toast.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				gameScreen.freeze(); //unfreezes the screen
				Intent intent = new Intent(PlayGameActivity.this, LauncherMenu.class);
				startActivity(intent);				
			}
		}, 2000);
		
	}
	
	public void storeSpecialMovement() {
		wasTouchModeActivated = isTouchModeActivated;
		wasSpeedBoostActivated = isSpeedBoostActivated;
	}
	
	public void restoreSpecialMovement() {
		isTouchModeActivated = wasTouchModeActivated;
		isSpeedBoostActivated = wasSpeedBoostActivated;
	}

	/**
	 * Converts a density pixels measurement to a pixels measurement
	 * @param dp measurement in dp to convert to pixels
	 * @return pixel size
	 */
	private int convertDpPixels(int dp) {
		final float SCALE = this.getResources().getDisplayMetrics().density;
		return (int)(dp * SCALE + 0.5f); //official android formula
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		gameScreen.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		gameScreen.resume();
	}
	
	public MainCharacter getMainCharacter() {
		return (MainCharacter)gameScreen.getDrawableObject(1);
	}
	
	public GameLevelHandler getGameLevelHandler() {
		return gameScreen.getGameLevelHandler();
	}
		
	public int getNumLives() {
		return numLives;
	}

	public void setNumLives(int numLives) {
		this.numLives = numLives;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public GhostHunterScreen getGhostHunterScreen() {
		return gameScreen;
	}

	

	
	
	
}

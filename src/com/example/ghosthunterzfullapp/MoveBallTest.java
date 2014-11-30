
package com.example.ghosthunterzfullapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoveBallTest extends Activity implements OnTouchListener {

	OurView v;
	Bitmap mainGhost, thing, resizedGhost;
	float ball_x, ball_y;
	
	
	private Button mRightButton;
	private Button mLeftButton;
	private Button mUpButton;
	private Button mDownButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		v = new OurView(this);
		v.setOnTouchListener(this);

		mainGhost = BitmapFactory
				.decodeResource(getResources(), R.drawable.ghost);
		resizedGhost = Bitmap.createScaledBitmap(mainGhost, (int)(mainGhost.getWidth()*.64), 
				(int)(mainGhost.getHeight()*.64), true);
		ball_x = ball_y = 0;
		// setContentView(v);


		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);


		
		LinearLayout innerLayout = new LinearLayout(this);
		innerLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		mRightButton = new Button(this);
		mRightButton.setText("Right");
		mRightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ball_x += 20;
			}
		});
		
		mLeftButton = new Button(this);
		mLeftButton.setText("Left");
		mLeftButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ball_x -= 20;
			}
		});
		
		mUpButton = new Button(this);
		mUpButton.setText("Up");
		mUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ball_y -= 20;
			}
		});
		
		mDownButton = new Button(this);
		mDownButton.setText("Down");
		mDownButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ball_y += 20;
			}
		});

		LayoutParams testbox = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 100, 1);
		LayoutParams vParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 100, 10);
		LayoutParams buttonParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,100);
		LayoutParams innerLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,50, 2);
		
		innerLayout.addView(mRightButton);
		innerLayout.addView(mLeftButton);
		innerLayout.addView(mUpButton);
		innerLayout.addView(mDownButton);

		layout.addView(v, vParams);
		//layout.addView(tv, testbox);
		layout.addView(innerLayout, innerLayoutParams);
		//layout.addView(mRightButton, buttonParams);
		setContentView(layout);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent me) { // Called every time we
														// touch the screen

		// x = me.getX();
		// y = me.getY();

		try {
			Thread.sleep(50); // Sleep for 50 ms, save processing speed
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			ball_x = me.getX();
			ball_y = me.getY();
			break;
		case MotionEvent.ACTION_UP:
			ball_x = me.getX();
			ball_y = me.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			ball_x = me.getX();
			ball_y = me.getY();
			break;
		}
		return true; // method will still look for actions, process more than 1
						// handling event

	}

	public class OurView extends SurfaceView implements Runnable {

		Thread thread = null;
		SurfaceHolder surfaceHolder; // Allows change in dimensions of view
		boolean isRunning = false;

		public OurView(Context context) {
			super(context);
			surfaceHolder = getHolder();
		}

		// SurfaceView behaves as thread

		@Override
		public void run() {
			while (isRunning) {
				// perform canvas drawing
				if (!surfaceHolder.getSurface().isValid()) {
					continue; // will skip anything below, go back up to while
				}
				Canvas canvas = surfaceHolder.lockCanvas();
				canvas.drawARGB(255, 100, 100, 100); // Transparency, red, green,
													// blue (0-255)
				canvas.drawBitmap(resizedGhost, ball_x - resizedGhost.getWidth() / 2, ball_y
						- resizedGhost.getHeight() / 2, null);
				surfaceHolder.unlockCanvasAndPost(canvas);

			}
		}

		public void pause() {
			isRunning = false;
			while (true) {
				try {
					thread.join(); // Cleaning up or finishing things
				} catch (InterruptedException e) { // Clears out the thread
					e.printStackTrace();
				}
				break;
			}
			thread = null;
		}

		public void resume() {
			isRunning = true;
			thread = new Thread(this);
			thread.start();
		}

	}

}

package com.example.ghosthunterzfullapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class GameScreenView extends SurfaceView implements Runnable {
	private final long FPS; //the framerate cap (ensures steady animation)
	private SurfaceHolder holder; //surface holder magics!
	boolean isRunning = false; //defines whether the animation is a go or not
	Thread animatorThread = null; //the thread that does the work of animating

	public GameScreenView(Context context, long fps) {
		super(context);
		holder = getHolder();
		FPS = fps; //added option of setting the fps
		
	}
	
	public GameScreenView(Context context, long fps, int paramWidth, int paramHeight) {
		super(context);
		holder = getHolder();
		FPS = fps; //added option of setting the fps
		setLayoutParams(new LinearLayout.LayoutParams(paramWidth, paramHeight));
		
	}

	@Override
	public void run() {
		//initialize FPS-capping variables
		final long milliSecPerFrame = 1000/FPS; //time per frame
		long renderStartTime, processingTime, sleepTime;
		
		while (isRunning) {
			renderStartTime = System.currentTimeMillis();
			if (!holder.getSurface().isValid()) {
				//Log.d("HELP!", "not valid");
				continue; // will skip anything below, go back up to while
			}
			Canvas frame = holder.lockCanvas(); //lock it up so you can draw on it
			drawCurrentFrame(frame);
			holder.unlockCanvasAndPost(frame); //unlock & post, baby!
			
			//FPS-capping code:
			processingTime = System.currentTimeMillis() - renderStartTime;
			sleepTime = milliSecPerFrame - processingTime;
			try {
				if (sleepTime > 0) //i.e. the process was faster than time per frame
					Thread.sleep(sleepTime); //wait out full time allotted per frame
				else { //i.e. the process took longer than the time allotted per frame
					Log.d("Framerate", "Framerate not achieved");
					Thread.sleep(10); //avoid being too CPU demanding
				}
			} catch (Exception e) {}
		}
	}

	protected void drawCurrentFrame(Canvas frame) {
		// OVERWRITE THIS IN A SUBCLASS
		frame.drawARGB(255, 100, 100, 100);
	}

	public void pause() {
		isRunning = false;
		while (true) {
			try {
				animatorThread.join(); // Cleaning up or finishing things
			} catch (InterruptedException e) { // Clears out the thread
				e.printStackTrace();
			}
			break;
		}
		animatorThread = null;
	}

	public void resume() {
		isRunning = true;
		animatorThread = new Thread(this);
		animatorThread.start();
	}

}

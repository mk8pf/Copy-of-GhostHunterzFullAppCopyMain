
package com.example.ghosthunterzfullapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreenActivity extends Activity {
	
    private static int DELAY=1000; //duration of splash screen
	MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//play music
        
        music=MediaPlayer.create(SplashScreenActivity.this , R.raw.test);
		music.start();
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i =new Intent(SplashScreenActivity.this, LauncherMenu.class);
                startActivity(i);
                finish();
            }
        },DELAY); 
        


    }
}

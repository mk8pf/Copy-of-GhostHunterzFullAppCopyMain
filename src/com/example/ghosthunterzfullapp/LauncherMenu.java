package com.example.ghosthunterzfullapp;



import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


public class LauncherMenu extends Activity {
	Button mStart;
	Button mResume;
	Button mHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //general startup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_menu);
        

        //setup the buttons
        mStart= (Button) findViewById(R.id.start_button);
        mResume= (Button) findViewById(R.id.resume_button);
        mHighScores= (Button) findViewById(R.id.hs_button);

        //create listener for start button
        mStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            //launch Game activity
            
            Intent i= new Intent(LauncherMenu.this, PlayGameActivity.class);
            startActivity(i);
            }
        });
        
        mResume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//RESUME
            	Intent i= new Intent(LauncherMenu.this, MoveBallTest.class);
                startActivity(i);
            }
        });
        
        mHighScores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//High Scores
            	Intent i= new Intent(LauncherMenu.this, Placeholder.class);
                startActivity(i);
            }
        });
    }
    
    	
    	
    
    }



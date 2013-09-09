package com.hear_your_image;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HearYourImage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.welcome);
        ImageButton btnNew = (ImageButton)findViewById(R.id.newphoto);
        ImageButton btnHis = (ImageButton)findViewById(R.id.history);
        ImageButton btnHelp = (ImageButton)findViewById(R.id.help);
        ImageButton btnExit = (ImageButton)findViewById(R.id.exit);
        File imageDir = new File("/sdcard/HearYourImage/image/");
        if(!imageDir.exists())
        	imageDir.mkdirs();
        File MusicDir = new File("/sdcard/HearYourImage/sounds/");
        if(!MusicDir.exists()){
        	MusicDir.mkdirs();
        }
        btnNew.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HearYourImage.this,photo_camera.class);
				startActivity(intent);
			}
        	
        });
        
        btnHis.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HearYourImage.this,HistoryView.class);
				startActivity(intent);
			}
        	
        });
        
        btnExit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HearYourImage.this.finish();
			}
        	
        });
        
        btnHelp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }
}
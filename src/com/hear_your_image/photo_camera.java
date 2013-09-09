package com.hear_your_image;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class photo_camera extends Activity{
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		LinearLayout ll = new LinearLayout(this);
		Button btn = new Button(this);
		btn.setText("≈ƒ’’");
		ll.addView(btn);
		setContentView(ll);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        File imageDir = new File("/sdcard/HearYourImage/image/");
				File image = new File("/sdcard/HearYourImage/image/",Integer.toString(imageDir.listFiles().length));
				Uri imageURI  = Uri.fromFile(image);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
				startActivityForResult(intent, 10);
			}
			
		});
	}
}

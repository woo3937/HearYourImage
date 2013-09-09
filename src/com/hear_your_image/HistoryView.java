package com.hear_your_image;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryView extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        Log.e("path", Environment.getExternalStorageDirectory().getAbsolutePath());
        File rootDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/HearYourImage/image/");
        File[] files = rootDir.listFiles();
        final ListView list = (ListView)findViewById(R.id.list);
		ArrayList<HashMap<String, Object>> listitem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			String filepath = files[i].getAbsolutePath();
			map.put("img", filepath);
			map.put("filename", files[i].getName());
			map.put("createtime",
					new Date(files[i].lastModified()).toLocaleString());
			listitem.add(map);
		}
        MyAdapter adapter = new MyAdapter(this,listitem,R.layout.listunit,new String[]{"img","filename","createtime"},new int[]{R.id.img,R.id.filename,R.id.createtime});
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv = (TextView)arg1.findViewById(R.id.filename);
				String filename = (String) tv.getText();
				String filepath = Global.ImageRoot+filename;
				Intent intent = new Intent(HistoryView.this,MusicImagePlayer.class);
				intent.putExtra("file", filepath);
				startActivity(intent);
			}
        	
        });
    }
}

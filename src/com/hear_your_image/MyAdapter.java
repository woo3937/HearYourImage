package com.hear_your_image;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.*;

public class MyAdapter extends SimpleAdapter {

	public MyAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	public void setViewImage(ImageView v, int value) {
		v.setImageResource(value);
		System.out.println("1");
	}

	public void setViewImage(ImageView v, String value) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		System.out.println(value);
		option.inJustDecodeBounds=true;
		Bitmap bm = BitmapFactory.decodeFile(value,option);
		int ws = option.outWidth/100;
		int hs = option.outHeight/100;
		int scale = 1;
		if(ws>hs)
			scale = ws;
		else
			scale = hs;
		scale = scale<1?1:scale;
		System.out.println(scale);
		option.inJustDecodeBounds=false;
		option.inSampleSize=scale;
		bm = BitmapFactory.decodeFile(value,option);
		v.setImageBitmap(bm);
	}
}

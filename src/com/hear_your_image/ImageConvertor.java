package com.hear_your_image;

import java.util.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 *  图像转换类
 *  用于对图像进行处理，生成音乐小节序列
 * 
 */

public class ImageConvertor {
	// 常量定义
	public static final int MAX_COLOR = 256;
	public static final int IMG_SIZE = 128;
	public static final int SEG = 16;
	public static final int SEG_LEN = 8;
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;
	public static final int RED_BIT = 0x00FF0000;
	public static final int GREEN_BIT = 0x0000FF00;
	public static final int BLUE_BIT = 0x000000FF;
	public static final double RED_VAL = 0.3;
	public static final double GREEN_VAL = 0.59;
	public static final double BLUE_VAL = 0.11;
	
	/**
	 * 生成音阶序列方法
	 * 传入参数：图像地址
	 * 返回值：音阶ArrayList
	 * 
	 * @param srcImg
	 * @return
	 */
	public ArrayList<MelodyCell> imageToMusic(String srcImg) {
		ArrayList<MelodyCell> music = new ArrayList<MelodyCell>();
		
		Bitmap img = getBitmap(srcImg, IMG_SIZE);
		int height = img.getHeight();
		int width = img.getWidth();
		int[][][] imgData = new int[height][width][3];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color = img.getPixel(j, i);
				imgData[i][j][RED] = (color & RED_BIT) >> 16;
				imgData[i][j][GREEN] = (color & GREEN_BIT) >> 8;
				imgData[i][j][BLUE] = (color & BLUE_BIT);
			}
		}
		
		for (int i = 0; i < height - 1; i++) {
			music.add(getMelodyCell(imgData[i]));
			System.out.println(music.get(i));
		}
		MelodyCell m = getMelodyCell(imgData[height - 1]);
		
		System.out.format("size = %d\n", music.size());
		
		m.Melody[m.Melody.length - 1] = NoteAndRhythm.DO;
		music.add(m);
		
		
		return music;
	}
	
	/**
	 * 获取缩略图方法，用于生成原图缩略图，且宽度为128
	 * @param source
	 * @param targetw
	 * @return
	 */
    private Bitmap getBitmap(String srcImg, int targetw) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		System.out.println(srcImg);
		option.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(srcImg, option);
		
		int scale = option.outWidth / targetw;
		scale = scale < 1 ? 1 : scale;
		System.out.println(scale);
		
		option.inJustDecodeBounds = false;
		option.inSampleSize = scale;
		bmp = BitmapFactory.decodeFile(srcImg, option);
		
		return bmp;
    }
    
    /**
     * 获取小节音阶方法
     * @param data
     * @return
     */
    private MelodyCell getMelodyCell(int[][] data) {
    	int[] avg = new int[SEG];
    	int[] tmpa = new int[SEG], tmpn = new int[SEG], ans = new int[SEG], num = new int[SEG];
    	int i, j, cnt = 0, sum = 0;
    	  	
    	for (i = 0; i < SEG - 1; i++) {
    		avg[i] = 0;
    		for (j = 0; j < SEG_LEN; j++) {
    			avg[i] += getPixelValue(data[i * SEG_LEN + j]);
    		}
    		avg[i] = (avg[i] / SEG_LEN) * NoteAndRhythm.MAX_NOTE / MAX_COLOR;
    	}
    	avg[i] = 0;
    	for (j = 0; i * SEG_LEN + j < data.length; j++) {
    		avg[i] += getPixelValue(data[i * SEG_LEN + j]);
    	}
    	avg[i] = (avg[i] / j) * NoteAndRhythm.MAX_NOTE / MAX_COLOR;
    	  	
    	tmpa[0] = avg[0];
    	tmpn[0] = 1;
    	for (i = 1; i < SEG; i++) {
    		if (avg[i] != avg[i - 1]) {
    			cnt++;
    			tmpa[cnt] = avg[i];
    			tmpn[cnt] = 1;
    		} else {
    			tmpn[cnt]++;
    		}
    	}
    	cnt++;
    	
    	for (i = 0; i < cnt; i++) {
    		for (j = 8; j >= 0 && tmpn[i] > 0; j--) {
    			if (tmpn[i] >= NoteAndRhythm.rhythm[j]) {			
    				ans[sum] = tmpa[i];
    				num[sum] = j;
    				tmpn[i] -= NoteAndRhythm.rhythm[j];
    				sum++;
    			}
    		}
    	}
    	
    	MelodyCell ret = new MelodyCell(sum);
    	for (i = 0; i < sum; i++) {
    		ret.Melody[i] = ans[i];
    		ret.Rhythm[i] = num[i];
    	}
    	
    	return ret;
    }
    
    /**
     * 获取像素灰度值方法
     * @param rgb
     * @return
     */
    private int getPixelValue(int[] rgb) {
    	return (int)(rgb[RED] * RED_VAL + rgb[GREEN] * GREEN_VAL + rgb[BLUE] * BLUE_VAL);
    }
 
}
	


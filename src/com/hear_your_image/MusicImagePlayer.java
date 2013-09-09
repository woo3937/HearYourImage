package com.hear_your_image;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MusicImagePlayer extends Activity{
	private MediaPlayer player;
	private SeekBar pb;
    Handler handler = new Handler();  
    Runnable updateThread = new Runnable(){  
        public void run() {  
            //获得歌曲现在播放位置并设置成播放进度条的值  
        pb.setProgress(player.getCurrentPosition());  
            //每次延迟100毫秒再启动线程  
            handler.postDelayed(updateThread, 100);  
        }  
    }; 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        String filepath = intent.getStringExtra("file");
        setContentView(R.layout.player);
        
        ImageView img = (ImageView)findViewById(R.id.imgplayed);
        img.setImageBitmap(BitmapFactory.decodeFile(filepath));
        
        pb = (SeekBar)findViewById(R.id.progress);
        player = new MediaPlayer();
        
        try {
        	MidiGenerator.WriteMidi(new ImageConvertor().imageToMusic(filepath));
			player.setDataSource(Global.SoundRoot+"play.mid");
			player.prepare();
			pb.setMax(player.getDuration());
			System.out.println(player.getDuration());
			player.start();
			System.out.println(player.getDuration());
			handler.post(updateThread);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
                if(fromUser==true){  
                    player.seekTo(progress);  
                }
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}

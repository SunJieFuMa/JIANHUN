package com.chantyou.janemarried.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class Player {

	private static final String TAG = "Player";
	private static MediaPlayer mMediaPlayer;
	private static Player player;
	private static boolean isPlaying = false;
	private Object mPs;

	private static Player getInstance() {
		if(player == null) {
			player = new Player();
		}
		return player;
	}

	public  static void stop() {
		getInstance().stopPlay();;
	}

	public static void playRaw(Context cxt, int rid) {
		getInstance().play(cxt, true, rid);
	}

	public static void playFile(Context cxt, String path) {
		if(path == null || !new File(path).exists()) {
			return;
		}
		getInstance().play(cxt, false, path);
	}

	//我自己加的一个方法，播放网络上的音乐
	public static void playNetMusic(String videoUrl) {
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(videoUrl);
			mMediaPlayer.prepare();//prepare之后自动播放
			mMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void play(Context cxt, Object... ps) {
		if(isPlaying) {
			if(mPs == ps[1]) {
				return;
			}
		}
		try {
			stopPlay();
			if((Boolean) ps[0]) {
				mMediaPlayer = MediaPlayer.create(cxt, (int) ps[1]);
			} else {
				mMediaPlayer = new MediaPlayer();

//				final AudioAttributes aa = new AudioAttributes.Builder().build();
//				mMediaPlayer.setAudioAttributes(aa);
//				mMediaPlayer.setAudioSessionId(audioSessionId);

				mMediaPlayer.setDataSource((String) ps[1]);
				mMediaPlayer.prepare();
			}
			mMediaPlayer.setLooping(true);
			mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					Log.i(TAG, "mMediaPlayer onCompletion");
					stopPlay();
				}
			});
			isPlaying = true;
			mPs = ps[1];
			mMediaPlayer.start();
		} catch (Exception e){
			e.printStackTrace();
			isPlaying = false;
		}
	}

	private Player() {
	}

	private void stopPlay() {
		isPlaying = false;
		mPs = null;
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
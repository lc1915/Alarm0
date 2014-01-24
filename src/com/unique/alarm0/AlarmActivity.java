package com.unique.alarm0;

import com.unique.alarm0.ShakeDetector.OnShakeListener;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class AlarmActivity extends Activity {
	// 声明MediaPlayer对象
	private MediaPlayer alarmMusic;
	private AlarmManager alarmManager;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		// ShakeDetector initialization
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();
		mShakeDetector.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake(int count) {

				handleShakeEvent(count);
				Log.e("sdf", "count"+count);
			}

			private void handleShakeEvent(int count) {
				// TODO Auto-generated method stub
				if (count > 30) {
					alarmMusic.stop();
					// 结束该Activity
					AlarmActivity.this.finish();
					alarmManager.cancel(AlarmTestActivity.pendingIntent);
				}else if(count>20){
					new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟")
					.setMessage("你已经摇了20次了！")
					.show();
				}else if(count>10){
					new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟")
					.setMessage("你已经摇了10次了！")
					.show();
				}

			}
		});

		Log.e("asdf", "asd");
		// 加载指定音乐，并为之创建MediaPlayer对象
		alarmMusic = MediaPlayer.create(this, R.raw.a1);
		// 设置为循环播放
		alarmMusic.setLooping(true);
		// 播放音乐
		alarmMusic.start();
		// 创建一个对话框
		new AlertDialog.Builder(AlarmActivity.this).setTitle("闹钟")
				.setMessage("嘿！快摇动手机哦~")
				.show();
	}

	@Override
	public void onResume() {
		super.onResume();
		// Add the following line to register the Session Manager Listener
		// onResume
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		// Add the following line to unregister the Sensor Manager onPause
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
	}

}
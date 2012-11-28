package com.example.potterwand;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int RESULT_SPEECH = 1;

	private ImageButton btnSpeak;
	private TextView txtText;
	private Camera camera;
	private boolean flashOn=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtText = (TextView) findViewById(R.id.txtText);

		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

		btnSpeak.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				

				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
				

				try {
					startActivityForResult(intent, RESULT_SPEECH);

				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Oops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

			}
		});

	}

	private void startActivityWithName(MyAccountConstants name) {
		switch (name) {
		case HELLO: {
			camera = Camera.open();
			Parameters p = camera.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(p);
			camera.startPreview();
			flashOn = true;
			break;
		}
		case GOOD: {
			System.exit(0);
			break;
		}
		
		case SILENT: {
			AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			audiomanage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			break;
		}
		case ROTATE: {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
			break;

		}
		case NOX: {
			if (flashOn){
				flashOn = false;
				camera.stopPreview();
				camera.release();
			}
			break;
		}
		case STRAIGHT: {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		}
		case CANCEL: {
			cancel();
		}
		case QUIET: {
			AudioManager audiomanage = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			audiomanage.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
			audiomanage.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
		}
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SPEECH: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				txtText.setText(text.get(0));
				
					startActivityWithName(MyAccountConstants.fromString(text
							.get(0)));
			}
			break;
		}

		}
	}
	
	private void cancel() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (flashOn){
			flashOn = false;
			camera.stopPreview();
			camera.release();
		}
	}
	
}

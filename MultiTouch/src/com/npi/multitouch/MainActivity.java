package com.npi.multitouch;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author Javier Moreno
 * @author Alberto Quesada
 * @version %I%, %G%
 * @since 1.0
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		testMultiTouch();
		super.onStart();
	}

	private void testMultiTouch() {
		Context context = getApplicationContext();
		CharSequence text;
		if (hasMultitouch())
			text = "MultiTouch funciona en este dispositivo.";
		else
			text = "MultiTouch no funciona en este dispositivo.";
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}

	private boolean hasMultitouch() {
		return getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
	}
}

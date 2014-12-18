package com.npi.multitouch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * @author Javier Moreno
 * @version %I%, %G%
 * @since 1.0
 */
public class MainActivity extends Activity {
    private ArrayList<Touch> touchEvents = new ArrayList<Touch>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	TouchView paintView = (TouchView)findViewById(R.id.touch_view);
	paintView.setOnTouchListener(paintView);
    }

    @Override
    protected void onStart() {
	testMultiTouch();
	super.onStart();
    }

    /*public boolean onTouchEvent(MotionEvent event) {
	//touchEvents.add(new Touch(event));
	return true;
    }*/

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

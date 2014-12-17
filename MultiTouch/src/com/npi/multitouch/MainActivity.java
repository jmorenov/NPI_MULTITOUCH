package com.npi.multitouch;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private String DEBUG_TAG = "DEBUG: ";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart()
    {
	testMultiTouch();
	super.onStart();
    }

    // Given an action int, returns a string description
    public static String actionToString(int action)
    {
	switch (action)
	{

	case MotionEvent.ACTION_DOWN:
	    return "Down";
	case MotionEvent.ACTION_MOVE:
	    return "Move";
	case MotionEvent.ACTION_POINTER_DOWN:
	    return "Pointer Down";
	case MotionEvent.ACTION_UP:
	    return "Up";
	case MotionEvent.ACTION_POINTER_UP:
	    return "Pointer Up";
	case MotionEvent.ACTION_OUTSIDE:
	    return "Outside";
	case MotionEvent.ACTION_CANCEL:
	    return "Cancel";
	}
	return "";
    }

    private int mActivePointerId;

    public boolean onTouchEvent(MotionEvent event)
    {
	/*// Get the pointer ID
	mActivePointerId = event.getPointerId(0);

	// ... Many touch events later...

	// Use the pointer ID to find the index of the active pointer
	// and fetch its position
	int pointerIndex = event.findPointerIndex(mActivePointerId);
	// Get the pointer's current position
	float x = event.getX(pointerIndex);
	float y = event.getY(pointerIndex);*/
	
	int action = MotionEventCompat.getActionMasked(event);
	// Get the index of the pointer associated with the action.
	int index = MotionEventCompat.getActionIndex(event);
	int xPos = -1;
	int yPos = -1;

	//Log.d(DEBUG_TAG, "The action is " + actionToString(action));

	if (event.getPointerCount() > 1)
	{
	    Log.d(DEBUG_TAG, "Multitouch event");
	    // The coordinates of the current screen contact, relative to
	    // the responding View or Activity.
	    xPos = (int) MotionEventCompat.getX(event, index);
	    yPos = (int) MotionEventCompat.getY(event, index);

	} else
	{
	    // Single touch event
	    Log.d(DEBUG_TAG, "Single touch event");
	    xPos = (int) MotionEventCompat.getX(event, index);
	    yPos = (int) MotionEventCompat.getY(event, index);
	}

	return false;
    }

    private void testMultiTouch()
    {
	Context context = getApplicationContext();
	CharSequence text;
	if (hasMultitouch())
	    text = "MultiTouch funciona en este dispositivo.";
	else
	    text = "MultiTouch no funciona en este dispositivo.";
	Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
	toast.show();
    }

    private boolean hasMultitouch()
    {
	return getPackageManager().hasSystemFeature(
		PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
    }
}

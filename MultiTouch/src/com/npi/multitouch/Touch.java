/**
 * 
 */
package com.npi.multitouch;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 
 * 
 * @author Javier Moreno
 * @version %I%, %G%
 * @since 1.0
 */
public class Touch {
    private int mActivePointerId;
    private String DEBUG_TAG = "DEBUG: ";

    public Touch() {

    }

    public Touch(MotionEvent event) {
	/*
	 * // Get the pointer ID mActivePointerId = event.getPointerId(0);
	 * 
	 * // ... Many touch events later...
	 * 
	 * // Use the pointer ID to find the index of the active pointer // and
	 * fetch its position int pointerIndex =
	 * event.findPointerIndex(mActivePointerId); // Get the pointer's
	 * current position float x = event.getX(pointerIndex); float y =
	 * event.getY(pointerIndex);
	 */

	int action = MotionEventCompat.getActionMasked(event);
	// Get the index of the pointer associated with the action.
	int index = MotionEventCompat.getActionIndex(event);
	int xPos = -1;
	int yPos = -1;

	Log.d(DEBUG_TAG, "The action is " + actionToString(action));

	if (event.getPointerCount() > 1) {
	    Log.d(DEBUG_TAG, "Multitouch event" + index);
	    // The coordinates of the current screen contact, relative to
	    // the responding View or Activity.
	    xPos = (int) MotionEventCompat.getX(event, index);
	    yPos = (int) MotionEventCompat.getY(event, index);

	} else {
	    // Single touch event
	    Log.d(DEBUG_TAG, "Single touch event" + index);
	    xPos = (int) MotionEventCompat.getX(event, index);
	    yPos = (int) MotionEventCompat.getY(event, index);
	}

    }

    // Given an action int, returns a string description
    public static String actionToString(int action) {
	switch (action) {
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
}

/**
 * 
 */
package com.npi.multitouch;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * @author jmorenov
 *
 */
public class TouchView extends View implements OnTouchListener {

    Paint mPaint;
    float mX;
    float mY;
    boolean draw = false;
    float pressure = 0F;
    int action;
    int index;

    ArrayList<PointF> points = new ArrayList<PointF>();
    
    public TouchView(Context context, AttributeSet attributeSet) {
	super(context, attributeSet);

	/** Initializing the variables */
	mPaint = new Paint();
	mX = mY = -100;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
	if (event.getAction() == MotionEvent.ACTION_DOWN
		|| event.getAction() == MotionEvent.ACTION_MOVE) {
	    /*mX = event.getX();
	    mY = event.getY();*/
	    action = MotionEventCompat.getActionMasked(event);
	    index = MotionEventCompat.getActionIndex(event);
	    mX = (int) MotionEventCompat.getX(event, index);
	    mY = (int) MotionEventCompat.getY(event, index);
	    
	    draw = true;
	} else
	    draw = false;
	return true;
    }

    /*
     * @Override public boolean performClick() { // Calls the super
     * implementation, which generates an AccessibilityEvent // and calls the
     * onClick() listener on the view, if any super.performClick();
     * 
     * // Handle the action for the custom click here
     * 
     * return true; }
     */

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	if (draw) {
	    // Setting the color of the circle
	    mPaint.setColor(Color.GREEN);

	    // Draw the circle at (x,y) with radius 15
	    // canvas.drawCircle(mX, mY, 15, mPaint);
	    for(PointF p : points)
		canvas.drawCircle(p.x, p.y, 30, mPaint);

	}
	// Redraw the canvas
	invalidate();
    }

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

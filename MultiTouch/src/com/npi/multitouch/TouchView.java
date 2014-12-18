package com.npi.multitouch;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Javier Moreno
 * @author Alberto Quesada
 * @version %I%, %G%
 * @since 1.0
 */
public class TouchView extends View {

	private static final int SIZE = 30;

	private Paint mPaint;
	private Paint paint;

	private SparseArray<PointF> mActivePointers;
	private SparseArray<Path> paths;

	public TouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		mActivePointers = new SparseArray<PointF>();
		paths = new SparseArray<Path>();

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.STROKE);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(6f);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
	}
	
	@Override
	 public boolean performClick() {
	  // Calls the super implementation, which generates an AccessibilityEvent
	        // and calls the onClick() listener on the view, if any
	        super.performClick();

	        // Handle the action for the custom click here

	        return true;
	 }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get pointer index from the event object
		int pointerIndex = event.getActionIndex();

		// get pointer ID
		int pointerId = event.getPointerId(pointerIndex);

		// get masked (not specific to a pointer) action
		int maskedAction = event.getActionMasked();
		
		switch (maskedAction) {

		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN: {
			// We have a new pointer. Lets add it to the list of pointers
			PointF point = new PointF();
			point.x = event.getX(pointerIndex);
			point.y = event.getY(pointerIndex);
			mActivePointers.put(pointerId, point);
			Path path = new Path();
			path.moveTo(point.x, point.y);
			paths.put(pointerId, path);
			break;
		}
		case MotionEvent.ACTION_MOVE: { // a pointer was moved
			for (int size = event.getPointerCount(), i = 0; i < size; i++) {
				PointF point = mActivePointers.get(event.getPointerId(i));
				Path path = paths.get(event.getPointerId(i));
				if (point != null && path != null) {
					point.x = event.getX(i);
					point.y = event.getY(i);
					path.lineTo(point.x, point.y);
				}
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_CANCEL: {
			mActivePointers.remove(pointerId);
			paths.remove(pointerId);
			performClick();
			break;
		}
		}
		invalidate();

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mActivePointers.size() > 1 && paths.size() > 1) {
			// draw all pointers
			for (int size = mActivePointers.size(), i = 0; i < size; i++) {
				PointF point = mActivePointers.valueAt(i);
				Path path = paths.valueAt(i);
				canvas.drawCircle(point.x, point.y, SIZE, mPaint);
				canvas.drawPath(path, paint);
			}
		}
	}

}
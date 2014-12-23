/**
 * Copyright 2014 Javier Moreno, Alberto Quesada
 * 
 * This file is part of MultiTouch.
 * 
 * MultiTouch is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MultiTouch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MultitTouch.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * Clase TouchView. Controla la interfaz de la app y los puntos de contacto
 * detectados en la pantalla.
 * 
 * @author Francisco Javier Moreno Vega
 * @author Alberto Quesada Aranda
 * @version 23.12.2014
 * @since 17.12.2014
 */
public class TouchView extends View {

    /**
     * Radio de la circunferencia que se pinta en el punto de contacto
     * detectado.
     */
    private static final int SIZE = 30;

    /**
     * Objetos Paint que controlan el estilo de pintar el recorrido y las
     * circunferencias.
     */
    private Paint mPaint;
    private Paint paint;

    /**
     * Estructuras de datos SparseArray donde almacenar los puntos de contacto
     * detectados y el recorrido en la pantalla.
     */
    private SparseArray<PointF> mActivePointers;
    private SparseArray<Path> paths;

    /**
     * Constructor de la clase TouchView, inicia la vista.
     * 
     * @param context
     * @param attrs
     * 
     * @see initView()
     */
    public TouchView(Context context, AttributeSet attrs) {
	super(context, attrs);
	initView();
    }

    /**
     * Inicia la vista que usaremos para la interfaz.
     */
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

    /**
     * Call this view's OnClickListener, if it is defined. Performs all normal
     * actions associated with clicking: reporting accessibility event, playing
     * a sound, etc.
     * 
     * @return True there was an assigned OnClickListener that was called, false
     *         otherwise is returned.
     */
    @Override
    public boolean performClick() {
	// Calls the super implementation, which generates an AccessibilityEvent
	// and calls the onClick() listener on the view, if any
	super.performClick();
	// Handle the action for the custom click here

	return true;
    }

    /**
     * Método que se llama cuando se detecta una pulsación en la pantalla.
     * 
     * Implement this method to handle touch screen motion events.
     * 
     * If this method is used to detect click actions, it is recommended that
     * the actions be performed by implementing and calling performClick(). This
     * will ensure consistent system behavior, including:
     * 
     * - obeying click sound preferences - dispatching OnClickListener calls -
     * handling ACTION_CLICK when accessibility features are enabled
     * 
     * @param event
     *            The motion event
     * 
     * @return True if the event was handled, false otherwise.
     * 
     * @see onDraw(Canvas canvas)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	int pointerIndex = event.getActionIndex();
	int pointerId = event.getPointerId(pointerIndex);
	int maskedAction = event.getActionMasked();

	// Tipo de acción que ha sido detectada.
	switch (maskedAction) {
	case MotionEvent.ACTION_DOWN:
	case MotionEvent.ACTION_POINTER_DOWN: {
	    PointF point = new PointF();
	    point.x = event.getX(pointerIndex);
	    point.y = event.getY(pointerIndex);
	    mActivePointers.put(pointerId, point);
	    Path path = new Path();
	    path.moveTo(point.x, point.y);
	    paths.put(pointerId, path);
	    break;
	}
	case MotionEvent.ACTION_MOVE: {
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

    /**
     * Método que se llama cuando se vuelve a pintar la vista de la pantalla. En
     * nuestro caso al inicio y al llamar a invalidate() en onTouchEvent()
     * 
     *      Implement this to do your drawing.
     * 
     * @param canvas
     *            the canvas on which the background will be drawn.
     *            
     * @see onTouchEvent(MotionEvent event)
     */
    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	if (mActivePointers.size() > 1 && paths.size() > 1) {
	    for (int size = mActivePointers.size(), i = 0; i < size; i++) {
		PointF point = mActivePointers.valueAt(i);
		Path path = paths.valueAt(i);
		canvas.drawCircle(point.x, point.y, SIZE, mPaint);
		canvas.drawPath(path, paint);
	    }
	}
    }

}
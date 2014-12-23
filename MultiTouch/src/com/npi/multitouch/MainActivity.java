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

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Clase MainActivity. Controla la ejecución de la app, llamando a la creación
 * de la interfaz.
 * 
 * @author Francisco Javier Moreno Vega
 * @author Alberto Quesada Aranda
 * @version 19.12.2014
 * @since 17.12.2014
 */
public class MainActivity extends Activity {

    /**
     * Called when the activity is starting. This is where most initialization
     * should go: calling setContentView(int) to inflate the activity's UI,
     * using findViewById(int) to programmatically interact with widgets in the
     * UI, calling managedQuery(android.net.Uri, String[], String, String[],
     * String) to retrieve cursors for data being displayed, etc.
     * <p>
     * You can call finish() from within this function, in which case
     * onDestroy() will be immediately called without any of the rest of the
     * activity lifecycle (onStart(), onResume(), onPause(), etc) executing.
     *
     *
     * @param savedInstanceState
     *            If the activity is being re-initialized after previously being
     *            shut down then this Bundle contains the data it most recently
     *            supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity
     * had been stopped, but is now again being displayed to the user. It will
     * be followed by onResume().
     * <p>
     * LLama al método que comprueba si el dispositivo soporta múltiples puntos
     * de detección en la pantalla.
     * 
     * @see testMultiTouch()
     */
    @Override
    protected void onStart() {
	testMultiTouch();
	super.onStart();
    }

    /**
     * Comprueba si el dispositivo soporta múltiples puntos de detección en la
     * pantalla.
     */
    private void testMultiTouch() {
	Context context = getApplicationContext();
	CharSequence text;
	if (getPackageManager().hasSystemFeature(
		PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH))
	    text = "MultiTouch funciona en este dispositivo.";
	else
	    text = "MultiTouch no funciona en este dispositivo.";
	Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
	toast.show();
    }
}

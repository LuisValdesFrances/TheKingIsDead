package com.luis.lgameengine.gameutils;


import java.io.IOException;
import java.io.RandomAccessFile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * @author Luis Valdes Frances
 */

public class Settings {

    public static final String LGAME_ENGINE_VERSION = "0.0.4";

    private Activity activity;

    public Activity getActiviy() {
        return activity;
    }

    private float fScaleX;
    private float fScaleY;

    public float getScaleX() {
        return fScaleX;
    }

    public float getScaleY() {
        return fScaleY;
    }

    public float getScale() {
        return (fScaleX + fScaleY) / 2f;
    }

    private int bufferX;
    private int bufferY;

    public int getScreenWidth() {
        return bufferX;
    }

    public int getScreenHeight() {
        return bufferY;
    }

    private int realWidth;
    private int realHeight;

    public int getRealWidth() {
        return realWidth;
    }

    public int getRealHeight() {
        return realHeight;
    }

    public static final boolean AVAIVABLE_CLICS = true;

    private int resolutionSet;

    public int getResolutionSet() {
        return resolutionSet;
    }

    private int nativeResolutionSet;

    public int getNativeResolutionSet() {
        return nativeResolutionSet;
    }

    public static final int[] MAX_SIZE_SET = {320, 480, 800, 1280};


    public static final int SIZE_240x320 = 0;
    public static final int SIZE_480x320 = 1;
    public static final int SIZE_800x480 = 2;
    public static final int SIZE_1280x720 = 3;

    public static final String[] BITMAP_FOLDER = {"drawable-ldpi",
            "drawable-mdpi",
            "drawable-hdpi",
            "drawable-hhdpi"};


    public static final int ORIENTATION_PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;   // Configuration.ORIENTATION_PORTRAIT;
    public static final int ORIENTATION_LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; // Configuration.ORIENTATION_LANDSCAPE;
    public static final int ORIENTATION_SENSOR = ActivityInfo.SCREEN_ORIENTATION_SENSOR; // Configuration.ORIENTATION_LANDSCAPE;
    public static final int DEFAULT_ORIENTATION = ORIENTATION_SENSOR;


    /**
     * Get documentation in: http://developer.android.com/intl/es/guide/topics/manifest/uses-sdk-element.html
     * <p>
     * <pre>
     * API           VERSION        VERSION CODE         OTHERS
     * 3             1.5            CUPCAKE              SINGLE TOUCH
     * 4             1.6            DONUT                SINGLE TOUCH
     * 7             2.1            ECLAIR               SINGLE TOUCH/MULTI TOUCH
     * 8             2.2            FROYO                MULTI TOUCH (OpenGL 2.0)
     * 9             2.3            GINGERBREAD          MULTI TOUCH (OpenGL 2.0)
     * 14            4.0            ICE_CREAM_SANDWICH   MULTI TOUCH (OpenGL 2.0)
     * 16            4.1            JELLY_BEAN           MULTI TOUCH (OpenGL 2.0)
     * 19            4.4            KITKAT               MULTI TOUCH (OpenGL 2.0)
     * 21            5.0            LOLLIPOP             MULTI TOUCH (OpenGL 2.0)
     * </pre>
     */
    public static final int KITKAT = Build.VERSION_CODES.KITKAT;//19
    public static final int LOLLIPOP = 21;
    public static final int MARSHMALLOW = 23;
    public static final int NOUGAT = 24;
    public static final int OREO = 26;
    public static final int PIE = 28;

    //Hardware info
    public int CPUMaxMhzs;
    public String deviceModel;
    public int androidApiVersion;

    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void init(Activity activity, boolean[] resolutionAvailableSet, int screenOrientation) {
        this.activity = activity;
        CPUMaxMhzs = getMaxCPUFreqMHz();
        deviceModel = System.getProperty("device.model");
        androidApiVersion = android.os.Build.VERSION.SDK_INT;

        Log.i("Debug", "Device model: " + deviceModel);
        Log.i("Debug", "Android API version: " + androidApiVersion);
        Log.i("Debug", "CPUMaxMhzs: " + CPUMaxMhzs);


        //Hide systemUI
        /*
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (androidApiVersion >= Build.VERSION_CODES.KITKAT) {

            activity.getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = activity.getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }
        */

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);


        //A partir de KITKAT es posible remover la barra de menu. Uso reflexion para saber si la clase contiene el metodo 'getRealMetrics'
        if (androidApiVersion < OREO) {
            try {
                android.view.Display.class.getMethod("getRealMetrics", DisplayMetrics.class).invoke(activity.getWindowManager().getDefaultDisplay(), dm);
            } catch (Exception e) {
                activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            }
        }


        realWidth = dm.widthPixels;
        realHeight = dm.heightPixels;

        /*
		if(Settings.getInstance().getScreenOrientation() == ORIENTATION_LANDSCAPE){
			if(realWidth < realHeight){
				int aux = realHeight;
				realHeight = realWidth;
				realWidth = aux;
			}
		}
		if(Settings.getInstance().getScreenOrientation() == ORIENTATION_PORTRAIT) {
            if (realHeight < realWidth) {
                int aux = realWidth;
                realWidth = realHeight;
                realHeight = aux;
            }
        }
        */

        int orientation = screenOrientation;//Settings.getInstance().getScreenOrientation();
        if (orientation == Settings.ORIENTATION_LANDSCAPE) {
            realWidth = dm.widthPixels > dm.heightPixels ? dm.widthPixels : dm.heightPixels;
            realHeight = dm.heightPixels > dm.widthPixels ? dm.widthPixels : dm.heightPixels;
            //Log.i("Debug", "Orientation LANDSCAPE");
        } else if (orientation == Settings.ORIENTATION_PORTRAIT) {
            realWidth = dm.widthPixels > dm.heightPixels ? dm.heightPixels : dm.widthPixels;
            realHeight = dm.heightPixels > dm.widthPixels ? dm.heightPixels : dm.widthPixels;
            //Log.i("Debug", "Orientation PORTRAIT");
        } else {
            realWidth = dm.widthPixels;
            realHeight = dm.heightPixels;
            //Log.i("Debug", "Orientation DEFAULT");
        }


        //Native resolution
        int w = (realWidth > realHeight ? realWidth : realHeight);
        for (int i = 0; i < MAX_SIZE_SET.length; i++) {
            if (w > MAX_SIZE_SET[i]) {
                nativeResolutionSet = Math.min(nativeResolutionSet + 1, MAX_SIZE_SET.length - 1);
            }
        }
        Log.i("Debug", "Native resolution: " + nativeResolutionSet);

        resolutionSet = -1;
        for (int i = nativeResolutionSet; i > 0; i--) {
            if (resolutionAvailableSet[i]) {
                resolutionSet = i;
                break;
            }
        }

		/*
		Si no se ha encontrado ningun set (Esto ocurre cuando el set minimo esta por encima
		del que le corresponde por resolucion.
		En ese caso, cojemos el primero mas pequeño
		 */
        if (this.resolutionSet == -1) {
            for (int i = 0; i < MAX_SIZE_SET.length; i++) {
                if (resolutionAvailableSet[i]) {
                    resolutionSet = i;
                    break;
                }
            }
        }

        Log.i("Debug", "Resolution: " + resolutionSet);

        boolean isLandscape = orientation == Settings.ORIENTATION_LANDSCAPE;

        int frameBufferWidth;
        int frameBufferHeight;
        switch (resolutionSet) {
            case SIZE_240x320:
                frameBufferWidth = isLandscape ? 320 : 240;
                frameBufferHeight = isLandscape ? 240 : 320;
                break;
            case SIZE_480x320:
                frameBufferWidth = isLandscape ? 480 : 320;
                frameBufferHeight = isLandscape ? 320 : 480;
                break;
            case SIZE_800x480:
                frameBufferWidth = isLandscape ? 800 : 480;
                frameBufferHeight = isLandscape ? 480 : 800;
                break;
            default:
                frameBufferWidth = isLandscape ? 1280 : 720;
                frameBufferHeight = isLandscape ? 720 : 1280;
                break;
        }


        bufferX = frameBufferWidth;
        bufferY = frameBufferHeight;

        fScaleX = ((float) frameBufferWidth / realWidth);
        fScaleY = ((float) frameBufferHeight / realHeight);

    }

    @SuppressLint("Assert")
    public static int getMaxCPUFreqMHz() {

        int maxFreq = -1;
        try {

            RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r");

            while (true) {
                String line = reader.readLine();
                if (null == line) {
                    break;
                }
                String[] splits = line.split("\\s+");
                assert (splits.length == 2);
                int timeInState = Integer.parseInt(splits[1]);
                if (timeInState > 0) {
                    int freq = Integer.parseInt(splits[0]) / 1000;
                    if (freq > maxFreq) {
                        maxFreq = freq;
                    }
                }
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maxFreq;
    }

    public int getScreenOrientation() {
        int orientation = ORIENTATION_PORTRAIT;
        if (realWidth > realHeight)
            orientation = ORIENTATION_LANDSCAPE;
        return orientation;
    }
}

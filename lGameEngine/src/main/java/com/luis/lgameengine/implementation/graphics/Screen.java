package com.luis.lgameengine.implementation.graphics;


import android.app.Activity;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.implementation.input.KeyboardHandler;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;

public class Screen extends SurfaceView implements SurfaceHolder.Callback{

    public SurfaceHolder surfaceHolder;
    public int screenW;
    public int screenH;

    protected MultiTouchHandler multiTouchHandler;
    protected KeyboardHandler keyboardHandler;

    public Screen(Activity activity, int screenW, int screenH) {
        super(activity);

        this.screenW = screenW;
        this.screenH = screenH;

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.setFixedSize(screenW, screenH);
        holder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        //Mejora la calidad de los colores. Probarlo con cuidado.
        getHolder().setFormat(PixelFormat.RGBA_8888);

        multiTouchHandler = new MultiTouchHandler(this, Settings.getInstance().getScaleX(), Settings.getInstance().getScaleY());
        keyboardHandler = new KeyboardHandler(this);
    }

    public void repaint(){

        if(surfaceHolder == null){
            return;
        }

        android.graphics.Canvas canvas = null;
        try{
            canvas = surfaceHolder.lockCanvas(null);//Devuelve una instancia de canvas y lo bloquea para que podamos pintar
            //canvas.getClipBounds(vDstRect);//Obtiene la totalidad de la pantalla.

            Graphics graphics = new Graphics(canvas);
            paint(graphics);//Se pinta sobre el lienzo DESDE MAIN

        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            if(canvas != null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    protected void paint(Graphics g){

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // get handles to some important objects
        surfaceHolder = holder;
        try{
            surfaceHolder.setFixedSize(this.screenW, this.screenH);
        }
        catch (IllegalArgumentException e) {}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}

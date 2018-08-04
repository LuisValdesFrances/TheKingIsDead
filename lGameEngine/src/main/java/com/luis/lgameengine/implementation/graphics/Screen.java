package com.luis.lgameengine.implementation.graphics;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.implementation.input.KeyboardHandler;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

	public class Screen extends SurfaceView{
      
	  public int screenW;
      public int screenH;
      
      protected MultiTouchHandler multiTouchHandler;
      protected KeyboardHandler keyboardHandler;
      
      protected SurfaceHolder surfaceHolder;
      protected Rect dstRect;
      protected Bitmap buffer;
      protected Canvas bufferCanvas;

      public Screen(Activity context, int screenW, int screenH){
    	  super(context);

          this.screenW = screenW;
          this.screenH = screenH;

          // register our interest in hearing about changes to our surface
          this.surfaceHolder = getHolder();
          this.buffer = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.RGB_565);
          this.bufferCanvas = new Canvas(buffer);
          this.dstRect = new Rect();

          Log.i("Debug", "scaleX: " + Settings.getInstance().getScaleX() + " scaleY: " + Settings.getInstance().getScaleY());


          multiTouchHandler = new MultiTouchHandler(this, Settings.getInstance().getScaleX(), Settings.getInstance().getScaleY());
          keyboardHandler =new KeyboardHandler(this);
      }
      
      public void repaint(){
  		if(surfaceHolder.getSurface().isValid()){
  		
  			 try {
  	                //gestureEvent.cancel();
  	                Canvas canvas = surfaceHolder.lockCanvas();
  	                //Construye un rect del tama�o del surfaceView
  	                canvas.getClipBounds(dstRect);
  	                //Todas las llamadas a paint pintan sobre el bufferCanvas asociado a la bufferImagen
  	                paint(new Graphics(bufferCanvas));
  	                canvas.drawBitmap(buffer, null, dstRect, null);
  	                surfaceHolder.unlockCanvasAndPost(canvas);
  	            }catch(Exception e){}
  		}
  	}
  	
  	protected void paint(Graphics g){}	
  }

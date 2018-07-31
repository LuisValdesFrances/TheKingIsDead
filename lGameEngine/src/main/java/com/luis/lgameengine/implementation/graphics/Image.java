package com.luis.lgameengine.implementation.graphics;

import java.io.IOException;
import java.io.InputStream;
import com.luis.lgameengine.gameutils.Settings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Image {
	
	private Bitmap bitmap;
	private Graphics graphics;
	
	public Image(String _name) throws IOException{
		try {
			String zPath = "";
	        // Create an input stream to read from the assets folder
	        //String zPath = Settings.BITMAP_FOLDER[Settings.ms_iSize] + _zPath.substring(1, _zPath.length());
	        zPath = Settings.BITMAP_FOLDER[Settings.getInstance().getResolutionSet()] + _name;
	        InputStream ins = Settings.getInstance().getActiviy().getAssets() .open(zPath);
	        bitmap = BitmapFactory.decodeStream(ins);
        if(ins!=null) ins.close();
		}catch(Exception e){
        	Log.e("error", e.toString());
        }
     }
	
	public Image (int[] _iColors, int _iWidth, int _iHeight){
		bitmap = Bitmap.createBitmap(_iColors, _iWidth, _iHeight, Bitmap.Config.ARGB_8888);
	}
	
	 public Image (int _iW, int _iH) {
		 bitmap = Bitmap.createBitmap(_iW, _iH, Bitmap.Config.ARGB_8888);
		 bitmap.eraseColor(0xffffff);
	}
	 
	 public Image (Bitmap _bitmap) {
		 bitmap = _bitmap;
     }
	 
	 public Graphics getGraphics () {
		 if (graphics == null)
	            graphics = new Graphics(bitmap);

	        return graphics;
	 }
	
    public static Image createImage (String _name) throws IOException {
	        return new Image(_name);
	}
    
    public static Image createImage (int _iWidth, int _iHeight) {
        return new Image(_iWidth, _iHeight);
    }
    
    public static Image createRGBImage(int[] _iColors, int _iWidth, int _iHeight, boolean fake){
    	return new Image(_iColors, _iWidth, _iHeight);
    }
    
    //Devuelve (Por paso por referencia) los valores RGB de la imagen usada al array que se le pasa por parametro:
    public void getRGB(int[] _vRgbData, int _vOffset, int _iScanlength, int _iX, int _iY, int _iWidth, int _iHeight){
    	bitmap.getPixels(_vRgbData, _vOffset, _iScanlength, _iX, _iY, _iWidth, _iHeight);
    }
	
	public Bitmap getBitmap(){
		return bitmap;
	}

	public int getWidth() {
		return bitmap.getWidth();
	}
	
	public int getHeight() {
		return bitmap.getHeight();
	}
}

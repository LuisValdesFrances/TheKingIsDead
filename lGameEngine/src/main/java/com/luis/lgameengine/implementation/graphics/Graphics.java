package com.luis.lgameengine.implementation.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.RectF;
import android.graphics.Region.Op;

public class Graphics {
	
	public float screenX = 1f;
    public float screenY = 1f;
    public float angle = 0f;
	
	
	public static final int HCENTER = 1;
	public static final int VCENTER = 2;
	public static final int LEFT = 4;
	public static final int RIGHT = 8;
	public static final int TOP = 16;
	public static final int BOTTOM = 32;
	public static final int BASELINE = 64;
	public static final int HFLIP = 128;
    public static final int VFLIP = 256;
	
	public Canvas canvas;
	private Paint paint;
	
	//Instancias privadas. Con esto evitamos crear objetos cada vez:
	private Rect screenRect = new Rect();
	private Rect destinyRect = new Rect();
	
	public Graphics(Canvas _c) {
		this.canvas = _c;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
	}
	
	
	public Graphics(Bitmap _bitmap) {
		this.canvas = new Canvas(_bitmap);
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
	}
	
	
	/*El valor debe de oscilar entre 255(opaco) a (0) transparente)
	 */
	public void setAlpha(int _iAlpha) {
		paint.setAlpha(_iAlpha);
	}
	
	public void clear(int _color) {
		
	}

	//Draw region
	public void drawRegion(Image _image, int _x, int _y, 
			int _srcX, int _srcY, int _srcWidth, int _srcHeight, 
			float angle, int rotationPointX, int rotationPointY) {
		screenRect.left = _srcX;
		screenRect.top = _srcY;
		screenRect.right = _srcX + _srcWidth;
		screenRect.bottom = _srcY + _srcHeight;

		destinyRect.left = _x;
		destinyRect.top = _y;
		destinyRect.right = _x + _srcWidth + 1;
		destinyRect.bottom = _y + _srcHeight + 1;
		
		canvas.save();
		canvas.rotate(angle, rotationPointX, rotationPointY);
		canvas.drawBitmap(_image.getBitmap(), screenRect, destinyRect, null);
		
		canvas.restore();
	}

	//Pinta una imagen con valores de reescalado y alpha
	public void drawImage(Image _vImage, int _iX, int _iY, int _iAnchor) {
		// anchor
        int iExtra_X = 0, iExtra_Y = 0;
        if ((_iAnchor & BOTTOM) != 0)
            iExtra_Y = _vImage.getHeight();
        if ((_iAnchor & RIGHT) != 0)
            iExtra_X = _vImage.getWidth();
        if ((_iAnchor & VCENTER) != 0)
            iExtra_Y = _vImage.getHeight() >> 1;
        if ((_iAnchor & HCENTER) != 0)
            iExtra_X = _vImage.getWidth() >> 1;
            
         // scale / flags
         float fX = screenX, fY = screenY;
         if ((_iAnchor & HFLIP) != 0)
        	 fX = -fX;
         if ((_iAnchor & VFLIP) != 0)
        	 fY = -fY;
            
        //Translation and transform:
        Matrix vMatrix = new Matrix();
        vMatrix.setTranslate(_iX - iExtra_X, _iY - iExtra_Y);
        
        //if (screenX == 1f && screenY == 1f)
            vMatrix.preScale(fX, fY, iExtra_X, iExtra_Y);

        //if (m_fAngle != 0)
        //    vMatrix.preRotate(m_fAngle, iExtra_X, iExtra_Y);
        
		canvas.drawBitmap(_vImage.getBitmap(), vMatrix, paint);
	}
	
	public void drawDistorisionImage(
    		Image _vImage, 
    		int _iX0, int _iY0,//Point corner top-left 
    		int _iX1, int _iY1,//Point corner top-right 
    		int _iX2, int _iY2,//Point corner button-left
    		int _iX3, int _iY3//Point corner button-left
    		){
    	
        
        //if (Settings.ms_iImageMode == Settings.IMAGE_BITMAP) {

            Matrix vMatrix = new Matrix();
            
            //Distorsion image
            vMatrix.setPolyToPoly(
                    new float[] { 
                        0, 0, 
                        _vImage.getWidth(), 0,
                        0, _vImage.getHeight(),
                        _vImage.getWidth(), _vImage.getHeight() 
                    }, 0, 
                    new float[] { 
                    	_iX0, _iY0, 
                    	_iX1, _iY1, 
                    	_iX2, _iY2,
                        _iX3, _iY3,
                    }, 0, 
                    4);
            
            canvas.drawBitmap(_vImage.getBitmap(), vMatrix, paint);
        /* 
        } else if (Settings.ms_iImageMode == Settings.IMAGE_DRAWABLE) {

            // draw
            _vImage.getDrawable().setAlpha(m_vPaint.getAlpha()); // alpha

            _vImage.getDrawable().setBounds(_iX - iExtra_X, _iY - iExtra_Y, // translation
                (int) (_iX - iExtra_X + (_vImage.getWidth())), // size x
                (int) (_iY - iExtra_Y + (_vImage.getHeight()))); // size y
            _vImage.getDrawable().draw(m_vCanvas);
        }
        */
      }
	
	/**
     * Sets the current color to the specified ARGB values. All subsequent rendering operations will use this specified color. 
     * The ARGB value passed in is interpreted with the least significant eight bits giving the blue component, 
     * the next eight more significant bits giving the green component, the next eight more significant bits giving the red component, 
     * and the next eight more significant bits giving the alpha component. That is to say, the color component is specified in the form of 0xAARRGGBB. 
     * If the high order byte of this value is 0 it will be ignored ignored in orther to get compatibility with the javax.microedition.lcdui.Graphic.setColor(int _iRGB)
     * @param _iColor the color being set
     * @ see getColor()
     */
    public void setColor (int _iColor) {
        if (((_iColor >> 24) & 0xFF) == 0x00)
            _iColor = (255<<24) + _iColor;
        if (_iColor == 0)
            _iColor = (255<<24) + 0x000000;
      
        paint.setColor(_iColor);
    }

    /**
     * Sets the current color to the specified ARGB values. All subsequent rendering operations will use this specified color.
     * @param _iA the alpha component of the color being set in range 0-255
     * @param _iR the red component of the color being set in range 0-255
     * @param _iG the green component of the color being set in range 0-255
     * @param _iB the blue component of the color being set in range 0-255
     * @throws IllegalArgumentException - if any of the color components are outside of range 0-255
     * @see getColor()
     */
    public void setColor (int _iA, int _iR, int _iG, int _iB) {
        paint.setColor((_iA<<24)|(_iR<<16)|(_iG<<8)|_iB);
    }

    /**
     * Sets the current color to the specified RGB values. All subsequent rendering operations will use this specified color.
     * @param _iR the red component of the color being set in range 0-255
     * @param _iG the green component of the color being set in range 0-255
     * @param _iB the blue component of the color being set in range 0-255
     * @throws IllegalArgumentException - if any of the color components are outside of range 0-255
     * @see getColor()
     */
    public void setColor (int _iR, int _iG, int _iB) {
        paint.setColor((255<<24)|(_iR<<16)|(_iG<<8)|_iB);
    }

    /**
     * Sets the current grayscale to be used for all subsequent rendering operations. 
     * For monochrome displays, the behavior is clear. For color displays, 
     * this sets the color for all subsequent drawing operations to be a gray color 
     * equivalent to the value passed in. The value must be in the range 0-255.
     * @param _bSetGrayScale the desired grayscale value 
     * @throws IllegalArgumentException - if the gray value is out of range
     * @see getGrayScale()
     */
	

	/**
     * Fills the specified rectangle with the current color. If either width or height is zero or less, nothing is drawn.
     * @param _iX the x coordinate of the rectangle to be drawn
     * @param _iY the y coordinate of the rectangle to be drawn
     * @param _iW the width of the rectangle to be drawn
     * @param _iH the height of the rectangle to be drawn
     * @see drawRect(int, int, int, int)
     */
    public void fillRect(int _iX, int _iY, int _iW, int _iH) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(_iX, _iY, _iX + Math.max(0, _iW), _iY + Math.max(0, _iH), paint);
    }

    /**
     * Draws the outline of a circular or elliptical arc covering the specified rectangle, using the current color and stroke style.<P>
     * The resulting arc begins at startAngle and extends for arcAngle degrees, using the current color.
     * Angles are interpreted such that 0 degrees is at the 3 o'clock position. A positive value indicates
     * a counter-clockwise rotation while a negative value indicates a clockwise rotation.<P>
     * The center of the arc is the center of the rectangle whose origin is (x, y) and whose size is specified by the width and height arguments.<P>
     * The resulting arc covers an area width + 1 pixels wide by height + 1 pixels tall. If either width or height is less than zero, nothing is drawn.<P>
     * The angles are specified relative to the non-square extents of the bounding rectangle such that 45 degrees always falls on the line
     * from the center of the ellipse to the upper right corner of the bounding rectangle. As a result, if the bounding rectangle is noticeably longer
     * in one axis than the other, the angles to the start and end of the arc segment will be skewed farther along the longer axis of the bounds.
     * @param _iX the x coordinate of the upper-left corner of the arc to be drawn
     * @param _iY the y coordinate of the upper-left corner of the arc to be drawn
     * @param _iW the width of the arc to be drawn
     * @param _iH the height of the arc to be drawn
     * @param _iStartAngle the beginning angle
     * @param _iArcAngle the angular extent of the arc, relative to the start angle
     * @see drawArc(int, int, int, int, int, int)
     */
    public void fillArc(int _iX, int _iY, int _iW, int _iH, int _iStartAngle, int _iArcAngle) {
        paint.setStyle(Paint.Style.FILL);
        RectF oval = new RectF(_iX, _iY, _iX + _iW, _iY + _iH);
        canvas.drawArc(oval, 360-_iStartAngle, -_iArcAngle, true, paint);
    }

    /**
     * Fills the specified rounded corner rectangle with the current color. If either width or height is zero or less, nothing is drawn.
     * @param _iX the x coordinate of the rectangle to be drawn
     * @param _iY the y coordinate of the rectangle to be drawn
     * @param _iW the width of the rectangle to be drawn
     * @param _iH the height of the rectangle to be drawn
     * @see drawRoundRect(int, int, int, int, int, int)
     */
    public void fillRoundRect(int _iX, int _iY, int _iW, int _iH) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iW, _iY + _iH), _iW, _iH, paint);
    }

    /**
     * Draws the outline of the specified rounded corner rectangle using the current color and stroke style.
     * The resulting rectangle will cover an area (width + 1) pixels wide by (height + 1) pixels tall.
     * If either width or height is less than zero, nothing is drawn.
     * @param _iX the x coordinate of the rectangle to be drawn
     * @param _iY the y coordinate of the rectangle to be drawn
     * @param _iW the width of the rectangle to be drawn
     * @param _iH the height of the rectangle to be drawn
     * @param _iArcW the horizontal diameter of the arc at the four corners
     * @param _iArcH the vertical diameter of the arc at the four corners
     * @see fillRoundRect(int, int, int, int, int, int)
     */
    public void fillRoundRect(int _iX, int _iY, int _iW, int _iH, int _iArcW, int _iArcH) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iW, _iY + _iH), _iArcW>>1, _iArcH>>1, paint);
    }

    /**
     * Fills a circle using the current color and stroke style. 
     * If either width or height is less than zero, nothing is drawn.
     * @param _iX the x coordinate of the center of the circle to be drawn
     * @param _iY the y coordinate of the center of the circle to be drawn
     * @param _iRadius the radius of the circle to be drawn
     * @see fillRoundRect(int, int, int, int, int, int)
     */
    public void fillCircle(int _iX, int _iY, int _iRadius) {
        paint.setStyle(Paint.Style.FILL);
        RectF rRect = new RectF(_iX - _iRadius, _iY - _iRadius, _iX + _iRadius, _iY + _iRadius);
        canvas.drawRoundRect(rRect, _iRadius, _iRadius, paint);
    }
   
    /**
     * Fills the specified triangle will the current color. The lines connecting each pair of points are included in the filled triangle.
     * @param _x0 the x coordinate of the first vertex of the triangle
     * @param _y0 the y coordinate of the first vertex of the triangle
     * @param _x1 the x coordinate of the second vertex of the triangle
     * @param _y1 the y coordinate of the second vertex of the triangle
     * @param _x2 the x coordinate of the third vertex of the triangle
     * @param _y2 the y coordinate of the third vertex of the triangle
     */
    public void fillTriangle(int _x0, int _y0, int _x1, int _y1, int _x2, int _y2) {
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(_x0, _y0);
        path.lineTo(_x1, _y1);
        path.lineTo(_x2, _y2);
        canvas.drawPath(path, paint);
    }

    /**
     * Draws the outline of the specified rectangle using the current color and stroke style. The resulting rectangle will cover an area (width + 1) pixels wide by (height + 1) pixels tall. If either width or height is less than zero, nothing is drawn.
     * @param _iX the x coordinate of the rectangle to be drawn
     * @param _iY the y coordinate of the rectangle to be drawn
     * @param _iW the width of the rectangle to be drawn
     * @param _iH the height of the rectangle to be drawn
     * @see fillRect(int, int, int, int)
     */
    public void drawRect(int _iX, int _iY, int _iW, int _iH) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(_iX, _iY, _iX + _iW + 1, _iY + _iH + 1, paint);
    }

    /**
     * Draws the outline of a circular or elliptical arc covering the specified rectangle, using the current color and stroke style.<P>
     * The resulting arc begins at startAngle and extends for arcAngle degrees, using the current color. 
     * Angles are interpreted such that 0 degrees is at the 3 o'clock position. A positive value indicates 
     * a counter-clockwise rotation while a negative value indicates a clockwise rotation.<P>
     * The center of the arc is the center of the rectangle whose origin is (x, y) and whose size is specified by the width and height arguments.<P>
     * The resulting arc covers an area width + 1 pixels wide by height + 1 pixels tall. If either width or height is less than zero, nothing is drawn.<P>
     * The angles are specified relative to the non-square extents of the bounding rectangle such that 45 degrees always falls on the line 
     * from the center of the ellipse to the upper right corner of the bounding rectangle. As a result, if the bounding rectangle is noticeably longer 
     * in one axis than the other, the angles to the start and end of the arc segment will be skewed farther along the longer axis of the bounds.
     * @param _iX the x coordinate of the upper-left corner of the arc to be drawn
     * @param _iY the y coordinate of the upper-left corner of the arc to be drawn
     * @param _iW the width of the arc to be drawn
     * @param _iH the height of the arc to be drawn
     * @param _iStartAngle the beginning angle
     * @param _iArcAngle the angular extent of the arc, relative to the start angle
     * @see fillArc(int, int, int, int, int, int)
     */
    public void drawArc(int _iX, int _iY, int _iW, int _iH, int _iStartAngle, int _iArcAngle) {
        paint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(_iX, _iY, _iX + _iW, _iY + _iH);
        canvas.drawArc(oval, 360-_iStartAngle, -_iArcAngle, true, paint);
    }

    /**
     * Draws the outline of the specified rounded corner rectangle using the current color and stroke style. 
     * The resulting rectangle will cover an area (width + 1) pixels wide by (height + 1) pixels tall. 
     * If either width or height is less than zero, nothing is drawn.
     * @param _iX the x coordinate of the rectangle to be drawn
     * @param _iY the y coordinate of the rectangle to be drawn
     * @param _iW the width of the rectangle to be drawn
     * @param _iH the height of the rectangle to be drawn
     * @see fillRoundRect(int, int, int, int, int, int)
     */
    public void drawRoundRect(int _iX, int _iY, int _iW, int _iH) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iW, _iY + _iH), _iW, _iH, paint);
    }

    /**
     * Draws the outline of the specified rounded corner rectangle using the current color and stroke style. 
     * The resulting rectangle will cover an area (width + 1) pixels wide by (height + 1) pixels tall. 
     * If either width or height is less than zero, nothing is drawn.
     * @param _iX the x coordinate of the rectangle to be drawn
     * @param _iY the y coordinate of the rectangle to be drawn
     * @param _iW the width of the rectangle to be drawn
     * @param _iH the height of the rectangle to be drawn
     * @param _iArcW the horizontal diameter of the arc at the four corners
     * @param _iArcH the vertical diameter of the arc at the four corners
     * @see fillRoundRect(int, int, int, int, int, int)
     */
    public void drawRoundRect(int _iX, int _iY, int _iW, int _iH, int _iArcW, int _iArcH) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iW, _iY + _iH), _iArcW>>1, _iArcH>>1, paint);
    }

    /**
     * Draws a circle using the current color and stroke style. 
     * If either width or height is less than zero, nothing is drawn.
     * @param _iX the x coordinate of the center of the circle to be drawn
     * @param _iY the y coordinate of the center of the circle to be drawn
     * @param _iRadius the radius of the circle to be drawn
     * @see fillRoundRect(int, int, int, int, int, int)
     */
    public void drawCircle(int _iX, int _iY, int _iRadius) {
        paint.setStyle(Paint.Style.STROKE);
        RectF rRect = new RectF(_iX - _iRadius, _iY - _iRadius, _iX + _iRadius, _iY + _iRadius);
        canvas.drawRoundRect(rRect, _iRadius, _iRadius, paint);
    }
   
    /**
     * Draws the specified triangle will the current color. The lines connecting each pair of points are included in the filled triangle.
     * @param _x0 the x coordinate of the first vertex of the triangle
     * @param _y0 the y coordinate of the first vertex of the triangle
     * @param _x1 the x coordinate of the second vertex of the triangle
     * @param _y1 the y coordinate of the second vertex of the triangle
     * @param _x2 the x coordinate of the third vertex of the triangle
     * @param _y2 the y coordinate of the third vertex of the triangle
     * @since MIDP 2.0
     */
    public void drawTriangle(int _x0, int _y0, int _x1, int _y1, int _x2, int _y2) {
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(_x0, _y0);
        path.lineTo(_x1, _y1);
        path.lineTo(_x2, _y2);
        canvas.drawPath(path, paint);
    }

    /**
     * Draws a line between the coordinates (x1,y1) and (x2,y2) using the current color and stroke style.
     * @param _iX0 the x coordinate of the start of the line
     * @param _iY0 the y coordinate of the start of the line
     * @param _iX1 the x coordinate of the end of the line
     * @param _iY1 the y coordinate of the end of the line
     */
    public void drawLine(int _iX0, int _iY0, int _iX1, int _iY1) {
        //vPaint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);        
        canvas.drawLine(_iX0, _iY0, _iX1, _iY1, paint);
    }

	
	public static final int CLAMP = 0;
	public static final int MIRROR = 1;
	public static final int REPEAT = 2;
	public void setShader(int _x, int _y, int _x2, int _y2, int[]_colors, float[]_positions, int _tileMode){
		switch(_tileMode){
			case CLAMP:
				paint.setShader(new LinearGradient(_x, _y, _x2, _y2, _colors, _positions, Shader.TileMode.CLAMP));
			break;
			case MIRROR:
				paint.setShader(new LinearGradient(_x, _y, _x2, _y2, _colors, _positions, Shader.TileMode.MIRROR));
			break;
			case REPEAT:
				paint.setShader(new LinearGradient(_x, _y, _x2, _y2, _colors, _positions, Shader.TileMode.REPEAT));
			break;
		}
		
	}
	
	
	public void cleanShader(){
		paint.setShader(null);
	}
	
	 /**
     * Set image scaling factor
     * <p>
     * The scale value ranges from 0.0f(0%) to 2.0f(200%). A scale value of 1.0f wont change the image size. 
     * @param _fScaleX scale factor in x (default value 1f)
     * @param _fScaleY scale factor in y (default value 1f) 
     */
	
    public void setImageSize (float _fScaleX, float _fScaleY) {
        if (_fScaleX < 0)
            _fScaleX = 0;
        if (_fScaleY < 0)
            _fScaleY = 0;

        screenX = _fScaleX;
        screenY = _fScaleY;
    }

	//Data fonts
	public static final float FNT_SIZE_SMALL = 16 * 1f;
	public static final float FNT_SIZE_MEDIUM = 24 * 1f;
	public static final float FNT_SIZE_LARGE = 32 * 1f;
	
	public void drawText(String _text, int _x, int _y, int _color) {
		//vPaint.setTextSize(FNT_SIZE_MEDIUM);
		paint.setColor(_color);
		
		canvas.drawText(_text, _x, _y, paint);
	}
	
	public void setTextSize(float _size){
		paint.setTextSize(_size);
	}
	
	public void drawText(String _text, int _x, int _y, float _size, int _color) {
		paint.setTextSize(_size);
	    paint.setColor(_color);
		
		canvas.drawText(_text, _x, _y, paint);
	}
	
	
	public int getTextHeight() {
		return (int) paint.getTextSize();
	}
	
	//Pinta en el canvas el array con la informacion RGB de colores:
	public void drawRGB(int[] _iColors, int _iOffset, int _iStride, int _iX, int _iY, int _iWidth, int _iHeight, boolean _isHasAlpha){
		canvas.drawBitmap(_iColors, _iOffset, _iStride, _iX, _iY, _iWidth, _iHeight, _isHasAlpha, paint);
	}
	
	public void setClip(int _iX, int _iY, int _iWidth, int _iHeight) {
       canvas.clipRect(_iX, _iY, _iX+_iWidth, _iY+_iHeight, Op.REPLACE);
    }
	
	public void clipRect(int _iX, int _iY, int _iWidth, int _iHeight){
		canvas.clipRect(_iX, _iY, _iX+_iWidth, _iY+_iHeight, Op.INTERSECT);
	}
	
	public int getClipX() {
        return canvas.getClipBounds().left;
    }
	
	public int getClipY() {
        return canvas.getClipBounds().top;
    }
	
	public int getClipWidth() {
		 return canvas.getClipBounds().width();
    }
	
	public int getClipHeight() {
		 return canvas.getClipBounds().height();
    }
	
//	private int convertX(int _x){
//		return (Settings.getRealWidth() * _x)/Settings.getWidth();
//	}
//	
//	private int convertY(int _x){
//		return (Settings.getRealHeight() * _x)/Settings.getHeight();
//	}
}




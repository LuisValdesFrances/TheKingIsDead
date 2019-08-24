package com.luis.lgameengine.gameutils.gameworld;

import com.luis.lgameengine.implementation.graphics.Image;

public class WorldConver3D extends WorldConver{
	
	private Image imgBuffer;
	private float distorsionX = 1f;
	private float distorsionY = 1f;

	public float getDistorsionX() {
		return distorsionX;
	}
	
	public float getDistorsionY() {
		return distorsionY;
	}
	
	public void setDistorsionX(float distorsion) {
		this.distorsionX = distorsion;
		this.distorsionY = 1f - (distorsionX-1f); 
	}
	
	public Image getImgBuffer() {
		return imgBuffer;
	}

	public WorldConver3D(int screenWidth, int screenHeight, int marginN,
			int marginS, int marginE, int marginW, float worldWidth,
			float worldHeight) {
		super(screenWidth, screenHeight, marginN, marginS, marginE, marginW,
				worldWidth, worldHeight);
		
		imgBuffer = Image.createImage(
				(int)getGameLayoutX(), 
				(int)getGameLayoutY());
	}

	public float getScale(GameCamera gameCamera, float y){
		
		//Rescalado maximo del objeto
		float scaleDistorionDifX = distorsionX -1f;
		float objectYInLay = getConversionDrawY(gameCamera.getPosY(), (int)y);
		float maxDistorsionPositionY = getCentlayoutY() + getLayoutY()/2;
		
		return 1f + (objectYInLay * scaleDistorionDifX) / maxDistorsionPositionY;
	}
	
	public float getModDistorsionX(float objectXInLay, float objectYInLay){
		
		//Diferencia del desplazamiento m�ximo del objeto respecto a la imagen no deformada
		float maxDistorisionDistance = (imgBuffer.getWidth()*distorsionX) - imgBuffer.getWidth();
		
		//Posicion abajo a la derecha: maximo punto de distorsion
		float layoutRoRight = getCentlayoutX() + getLayoutX()/2;
		float layoutToBotton = getCentlayoutY() + getLayoutY()/2;
		
		/**
		Regla de tres:
		* 
		* layoutToRight	=	maxDistorsionDistance
		* objectXInLay	=	x
		*/
		float modByDistX = (objectXInLay * maxDistorisionDistance) / layoutRoRight;
		
		/**
		 * Para la misma posici�n x la deformaci�n es menor cuanto menor sea la posici�n y del objeto
		 * (Est� mas arriba) Por este m�tivo, se genera una regla de tres donde la posici�n layoutToBotton
		 * se corresponde con la deformaci�n m�xima aplicada en el punto anterior:
		 * 
		 * layoutToBotton	=	1f
		 * objectYInLay		=	modDistY	
		 */
		float modByDistY = objectYInLay / layoutToBotton;
		
		return -(maxDistorisionDistance/2) * modByDistY +  modByDistX * modByDistY;
		
	}
	
	public float getModDistorsionY(float objectYInLay){
		float difLay = getLayoutY() - (getLayoutY()*distorsionY);
		return -((objectYInLay * difLay) / getLayoutY());
	}

}

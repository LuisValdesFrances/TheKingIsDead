/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luis.lgameengine.gameutils.gameworld;

/**
 * 
 * @author Luis Valdes Frances
 */
public class WorldConver {

	private float gameLayoutX;
	private float gameLayoutY;

	private float worldWidth;
	private float worldHeight;

	private float centGameLayoutX;
	private float centGameLayoutY;
	
	private int marginN;
	private int marginS;
	private int marginE;
	private int marginW;

	public WorldConver(int screenWidth, int screenHeight, 
			int marginN, int marginS, int marginE, int marginW, 
			float worldWidth, float worldHeight) {

		this.gameLayoutX = screenWidth - (marginE + marginW);
		this.gameLayoutY = screenHeight - (marginN + marginS);
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;

		this.centGameLayoutX = (this.gameLayoutX / 2) + marginW;
		this.centGameLayoutY = (this.gameLayoutY / 2) + marginN;
		
		this.marginN = marginN;
		this.marginS = marginS;
		this.marginE = marginE;
		this.marginW = marginW;
	}

	// Phisic methodes:
	public float getWorldWidth() {
		return worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}

	// DrawMethodes:
	
	/**
	 * Used to traverse a tiles array with target to paint. We only paint those tiles thats enter on the screen.
	 * @param _fCameraX: Position in axis x of camera
	 * @param _iNumberTilesX: Number of tiles of the array in x
	 * @param _iTileSize: Width of tile
	 * 
	 * @return 0: Starting index position. 1: Final index
	 * position Important: QUIT FIXER POINTS FOR ALL PHARAMETRES
	 */
	public int[] getIndexDrawX(float cameraX, int numberTilesX, int tileSize) {
		
		int iniX = 0;
		int finalX = numberTilesX;

		int indexCameraPos = (int) (cameraX / tileSize);
		iniX = (int) (indexCameraPos - ((gameLayoutX / 2) / tileSize)) - 1;
		finalX = (int) (indexCameraPos + ((gameLayoutX / 2) / tileSize)) + 2;

		// Si la camara se mueve a un extremo del mundo, seja de ser el centro de la pantalla. Cuando esto ocurre, hay un lado que disminuye y otro que aumenta:
		int exc = 0;
		if (iniX < 0) {
			exc = iniX * -1;
			iniX = 0;
		}
		finalX += exc;
		if (finalX > numberTilesX - 1) {
			exc = finalX - numberTilesX;

			iniX = (iniX - exc >= 0 ? iniX - exc : 0);
			finalX = numberTilesX;
		}
		return new int[] { iniX, finalX };
	}

	/**
	 * Used to traverse a tiles array with target to paint. We only paint those tiles thats enter on the screen.
	 * @param _fCameraX: Position in axis y of camera
	 * @param _iNumberTilesX: Number of tiles of the array in y
	 * @param _iTileSize: Width of tile
	 * 
	 * @return 0: Starting index position. 1: Final index
	 * position Important: QUIT FIXER POINTS FOR ALL PHARAMETRES
	 */
	public int[] getIndexDrawY(float cameraY, int numberTilesY, int tileSize) {
		
		int iniY = 0;
		int finalY = numberTilesY;

		int indexCameraPos = (int) (cameraY / tileSize);
		iniY = (int) (indexCameraPos - ((gameLayoutY / 2) / tileSize)) - 1;
		finalY = (int) (indexCameraPos + ((gameLayoutY / 2) / tileSize)) + 2;

		// Si la camara se mueve a un extremo del mundo, seja de ser el centro de la pantalla. Cuando esto ocurre, hay un lado que disminuye y otro que aumenta:
		int exc = 0;
		if (iniY < 0) {
			exc = iniY * -1;
			iniY = 0;
		}
		finalY += exc;
		if (finalY > numberTilesY - 1) {
			exc = finalY - numberTilesY;

			iniY = (iniY - exc >= 0 ? iniY - exc : 0);

			finalY = numberTilesY;
		}
		return new int[] { iniY, finalY };
	}

	public int getConversionDrawX(float cameraX, float objectPosX) {

		int posDrawX;
		float camX = cameraX;
		//posDrawX = (int) (marginW + gameLayoutX/2 + (objectPosX - camX));// ****P****
		posDrawX = (int) (marginW + (objectPosX - camX));// ****P****
		return posDrawX;
	}

	public int getConversionDrawY(float cameraY, float objectPosY) {
		
		int posDrawY;
		float camY = cameraY;
		//posDrawY = (int) (marginN + gameLayoutY/2 + (objectPosY - camY));// ****P****
		posDrawY = (int) (marginN + (objectPosY - camY));// ****P****
		return posDrawY;
	}

	// Casting to paint position for camera:
	private int getConversionDrawX(float cameraX) {
		
		float excededX = 0;
		if (cameraX > worldWidth - ((gameLayoutX / 2))) {
			excededX = cameraX - (worldWidth - (gameLayoutX / 2));// *******P*
		} else if (cameraX < (gameLayoutX / 2)) {
			excededX = cameraX - (gameLayoutX / 2);// *P*******
		}
		return (int) (centGameLayoutX + excededX);
	}

	private int getConversionDrawY(int cameraY) {
		
		float excededY = 0;
		if (cameraY > worldHeight - (gameLayoutY / 2)) {
			excededY = cameraY - (worldHeight - (gameLayoutY / 2));// *******P*
		} else if (cameraY < (gameLayoutY / 2)) {
			excededY = cameraY - (gameLayoutY / 2);// *P*******
		}
		return (int) (centGameLayoutY + excededY);
	}

	// A partir de unas cordenadas de pantalla, devuelve las coordenadas x-y del del mundo:
	public float getConversionLogicX(float cameraX, int posScrX) {
		
		return cameraX + (posScrX - getConversionDrawX((int) cameraX));
	}

	public float getConversionLogicY(float _fCameraY, long _iPosScrY) {
		
		return _fCameraY + (_iPosScrY - getConversionDrawY((int) _fCameraY));
	}

	/**
	 * Devuelve si un punto esta dentro de la pantalla
	 * @param cameraX
	 * @param cameraY
	 * @param objectX
	 * @param objectY
	 * @return
	 */
	public boolean isPointInGameLayout(float cameraX, float cameraY, float objectX, float objectY) {
		int posDrawX = getConversionDrawX(cameraX, objectX);
		int posDrawY = getConversionDrawY(cameraY, objectY);
		if (posDrawX <= centGameLayoutX + (gameLayoutX / 2) 
				&& posDrawX >= centGameLayoutX - (gameLayoutX / 2)
				&& posDrawY <= centGameLayoutY + (gameLayoutY / 2)
				&& posDrawY >= centGameLayoutY - (gameLayoutY / 2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Devuelve si un objeto con anclaje Arriba-Izq esta dentro de la pantalla
	 * @param cameraX
	 * @param cameraY
	 * @param objectX
	 * @param objectY
	 * @param _fObjectW
	 * @param _fObjectH
	 * @return
	 */
	public boolean isObjectInGameLayout(float cameraX, float cameraY, float objectX, float objectY, float objetW, float objetH) {
		int posDrawX = getConversionDrawX(cameraX, objectX);
		int posDrawY = getConversionDrawY(cameraY, objectY);
		if (
				posDrawX <= centGameLayoutX + (gameLayoutX / 2) && 
				posDrawX + objetW >= centGameLayoutX - (gameLayoutX / 2) && 
				posDrawY <= centGameLayoutY + (gameLayoutY / 2) && 
				posDrawY + objetH >= centGameLayoutY - (gameLayoutY / 2)) {
			return true;
		} else {
			return false;
		}
	}

	public float getLayoutX() {
		return gameLayoutX;
	}

	public float getLayoutY() {
		return gameLayoutY;
	}

	public float getCentlayoutX() {
		return centGameLayoutX;
	}

	public float getCentlayoutY() {
		return centGameLayoutY;
	}

	public int getMarginN() {
		return marginN;
	}

	public float getGameLayoutX() {
		return gameLayoutX;
	}

	public float getGameLayoutY() {
		return gameLayoutY;
	}

	public float getCentGameLayoutX() {
		return centGameLayoutX;
	}

	public float getCentGameLayoutY() {
		return centGameLayoutY;
	}

	public int getMarginS() {
		return marginS;
	}

	public int getMarginE() {
		return marginE;
	}

	public int getMarginW() {
		return marginW;
	}

	

	
	
	

}

package com.luis.lgameengine.gameutils.gameworld;

import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;

/**
 *
 * @author Luis Valdes Frances
 */
public class SpriteImage {

	/**
	 * Esta clase gestiona el dibujado de imagenes a modo de sprites animados de
	 * forma simple.
	 *
	 */
	private int[] width;
	private int[] height;
	private int totalWidth;
	private int totalHeight;
	// Frame logic
	private int[] frame;// Frame de la animación
	private int fileIndex;// Animacion (Cada fila es una nueva animacion)
	private int[] numberFrames;

	// Contador de animaciones
	private int[] animationCounter;

	// Controla en milisegundos el tiempo de actualización del frame
	private float[] timeUpdate;
	private float[] currentTime;
	/*
	 * Cuando se trata de imagenes con varias filas de sprites, cada una puede
	 * medir un alto diferente. La referencias son.
	 *
	 * iModFileH guarda la posicion desde donde debe de empezar a pintarse cada
	 * fila respecto a la imagen total.
	 */
	private int[] modFileH;
	public static final int HCENTER = 1;
	public static final int VCENTER = 2;
	public static final int LEFT = 4;
	public static final int RIGHT = 8;
	public static final int TOP = 16;
	public static final int BOTTOM = 32;
	public static final int BASELINE = 64;
	public static final int HFLIP = 128;
	public static final int VFLIP = 256;

	/**
	 * Frm 0 Frm 1 Frm 2 File 0: X X X File 1: X X X File 2: X X X Colum 0 Colum
	 * 1 Colum 2
	 */

	private boolean isEndAnimation;

	public SpriteImage(int imageWidth, int imageHeight,
					   float timeMilisUpdate, int numberFrames) {

		this.width = new int[1];
		this.width[0] = (int) imageWidth / numberFrames;
		this.height = new int[1];
		this.height[0] = imageHeight;
		this.timeUpdate = new float[1];
		this.timeUpdate[0] = timeMilisUpdate;
		this.currentTime = new float[1];
		this.currentTime[0] = 0;
		this.modFileH = new int[1];
		this.modFileH[0] = 0;

		this.numberFrames = new int[1];
		this.numberFrames[0] = numberFrames;
		this.frame = new int[1];
		this.frame[0] = 0;

		this.fileIndex = 0;
		this.isEndAnimation = false;

		this.animationCounter = new int[1];
	}

	/**
	 * Cada fila es una animacion distinta
	 *
	 * @param frameWidth
	 * @param frameHeight
	 * @param timeMilisUpdate
	 * @param numberFrames
	 */
	public SpriteImage(int[] frameWidth, int frameHeight[],
					   float[] timeMilisUpdate, int[] numberFrames) {

		this.width = new int[numberFrames.length];
		this.height = new int[numberFrames.length];
		this.timeUpdate = new float[numberFrames.length];
		this.currentTime = new float[numberFrames.length];
		this.numberFrames = new int[numberFrames.length];
		this.frame = new int[numberFrames.length];

		this.modFileH = new int[numberFrames.length];

		this.animationCounter = new int[numberFrames.length];

		// Obtenemos la anchura de cada una de las imaganes de cada fila
		int maxWidth = 0;
		int modHeight = 0;
		for (int i = 0; i < numberFrames.length; i++) {

			if (frameWidth[i] * numberFrames[i] >= maxWidth) {
				maxWidth = frameWidth[i] * numberFrames[i];
			}

			this.modFileH[i] = modHeight;
			modHeight += frameHeight[i];

			this.width[i] = frameWidth[i];
			this.height[i] = frameHeight[i];
			this.timeUpdate[i] = timeMilisUpdate[i];
			this.currentTime[i] = 0;

			this.numberFrames[i] = (byte) numberFrames[i];
			this.frame[i] = 0;
		}
		this.fileIndex = 0;
		this.isEndAnimation = false;
	}

	/**
	 * Cada fila es una animacion distinta y cada frame tiene su duracion propia
	 *
	 * @param imageWidth
	 * @param imageHeight
	 * @param timeMilisUpdate
	 * @param numberFrames
	 */
	public SpriteImage(int[] imageWidth, int imageHeight[],
					   float[][] timeMilisUpdate, int[] numberFrames) {
	}

	public void updateAnimation(float deltaTime) {

		currentTime[fileIndex] += deltaTime;
		isEndAnimation = false;

		if (currentTime[fileIndex] >= timeUpdate[fileIndex]) {
			frame[fileIndex]++;

			if (frame[fileIndex] == numberFrames[fileIndex]) {
				isEndAnimation = true;
				frame[fileIndex] = 0;
				animationCounter[fileIndex]++;
			}
			currentTime[fileIndex] = 0;
		}
	}

	public void resetAnimation(int _iFileIndex) {
		frame[_iFileIndex] = 0;
		isEndAnimation = false;
		frame[fileIndex] = 0;
		animationCounter[fileIndex] = 0;
		currentTime[fileIndex] = 0;
	}

	public void setFileIndex(int _iNewFileIndex) {

		// Reset current file index:
		if (_iNewFileIndex != fileIndex) {
			frame[fileIndex] = 0;
			currentTime[fileIndex] = 0;
		}
		fileIndex = _iNewFileIndex;
	}

	public int getFileIndex() {
		return fileIndex;
	}

	public void drawFrame(Graphics g, Image _vImage, int posX, int posY,
						  float scaleX, float scaleY,
						  boolean flipH, boolean flipV, int anchor) {
		draw(g, _vImage, posX, posY, scaleX, scaleY, fileIndex, anchor, flipH, flipV);

	}

	private void draw(Graphics g, Image image, int posX, int posY, float scaleX, float scaleY, int index,
					  int anchor,
					  boolean flipH, boolean flipV) {
		/*
		if(flip){
			int modFlip = width[fileIndex];
			g.drawImage(
					image,
					posX + modPosFrame(frame[fileIndex], index) + modFlip,
					posY - modFileH[fileIndex],
					Graphics.HFLIP);
		}else{
			//g.drawImage(image, posX - modPosFrame(frame[fileIndex], index), posY - modFileH[fileIndex], 0);
		}
		*/
		g.drawRegion(
				image,
				modPosFrame(frame[fileIndex], index),
				modFileH[fileIndex],//ojo
				width[fileIndex], height[fileIndex],
				posX, posY,
				(int)(width[fileIndex]*scaleX), (int)(height[fileIndex]*scaleY),
				anchor,
				0, 0, 0,
				flipH, flipV);

	}

	private int modPosFrame(int frame, int _iIndex) {
		return (width[_iIndex] * frame);
	}

	public void putNextFrame() {
		frame[fileIndex]++;
		if (frame[fileIndex] == numberFrames[fileIndex]) {
			frame[fileIndex] = 0;
		}
	}

	public float getTimeUpdame() {
		return timeUpdate[fileIndex];
	}

	public void setTimeUpdate(int index, float timeUpdate) {
		this.timeUpdate[index] = timeUpdate;
	}

	public void setTimeUpdate(float timeUpdate) {
		this.timeUpdate[fileIndex] = timeUpdate;
	}

	public void setFrame(int frame) {
		this.frame[fileIndex] = (byte) frame;
	}

	public int getFrame() {
		return frame[fileIndex];
	}

	public void setFrame(int index, int frame) {
		this.frame[index] = (byte) frame;
	}

	public int getFrame(int index) {
		return frame[index];
	}

	public int getWidth() {
		return width[fileIndex];
	}

	public int getHeight() {
		return height[fileIndex];
	}

	public int getWidth(int index) {
		return width[index];
	}

	public int getHeight(int index) {
		return height[index];
	}

	public int getTotalWidth() {
		return totalWidth;
	}

	public void setTotalWidth(int totalWidth) {
		this.totalWidth = totalWidth;
	}

	public int getTotalHeight() {
		return totalHeight;
	}

	public void setTotalHeight(int totalHeight) {
		this.totalHeight = totalHeight;
	}

	public boolean isEndAnimation() {
		return isEndAnimation;
	}

	public int getAnimationCounter(int index) {
		return animationCounter[index];
	}
}

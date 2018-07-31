package com.luis.lgameengine.gui;

public abstract class MenuElement {

	private int x;
	private int y;
	
	protected int width;
	protected int height;
	
	public static int alpha = 100;
	
	public static int bgAlpha;
	
	protected int screenWidth;
	protected int screenHeight;
	
	public MenuElement(int screenWidth, int screenHeight) {
		super();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

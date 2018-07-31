package com.luis.lgameengine.implementation.input;

public class TouchData {
	
	public static final int ACTION_UP = 0;
	public static final int ACTION_DOWN = 1;
	public static final int ACTION_MOVE = 2;
	
	private int action;
	private int x;
	private int y;
	private int originX;
	private int originY;
	private int pointer;
	
	private int distanceX;
	private int distanceY;
	private int frames;
	
	
	
	public TouchData() {
		reset();
	}
	
	public void setData(TouchData touchData){
		setPointer(touchData.getPointer());
		setAction(touchData.getAction());
		setX(touchData.getX());
		setY(touchData.getY());
		setOriginX(touchData.getOriginX());
		setOriginY(touchData.getOriginY());
		setDistanceX(touchData.getDistanceX());
		setDistanceY(touchData.getDistanceY());
		setFrames(touchData.getFrames());
	}
	
	public void reset(){
		setPointer(-1);
		setAction(ACTION_UP);
		setX(0);
		setY(0);
		setOriginX(0);
		setOriginY(0);
		setDistanceX(0);
		setDistanceY(0);
		setFrames(0);
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
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

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public int getDistanceX() {
		return distanceX;
	}

	public void setDistanceX(int distanceX) {
		this.distanceX = distanceX;
	}

	public int getDistanceY() {
		return distanceY;
	}

	public void setDistanceY(int distanceY) {
		this.distanceY = distanceY;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}
	
	
	
	
}

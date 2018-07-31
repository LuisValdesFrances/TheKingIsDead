package com.luis.lgameengine.implementation.input;

public class KeyData {
	
	public static final int KEY_UP = 0;
	public static final int KEY_DOWN = 1;
	public static final int KEY_PRESS = 2;
	
	private int action;
	private int keyCode;
	private char keyChar;
	private int frames;
	
	public void setData(KeyData buttonData){
		setAction(buttonData.getAction());
		setKeyCode(buttonData.getKeyCode());
		setKeyChar(getKeyChar());
		setFrames(buttonData.getFrames());
	}
	
	public void reset(){
		setAction(KEY_UP);
		setKeyCode(0);
		setKeyChar('0');
		setFrames(0);
	}
	
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	public char getKeyChar() {
		return keyChar;
	}
	public void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}
	
	

}

package com.luis.strategy;


import com.luis.lgameengine.implementation.input.KeyboardHandler;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;

public class UserInput {
	
	private MultiTouchHandler multiTouchHandler;
	public MultiTouchHandler getMultiTouchHandler(){
		return multiTouchHandler;
	}
	
	private KeyboardHandler keyboardHandler;
	public KeyboardHandler getKeyboardHandler(){
		return keyboardHandler;
	}
	
	public static final int KEYCODE_UP = 19;
	public static final int KEYCODE_DOWN = 20;
	public static final int KEYCODE_LEFT = 21;
	public static final int KEYCODE_RIGHT = 22;
	public static final int KEYCODE_FIRE = 23;
	public static final int KEYCODE_NUM2 = 50;
	public static final int KEYCODE_NUM8 = 56;
	public static final int KEYCODE_NUM9 = 57;
	public static final int KEYCODE_NUM4 = 52;
	public static final int KEYCODE_NUM6 = 54;
	public static final int KEYCODE_NUM5 = 12;
	public static final int KEYCODE_SK_LEFT = -6;
	public static final int KEYCODE_SK_RIGHT = -7;
	public static final int KEYCODE_CLEAR = -8;
	public static final int KEYCODE_CALL = 5;
	public static final int KEYCODE_ENDCALL = 6;
	public static final int KEYCODE_ENTER = 66;
	
	//Controls for pads
	public static final int KEYCODE_SHIELD_A = 96;
	public static final int KEYCODE_SHIELD_B = 97;
	public static final int KEYCODE_SHIELD_X = 99;
	public static final int KEYCODE_SHIELD_Y = 100;
	
	public static UserInput userInput;
	public static UserInput getInstance(){
		if(userInput == null)
			userInput = new UserInput();
		return userInput;
	}
	
	public void init(MultiTouchHandler multiTouchHandler, KeyboardHandler keyboardHandler){
		this.multiTouchHandler = multiTouchHandler;
		this.multiTouchHandler.resetTouch();
		this.keyboardHandler = keyboardHandler;
		this.keyboardHandler.resetKeys();
	}
	
	
	/*
    public void putTouchDistance(int _iPoint){
    	multiTouchHandler.setTouchDistanceX(multiTouchHandler.getTouchX() - multiTouchHandler.getTouchOriginX());
    	multiTouchHandler.setTouchDistanceY(multiTouchHandler.getTouchY() - multiTouchHandler.getTouchOriginY());
    }
    */
    
    public boolean compareTouch(int _iX0, int _iY0, int _iX1, int _iY1, int _iPoint) {
        if ((multiTouchHandler.getTouchX(0) > _iX0
                && multiTouchHandler.getTouchX(0) < _iX1)
                && (multiTouchHandler.getTouchY(0) > _iY0
                && multiTouchHandler.getTouchY(0) < _iY1)) {
        	return true;
        } else {
            return false;
        }
    }
}

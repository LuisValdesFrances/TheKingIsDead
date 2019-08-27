package com.luis.lgameengine.implementation.input;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MultiTouchHandler implements OnTouchListener {

	public static final int NUMBER_POINTS = 5;

	public MultiTouchHandler(View view, float _scaleX, float _scaleY) {
		view.setOnTouchListener(this);
		this.scaleX = _scaleX;
		this.scaleY = _scaleY;
	}

	float scaleX;
	float scaleY;

	public static boolean isTouchingScreen = false;

	private TouchData[] touchList;

	private int[] x;
	private int[] y;
	private int[] originX;
	private int[] originY;
	private int[] action;

	public void resetTouch() {

		isTouchingScreen = false;
		touchList = new TouchData[NUMBER_POINTS];
		for (int i = 0; i < NUMBER_POINTS; i++) {
			touchList[i] = new TouchData();
			touchList[i].reset();
		}

		x = new int[NUMBER_POINTS];
		y = new int[NUMBER_POINTS];
		originX = new int[NUMBER_POINTS];
		originY = new int[NUMBER_POINTS];
		action = new int[NUMBER_POINTS];
	}

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {

		synchronized(this) {

			int pointerIndex = event.getActionIndex();
			int pointerId = event.getPointerId(pointerIndex);

			if(pointerId >= NUMBER_POINTS)
				return false;

			switch (event.getActionMasked()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:

					//touchList[pointerId].reset();
					touchList[pointerId].setPointer(pointerId);
					touchList[pointerId].setX((int) (event.getX(pointerIndex) * scaleX));
					touchList[pointerId].setY((int) (event.getY(pointerIndex) * scaleY));
					touchList[pointerId].setOriginX(touchList[pointerId].getX());
					touchList[pointerId].setOriginY(touchList[pointerId].getY());
					touchList[pointerId].setAction(TouchData.ACTION_DOWN);
					break;
				case MotionEvent.ACTION_MOVE:
	            	/*
	            	Explicacion de porque se itera
	            	El problema:
	            	https://stackoverflow.com/questions/28417492/android-multi-touch-pointers-with-index-0-not-triggering-event-action-move
	            	La solucion:
	            	https://stackoverflow.com/questions/9028357/android-multitouch-second-finger-action-move-ignored
	            	 */
					for(int i = 0; i < event.getPointerCount(); ++i) {
						pointerIndex = i;
						pointerId = event.getPointerId(pointerIndex);

						touchList[pointerId].setPointer(pointerId);
						touchList[pointerId].setX((int) (event.getX(pointerIndex) * scaleX));
						touchList[pointerId].setY((int) (event.getY(pointerIndex) * scaleY));
						touchList[pointerId].setAction(TouchData.ACTION_MOVE);
					}
					break;

				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:

					touchList[pointerId].setPointer(pointerId);
					touchList[pointerId].setX((int) (event.getX(pointerIndex) * scaleX));
					touchList[pointerId].setY((int) (event.getY(pointerIndex) * scaleY));
					touchList[pointerId].setAction(TouchData.ACTION_UP);
					break;

			}
			return true;
		}
	}

	public void update() {

		for (int i = 0; i < touchList.length; i++) {
			if (touchList[i].getAction() == TouchData.ACTION_MOVE) {
				touchList[i].setDistanceX(touchList[i].getOriginX()- touchList[i].getX());
				touchList[i].setDistanceY(touchList[i].getOriginY()- touchList[i].getY());
			}
		}

		for (int i = 0; i < touchList.length; i++) {
			if (touchList[i].getAction() == TouchData.ACTION_DOWN || touchList[i].getAction() == TouchData.ACTION_MOVE) {
				touchList[i].setFrames(touchList[i].getFrames()+1);
			} else {
				touchList[i].setFrames(0);
			}
		}
		for (int i = 0; i < touchList.length; i++) {
			x[i] = touchList[i].getX();
			y[i] = touchList[i].getY();
			originX[i] = touchList[i].getX();
			originY[i] = touchList[i].getY();
			action[i] = touchList[i].getAction();
		}
	}


	public int getTouchAction(int index){
		return touchList[index].getAction();
	}
	public int getTouchFrames(int index){
		return touchList[index].getFrames();
	}
	public int getTouchOriginX(int index){
		return touchList[index].getOriginX();
	}
	public int getTouchOriginY(int index){
		return touchList[index].getOriginY();
	}
	public int getTouchX(int index){
		return touchList[index].getX();
	}
	public int getTouchY(int index){
		return touchList[index].getY();
	}
	public int getTouchDistanceX(int index){
		return touchList[index].getDistanceX();
	}
	public int getTouchDistanceY(int index){
		return touchList[index].getDistanceY();
	}

	public int[] getTouchAction(){
		return action;
	}
	public int[] getTouchOriginX(){
		return originX;
	}
	public int[] getTouchOriginY(){
		return originY;
	}
	public int[] getTouchX(){
		return x;
	}
	public int[] getTouchY(){
		return y;
	}

	public boolean isTouchingScreen(){
		for(int i = 0; i < touchList.length; i++){
			if(touchList[i].getAction() == TouchData.ACTION_DOWN || touchList[i].getAction() == TouchData.ACTION_MOVE){
				return true;
			}
		}
		return false;
	}
}
package com.luis.lgameengine.implementation.input;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MultiTouchHandlerBuffer implements OnTouchListener {
	
	public static final int NUMBER_POINTS = 5;
	public static final int BUFFER_SIZE = 128;
	
	private static MultiTouchHandlerBuffer multiTouchHandler;
	
	public static MultiTouchHandlerBuffer getInstance(View view, float _scaleX, float _scaleY){
		if(multiTouchHandler == null){
			multiTouchHandler = new MultiTouchHandlerBuffer(view, _scaleX, _scaleY);
		}
		return multiTouchHandler;
	}
	
	private MultiTouchHandlerBuffer(View view, float _scaleX, float _scaleY) {
	    view.setOnTouchListener(this);
	    this.scaleX = _scaleX;
        this.scaleY = _scaleY;
    }
	
	float scaleX;
	float scaleY;
	
	public static boolean isTouchingScreen = false;

	//private List<TouchData> touchList;
	private List<TouchData> touchBuffer;
	private TouchData touchData;
	private TouchData touchResponse;
	
	private int[] touchAction;
	private int[] touchFrames;
	private int[] touchX;
	private int[] touchY;
	private int[] touchOriginX;
	private int[] touchOriginY;
	private int[] touchDistanceX;
	private int[] touchDistanceY;
	//private int[] lastTouchX;
	//private int[] lastTouchY;
	
	
	public void resetTouch() {

		isTouchingScreen = false;
		touchData = new TouchData();
		//touchList = new ArrayList<TouchData>();
		touchBuffer = new ArrayList<TouchData>();
		touchAction = new int[NUMBER_POINTS];
		touchFrames = new int[NUMBER_POINTS];
		touchX = new int[NUMBER_POINTS];
		touchY = new int[NUMBER_POINTS];
		touchOriginX = new int[NUMBER_POINTS];
		touchOriginY = new int[NUMBER_POINTS];
		touchDistanceX = new int[NUMBER_POINTS];
		touchDistanceY = new int[NUMBER_POINTS];
		//lastTouchX = new int[NUMBER_POINTS];
		//lastTouchY = new int[NUMBER_POINTS];
		///*
		for (int i = 0; i < BUFFER_SIZE; i++) {
			touchBuffer.add(new TouchData());
		}
		//*/
	}
    
    @SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
    	
        synchronized(this) {
        	
        	//Obtengo el primer elemento
        	int action = event.getAction() & MotionEvent.ACTION_MASK;
        	int pointerIndex = (event.getAction() & 65280) >> 8;
        	int pointerId = (int) event.getPointerId(pointerIndex);
        	
        	switch (action) {
	            case MotionEvent.ACTION_DOWN:
	            case MotionEvent.ACTION_POINTER_DOWN:
	            	
	            	touchData.reset();
	            	touchData.setPointer(pointerId);
	        		touchData.setX((int) (event.getX(pointerIndex) * scaleX));
	        		touchData.setY((int) (event.getY(pointerIndex) * scaleY));
	        		touchData.setOriginX(touchData.getX());
	        		touchData.setOriginY(touchData.getY());
	        		touchData.setAction(TouchData.ACTION_DOWN);
	        		addToList();
	                break;
	            case MotionEvent.ACTION_MOVE:
	            	
	            	touchData.setPointer(pointerId);
	            	touchData.setX((int) (event.getX(pointerIndex) * scaleX));
	            	touchData.setY((int) (event.getY(pointerIndex) * scaleY));
	            	touchData.setAction(TouchData.ACTION_MOVE);
	        		addToList();
	                break;
	                
	            case MotionEvent.ACTION_POINTER_UP:
	            case MotionEvent.ACTION_CANCEL:                
	            case MotionEvent.ACTION_UP:
	            	
	            	touchData.setPointer(pointerId);
	            	touchData.setX((int) (event.getX(pointerIndex) * scaleX));
	            	touchData.setY((int) (event.getY(pointerIndex) * scaleY));
	            	touchData.setAction(TouchData.ACTION_UP);
	        		addToList();
	        		break;
	           
            }
            return true;
        }
    }
    
    private void addToList(){
    	for(int i = touchBuffer.size()-1; i > -1 ; i--){
    		if(touchBuffer.get(i).getPointer() == -1){
    			touchBuffer.get(i).setData(touchData);
    			return;
    		}
    	}
    	resetTouch();
    	/*
    	TouchData td = new TouchData();
    	td.setData(touchData);
    	touchBuffer.add(td);
    	*/
    }
    
    /*
    private void moveElementsList(){
    	//Copio todos los datos al buffer
    	for(int i = 0; i < touchList.length; i++){
    		touchBuffer[i].setData(touchList[i]);
    	}
    	//Desplazo una posicion
    	for(int i = 0; i < touchList.length-1; i++){
    		touchList[i+1].setData(touchBuffer[i]);
    	}
    	//Dejo libre el ultimo
    	touchList[0].reset();
    }
    */
    
    public void update(){
    	
    	if(touchBuffer.size() > 0 && touchBuffer.get(touchBuffer.size()-1).getPointer() != -1){
    		touchResponse = new TouchData();
    		touchResponse.setData(touchBuffer.get(touchBuffer.size()-1));
	    	touchAction[touchResponse.getPointer()] = touchResponse.getAction();
	        touchX[touchResponse.getPointer()] = touchResponse.getX();
	        touchY[touchResponse.getPointer()] = touchResponse.getY();
	        touchOriginX[touchResponse.getPointer()] = touchResponse.getOriginX();
	        touchOriginY[touchResponse.getPointer()] = touchResponse.getOriginY();
	        
	        
	        for(int i = 0; i < touchAction.length; i++){
	        	if(touchAction[i] == TouchData.ACTION_MOVE){
			        touchDistanceX[i] = touchOriginX[i]-touchX[i];
			        touchDistanceY[i] = touchOriginY[i]-touchY[i];
	        	}
	        }
	        
	    	for(int i = 0; i < touchFrames.length; i++){
	    		if(touchAction[i] == TouchData.ACTION_DOWN || touchAction[i] == TouchData.ACTION_MOVE){
	    			touchFrames[i]++;
	    		}else{
	    			touchFrames[i]=0;
	    		}
	    	}
	    	
	    	//if(touchResponse.getAction() == TouchData.ACTION_UP){
		    	for(int i = touchBuffer.size()-1; i >= 1 ; i--){
		    		touchBuffer.get(i).setData(touchBuffer.get(i-1));
		    	}
		    	touchBuffer.get(0).reset();
	    	//}
    		}
    	}
    
    public int getTouchAction(int index){
    	return touchAction[index];
    }
    
    public int getTouchFrames(int index){
    	return touchFrames[index];
    }
    
    public int getTouchOriginX(int index){
    	return touchOriginX[index];
    }
    
    public int getTouchOriginY(int index){
    	return touchOriginY[index];
    }
    
    public int getTouchX(int index){
    	return touchX[index];
    }
    
    public int getTouchY(int index){
    	return touchY[index];
    }
    
    public int getTouchDistanceX(int index){
    	return touchDistanceX[index];
    }
    
    public int getTouchDistanceY(int index){
    	return touchDistanceY[index];
    }
    
    public int[] getTouchAction(){
    	return touchAction;
    }
    
    public int[] getTouchFrames(){
    	return touchFrames;
    }
    
    public int[] getTouchOriginX(){
    	return touchOriginX;
    }
    
    public int[] getTouchOriginY(){
    	return touchOriginY;
    }
    
    public int[] getTouchX(){
    	return touchX;
    }
    
    public int[] getTouchY(){
    	return touchY;
    }
    
    public int[] getTouchDistanceX(){
    	return touchDistanceX;
    }
    
    public int[] getTouchDistanceY(){
    	return touchDistanceY;
    }
    
    public int getBufferSize(){
    	return touchBuffer.size();
    }
    
    public boolean isTouchingScreen(){
    	for(int i = 0; i < touchAction.length; i++){
    		if(touchAction[i] == TouchData.ACTION_DOWN || touchAction[i] == TouchData.ACTION_MOVE){
    			return true;
    		}
    	}
    	return false;
    }
}

package com.luis.lgameengine.implementation.input;

import android.view.View;
import android.view.View.OnKeyListener;

public class KeyboardHandler implements OnKeyListener { 
	
	public static final int BUFFER_SIZE = 20;
	
	private static KeyboardHandler keyboardHandler;
	
	public static KeyboardHandler getInstance(View view){
		if(keyboardHandler == null){
			keyboardHandler = new KeyboardHandler(view);
		};
		return keyboardHandler;
	}
	
	public KeyData[] keyList;
	
	private KeyboardHandler(View view){
		view.setOnKeyListener(this);
	}
	
	public KeyData getPressedKeys(int keyCode) {
		return keyList[keyCode];
	}

	public void resetKeys(){
		keyList = new KeyData[128];
		for (int i = 0; i < 128; i++) {
			keyList[i] = new KeyData();
		}
	}
	
	 public void update(){
		 for (int i = 0; i < keyList.length; i++) {
			 if(keyList[i].getAction() == KeyData.KEY_DOWN || keyList[i].getAction() == KeyData.KEY_PRESS){
				 if(keyList[i].getFrames() > 0){
					 keyList[i].setAction(KeyData.KEY_PRESS);
				 }
				 keyList[i].setFrames(keyList[i].getFrames()+1);
			 }else{
				 keyList[i].setFrames(0);
			 }
		 }
	 }
	
	@Override
	public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
		/*
		if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;
        */
		synchronized (this) {
           if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
        	   if(keyCode > 0 && keyCode < 127)
                	keyList[keyCode].setAction(KeyData.KEY_DOWN);
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
            	if(keyCode > 0 && keyCode < 127)
                	keyList[keyCode].setAction(KeyData.KEY_UP);
            }
            //Controlamos el botón back para que no salga de la aplicacion:
//            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
//            	return true;
//            }
            return true;
        }
    }
	
	/*
	private void addToList(KeyData keyData){
    	moveElementsList();
    	for(int i = keyList.length-1; i >= 0; i--){
    		if(keyList[i].getAction() == KeyData.NO_ACTION){
    			keyList[i].setData(keyData);
    			return;
    		}
    	}
    }

	private void moveElementsList(){
    	//Copio todos los datos al buffer
    	for(int i = 0; i < keyList.length; i++){
    		keyBuffer[i].setData(keyList[i]);
    	}
    	//Desplazo una posicion
    	for(int i = 0; i < keyList.length-1; i++){
    		keyList[i+1].setData(keyBuffer[i]);
    	}
    	//Dejo libre el ultimo
    	keyList[0].reset();
    }
    */
	
}

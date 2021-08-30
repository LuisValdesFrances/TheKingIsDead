package com.luis.lgameengine.gui;

import java.io.Serializable;
import java.util.ArrayList;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.lgameengine.implementation.sound.SndManager;

public class ListBox extends MenuBox{
	
	protected boolean scroll;
	
	public ListBox(
			int screenWidth, int screenHeight,
			Image imgBox, Image imgRelease, Image imgFocus,
			int x, int y,
			String textHeader,
			String[] textOptions,
			int fontHeader,
			int fontOptions, 
			final int onDownSoundIndex, final int onUpSoundIndex) {
		super(
				screenWidth, screenHeight, 
				imgBox, imgRelease, imgFocus,
				x, y,
				textHeader,
				null,
				fontHeader,
				fontOptions,
				onDownSoundIndex, onUpSoundIndex);
		
		init(imgRelease, imgFocus, textOptions, fontOptions, onDownSoundIndex, onUpSoundIndex);
	}
	
	public void refresh(Serializable serializable, String textHeader, String[] textOptions){}
	
	
	public void refresh(
			Image imgRelease, Image imgFocus, 
			String textHeader, int fontHeader, String[] textOptions, int fontType, 
			final int onDownSoundIndex, final int onUpSoundIndex){
		//super.init(textHeader, fontHeader, textOptions, imgRelease, imgFocus, fontType);
		this.btnList = new ArrayList<Button>();
		this.textHeader = textHeader;
		this.fontHeader = fontHeader;
		init(imgRelease, imgFocus, textOptions, fontType, onDownSoundIndex, onUpSoundIndex);
	}
	
	public void init(Image imgRelease, Image imgFocus, String[] textOptions, int fontType, 
			final int onDownSoundIndex, final int onUpSoundIndex){
		//El espacio que ocupa el header, en la case madre es un 150% del tamanyo de la fuente
		int headerHeight = textHeader != null?(int)(Font.getFontHeight(fontHeader)*1.5f):0;
		int boxHeight = 
			(imgBox!=null?imgBox.getHeight():screenHeight)-headerHeight;
				
		int buttonSep = imgRelease.getHeight()/4;
		int scrollSize = (imgRelease.getHeight()*textOptions.length) + (buttonSep*(textOptions.length+1));
				
		int initY = 0;
		scroll = scrollSize > boxHeight;
				
		if(scroll){
			initY = getY()- (imgBox!=null?imgBox.getHeight():screenHeight)/2 + headerHeight + imgRelease.getHeight()/2 + buttonSep;
		}else{
			initY = getY() -scrollSize/2 + headerHeight/2 + imgRelease.getHeight()/2 - buttonSep;
		}
		if(textOptions != null && imgRelease != null && imgFocus != null){
					
			for(int i = 0; i < textOptions.length; i++){
				int pY = (imgRelease.getHeight()*i) + buttonSep*(i);
				Button btn = 
						new Button(
								imgRelease, 
								imgFocus, 
								getX(), initY+pY,
								textOptions[i], 
								fontType){
					
					@Override
					public void onButtonPressUp() {
						super.onButtonPressUp();
						if(onUpSoundIndex != -1){
							SndManager.getInstance().playFX(onUpSoundIndex, 0);
						}
					}
					@Override
					public void onButtonPressDown() {
						super.onButtonPressDown();
						if(onDownSoundIndex != -1){
							SndManager.getInstance().playFX(onDownSoundIndex, 0);
						}
					}
				};
				btnList.add(btn);
			}
			maxModY = scrollSize - (boxHeight - initY) -imgRelease.getHeight()/2-buttonSep;
		}
	}
	
	public void start(){
		super.start();
		//Limito(En caso de que el numero de opciones sea insuficiente para el scroll)
		modPosY = Math.min(0, modPosY);
	    modPosY = Math.max(-maxModY, modPosY);
	    initOptionY = new int[btnList.size()];
	    for(int i = 0; i < initOptionY.length; i++){
	    	initOptionY[i]=btnList.get(i).getY();
	    }
	}
	
	public void setDisabledList(boolean[] disableList){
		for(int i = 0; i < btnList.size(); i++){
			btnList.get(i).setDisabled(disableList[i]);
		}
	}
	
	public void setDisabledList(){
		for(int i = 0; i < btnList.size(); i++){
			btnList.get(i).setDisabled(true);
		}
	}
	
	protected int[] initOptionY;
	
	private int maxModY;
	
	private int lastTouchY;
	private float speedY;
	public static final float PRECISION_SPEED = 12f;
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(scroll){
			if(state == STATE_ACTIVE){
				boolean reset = (int)speedY!=0f;
				if(
						touchHandler.getTouchAction(0) == TouchData.ACTION_MOVE
						&& touchHandler.getTouchFrames(0) > 1){
					if(lastTouchY != touchHandler.getTouchY(0)){
						speedY = speedY + lastTouchY - touchHandler.getTouchY(0);
					}
				}
				lastTouchY = touchHandler.getTouchY(0);
				
				if(speedY>0){
					speedY-=((speedY*6f)*delta);//+0.005f;
					speedY = Math.max(0, speedY);
					
				}else if(speedY<0){
					speedY-=((speedY*6f)*delta);//-0.005f;
					speedY = Math.min(0, speedY);
				}
				
				/*
				if(Math.abs(speedY) < 0.01f)
					speedY = 0;
				*/
				
				modPosY -= speedY/4f;
				
				//Limito
				modPosY = Math.min(0, modPosY);
			    modPosY = Math.max(-maxModY, modPosY);
			    
			    if(reset){
			    	for(Button b : btnList){
						b.reset();
					}
			    }
			    
		    }
			
			//Actualizar p botones
		  	for(int i = 0; i < initOptionY.length; i++){
		  	    btnList.get(i).setY(initOptionY[i] + (int)modPosY);
		  	}
		}
		return super.update(touchHandler, delta);
	}
	
	

}

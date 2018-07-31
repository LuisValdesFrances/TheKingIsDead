package com.luis.lgameengine.gui;

import android.util.Log;

import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;

public class Keyboard{
	
	public static final String[] KEY_LIST = {
											"1","2","3","4","5","6","7","8","9","0",
											"Q","W","E","R","T","Y","U","I","O","P",
											"A","S","D","F","G","H","J","K","L",
											"Z","X","C","V","B","N","M",
											" ","DEL"
											};
	
	private Button[] buttonList;
	
	private float modY;
	
	private String textChain;
	
	public Keyboard(
			int x, int y,
			Image imgRelease, Image imgFocus, 
			Image imgReleaseLarge, Image imgFocusLarge, int fontTypeBig, int fontTypeSmall, 
			final int maxLetters,
			final int optionSoundIndex) {
		this.buttonList = new Button[KEY_LIST.length];
		
		int pX = x - (imgRelease.getWidth()*5) + imgRelease.getWidth()/2;
		int pY= y - (imgRelease.getHeight()*2) - imgRelease.getHeight()/2;
		for(int i = 0; i < KEY_LIST.length; i++){
			if(i==10){
				pX= x - (imgRelease.getWidth()*5) + imgRelease.getWidth()/2;
				pY += imgRelease.getHeight();
			}
			if(i==20){
				pX= x - (int)(imgRelease.getWidth()*4.5) + imgRelease.getWidth()/2;
				pY += imgRelease.getHeight();
			}
			else if(i==29){
				pX= x - (int)(imgRelease.getWidth()*3.5) + imgRelease.getWidth()/2;
				pY += imgRelease.getHeight();
			}
			else if(i==36){
				pX= x - imgReleaseLarge.getWidth()/2;
				pY += imgRelease.getHeight();
			}
			
			buttonList[i] = new Button(
					i < KEY_LIST.length-2?imgRelease:imgReleaseLarge, 
					i < KEY_LIST.length-2?imgFocus:imgFocusLarge, 
					pX, pY, (""+KEY_LIST[i]), 
					i < 36 ? fontTypeBig:fontTypeSmall){
				
				@Override
				public void onButtonPressDown() {
					super.onButtonPressDown();
					SndManager.getInstance().playFX(optionSoundIndex, 0);
				}
				
				@Override
				public void onButtonPressUp() {
					super.onButtonPressUp();
					if(textChain.length() > 0 && getText().equals("DEL")){
						String del = "";
						for(int i = 0; i < textChain.length()-1;i++){
							del+=textChain.charAt(i);
						}
						textChain = del;
					}else if(!getText().equals("DEL") 
							&& (maxLetters == -1 || (maxLetters > 0 && textChain.length() < maxLetters))){
						textChain += getText();
					}
					
					Log.i("Debug", "Tecleado: " + textChain);
					reset();
				}
			};
			if(i < 35){
				pX += imgRelease.getWidth();
			}else{
				pX += imgReleaseLarge.getWidth();
			}
		}
	}
	
	
	
	

	public String getTextChain() {
		return textChain;
	}

	public void setTextChain(String textChain) {
		this.textChain = textChain;
	}

	public boolean update(MultiTouchHandler touchHandler){
		for(Button button: buttonList){
			button.update(touchHandler);
		}
		return true;
	}
	
	public void draw(Graphics g){
		for(Button button: buttonList){
			button.draw(g, 0, (int)modY, 1.15f);
		}
	}
	
	public int getWidth(){
		return buttonList[0].getWidth()*10;
	}
	
	public int getHeight(){
		return buttonList[0].getHeight()*5;
	}
	
	public float getModY() {
		return modY;
	}

	public void setModY(float modY) {
		this.modY = modY;
	}


	public void onButtonPressDown(){}
	public void onButtonPressUp(){}

}

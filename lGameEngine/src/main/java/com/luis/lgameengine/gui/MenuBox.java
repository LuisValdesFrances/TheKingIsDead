package com.luis.lgameengine.gui;

import java.util.ArrayList;
import java.util.List;



import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;

public class MenuBox extends MenuElement {
	
	public static final int STATE_UNACTIVE = 0;
	public static final int STATE_TO_ACTIVE = 1;
	public static final int STATE_ACTIVE = 2;
	public static final int STATE_TO_UNACTIVE = 3;
	//Espera tres frames por el triple buffer
	public static final int STATE_WAIT_FINISH = 4;
	
	protected Image imgBox;
	protected int screenWidth;
	protected int screenHeight;
	protected String textHeader;
	protected int fontHeader;
	
	protected List<Button> btnList;
	
	protected int state;
	protected int indexPressed;
	protected float modPosX;
	protected float modPosY;
	
	//Espera tres frames para si luego se produce una carga, evitar le efecto de triple buffer
	public static final int BUFFER_WAIT_FRAMES = 3;
	private int unactiveCount;
	
	public MenuBox(
			int screenWidth,
			int screenHeight,
			Image imgBox,
			Image imgRelease, 
			Image imgFocus,
			int x,
			int y,
			String textHeader,
			String[] textOptions,
			int fontHeader,
			int fontOptions,
			final int onDownSoundIndex, final int onUpSoundIndex) {
		super(screenWidth, screenHeight);
		this.imgBox = imgBox;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.width = imgBox != null ? imgBox.getWidth() : screenWidth;
		this.height = imgBox != null ? imgBox.getHeight() : screenHeight;
		
		setX(x);
		setY(y);
		
		init(textHeader, fontHeader, textOptions, imgRelease, imgFocus, fontOptions, onDownSoundIndex,  onUpSoundIndex);
	}
	
	public void start(){
		modPosX = -screenWidth/2-(imgBox!=null?imgBox.getWidth():screenWidth)/2;
		modPosY = 0;
		indexPressed = -1;
		for(Button button : btnList){
			button.reset();
		}
		state = STATE_TO_ACTIVE;
		onStart();
	}
	
	protected void init(String textHeader, int fontHeader, 
			String[] textOptions, Image imgRelease, Image imgFocus, int fontType, final int onDownSoundIndex, final int onUpSoundIndex){
		
		this.btnList = new ArrayList<Button>();
		this.textHeader = textHeader;
		this.fontHeader = fontHeader;
		
		
		int headerHeight=0;
		if(textHeader!=null){
			headerHeight= Font.getFontHeight(fontHeader)+Font.getFontHeight(fontHeader)/2;
		}
		
		int boxHeight = (imgBox!=null?imgBox.getHeight():screenHeight);
		
		if(textOptions != null && imgRelease != null && imgFocus != null){
			int menuSection = ((boxHeight-headerHeight) / textOptions.length);
			for(int i = 0; i < textOptions.length; i++){
				Button btn = 
						new Button(
								imgRelease, 
								imgFocus, 
								getX(), 
								(getY() - (boxHeight/2) + headerHeight) +
								(menuSection * (i+1)) - menuSection/2,
								textOptions[i], 
								fontType){
					@Override
					public void onButtonPressUp() {
						super.onButtonPressUp();
						if(onUpSoundIndex != -1){
							SndManager.getInstance().playFX(onUpSoundIndex, 0);
						}
					};
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
		}
	}
	
	public void cancel(){
		if(state == STATE_TO_ACTIVE || state == STATE_ACTIVE){
			state = STATE_TO_UNACTIVE;
		}
	}
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(state != STATE_UNACTIVE){
			switch(state){
			case STATE_TO_ACTIVE:
				modPosX -= (modPosX*8f)*delta - 1f;
				if(modPosX >= 0){
					modPosX = 0;
					state = STATE_ACTIVE;
				}
				break;
			case STATE_ACTIVE:
				for(int i = 0; i < btnList.size(); i++){
					if(btnList.get(i).update(touchHandler)){
						indexPressed = i;
						state = STATE_TO_UNACTIVE;
						break;
					}
				}
				break;
			case STATE_TO_UNACTIVE:
				modPosX += (modPosX*16f)*delta + 1f;
				
				int boxWidth = imgBox!=null?imgBox.getWidth():screenWidth;
				//modPosX = Math.min(screenWidth/2+boxWidth/2, modPosX);
				if(modPosX >= screenWidth/2+boxWidth/2){
					modPosX = screenWidth/2+boxWidth/2;
					state = STATE_WAIT_FINISH;
					unactiveCount = 0;
				}
				break;
				
			case STATE_WAIT_FINISH:
				if(unactiveCount < BUFFER_WAIT_FRAMES){
					unactiveCount++;
				}else{
					state = STATE_UNACTIVE;
					onFinish();
				}
				break;
			}
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g, Image imgBG){
		if(state != STATE_UNACTIVE){
			
			if(imgBG != null){
				int modAlpha = (int) ((Math.abs(modPosX) * bgAlpha) / (screenWidth/2+width/2));
				g.setAlpha(bgAlpha-modAlpha);
				g.drawImage(imgBG, screenWidth/2, screenHeight/2, Graphics.VCENTER | Graphics.HCENTER);
				g.setAlpha(255);
			}
			
			if(imgBox != null)
				g.drawImage(imgBox, getX()+(int)modPosX, getY(), Graphics.VCENTER | Graphics.HCENTER);
			
			if(textHeader != null){
				TextManager.drawSimpleText(g, 
					fontHeader,
					textHeader,
					getX()+(int)modPosX, 
					getY()-height/2 + Font.getFontHeight(fontHeader), 
					Graphics.VCENTER|Graphics.HCENTER);
			}
			for(Button btn: btnList){
				btn.draw(g, (int)modPosX, 0);
			}
		}
	}
	
	public void onStart(){};
	
	public void onFinish(){}
	
	public boolean isActive(){
		return state != STATE_UNACTIVE;
	}

	public int getIndexPressed() {
		return indexPressed;
	}

	public void setIndexPressed(int indexPressed) {
		this.indexPressed = indexPressed;
	}

	public List<Button> getBtnList() {
		return btnList;
	}

	public void setBtnList(List<Button> btnList) {
		this.btnList = btnList;
	}

	public String getTextHeader() {
		return textHeader;
	}

	public void setTextHeader(String textHeader) {
		this.textHeader = textHeader;
	}

	public float getModPosX() {
		return modPosX;
	}

	public float getModPosY() {
		return modPosY;
	}
}

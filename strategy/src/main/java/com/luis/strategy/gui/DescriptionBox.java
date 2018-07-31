package com.luis.strategy.gui;

import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;

public class DescriptionBox extends SimpleBox{
	
	
	private Button buttonLeft;
	private Button buttonRight;
	
	private int indexTextHeader;
	private int indexTextBody;
	
	private int totalIndex;
	private int index;

	public DescriptionBox(Image imgBox) {
		super(imgBox, false, true);
		
		buttonLeft = new Button(
				GfxManager.imgPadWest, GfxManager.imgPadWest, 
				Define.SIZEX2 - imgBox.getWidth()/2 - GfxManager.imgPadWest.getWidth(), 
				Define.SIZEY2, null, -1){
			
			@Override
			public void onButtonPressDown() {}
			
			public void onButtonPressUp() {
				SndManager.getInstance().playFX(Main.FX_SELECT, 0);
				reset();
				index = index+1%(totalIndex-1);
				index = index > totalIndex-1 ? 0 : index;
				
				setTextHeader(RscManager.allText[indexTextHeader + index]);
				setTextBody(RscManager.allText[indexTextBody + index]);
			};
		};
		
		buttonRight = new Button(
				GfxManager.imgPadEast, GfxManager.imgPadEast, 
				Define.SIZEX2 + imgBox.getWidth()/2 + GfxManager.imgPadWest.getWidth(), 
				Define.SIZEY2, null, -1){
			
			@Override
			public void onButtonPressDown() {}
			
			public void onButtonPressUp() {
				SndManager.getInstance().playFX(Main.FX_SELECT, 0);
				reset();
				index = index-1;
				index = index < 0 ? totalIndex-1 : index;
				
				setTextHeader(RscManager.allText[indexTextHeader + index]);
				setTextBody(RscManager.allText[indexTextBody + index]);
				
			};
		};
	}
	
	public void start(int indexTextHeader, int indexTextBody, int totalIndex){
		this.index = 0;
		this.totalIndex = totalIndex;
		this.indexTextHeader = indexTextHeader;
		this.indexTextBody = indexTextBody;
		super.start(RscManager.allText[indexTextHeader], RscManager.allText[this.indexTextBody]);
	}
	
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(state != STATE_UNACTIVE){
			buttonLeft.update(touchHandler);
			buttonRight.update(touchHandler);
			
		}
		return super.update(touchHandler, delta);
	}
	
	public void draw(Graphics g, Image imgBG){
		super.draw(g, imgBG);
		if(state != STATE_UNACTIVE){
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			buttonLeft.draw(g, (int)getModPosX(), 0);
			buttonRight.draw(g, (int)getModPosX(), 0);
		}
	}

}

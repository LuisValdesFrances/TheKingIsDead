package com.luis.strategy.gui;

import java.io.Serializable;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gui.ListBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;

public class SceneDataListBox extends ListBox{
	
	private Serializable sceneListData;

	public SceneDataListBox() {
		super(Define.SIZEX, Define.SIZEY, null, 
				GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
				Define.SIZEX2, Define.SIZEY2, 
				RscManager.allText[RscManager.TXT_SELECT_GAME], 
				null,
				Font.FONT_BIG, Font.FONT_SMALL, -1, Main.FX_NEXT);
		
	}
	
	public SceneDataListBox(Serializable sceneListData, String header, String[] textOptions) {
		
		super(Define.SIZEX, Define.SIZEY, null, 
				GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
				Define.SIZEX2, Define.SIZEY2, 
				header, 
				textOptions,
				Font.FONT_BIG, Font.FONT_SMALL, -1, Main.FX_NEXT);
		
		this.sceneListData = sceneListData;
	}
	
	@Override
	public void refresh(Serializable sceneListData, String textHeader, String[] textOptions){
		super.refresh(
				GfxManager.imgNotificationBox,
				GfxManager.imgNotificationBox, 
				textHeader,
				Font.FONT_BIG,
				textOptions, 
				Font.FONT_SMALL, -1, Main.FX_NEXT);
		this.sceneListData = sceneListData;
	}
	
	public Serializable getSceneListData() {
		return sceneListData;
	}

	public void setSceneListData(Serializable sceneListData) {
		this.sceneListData = sceneListData;
	}
	
	
}

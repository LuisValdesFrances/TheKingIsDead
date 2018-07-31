package com.luis.strategy.gui;

import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Player;

public class MapBox extends MenuBox{
	
	private List<Player>playerList;
	private Image imgMap;
	
	private int numberPartsW;
	private int numberPartsH;

	public MapBox(WorldConver worldConver, int numberPartsW, int numberPartsH) {
		super(
				Define.SIZEX, Define.SIZEY, GfxManager.imgMediumBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				null,
				null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_NEXT);
		
		btnList.add(new Button(
						GfxManager.imgButtonCancelRelease,
						GfxManager.imgButtonCancelFocus,
						getX() - GfxManager.imgMediumBox.getWidth()/2, 
						getY() - GfxManager.imgMediumBox.getHeight()/2, 
						null, 
						-1){
					@Override
					public void onButtonPressDown() {}
					
					@Override
					public void  onButtonPressUp(){
						SndManager.getInstance().playFX(Main.FX_BACK, 0);
						cancel();
					}
		});
		
		this.numberPartsW = numberPartsW;
		this.numberPartsH = numberPartsH;
		this.imgMap = new Image((int)worldConver.getWorldWidth(), (int)worldConver.getWorldHeight());
	}
	
	public void start(List<Player>playerList){
		super.start();
		this.playerList = playerList;
	}
	
	public void draw(Graphics g){
		super.draw(g, GfxManager.imgBlackBG);
		if(state != STATE_UNACTIVE){
			Graphics _g = imgMap.getGraphics();
			_g.setClip(0, 0, imgMap.getWidth(), imgMap.getHeight());
			int pW = GfxManager.imgMapList.get(0).getWidth();
			int pH = GfxManager.imgMapList.get(0).getHeight();
			
			int i = 0;
			for(int y = 0; y < numberPartsH; y++){
				for(int x = 0; x < numberPartsW; x++){
					_g.drawImage(GfxManager.imgMapList.get(i),x*pW,y*pH,Graphics.TOP | Graphics.LEFT);
				i++;
				}
			}
			
			for(Player player : playerList){
				i=0;
				for(Kingdom kingdom : player.getKingdomList()){
					
						
					_g.drawImage(GfxManager.imgFlagBigList.get(player.getFlag()),
							kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteX() + 
								GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth()/2,
							kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteY()+
								GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight()/2,
								Graphics.BOTTOM | Graphics.HCENTER);
					i++;
					
				}
			}
			
			float mapAverage = (imgMap.getWidth() + imgMap.getHeight()) / 2f;
			float boxAverage = (GfxManager.imgMediumBox.getWidth() + GfxManager.imgMediumBox.getHeight()) / 2f;
			
			//Relativizo la escala al size de mapa (escalado cuando mas grande)
			float scale = (boxAverage / mapAverage)*0.8f;
			
			g.setImageSize(scale, scale);
			g.drawImage(imgMap, 
					getX()+(int)modPosX,// + (int)(imgMap.getWidth()-(imgMap.getWidth()*scale))/2, 
					getY()+(int)modPosY,// + (int)(imgMap.getHeight()-(imgMap.getHeight()*scale))/2, 
					Graphics.VCENTER | Graphics.HCENTER);
			g.setImageSize(1f, 1f);
			
			
		
		}
	
	}

}

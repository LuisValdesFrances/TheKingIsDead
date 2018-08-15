package com.luis.strategy.gui;

import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
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

	private int worldWidth;
	private int worldHeight;

	public MapBox(WorldConver worldConver, int numberPartsW, int numberPartsH) {
		super(
				Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				null,
				null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_NEXT);
		
		btnList.add(new Button(
						GfxManager.imgButtonCancelRelease,
						GfxManager.imgButtonCancelFocus,
						getX() - GfxManager.imgBigBox.getWidth()/2,
						getY() - GfxManager.imgBigBox.getHeight()/2,
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
		this.worldWidth = (int)worldConver.getWorldWidth();
		this.worldHeight = (int)worldConver.getWorldHeight();

	}
	
	public void start(List<Player>playerList){
		super.start();
		this.playerList = playerList;
		this.imgMap = new Image(worldWidth, worldHeight);
	}

	@Override
	public void onFinish(){
		this.imgMap = null;
		System.gc();
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
			float boxAverage = (GfxManager.imgBigBox.getWidth() + GfxManager.imgBigBox.getHeight()) / 2f;
			
			//Relativizo la escala al size de mapa (escalado cuando mas grande)
			float scale = (boxAverage / mapAverage)*0.7f;
            drawPlayerInfo(g, (int)(imgMap.getWidth()*scale), (int)(imgMap.getHeight()*scale));

			g.setImageSize(scale, scale);
			g.drawImage(imgMap,
					getX()+(int)modPosX - GfxManager.imgBigBox.getWidth()/2 + Define.SIZEX32,
					getY()+(int)modPosY,
					Graphics.VCENTER | Graphics.LEFT);
			g.setImageSize(1f, 1f);

		}
	}

	private void drawPlayerInfo(Graphics g, int mapWidth, int mapHeight){

		int i = 0;
		for(Player p : playerList){
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.drawImage(
					GfxManager.imgFlagSmallList.get(p.getFlag()),
                    getX()+(int)modPosX - GfxManager.imgBigBox.getWidth()/2 + Define.SIZEX32 + mapWidth,
					getY() + mapHeight/2 - (i*GfxManager.imgFlagSmallList.get(p.getFlag()).getHeight()),
					Graphics.BOTTOM | Graphics.LEFT);
			TextManager.drawSimpleText(g, Font.FONT_SMALL,
					p.getName(),
                    getX()+(int)modPosX - GfxManager.imgBigBox.getWidth()/2 + Define.SIZEX32 + GfxManager.imgFlagSmallList.get(p.getFlag()).getWidth()+ mapWidth,
                    getY() + mapHeight/2 - (i*GfxManager.imgFlagSmallList.get(p.getFlag()).getHeight())-
                            GfxManager.imgFlagSmallList.get(p.getFlag()).getHeight()/4,
					Graphics.BOTTOM | Graphics.LEFT);

			i++;
		}
    }

}

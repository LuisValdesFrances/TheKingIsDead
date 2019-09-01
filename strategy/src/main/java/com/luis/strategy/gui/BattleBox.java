package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Terrain;

public class BattleBox extends MenuBox{
	
	private BattleDiceBox battleDiceBox;
	
	private Army armyAtack;
	private Army armyDefense;
	
	private Kingdom kingdom;
	
	private int troopY;
	private int centerY;
	private int separation;
	
	private Button cancelButton;
	private Button waitButton;
	
	private int kingdomFlag;
	
	private int fxLoopId;//Guarda el id del sonido que inicia con la caja
	
	//Controla si al terminar la ventana, se debe de resolver alguna accion de la batalla, como la huida
	private boolean scapeOptions;
	
	public BattleBox(){
		
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
				Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
				RscManager.allText[RscManager.TXT_GAME_BALANCE_POWER],
				null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_NEXT);
		
		btnList.add(new Button(
				GfxManager.imgButtonYellowRelease,
				GfxManager.imgButtonYellowFocus,
				getX() + GfxManager.imgBigBox.getWidth()/2  - GfxManager.imgButtonYellowRelease.getWidth()/2, 
				getY() + GfxManager.imgBigBox.getHeight()/2, 
				RscManager.allText[RscManager.TXT_GAME_ATACK],
				Font.FONT_SMALL){
			@Override
			public void onButtonPressDown() {
				super.onButtonPressDown();
				SndManager.getInstance().playFX(Main.FX_SWORD, 0);
			}
			
			@Override
			public void onButtonPressUp(){
				reset();
				battleDiceBox.start(kingdom, armyAtack, armyDefense, autoPlay);
			}
		});
		
		separation = GfxManager.imgIconTroop.get(0).getHeight()/4;
		
		int totalHeight = 
				GfxManager.imgIconTroop.get(0).getHeight()*GfxManager.imgIconTroop.size() +
				(separation*(GfxManager.imgIconTroop.size()-1));
		troopY = getY() - totalHeight/2;
		
		totalHeight = 
				Font.getFontHeight(Font.FONT_SMALL) +
				GfxManager.imgFlagBigList.get(0).getHeight() +
				GfxManager.imgTerrainBox.get(0).getHeight() +
				separation*2 +
				Font.getFontHeight(Font.FONT_MEDIUM);
		centerY = getY() - totalHeight/2;
		
		battleDiceBox = new BattleDiceBox(){
			@Override
			public void onResult() {
				
			}
		};
	}
	
	private boolean autoPlay;
	public void start(
			Kingdom kingdom,
			Army armyAtack, Army armyDefense, int kingdomFlag,
			boolean waitOption, boolean scapeOption, boolean cancelOption, boolean autoPlay){
		super.start();
		this.kingdom = kingdom;
		this.armyAtack = armyAtack;
		this.armyDefense = armyDefense;
		this.kingdomFlag = kingdomFlag;
		this.scapeOptions = scapeOption;
		this.autoPlay = autoPlay;
		
		if(waitOption || scapeOption){
			
			//Cambiar icono dependiendo de si noAtackOpt o scapeOpt
			
			waitButton =  new Button(
					GfxManager.imgButtonGreenRelease,
					GfxManager.imgButtonGreenFocus,
					//getX(),
					getX() - GfxManager.imgBigBox.getWidth()/2 + GfxManager.imgButtonRedRelease.getWidth()/2, 
					getY() + GfxManager.imgBigBox.getHeight()/2, 
					waitOption?RscManager.allText[RscManager.TXT_GAME_WAIT]:RscManager.allText[RscManager.TXT_GAME_ESCAPE],
					Font.FONT_SMALL){
				@Override
				public void onButtonPressDown() {}
				
				@Override
				public void onButtonPressUp(){
					SndManager.getInstance().playFX(Main.FX_BACK, 0);
					indexPressed = 1;//Al controlador de juego le interesa saber que he cancelado el combate
					reset();
					cancel();
				}
			};
		}else{
			waitButton = null;
		}
		
		if(cancelOption){
			cancelButton =  new Button(
					GfxManager.imgButtonRedRelease,
					GfxManager.imgButtonRedFocus,
					getX() - GfxManager.imgBigBox.getWidth()/2 + GfxManager.imgButtonRedRelease.getWidth()/2, 
					getY() + GfxManager.imgBigBox.getHeight()/2, 
					RscManager.allText[RscManager.TXT_GAME_CANCEL], 
					Font.FONT_SMALL){
				@Override
				public void onButtonPressDown() {}
				
				@Override
				public void onButtonPressUp(){
					SndManager.getInstance().playFX(Main.FX_BACK, 0);
					indexPressed = 2;//Al controlador de juego le interesa saber que he cancelado el combate
					reset();
					cancel();
				}
			};
		}else{
			cancelButton = null;
		}
		
		fxLoopId = SndManager.getInstance().playFX(Main.FX_BATTLE, -1);
	}
	
	@Override
	public void onFinish() {
		super.onFinish();
		SndManager.getInstance().stopFX(fxLoopId);
	};
	
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(!battleDiceBox.update(touchHandler, delta)){
			
			if(waitButton != null){
				waitButton.update(touchHandler);
			}
			if(cancelButton != null){
				cancelButton.update(touchHandler);
			}
			
			return super.update(touchHandler, delta);
		}
		return true;
	}
	
	public void draw(Graphics g){
		super.draw(g, GfxManager.imgBlackBG);
		if(state != STATE_UNACTIVE){
			for(int i = 0; i < GfxManager.imgIconTroop.size(); i++){
				if(waitButton != null){
					waitButton.draw(g, (int)modPosX, 0);
				}
				if(cancelButton != null){
					cancelButton.draw(g, (int)modPosX, 0);
				}
				//Left
				g.drawImage(
						GfxManager.imgIconTroop.get(i),
						getX()-GfxManager.imgBigBox.getWidth()/2 +
						separation*2 + GfxManager.imgIconTroop.get(i).getWidth()/2 +
						(int)modPosX, 
						troopY + GfxManager.imgIconTroop.get(i).getHeight()/2 +
						GfxManager.imgIconTroop.get(i).getHeight()*i + 
						separation * i, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				String text = "X" + armyAtack.getNumberTroops(i);
				TextManager.drawSimpleText(g, 
						Font.FONT_MEDIUM,
						text,
						getX()-GfxManager.imgBigBox.getWidth()/2 +
						separation*3 + GfxManager.imgIconTroop.get(i).getWidth()+
						(Font.getFontWidth(Font.FONT_MEDIUM)*text.length())/2 +
						(int)modPosX, 
						troopY + GfxManager.imgIconTroop.get(i).getHeight()/2+
						GfxManager.imgIconTroop.get(i).getHeight()*i + 
						separation * i,
						Graphics.VCENTER|Graphics.HCENTER);
				
				
				//Right
				if(armyDefense != null){
					g.drawImage(
							GfxManager.imgIconTroop.get(i),
							getX()+GfxManager.imgBigBox.getWidth()/2 - 
							separation*2 - GfxManager.imgIconTroop.get(i).getWidth()/2 +
							(int)modPosX, 
							troopY + GfxManager.imgIconTroop.get(i).getHeight()/2 +
							GfxManager.imgIconTroop.get(i).getHeight()*i + 
							separation * i, 
							Graphics.VCENTER | Graphics.HCENTER);
					
					text = "X" + armyDefense.getNumberTroops(i);
					TextManager.drawSimpleText(g, 
							Font.FONT_MEDIUM,
							text,
							getX()+GfxManager.imgBigBox.getWidth()/2 -
							separation*3 - GfxManager.imgIconTroop.get(i).getWidth()-
							(Font.getFontWidth(Font.FONT_MEDIUM)*text.length())/2 +
							(int)modPosX,
							troopY + GfxManager.imgIconTroop.get(i).getHeight()/2 +
							GfxManager.imgIconTroop.get(i).getHeight()*i + 
							separation * i,
							Graphics.VCENTER|Graphics.HCENTER);
				}
			}
			
			if(armyDefense == null){
				int initY = getY() -
						((GfxManager.imgVillagers.getHeight() + 
								Font.getFontHeight(Font.FONT_MEDIUM)+ separation)/2);
				String text = "X1";
				
				TextManager.drawSimpleText(g, 
						Font.FONT_MEDIUM,
						text,
						getX()+GfxManager.imgBigBox.getWidth()/2 - 
						separation*2 - GfxManager.imgVillagers.getWidth()/2 +
						(int)modPosX,
						initY + Font.getFontHeight(Font.FONT_MEDIUM)/2,
						Graphics.VCENTER|Graphics.HCENTER);
				
				g.drawImage(
						GfxManager.imgVillagers,
						getX()+GfxManager.imgBigBox.getWidth()/2 - 
						separation*2 - GfxManager.imgVillagers.getWidth()/2 +
						(int)modPosX,
						initY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + separation +
						GfxManager.imgVillagers.getHeight()/2,
						Graphics.VCENTER | Graphics.HCENTER);
			}
					
			Terrain terrain = armyDefense != null ? 
					kingdom.getTerrainList().get(0) : kingdom.getTerrainList().get(kingdom.getState());
			
			int relativeFlagX = GfxManager.imgTerrainBox.get(terrain.getType()).getWidth()/3;
			
			
			//Left
			TextManager.drawSimpleText(g, 
					Font.FONT_SMALL,
					""+armyAtack.getPower(terrain),
					getX()-relativeFlagX +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_SMALL)/2,
					Graphics.VCENTER|Graphics.HCENTER);
			
			g.drawImage(GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()),
					getX()-GfxManager.imgTerrainBox.get(terrain.getType()).getWidth()/3 +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()/2,
					Graphics.VCENTER|Graphics.HCENTER);
			
			//Right
			TextManager.drawSimpleText(g,
					Font.FONT_SMALL,
					""+
					(armyDefense != null?
						armyDefense.getPower(terrain):GameParams.TERRAIN_DEFENSE[terrain.getType()]),
						getX()+GfxManager.imgTerrainBox.get(terrain.getType()).getWidth()/3 +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_SMALL)/2,
					Graphics.VCENTER|Graphics.HCENTER);
				
			g.drawImage(
					(armyDefense != null?
					GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()):
					GfxManager.imgFlagBigList.get(kingdomFlag)), 
					getX()+relativeFlagX +
					(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					(armyDefense != null?
						GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight()/2:
						GfxManager.imgFlagBigList.get(kingdomFlag).getHeight()/2),
					Graphics.VCENTER|Graphics.HCENTER);
			
			
			g.drawImage(GfxManager.imgTerrainBox.get(terrain.getType()),
					getX()+(int)modPosX, 
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					(armyDefense != null?
						GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight() :
						GfxManager.imgFlagBigList.get(kingdomFlag).getHeight())+
					GfxManager.imgTerrainBox.get(terrain.getType()).getHeight()/2, 
					Graphics.VCENTER|Graphics.HCENTER);
			
			String terrainText="";
			switch(terrain.getType()){
				case GameParams.PLAIN: terrainText = RscManager.allText[RscManager.TXT_GAME_PLAIN]; break;
				case GameParams.FOREST: terrainText = RscManager.allText[RscManager.TXT_GAME_FOREST]; break;
				case GameParams.MONTAIN: terrainText = RscManager.allText[RscManager.TXT_GAME_MONTAIN]; break;
				case GameParams.CITY: terrainText = RscManager.allText[RscManager.TXT_GAME_CITY]; break;
			}
			
			TextManager.drawSimpleText(g, 
					Font.FONT_SMALL,
					terrainText,
					getX()+(int)modPosX,
					centerY +
					Font.getFontHeight(Font.FONT_BIG) + 
					separation +
					(armyDefense != null ?
							GfxManager.imgFlagBigList.get(armyDefense.getPlayer().getFlag()).getHeight():
							GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getHeight()) +
							GfxManager.imgTerrainBox.get(terrain.getType()).getHeight(),
					Graphics.VCENTER|Graphics.HCENTER);
			
			//Equilibrio de poder
			
			//Distancia total
			int barWidth = 
					GfxManager.imgTerrainBox.get(terrain.getType()).getWidth() - relativeFlagX - 
					GfxManager.imgFlagBigList.get(GfxManager.imgFlagBigList.size()-1).getWidth();
			int barHeight = GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()/10;
			
			int atackForces = armyAtack.getPower(terrain);
			int defenseForces = armyDefense != null?
					armyDefense.getPower(terrain): kingdom.getDefense(kingdom.getState());
			int totalForces = atackForces+defenseForces;
			int atackWidth = (atackForces*barWidth)/totalForces;
			int defenseWidth = (defenseForces*barWidth)/totalForces;
			
			g.setColor(Main.COLOR_GREEN);
			g.fillRect(getX()-barWidth/2 +(int)modPosX,
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()-
					separation-barHeight,
					atackWidth, barHeight);
			
			g.setColor(Main.COLOR_RED);
			g.fillRect(getX()+barWidth/2 - defenseWidth +(int)modPosX,
					centerY +
					Font.getFontHeight(Font.FONT_BIG)/2 + 
					separation +
					GfxManager.imgFlagBigList.get(armyAtack.getPlayer().getFlag()).getHeight()-
					separation-barHeight,
					defenseWidth, barHeight);
			
			battleDiceBox.draw(g);
		}
	}

	public int getResult() {
		return battleDiceBox.getResult();
	}

	public boolean isScapeOptions() {
		return scapeOptions;
	}

	
	

}

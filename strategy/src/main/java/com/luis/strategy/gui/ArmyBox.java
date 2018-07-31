package com.luis.strategy.gui;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
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
import com.luis.strategy.map.Troop;


public class ArmyBox extends MenuBox{
	
	private ArmyBuyBox armyBuyBox;
	private SimpleBox confirmationBox;
	
	private Army army;
	private boolean isCurrentPlayer;
	
	private List<Button>deleteButtonList;
	private Button crossButton;
	
	private boolean enableCrossButton;
	
	private int selectedTroop;
	
	public ArmyBox() {
		super(Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
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
			public void onButtonPressUp() {
				SndManager.getInstance().playFX(Main.FX_BACK, 0);
			}
		});
		
		armyBuyBox = new ArmyBuyBox(){
			@Override
			public void onBuy() {
				if(army.getPlayer().getGold() >= GameParams.TROOP_COST[this.getIndex()]){
					army.getPlayer().setGold(army.getPlayer().getGold()-GameParams.TROOP_COST[this.getIndex()]);
					army.getTroopList().add(new Troop(this.getIndex(), false));
					updateTroops();
					
					if(army.getTroopList().size() == GameParams.MAX_NUMBER_OF_TROOPS){
						armyBuyBox.cancel();
						crossButton.setDisabled(true);
					}
				}
			}
		};
		
		confirmationBox = new SimpleBox(GfxManager.imgSmallBox, true, true){
			@Override
			public void onFinish(){
				if(indexPressed == 0){
					Log.i("Debug", "Descartado tropa: " + army.getTroopList().get(selectedTroop).getId() + " - " + army.getTroopList().get(selectedTroop).getType());
					army.getPlayer().setGold(army.getPlayer().getGold() + 
							(GameParams.TROOP_COST[army.getTroopList().get(selectedTroop).getType()]/2));
					army.getTroopList().remove(army.getTroopList().get(selectedTroop));
					check();
				}else if(indexPressed == 1){
					
				}
			}
		};
	}
	
	private int fileWidth;
	private int columnHeight;
	private int totalColums;
	private int totalFiles;
	private int marginW;
	private int marginH;
	
	public void start(Army a, boolean isCurrentPlayer, boolean discardMode){
		super.start();
		this.army = a;
		this.isCurrentPlayer = isCurrentPlayer;
		
		int imageW = GfxManager.imgSmallTroop.get(0).getWidth();
		int imageH = GfxManager.imgSmallTroop.get(0).getHeight();
		marginW = imageW/10;
		marginH = imageH/10;
		
		totalColums = GfxManager.imgBigBox.getWidth() / (imageW+marginW);
		
		totalFiles = GfxManager.imgBigBox.getHeight() / (imageH+marginH);
		fileWidth = imageW * totalColums + (marginW * (totalColums-1));
		columnHeight = imageH * totalFiles + (marginH * (totalFiles-1));
		
		updateTroops();
		
		if(isCurrentPlayer && a.getTroopList().size() < GameParams.MAX_NUMBER_OF_TROOPS){
			crossButton = new Button(
					GfxManager.imgButtonCrossBigRelease, 
					GfxManager.imgButtonCrossBigFocus, 
					getX()+GfxManager.imgBigBox.getWidth()/2-GfxManager.imgButtonCrossBigRelease.getWidth()/4, 
					getY()+GfxManager.imgBigBox.getHeight()/2-GfxManager.imgButtonCrossBigRelease.getHeight()/4, 
					null, -1){
				
				@Override
				public void onButtonPressDown() {}
				
				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					armyBuyBox.start(army);
				}
			};
			
			enableCrossButton = !discardMode && army.getKingdom().isACity() && army.getPlayer().hasKingom(army.getKingdom());
			crossButton.setDisabled(!enableCrossButton);
		}else{
			crossButton = null;
		}
		
	}
	
	public void updateTroops(){
		
		int initX = getX() - fileWidth/2 + GfxManager.imgSmallTroop.get(0).getWidth()/2;
		int initY = getY() - columnHeight/2 + GfxManager.imgSmallTroop.get(0).getHeight()/2;
		
		int countColumns = 0;
		int countFiles = 0;
		
		deleteButtonList = new ArrayList<Button>();
		for(int i = 0; i < army.getTroopList().size(); i++){
				
			if(countColumns == totalColums){
				countColumns=0;
				countFiles++;
			}
				
			if(isCurrentPlayer){
				Button discard = null;
				if(!army.getTroopList().get(i).isSubject()){
					discard = new Button(
						GfxManager.imgButtonDeleteRelease,
						GfxManager.imgButtonDeleteFocus,
							
						initX + 
						(GfxManager.imgSmallTroop.get(0).getWidth()*countColumns + marginW*countColumns)-
						GfxManager.imgSmallTroop.get(0).getWidth()/2+GfxManager.imgButtonDeleteRelease.getWidth()/2
						-GfxManager.imgButtonDeleteRelease.getWidth()/4,
							
						initY + 
						(GfxManager.imgSmallTroop.get(0).getHeight()*countFiles + marginH*countFiles) -
						GfxManager.imgSmallTroop.get(0).getHeight()/2+GfxManager.imgButtonDeleteRelease.getHeight()/2
						-GfxManager.imgButtonDeleteRelease.getHeight()/4,
							
						null, 0){
						
						@Override
						public void onButtonPressDown(){}
							
						@Override
						public void onButtonPressUp(){
							reset();
						}
					};
				}
				deleteButtonList.add(discard);
				countColumns++;
			}
		}
	}
	
	@Override
	public boolean update(MultiTouchHandler touchHandler, float delta){
		if(!armyBuyBox.update(touchHandler, delta)){
			if(isCurrentPlayer && state == STATE_ACTIVE){
				for(int i = 0; i < army.getTroopList().size(); i++){
					if(!army.getTroopList().get(i).isSubject()){
						if(deleteButtonList.get(i).update(touchHandler)){
							selectedTroop = i;
							String text =
									RscManager.allText[RscManager.TXT_GAME_DO_YOU_WANT_DISCARD] + " " + 
									RscManager.allText[RscManager.TXT_GAME_INFANTRY + army.getTroopList().get(selectedTroop).getType()] + " " + 
									RscManager.allText[RscManager.TXT_GAME_FOR] + " " + 
									(GameParams.TROOP_COST[army.getTroopList().get(selectedTroop).getType()]/2) + " " +
									RscManager.allText[RscManager.TXT_GAME_COINS] + " " + 
									RscManager.allText[RscManager.TXT_GAME_INTERROGATION_ICON];
							confirmationBox.start(null, text);
							break;
						}
					}
				}
				
				if(crossButton != null & enableCrossButton){
					crossButton.update(touchHandler);
				}
				
				if(deleteButtonList != null && deleteButtonList.size() > 0){
					confirmationBox.update(touchHandler, delta);
				}
			}
			return super.update(touchHandler, delta);
			}
		return true;
	}
	
	public void draw(Graphics g){
		super.draw(g, GfxManager.imgBlackBG);
		if(state != STATE_UNACTIVE){
			
			int countColumns = 0;
			int countFiles = 0;
			int initX = getX() - fileWidth/2 + GfxManager.imgSmallTroop.get(0).getWidth()/2;
			int initY = getY() - columnHeight/2 + GfxManager.imgSmallTroop.get(0).getHeight()/2;
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			for(int i = 0; i < army.getTroopList().size(); i++){
				
				if(countColumns == totalColums){
					countColumns=0;
					countFiles++;
				}
				
				int pX = initX + 
						(GfxManager.imgSmallTroop.get(0).getWidth()*countColumns + marginW*countColumns)+(int)modPosX;
				int pY = initY + 
						(GfxManager.imgSmallTroop.get(0).getHeight()*countFiles + marginH*countFiles);
				
				countColumns++;
				if(army.getTroopList().get(i).isSubject())
					g.setAlpha(120);
				
				g.drawImage(GfxManager.imgSmallTroop.get(army.getTroopList().get(i).getType()), pX, pY, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				g.setAlpha(255);
				
			}
			
			for(int i = 0; i < army.getTroopList().size(); i++){
				if(isCurrentPlayer && !army.getTroopList().get(i).isSubject()){
					deleteButtonList.get(i).draw(g, (int)modPosX, 0);
				}
			}
			
			if(deleteButtonList != null && deleteButtonList.size() > 0){
				confirmationBox.draw(g, GfxManager.imgBlackBG);
			}
			
			if(crossButton != null)
				crossButton.draw(g, (int)modPosX, 0);
			
			armyBuyBox.draw(g);
		}
	}
	
	public void check(){}
	
	

}

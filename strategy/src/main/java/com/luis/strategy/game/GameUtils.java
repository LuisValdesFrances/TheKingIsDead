package com.luis.strategy.game;

import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.Terrain;

public class GameUtils {
	
	private static GameUtils instance;
	
	public static GameUtils getInstance(){
		if(instance == null){
			instance = new GameUtils();
		}
		return instance;
	}
	
	public int calculateDifficult(
			Kingdom kingdom, 
			Army armyAtack, Army armyDefense){
		int value=0;
		
		int pAtack = 0;
		int pDefense = 0;
		
		Terrain terrain;
		
		if(armyDefense == null){
			terrain = kingdom.getTerrainList().get(kingdom.getState());
			pAtack = armyAtack.getPower(terrain);
			pDefense = kingdom.getDefense(kingdom.getState());
		}else{
			terrain = kingdom.getTerrainList().get(0);
			pAtack = armyAtack.getPower(terrain);
			pDefense = armyDefense.getPower(terrain);
		}
		
		value = GameParams.ROLL_SYSTEM-((armyAtack.getPower(terrain) * GameParams.ROLL_SYSTEM)/(pAtack+pDefense));
		
		return value;
	}
	
	public boolean checkArmyColision(Army army){
		int x1 = army.getAbsoluteX();
		int y1 = army.getAbsoluteY();
		int w1 = GfxManager.imgArmyIdle.getWidth()/28;
		int h1 = GfxManager.imgArmyIdle.getHeight()/8;
		int x2 = army.getKingdom().getAbsoluteX();
		int y2 = army.getKingdom().getAbsoluteY();
		int w2 = GfxManager.imgTargetDomain.getWidth()/8;
		int h2 = GfxManager.imgTargetDomain.getHeight()/8;
		
		return checkColision(x1, y1, w1, h1, x2, y2, w2, h2);
	}
	
	public boolean checkColision(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2){
		return x1+w1/2>x2-w2/2 && x1-w1/2<x2+w2 && y1+h1/2>y2-h2/2 && y1-h1/2<y2+h2;
	}

}

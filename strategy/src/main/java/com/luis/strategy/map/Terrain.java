package com.luis.strategy.map;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.Main;


public class Terrain extends MapObject{
	
	private int type;
	private boolean conquest;
	
	public Terrain(
			MapObject map,
			float x, float y, int width, int height,
			float mapX, float mapY,
			int mapWidth, int mapHeight, int type, boolean conquest) {
		super(map, x, y, width, height, mapX, mapY, mapWidth, mapHeight, -1, Main.FX_NEXT);
		this.type = type;
		this.conquest = conquest;
	}
	
	public void update(MultiTouchHandler multiTouchHandler, WorldConver worldConver, GameCamera gameCamera){
		super.update(multiTouchHandler, worldConver, gameCamera);
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isConquest() {
		return conquest;
	}
	public void setConquest(boolean conquest) {
		this.conquest = conquest;
	}
	
	
	

}

package com.luis.strategy.map;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;

public abstract class MapObject{
	
	protected MapObject map;
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	
	protected boolean selected;
	
	protected boolean select;
	
	//Si hay ejercito enemigo o es del dominio, estandarte
	//En otro caso state
	protected int touchX;
	protected int touchY;
	
	protected float mapX;
	protected float mapY;
	protected int mapWidth;
	protected int mapHeight;
	
	protected Button button;
	
	public MapObject(
			MapObject map,
			float x, float y, int width, int height, 
			float mapX, float mapY, int mapWidth, int mapHeight,
			final int onDownSoundIndex, final int onUpSoundIndex) {
		super();
		this.map = map;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mapX = mapX;
		this.mapY = mapY;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.touchX = getAbsoluteX();
		this.touchY = getAbsoluteY();
		
		this.button = new Button(width, height, -1, -1){
			@Override
			public void onButtonPressDown(){
				SndManager.getInstance().playFX(onDownSoundIndex, 0);
			};
			@Override
			public void onButtonPressUp() {
				SndManager.getInstance().playFX(onUpSoundIndex, 0);
				reset();
				select = true;
			};
			
			@Override
			public void reset(){
				super.reset();
				select = false;
			}
		};
	}
	
	public void update(MultiTouchHandler multiTouchHandler, WorldConver worldConver, GameCamera gameCamera){
		button.setX(getTouchX(worldConver, gameCamera));
		button.setY(getTouchY(worldConver, gameCamera));
		if(worldConver.isObjectInGameLayout(
				gameCamera.getPosX(), 
				gameCamera.getPosY(),
				getAbsoluteX()-button.getWidth()/2, 
				getAbsoluteY()-button.getHeight()/2, 
				button.getWidth(), button.getHeight())){
			button.update(multiTouchHandler);
		}
	}
	
	public int getAbsoluteX() {
		return (int)((x*mapWidth)/100f);
	}
	public int getAbsoluteY() {
		return (int)((y*mapHeight)/100f);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTouchX(WorldConver worldConver, GameCamera gameCamera) {
		return worldConver.getConversionDrawX(gameCamera.getPosX(), touchX);
	}

	public void setTouchX(int touchX) {
		this.touchX = touchX;
	}
	public int getTouchY(WorldConver worldConver, GameCamera gameCamera) {
		return worldConver.getConversionDrawY(gameCamera.getPosY(), touchY);
	}

	public void setTouchY(int touchY) {
		this.touchY = touchY;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean isSelect() {
		return select;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	public Button getButton() {
		return button;
	}
	
	

}

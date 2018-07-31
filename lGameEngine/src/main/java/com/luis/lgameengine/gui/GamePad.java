package com.luis.lgameengine.gui;

import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;

public class GamePad {
	
	public Button btnNorth;
	public Button btnNorthEast;
	public Button btnNorthWest;
	public Button btnWest;
	public Button btnEast;
	public Button btnSouth;
	public Button btnSouthEast;
	public Button btnSouthWest;
	
	public GamePad(
			Image imgNorth, Image imgNorthEast, Image imgNorthWest, 
			Image imgEast, 
			Image imgSouth, Image imgSouthEast, Image imgSouthWest,
			Image imgWest, 
			int x, int y) {
		super();
		int sep = (imgNorth.getWidth()+imgNorth.getHeight())/4;
		btnNorth = new Button(imgNorth, imgNorth, x, y-imgNorth.getHeight()-sep, null, 0);
		btnNorthEast = new Button(imgNorthEast, imgNorthEast, x+imgNorth.getWidth()+sep, y-imgNorth.getHeight()-sep, null, 0);
		btnNorthWest = new Button(imgNorthWest, imgNorthWest, x-imgNorth.getWidth()-sep, y-imgNorth.getHeight()-sep, null, 0);
		btnEast = new Button(imgEast, imgEast, x+imgNorth.getWidth()+sep, y, null, 0);
		btnSouth = new Button(imgSouth, imgSouth, x, y+imgNorth.getHeight()+sep, null, 0);
		btnSouthEast = new Button(imgSouthEast, imgSouthEast, x+imgNorth.getWidth()+sep, y+imgNorth.getHeight()+sep, null, 0);
		btnSouthWest = new Button(imgSouthWest, imgSouthWest, x-imgNorth.getWidth()-sep, y+imgNorth.getHeight()+sep, null, 0);
		btnWest = new Button(imgWest, imgWest, x-imgNorth.getWidth()-sep, y, null, 0);
		
	}
	
	public void update(MultiTouchHandler touchHandler){
		btnNorth.update(touchHandler);
		btnNorthEast.update(touchHandler);
		btnNorthWest.update(touchHandler);
		btnSouth.update(touchHandler);
		btnSouthEast.update(touchHandler);
		btnSouthWest.update(touchHandler);
		btnEast.update(touchHandler);
		btnWest.update(touchHandler);
		
		if(btnNorth.isTouching()){
			onButtonNorthPress();
		}
		else if(btnNorthEast.isTouching()){
			onButtonNorthEastPress();
		}
		else if(btnNorthWest.isTouching()){
			onButtonNorthWestPress();
		}else if(btnSouth.isTouching()){
			onButtonSouthPress();
		}
		else if(btnSouthEast.isTouching()){
			onButtonSouthEastPress();
		}
		else if(btnSouthWest.isTouching()){
			onButtonSouthWestPress();
		}
		else if(btnEast.isTouching()){
			onButtonEastPress();
		}else if(btnWest.isTouching()){
			onButtonWestPress();
		}
		
		btnNorth.reset();
		btnNorthEast.reset();
		btnNorthWest.reset();
		btnSouth.reset();
		btnSouthEast.reset();
		btnSouthWest.reset();
		btnEast.reset();
		btnWest.reset();
	}
	
	public void draw(Graphics g){
		btnNorth.draw(g, 0, 0);
		btnNorthEast.draw(g, 0, 0);
		btnNorthWest.draw(g, 0, 0);
		btnEast.draw(g, 0, 0);
		btnSouth.draw(g, 0, 0);
		btnSouthEast.draw(g, 0, 0);
		btnSouthWest.draw(g, 0, 0);
		btnWest.draw(g, 0, 0);
	}
	
	public void onButtonNorthPress(){}
	
	public void onButtonNorthEastPress(){}
	
	public void onButtonNorthWestPress(){}
	
	public void onButtonEastPress(){}
	
	public void onButtonSouthPress(){}
	
	public void onButtonSouthEastPress(){}
	
	public void onButtonSouthWestPress(){}
	
	public void onButtonWestPress(){}

}

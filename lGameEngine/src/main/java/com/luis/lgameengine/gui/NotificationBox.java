package com.luis.lgameengine.gui;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Color;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;

public class NotificationBox extends MenuElement{

	private static NotificationBox instance;
	
	public static NotificationBox getInstance(){
		if(instance == null){
			instance = new NotificationBox();
		}
		return instance;
	}
	
	private Queue<String> notificationList;
	private String currentMessage;
	private float currentTime;
	private float duration;
	private float currentDuration;
	private Image imgBox;
	private float currentModY;
	private float modY;
	
	
	private int state;
	public static final int STATE_START = 1;
	public static final int STATE_SHOW = 2;
	public static final int STATE_END = 3;
	public static final float DURATION_SHORT = 2f;
	public static final float DURATION_MEDIUM = 2.5f;
	public static final float DURATION_LONG = 3f;
	
	private NotificationBox(){
		super(0, 0);
		this.notificationList = new LinkedList<String>();
		this.duration = DURATION_MEDIUM;
	}
	
	public void init(int screenWidth, int screenHeight, Image imgBox, float duration){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.imgBox = imgBox;
		this.state = 0;
		this.duration = duration;
	}
	
	public void addMessage(String message){
		if(!notificationList.isEmpty()){
			currentDuration = currentDuration/2;//Cuantos mas mensajes, mas rapidos
		}else{
			currentDuration =duration;
		}
		notificationList.add(message);
	}
	
	public void update(float delta){
		if(state != 0){
			switch(state){
			case STATE_START:
				currentModY-=(currentModY*8f)*delta - 1f;
				if(currentModY >= 0){
					state = STATE_SHOW;
					currentModY = 0;
				}
				break;
			case STATE_SHOW:
				currentTime += delta;
				if(currentTime >= currentDuration){
					state = STATE_END;
				}
				break;
			case STATE_END:
				currentModY+=(currentModY*8f)*delta - 1f;
				if(currentModY <= modY){
					state = 0;
				}
				break;
			}
		}else{
			if(!notificationList.isEmpty()){
				if(imgBox != null){
					currentModY = -imgBox.getHeight();
				}else{
					currentModY = -Font.getFontHeight(Font.FONT_MEDIUM);
				}
				modY = currentModY;
				currentMessage = notificationList.poll();
				currentTime = 0;
				state = STATE_START;
			}
		}
	}
	
	public void draw(Graphics g){
		if(state != 0){
			g.setClip(0, 0, screenWidth, screenHeight);
			int posY = (int)modY*-1;
			if(imgBox != null){
				g.drawImage(imgBox, 
						screenWidth/2, (int)(posY + currentModY - posY/2), Graphics.VCENTER | Graphics.HCENTER);
			}else{
				g.setColor(Color.BLACK);
				g.setAlpha(MenuElement.alpha);
				g.fillRect(screenWidth/8, 0, screenWidth-screenWidth/4, (int)(posY+currentModY));
				g.setAlpha(255);
			}
			
			TextManager.drawSimpleText(g, Font.FONT_SMALL, currentMessage, 
					screenWidth/2, 
					(int)(posY + currentModY - posY/2),
					Graphics.VCENTER | Graphics.HCENTER);
			
		}
	}
}

package com.luis.lgameengine.gameutils.controls;

import com.luis.lgameengine.implementation.graphics.Graphics;

public interface GameControl {
	
	/**
	 * Virtual pad
	 */
	
	/**
	 * Singletouch engines
	 * @param _iOriginX
	 * @param _iOriginY
	 * @param _iTouchX
	 * @param _iTouchY
	 * @param _iTouchEvent
	 */
	public void update(int originX, int originY, int touchX, int touchY, int touchEvent);
	
	/**
	 * Multitouch engines
	 * @param _iOriginX
	 * @param _iOriginY
	 * @param _iTouchX
	 * @param _iTouchY
	 * @param _iTouchEvents
	 */
	public void update(int[] originX, int[] originY, int[] touchX, int[] touchY, int[] touchEvents);
	
	public void draw(Graphics _g);
	
	public void reset();
	
	/*Pad methodes*/
	
	/**
	 * 
	 * @return Devuelve la presion del virtual pad en un rango de 0f a 1f
	 */
	public float getForce();
	
	/**
	 * 
	 * @return Devuelve el angulo del pad
	 */
	public int getAngle();
	
	/*Button methodes*/
	
	/**
	 * 
	 * @param index
	 * @return Retorna si el botón esta siendo pulsado
	 */
	public boolean isButtonPressed(int index);
	
	/**
	 * 
	 * @param _iIndex El index del boton
	 * @return Retorna si el boton acaba de ser pulsado en un evento de key-up
	 */
	public boolean  isButtonActived(int index);
	
	/**
	 * 
	 * @param index
	 * @return Retorna el numero de frames durante el que se mantiene el boton pulsado
	 */
	public int getButtonCounter(int index);
	
	

}

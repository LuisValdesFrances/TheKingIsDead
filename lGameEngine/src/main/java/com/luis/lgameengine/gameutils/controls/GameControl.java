package com.luis.lgameengine.gameutils.controls;

import com.luis.lgameengine.implementation.graphics.Graphics;

public interface GameControl {

	/**
	 * Virtual pad
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
	 * @return Retorna si el boton esta siendo pulsado
	 */
	public boolean isButtonPressed(int index);

	/**
	 *
	 * @param index
	 * @return Retorna el numero de frames durante el que se mantiene el boton pulsado
	 */
	public int getButtonCounter(int index);
}

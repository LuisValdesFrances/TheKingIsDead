package com.luis.lgameengine.gameutils.gameworld;

/**
 * 
 * @author Luis Valdes Frances
 */
public class GameCamera {
	
	protected float posX;
	protected float posY;
	
	private float speedX;
	private float speedY;
	
	//private WorldConver worldConver;
	
	private float multiplicatorFPS;
	
	public static final float PRECISION_SPEED = 12f;
	
	/**
	 * Crea una instancia de GameCamera a la cual se le puede aplicar un modificador de pharallax
	 * @param _fPosX: Posicion x de la camara en el mundo
	 * @param _fPosY: Posicion y de la camara en el mundo
	 * @param _fMultiplicatorFPS: Multiplicador de movimiento. Puede ser 1 si no se desea ningun modificador
	 */
	public GameCamera(WorldConver worldConver, float posX, float posY, float multiplicatorFPS){
		//this.worldConver = worldConver;
		this.multiplicatorFPS = multiplicatorFPS;
		this.posX = posX;
		this.posY = posY;
	}
	
	protected void moveCamera(float fDestinationX, float fDestinationY){
		float distX = Math.abs(fDestinationX - posX);
		float distY = Math.abs(fDestinationY - posY);

		if (fDestinationX != posX) {
			if (posX < fDestinationX) {
				speedX = ((distX / PRECISION_SPEED) * multiplicatorFPS + 1f);
				posX += speedX;//  / fModPharallax;
				if (posX > fDestinationX) {
					posX = fDestinationX;
				}
			}
			else if (posX > fDestinationX) {
				speedX = ((distX / PRECISION_SPEED) * multiplicatorFPS + 1f);
				posX -= speedX;//  / fModPharallax;
				if (posX < fDestinationX) {
					posX = fDestinationX;
				}
			}
		}
		//Limito
		//posX = Math.max(posX, 0);
		//posX = Math.min(posX, worldConver.getWorldWidth()-worldConver.getLayoutX());
		
		if (fDestinationY != posY) {
			if (posY < fDestinationY) {
				speedY = ((distY / PRECISION_SPEED) * multiplicatorFPS + 1f);
				posY += speedY;// / fModPharallax;
				if (posY > fDestinationY) {
					posY = fDestinationY;
				}
			}
			else if (posY > fDestinationY) {
				speedY = ((distY / PRECISION_SPEED) * multiplicatorFPS + 1f);
				posY -= speedY;//  / fModPharallax;
				if (posY < fDestinationY) {
					posY = fDestinationY;
				}
			}
		}
		//Limito
		//posY = Math.max(posY, 0);
		//posY = Math.min(posY, worldConver.getWorldHeight()-worldConver.getLayoutY());
	}
	
	public void updateCamera(float fDestinationX, float fDestinationY) {
		moveCamera(fDestinationX, fDestinationY);
	}
	
	public float getPosX() {
		return posX;
	}
	
	public void setPosX(float _fPosX) {
		posX = _fPosX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float _fPosY) {
		posY = _fPosY;
	}
	
}

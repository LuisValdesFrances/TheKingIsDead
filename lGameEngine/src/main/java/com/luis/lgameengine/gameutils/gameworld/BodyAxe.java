package com.luis.lgameengine.gameutils.gameworld;


public class BodyAxe extends RigidBody{
	
	protected float newPosZ;
	private int layerZ;
	private float modLayerZ;
	private float tileSizeH;
	
	private float soilHight;
	
	protected void init(float posX, float posY, float posZ, float width, float height, float tileH){
		super.init(posX, posY, width, height);
		this.tileSizeH = tileH;
		this.layerZ = (int)(posZ/tileH);
		this.modLayerZ = 0;
		
	}
	
	@Override
	protected void checkColision2(int[][] tilesMatrixID, float tileW, float tileH, int numFiles, int numColumns){
	    	
	    ResetDataColisions();
	    	
	    int levelWidth = numColumns * (int)tileW;
	    int levelHeight = numFiles * (int)tileH;
	    	
	    colisionY(tilesMatrixID, tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, tileH);
	    
	    colisionX(tilesMatrixID, tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, tileH);
	    	
	    //La altura del es negativa, porque que la colision, para adaptarse a colisionY y colisionX se
	    //tiene que hacer justo un tile mas "arriba" que el que tocaria.
	    colisionZ(tilesMatrixID, tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, -tileH);
	    
	}
	
	/**
	 * Esto hay que retacarlo. Se debe d pasar un array de tipo de tile, 
	 * con el tipo de coilision correspondiente
	 */
	public static final int NONE = 1;
	public static final int UP = 2;
	public static final int CONTINUE = 3;
	public static final int END_UP = 4;
	public static final int DOWN = 5;
	public static final int END_DOWN = 6;
	
	/*/
	 * 
	 */
	public static final int COLISION_ALL = 0;
	public static final int COLISION_V = 1;
	public static final int COLISION_H = 2;
	public void buildLayerColisionVH(
			int[][] tilesMatrixID, int[][] tilesMatrixLayerID, float tileH, int layerZ, int colisionType){
		for(int i = 0; i < tilesMatrixID.length; i++){
			for(int j = 0; j < tilesMatrixID[i].length; j++){
				tilesMatrixLayerID[i][j] = 0;
			}
		}
		
		//left -> left -> up -> up -> up -> down -> left -> left
		/**
		 * COLISION ALL
		 * 
		 * 
		 *   ***
		 *   * ***
		 * *** 
		 * 
		 *
		 * COLISION V
		 * 
		 * 
		 *   * *
		 *   * *
		 *   * 
		 * 
		 *
		 * COLISION H
		 * 
		 * 
		 *   ***
		 *     ***
		 * *** 
		 * 
		 */
		
		int i = layerZ;
		int j = 0;
		int direction = -1;
		try{
		while(j < tilesMatrixLayerID[layerZ].length){
			
			if(colisionType != COLISION_ALL){
				if(colisionType == COLISION_H){
					if(tilesMatrixID[i][j] != CONTINUE){
						tilesMatrixLayerID[i][j] = tilesMatrixID[i][j];
					}
				}else if(colisionType == COLISION_V){
					if(
							tilesMatrixID[i][j] == CONTINUE || 
							tilesMatrixID[i][j] == UP || 
							tilesMatrixID[i][j] == DOWN ||
							tilesMatrixID[i][j] == END_UP ||
							tilesMatrixID[i][j] == END_DOWN){
						tilesMatrixLayerID[i][j] = tilesMatrixID[i][j];
					}
				}
			}else{
				tilesMatrixLayerID[i][j] = tilesMatrixID[i][j];
			}
			
			
			switch(tilesMatrixID[i][j]){
			
			case UP: 
					direction = tilesMatrixID[i][j];
					if(i > 0){
						i--; 
					}else{
						j++;
					}
				break;
			case DOWN: 
					direction = tilesMatrixID[i][j];
					if(i < tilesMatrixID.length-1){
						i++; 
					}else{
						j++;
					}
				break;
			case CONTINUE:
				if(direction == UP){
					if(i > 0){
						i--; 
					}else{
						j++;
					}
				}else{
					if(i < tilesMatrixID.length-1){
						i++; 
					}else{
						j++;
					}
				}
				break;
			
				default: j++; break;
				
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}
	
	public void buildLayerColisionZ(
			int[][] tilesMatrixID, int[][] tilesMatrixLayerID, float tileH){
		for(int i = 0; i < tilesMatrixID.length; i++){
			for(int j = 0; j < tilesMatrixID[i].length; j++){
				if(tilesMatrixID[i][j] != 1){
					//Chequeo si el tile esta en la capa Y correcta
					int playerLayerY = (int)(getPosY() / tileH);
					int tileLayerY = 0;
					int l = i;
					float lf = 0;
					while(l < tilesMatrixID.length-2){
						l++;
						
						if(tilesMatrixID[l][j] == CONTINUE){
							lf += 1;
						}
						else if(tilesMatrixID[l][j] != NONE){
							lf += 0.5f;
						}
						if(lf >= 1){
							tileLayerY--;
							lf = lf%1;//Obtiene la parte decimal
						}
					}
					if(tileLayerY == playerLayerY){
						tilesMatrixLayerID[i][j] = CONTINUE;
					}
				}else{
					tilesMatrixLayerID[i][j] = 0;
				}
			}
		}
	}
	
	@Override
	protected void colisionY(int[][] tilesMatrixID, float tileW, float tileH, int levelWidth, int levelHeight, 
			float newPosX, float newPosY, float width, float height){
		
		
		//La posicion Y pasa a ser una posicion local, as� que para que colisione con la capa de tiles correcta,
	    //se debe de sumar la posicion Z
	    float l = ((layerZ)*tileSizeH);
	    newPosY = l+(this.newPosY);
		
		if(newPosX - width/2 >= 0 && newPosX + width/2 <= levelWidth && newPosY - height >= 0 && newPosY <= levelHeight){
			
			//Transformar la matriz para obtener solo la capa de tiles respecto a la posicion z.
			int[][] tilesMatrixLayerID = new int[tilesMatrixID.length][tilesMatrixID[0].length];
			buildLayerColisionVH(tilesMatrixID, tilesMatrixLayerID, tileH, layerZ, COLISION_H);
			
			
    		float newSpeedY = 0;
    		if(elasticity > 0){
    			newSpeedY = (speedY * elasticity)*-1;
    		}
    		/*
    		 * Para chequear las colisiones en y color el punto de anclaje en x de personaje
    		 * a la izquierda y anclaje y en los pies/cabeza para saber si colisiona por debajo o por arriba
    		 */
    		int colDown = checkColisionY(
    				tilesMatrixLayerID, 
	    			posX-width/2, 
	    			newPosY, width, tileW, tileH);
	    	if(colDown != -1){
	    		speedY = newSpeedY;
	    		this.posY = Math2D.adjustDiv(this.posY, tileH);
	    		this.newPosY = posY;
	    		this.soilHight = posY;
	    		isColBotton = true;
	    	}
	    }
	    posY = this.newPosY + moveY;
	    this.newPosY = posY;
    }
	
	@Override
	protected void colisionX(int[][] tilesMatrixID, float tileW, float tileH, int levelWidth, int levelHeight,
			float newPosX, float newPosY, float width, float height){
		
		int[][] tilesMatrixLayerID = new int[tilesMatrixID.length][tilesMatrixID[0].length];
		buildLayerColisionVH(tilesMatrixID, tilesMatrixLayerID, tileH, layerZ, COLISION_V);
		
		//La posicion Y pasa a ser una posicion local, asi que para que colisione con la capa de tiles correcta,
	    //se debe de sumar la posicion Z
	    float l = ((layerZ)*tileSizeH);
	    newPosY = l+(this.newPosY);
		
		super.colisionX(tilesMatrixLayerID,
				tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, height);
	}
	
	@Override
	protected void colisionZ(int[][] tilesMatrixID, float tileW, float tileH, int levelWidth, int levelHeight, 
			float newPosX, float newPosY, float width, float height){
		
		{
			newPosY = newPosZ + getPosY();
			
			int[][] tilesMatrixLayerID = new int[tilesMatrixID.length][tilesMatrixID[0].length];
			buildLayerColisionZ(tilesMatrixID, tilesMatrixLayerID, tileH);
			
			
			int colTop = -1;
			
			if(isColBotton && getJump() == 0){
				colTop = checkColisionY(
						tilesMatrixLayerID, 
						newPosX - width/2, 
		    			newPosY-height, width, tileW, tileH);
				
				if(colTop != -1){
					
					float pZY = (colTop*tileH) - getPosY();
					pZY += (tileH+height);
					
					//this.layerZ = (int)(pZY/tileH);
					//this.modLayerZ = 0;
					this.newPosZ = pZY;
					isColTop = true;
					
				}
			}
			
			
			if(isColBotton && getJump() == 0){
				//Ajuste de cambio de capa tiles
				if(newPosZ > getPosZ()){
					if(modLayerZ > 0){
						layerZ++;
						modLayerZ = 0;
					}
				}
				else if(newPosZ < getPosZ()){
					if(modLayerZ < -tileH){
						layerZ--;
						modLayerZ = 0;
					}
				}
		    	
				if(newPosZ != 0){
					float m = newPosZ-getPosZ();
					modLayerZ += m;
					newPosZ = getPosZ();
				}
			}
		}
	}
	
	public void movePosZ(float newPosZ){
		this.newPosZ = newPosZ;
	}
	
	public float getPosZ() {
		return (layerZ*tileSizeH) + modLayerZ;
	}

	/**
	 * Informacion de control: Solo tiene relevancia a "nivel" de debug.
	 */
	public int getLayer(){
		return layerZ;
	}
	

	public float getModLayerZ() {
		return modLayerZ;
	}
	
	public float getJump(){
		return (getPosY() - soilHight);
	}
	
	
}

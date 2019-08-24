package com.luis.lgameengine.gameutils.gameworld;


/**
 * 
 * @author Luis Valdes Frances
 * 
 * Todos los objetos que extiendan estan clases estaran expuestas a la fisica en 2D en
 * cuanto a efectos de la gravedad y colisiones. 
 * El punto de anclaje con el que se realizan las colisiones es abajo-centro
 *
 */
public class RigidBody {
	
	/**
	 * Transforma una unidad de unity en pixeles
	 * @param unityTileSize Tamanyo de los tiles en Unity3D en unidades de Unity3D
	 * @param tileSize Tamanyo de los tiles pixeles
	 * @param value Valor a convertir (Extraido de Unity3D)
	 * @return
	 */
	public static float transformUnityValue(float unityTileSize, float tileSize, float value){
		return (tileSize * value) / unityTileSize;
	}
	
	protected float weight;
	/**
	 * rango de 0 a 1f;
	 */
	protected float elasticity;

    /*
    Antiguas fuerzas
    */
    protected float speedX;
    protected float speedY;

    public float getSpeedX()
    {
        return speedX;
    }
    public float getSpeedY()
    {
        return speedY;
    }

    public void setSpeedX(float newSpeedX)
    {
        this.speedX = newSpeedX;
    }
    public void setSpeedY(float newSpeedY)
    {
        this.speedY = newSpeedY;
    }

    protected float posX;
    protected float posY;
    protected float width;
    protected float height;
    protected float newPosX;
    protected float newPosY;
    protected float gravityForce;

    protected float moveX;
    protected float moveY;

    public float GetMoveX()
    {
        return moveX;
    }
    public void SetMoveX(float newMoveX)//TC
    {
        this.moveX = newMoveX;
    }
    public float GetMoveY()
    {
        return moveY;
    }
    public void SetMoveY(float newMoveY)
    {
        this.moveY = newMoveY;
    }
   

    /*
    Debug
    */
    protected boolean isColTop;
    protected boolean isColBotton;
    protected boolean isColRight;
    protected boolean isColLeft;


    /*
    Colision side
    */
    //Dimension 0
    protected static int COL_RIGHT = 0;
    protected static int COL_LEFT = 1;
    protected static int COL_TOP = 2;
    protected static int COL_BOTTON = 3;
    //Dimension 1
    protected static int COL_X = 0;
    protected static int COL_Y = 1;
    protected static int COL_WIDTH = 2;
    protected static int COL_HEIGHT = 3;

    protected float[][] colisionDataPosition = new float[COL_BOTTON + 1][COL_HEIGHT + 1];
    /*
    */
    protected void init(float posX, float posY, float width, float height){
        ResetDataColisions();

        this.speedX = 0f;
        this.speedY = 0f;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.newPosX = posX;
        this.newPosY = posY;
    }

    /**
     * 
     * @param deltaTime
     * @param _iTilesMatrixID
     * @param _fTileW
     * @param _fTileH
     * @param _iScreenW
     * @param _iScreenH
     */
    protected void runPhysics (
    		float deltaTime, 
    		int[][] _iTilesMatrixID, 
    		float _fTileW, float _fTileH, int _iScreenW, 
    		int _iScreenH){

        //Aplico la gravedad
        RunGravity(deltaTime, _fTileW, _fTileH);

        //Aplico las distintas fuerzas
        RunForceX(deltaTime);

        checkColision2(_iTilesMatrixID, _fTileW, _fTileH, _iTilesMatrixID.length, _iTilesMatrixID[0].length);
        /*
        if(systemColision == 0){
        	checkColision2(_iTilesMatrixID, _fTileW, _fTileH, _iTilesMatrixID.length, _iTilesMatrixID[0].length);
        }else{
        	CheckColision(_iTilesMatrixID, _fTileW, _fTileH, _iScreenW, _iScreenH);
        }
        */
        
        //Si se le esta aplicando velocidad, la decremento
        DecrementSpeed();
    }

    protected void RunGravity(float deltaTime, float _fTileW, float _fTileH){
        {
            float cAcceleration = gravityForce * weight;
            /*
            La aceleracion es la modificacion de la velocidad (una cantidad), asi que tambien debe de multiplicarse
            por el delta time, ya que si el movil hace menos pasadas de frames, esta modificacion, aplicara menos
            veces y dismuira mas lentamente.
            */
            speedY += (cAcceleration * deltaTime);

            float movement = speedY * deltaTime;

            /*
            Limitador para que la aceleracion no crezca mas de un cierto valor
            if (movement > _fTileH / 4f)
            {
                movement = _fTileH / 4f;
            }
            */
            if(movement >= 0){
            	movement = Math.min(movement, _fTileH / 4f);
            }else{
            	movement = Math.max(-_fTileH / 4f, movement);
            }
            //

            newPosY = posY + movement;
        }
    }

    protected void RunForceX(float deltaTime){
        if (speedX != 0f){
            float cAcceleration = (gravityForce/2f) * weight;

            float rest = (cAcceleration * deltaTime);
            if (!isColisionBotton()){
                rest = rest / 2f;
            }

            if (speedX > 0){
                speedX -= rest;
                if (speedX < 0) speedX = 0;
            }
            else{
                speedX += rest;
                if (speedX > 0) speedX = 0;
            }

            float mov = speedX * deltaTime;
            newPosX = posX + mov;
        }
    }

    void ResetDataColisions(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                colisionDataPosition[i][j] = -1;
            }
        }
        isColTop = false;
        isColBotton = false;
        isColRight = false;
        isColLeft = false;
    }
    
    public boolean isColision(RigidBody rigidBody){
    	return isColisionX(rigidBody) && isColisionY(rigidBody);
		}
    
    public boolean isColisionX(RigidBody rigidBody){
        return isColisionCenterBottonX(getPosX(), getWidth(), rigidBody.getPosX(), rigidBody.getWidth());
    }
    
    public boolean isColisionY(RigidBody rigidBody){
        return isColisionCenterBottonY(getPosY(), getHeight(), rigidBody.getPosY(), rigidBody.getHeight());
    }
    
    public boolean isColisionCenterBottonX(
            float objX1, float objW1,
            float objX2, float objW2){
        if (objX1 + (objW1 / 2f) >= objX2 - (objW2 / 2f) && objX1 - (objW1 / 2f)  <= objX2 + (objW2 / 2f)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isColisionCenterBottonY(
            float objY1, float objH1,
            float objY2, float objH2){
        if (objY1 >= objY2 - objH2 && objY1 - objH1 <= objY2){
            return true;
        }
        else{
        	return false;
        }
    }
    
    /**
     * 
     * @param tilesMatrixID
     * @param pX
     * @param pY
     * @param pH
     * @param tileW
     * @param tileH
     * @return Array con la informacion del tile con el que colisiona: 0:id, 1:fila, 2:columna, 3:y, 4:x
     */
    public int[] checkTileColisionX(
    		int[][] tilesMatrixID, 
    		int tileId, 
    		float pX, float pY, 
    		float pW, float pH, 
    		float tileW, float tileH){
    	
    	int indexY;
    	int indexX;
    	//x
    	int offsetY = 0;
    	for(int i = 0; i <= (pH/tileH); i++){
    		offsetY = (int)tileH*i;
    		
    		if(i != 0){
                offsetY--;
            }
    		
	    	indexY = (int)((pY+offsetY) / tileH);
	    	indexX = (int)((pX) / tileW);
	    	//Limitadores
	    	indexY = Math.min(indexY, tilesMatrixID.length-1);
	    	indexY = Math.max(indexY, 0);
	    	indexX = Math.min(indexX, tilesMatrixID[0].length-1);
	    	indexX = Math.max(indexX, 0);
	    	
	    	if(tilesMatrixID[indexY][indexX] == tileId){
	    		return new int[]{tilesMatrixID[indexY][indexX], indexY, indexX, (int)(indexY*tileH), (int)(indexX*tileW)};
	    	}
	    }
    	return null;
    }
    
    public int[] checkTileColisionY(
    		int[][] tilesMatrixID, 
    		int tileId, 
    		float pX, float pY, 
    		float pW, float pH, 
    		float tileW, float tileH){
    	
    	int indexY;
    	int indexX;
    	//y
    	int offsetX = 0;
    	for(int i = 0; i <= (pW/tileW); i++){
    		offsetX = (int)tileW*i;
    		
    		if(i != 0){
                offsetX--;
            }
    		
	    	indexY = (int)((pY) / tileH);
	    	indexX = (int)((pX+offsetX) / tileW);
	    	
	    	//Limitadores
	    	indexY = Math.min(indexY, tilesMatrixID.length-1);
	    	indexY = Math.max(indexY, 0);
	    	indexX = Math.min(indexX, tilesMatrixID[0].length-1);
	    	indexX = Math.max(indexX, 0);
	    	
	    	if(tilesMatrixID[indexY][indexX] == tileId){
	    		return new int[]{tilesMatrixID[indexY][indexX], indexY, indexX, (int)(indexY*tileH), (int)(indexX*tileW)};
	    	}
    	}
    	return null;
    }
    
    public boolean checkColision(int[][] tilesMatrixID, float pX, float pY, float pW, float pH, float tileW, float tileH){
    	return
    			!(checkColisionX(tilesMatrixID, pX, pY, pH, tileW, tileH) == -1 && 
    			checkColisionY(tilesMatrixID, pX, pY, pW, tileW, tileH) == -1);
    }
    
    /**
     * Sistema 2
    */
    protected int checkColisionY(int[][] tilesMatrixID, float pX, float pY, float pW, float tileW, float tileH){
    	//Izquierda
    	int indexY;
    	int indexX;
    	int offsetX = 0;
    	for(int i = 0; i <= (pW/tileW); i++){
    		offsetX = (int)tileW*i;
    		
    		if(i != 0){
                offsetX--;
            }
    		
	    	indexY = (int)((pY) / tileH);
	    	indexX = (int)((pX+offsetX) / tileW);
	    	if(indexX < 0 || indexY < 0 || indexY > tilesMatrixID.length-1 || indexX > tilesMatrixID[indexY].length-1){
	    		return -1;
	    	}
	    	if(tilesMatrixID[indexY][indexX] != 0){
	    		return indexY;
	    	}
    	}
    	return -1;
    }
    
    protected int checkColisionX(int[][] tilesMatrixID, float pX, float pY, float pH, float tileW, float tileH){
    	//Cabeza
    	int indexY;
    	int indexX;
    	int offsetY = 0;
    	for(int i = 0; i <= (pH/tileH); i++){
    		offsetY = (int)tileH*i;
    		
    		if(i != 0){
                offsetY--;
            }
    		
	    	indexY = (int)((pY+offsetY) / tileH);
	    	indexX = (int)((pX) / tileW);
	    	if(indexX < 0 || indexY < 0 || indexY > tilesMatrixID.length-1 || indexX > tilesMatrixID[indexY].length-1){
	    		return -1;
	    	}
	    	if(tilesMatrixID[indexY][indexX] != 0){
	    		return indexX;
	    	}
	    }
    	return -1;
    }
    
    protected void checkColision2(int[][] tilesMatrixID, float tileW, float tileH, int numFiles, int numColumns){
    	
    	ResetDataColisions();
    	
    	int levelWidth = numColumns * (int)tileW;
    	int levelHeight = numFiles * (int)tileH;
    	
    	colisionY(tilesMatrixID, tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, height);
    	
    	colisionX(tilesMatrixID, tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, height);
    	
    	colisionZ(tilesMatrixID, tileW, tileH, levelWidth, levelHeight, newPosX, newPosY, width, height);
	}
    
    protected void colisionY(int[][] tilesMatrixID, float tileW, float tileH, int levelWidth, int levelHeight,
    		float newPosX, float newPosY, float width, float height){
    	if(newPosX - width/2 >= 0 && newPosX + width/2 <= levelWidth && newPosY - height >= 0 && newPosY <= levelHeight){
    		float newSpeedY = 0;
    		if(elasticity > 0){
    			newSpeedY = (speedY * elasticity)*-1;
    		}
    		/*
    		 * Para chequear las colisiones en y color el punto de anclaje en x de personaje
    		 * a la izquierda y anclaje y en los pies/cabeza para saber si colisiona por debajo o por arriba
    		 */
	    	int colDown = checkColisionY(
	    			tilesMatrixID, 
	    			posX-width/2, 
	    			newPosY, width, tileW, tileH);
	    	if(colDown != -1){
	    		speedY = newSpeedY;
	    		this.newPosY = (colDown*tileH);
	    		isColBotton = true;
	    	}
	    	
	    	int colUp = checkColisionY(
	    			tilesMatrixID, 
	    			posX-width/2, 
	    			newPosY-height, width, tileW, tileH);
	    	if(colUp != -1){
	    		speedY = newSpeedY;
	    		this.newPosY = colUp*tileH + tileH + height;
	    		isColTop = true;
	    	}
    	}
	    posY = this.newPosY + moveY;
	    this.newPosY = posY;
    }
    
    protected void colisionX(int[][] tilesMatrixID, float tileW, float tileH, int levelWidth, int levelHeight,
    		float newPosX, float newPosY, float width, float height){
    	if(newPosX - width/2 >= 0 && newPosX + width/2 <= levelWidth && newPosY - height >= 0 && newPosY <= levelHeight){
	    	
	    	float newSpeedX = 0;
	    	if(elasticity > 0){
	    		newSpeedX = (speedX * elasticity)*-1;
	    	}
	    	
	    	int colRight = checkColisionX(
	    			tilesMatrixID, 
	    			newPosX + width/2, 
	    			newPosY-height, height, tileW, tileH);
	    	if(colRight != -1){
	    		speedX = newSpeedX;
	    		this.newPosX = colRight*tileW - width/2;
	    		isColRight = true;
	    	}
	    	
	    	int colLeft = checkColisionX(
	    			tilesMatrixID, 
	    			newPosX - width/2, 
	    			newPosY-height, height, tileW, tileH);
	    	if(colLeft != -1){
	    		speedX = newSpeedX;
	    		this.newPosX = colLeft*tileW + tileW + width/2;
	    		isColLeft = true;
	    	}
	    }
	    posX = this.newPosX + moveX;
	    this.newPosX = posX;
    }
    
    protected void colisionZ(int[][] tilesMatrixID, float tileW, float tileH, int levelWidth, int levelHeight, 
    		float newPosX, float newPosY, float width, float height){
    	
    }
    
    /**
     * Sistema 1
    */
//    protected int[] indexX = new int[2];
//    protected int[] indexY = new int[2];
//    protected void PutIndexX(float tileW, int numColums, float screenX){
//    	float minX = getPosX() - screenX / 2f;
//        float maxX = getPosX() + screenX / 2f;
//        float levelWidth = numColums * tileW;
//        indexX[0] = (int)((minX * numColums) / levelWidth);
//        indexX[1] = (int)((maxX * numColums) / levelWidth);
//
//        if (indexX[0] < 0) indexX[0] = 0;
//        else if (indexX[1] > numColums) indexX[1] = numColums;
//    }
//
//    protected void PutIndexY(float tileH, int numFiles, float screenY){
//    	 float minY = getPosY() - screenY / 2f;
//	     float maxY = getPosY() + screenY / 2f;
//	     float levelHeight = numFiles * tileH;
//	     indexY[0] = (int)((minY * numFiles) / levelHeight);
//	     indexY[1] = (int)((maxY * numFiles) / levelHeight);
//	
//	     if (indexY[0] < 0) indexY[0] = 0;
//	     else if (indexY[1] > numFiles) indexY[1] = numFiles;
//    }
//
//	void CheckColision(int[][] tilesMatrixID, float tileW, float tileH, int screenW, int screenH) {
//		
//		
//		/*
//    	 * Como los tiles se pintan con anclaje arriba-izquierda, pero se procesan en la logica abajo centro
//    	 * es necesario desplazar todos los objetos para que coincidan en la posicion real de los tiles 
//    	 */
//    	float adjustTileX = tileW/2;
//    	float adjustTileY = tileH;
//		
//		
//		PutIndexX(tileW, tilesMatrixID[0].length, screenW);
//		PutIndexY(tileH, tilesMatrixID.length, screenH);
//		ResetDataColisions();
//		boolean colX = false;
//		boolean colY = false;
//
//		float distX = newPosX - getPosX();
//		float distY = newPosY - getPosY();
//
//		for (int f = indexY[0]; f < indexY[1] && (!colX || !colY); f++) {
//			for (int c = indexX[0]; c < indexX[1] && (!colX || !colY); c++) {
//				if (tilesMatrixID[f][c] > 0) {
//					float tX = c * tileW + adjustTileX;
//					float tY = f * tileH + adjustTileY;
//					// String t = _iTilesMatrixID[f][c].GetTag();
//
//					while (isColisionCenterBottonY(getPosY(), getHeight(), tY, tileH)
//							&& isColisionCenterBottonX(getPosX() + distX, getWidth(), tX, tileW) && distX != 0) {
//						// Solo se llama 1 vez dentro del bucle while
//						if (!colX) {
//							colX = true;
//							speedX = 0f;
//							if (distX > 0) {
//								isColRight = true;
//							} else {
//								isColLeft = true;
//							}
//						}
//						distX = distX / 2f;
//					}
//					newPosX = getPosX() + distX;
//				}
//			}
//		}
//
//		for (int f = indexY[0]; f < indexY[1] && (!colX || !colY); f++) {
//			for (int c = indexX[0]; c < indexX[1] && (!colX || !colY); c++) {
//				if (tilesMatrixID[f][c] > 0) {
//					float tX = c * tileW + adjustTileX;
//					float tY = f * tileH + adjustTileY;
//					while (isColisionCenterBottonY(getPosY() + distY, getHeight(),
//							tY, tileH)
//							&& isColisionCenterBottonX(getPosX() + distX, getWidth(), tX, tileW) && distY != 0) {
//						// Solo se llama 1 vez dentro del bucle while
//						if (!colY) {
//							colY = true;
//							speedY = 0f;
//							if (distY > 0) {
//								isColBotton = true;
//							} else {
//								isColTop = true;
//							}
//						}
//						distY = distY / 2f;
//					}
//					newPosY = getPosY() + distY;
//				}
//			}
//		}
//		AppliColisions();
//	}
//    
//    protected boolean isColisionBotton(float objX1, float objY1, float objW1, float objH1,
//			float objX2, float objY2, float objW2, float objH2){
//		return isColisionCenterBottonX(objX1, objW1, objX2, objW2) && isColisionCenterBottonY(objY1, objH1, objY2, objH2);
//	}
//    
//    protected void AppliColisions(){
//    	posX = newPosX + moveX;
//    	posY = newPosY + moveY;
//    	newPosX = posX;
//    	newPosY = posY;
//    }

    public void DecrementSpeed(){
        moveX = 0;
        moveY = 0;
        /*
        if (speedX > 0f)
        {
            speedX -= ((speedX / 32f) + 0.0005f);
            if(speedX < 0f)
            {
                speedX = 0f;
            }
        }
        else if (speedX < 0f)
        {
            speedX -= ((speedX / 32f) - 0.0005f);
            if (speedX > 0f)
            {
                speedX = 0f;
            }
        }
        */
    }

    public boolean isColisionRight(){
        return isColRight;
    }
    
    public boolean isColisionLeft(){
        return isColLeft;
    }
    
    public boolean isColisionTop(){
        return isColTop;
    }
    
    public boolean isColisionBotton(){
        return isColBotton;
    }

    public float getPosX(){
        return posX;
    }
    
    public void setPosX(float newPosX){
        this.posX = newPosX;
        this.newPosX = this.posX;
    }

    public void movePosX(float newPosX){
        this.newPosX = newPosX;
    }

    public void movePosY(float newPosY){
        this.newPosY = newPosY;
    }

    public float getPosY(){
        return posY;
    }

    public void setPosY(float newPosY){
        this.posY = newPosY;
        this.newPosY = this.posY;
    }
    
    public float getWidth(){
        return width;
    }
    
    public float getHeight(){
        return height;
    }
	public float getWeight() {
		return weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public float getGravityForce() {
		return gravityForce;
	}
	
	public void setGravityForce(float gravityForce) {
		this.gravityForce = gravityForce;
	}
	
	public float getElasticity() {
		return elasticity;
	}
	
	public void setElasticity(float elasticity) {
		this.elasticity = elasticity;
	}
	
	
	

	
    
}

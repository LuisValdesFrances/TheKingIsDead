package com.luis.lgameengine.gameutils.gameworld;

import java.io.DataInputStream;
import java.io.IOException;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;

/**
 * 
 * @author Luis Valdes Frances
 */
public class TileManager {
	
	//Lectura del .map
	private int numbLevelTileX;
	private int numbLevelTileY;
	private int levelTileWidth;
	private int levelTileHeight;
	private int[][] imgTileData; //0: image, 1: tile width, tile height

	public int numberLayers;
	public int numberImages;
	
	
	public static int[][][] tilesMatrixID; //0: Layer, 1: File, 2 Column
	public int[][] getLayerID(int _iLayer) {
        return tilesMatrixID[_iLayer];
    }
	
	
	
	/**
	 * Create a object level width diferents layers from a specific binary .Map make width LVTiledParser
	 * @param _iTilesBinPath: Archive .map created by LVTiledParser
	 * @param _iNumberTilesLevelX: Number tiles in axis x for level
	 * @param _iNumberTilesLevelY: Number tiles in axis y for level
	 * @param _iSizeTiles: Array of size of tiles where each dimension (of the array) is a tile size of this layer
	 */
	public TileManager(String tilesBinPath){
		loadLevel(tilesBinPath);
	}
	
	//ID conversion
	//Guarda el rango de ID de cada capa
	public int[][] idRange; 
	public void idConversionData(Image[] imgLayers){
		idRange = new int[numberImages][1];
		
		for(int i = 0; i < idRange.length; i++){
			int numTilesX = imgLayers[i].getWidth() /  imgTileData[i][0];
			int numTilesY = imgLayers[i].getHeight() /  imgTileData[i][1]; 
			idRange[i][0] = numTilesX * numTilesY;
			//Si estoy por encima de la capa 0 sumamos sus ID acumulados
			for(int j = 0; j < i; j++){
				numTilesX = imgLayers[j].getWidth() /  imgTileData[j][0];
				numTilesY = imgLayers[j].getHeight() /  imgTileData[j][1]; 
				idRange[i][0] += (numTilesX * numTilesY);
			}
		}
		System.out.println("");
	}
	
	/**
	 * 
	 * @param tilesBinPath
	 */
	public void loadLevel(String tilesBinPath) {

		DataInputStream dis = null;

		// dis = new DataInputStream(new FileInputStream(_iTilesBinPath));
		dis = new DataInputStream(Settings.getInstance().getActiviy().getClass().getResourceAsStream(tilesBinPath));
		//Leo la informacion de la cabecera en el mismo orden en que se escribe:
		try {
			numbLevelTileX = dis.readShort();
			numbLevelTileY = dis.readShort();
			levelTileWidth = dis.readShort();
			levelTileHeight = dis.readShort();
			numberLayers = dis.readShort();
			numberImages = dis.readShort();
			
			imgTileData = new int[numberImages][2];
			
			for(int i = 0; i < imgTileData.length; i++){
				imgTileData[i][0] = dis.readShort();//Ancho	
				imgTileData[i][1] = dis.readShort();//Alto
			}
			
			tilesMatrixID = new int[numberLayers][numbLevelTileY][numbLevelTileX];
			
			// Leo las capas una a una
			for (int l = 0; l < tilesMatrixID.length; l++) {
				System.out.println("Start to load tiles of layer " + l);
				// Filas
				for (int f = 0; f < tilesMatrixID[l].length; f++) {
					// Columnas
					for (int c = 0; c < tilesMatrixID[l][f].length; c++) {
						
							/*
							 * El la llamada de lectura al tipo de dato debe de conincidir con el formato con que se guarda
							 */
							//iTilesMatrixID[l][f][c] = dis.readByte();
							tilesMatrixID[l][f][c] = dis.readShort();
						
						System.out.print(tilesMatrixID[l][f][c] + ",");
					}
					System.out.println();
				}
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A partir de un ID absoluto, devuelve el index de la imagen
	 * @param id El ID absoluto de la tile
	 * @return El ID de la imagen que contiene el tile
	 */
	private int getImageByID(int id){
		for(int i = 0; i < idRange.length; i++){
			if(id <= idRange[i][0]){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * A partir de un ID absoluto, devuelve el ID relativo correspondiente a la imagen
	 * @param id El ID absoluto de la tile
	 * @return El ID relativo
	 */
	private int getRelativeID(int id){
		//Si esta en el primer rango, no se aplica modificador
		if(id <= idRange[0][0]){
			return id;
		}else{
			return id - idRange[getImageByID(id) - 1][0];
		}
	}
	/**
	 * 
	 * @param _g
	 * @param imgLayers
	 * @param worldConver
	 * @param cameraX
	 * @param cameraY
	 * @param modDrawX
	 * @param modDrawY
	 * @param isDrawFast
	 * @param anchor
	 */
	public void drawLayers(Graphics _g, Image[] imgLayers,
			WorldConver worldConver, 
			float cameraX, float cameraY,
			int[] modDrawX, int[] modDrawY,
			int anchor){
		
		for(int i = 0; i < imgLayers.length; i++){
			drawLayer(_g, imgLayers, i, worldConver, cameraX, cameraY, modDrawX[i], modDrawY[i], anchor);
		}
	}



	/**
	 * 
	 * @param _g
	 * @param imgLayers
	 * @param layer
	 * @param worldConver
	 * @param cameraX
	 * @param cameraY
	 * @param modDrawX
	 * @param modDrawY
	 * @param isDrawFast
	 * @param anchor
	 */
	public void drawLayer(Graphics _g, Image[] imgLayers, int layer,
			WorldConver worldConver,
			float cameraX, float cameraY,
			int modDrawX, int modDrawY,
			int anchor){

	        for (int f = 0; f < tilesMatrixID[layer].length; f++) {

	            for (int c = 0; c < tilesMatrixID[layer][f].length; c++) {

	                if (tilesMatrixID[layer][f][c] > 0) {
	                    int relativeID = getRelativeID(tilesMatrixID[layer][f][c]);


	                    //Obtengo el index de la imagen en la que se almacena el tile:
	                    int imgIndex = getImageByID(tilesMatrixID[layer][f][c]);
	                    int tileW = imgTileData[imgIndex][0];
	                    int tileH = imgTileData[imgIndex][1];
	                    /*
	                     * Cuando el tamaño de un tile es mayor (en y) que el tile base, el punto de anclaje de las posiciones que ocupa en la
	                     * en la matriz es abajo-izquierda y no arriba-izquierda. Por ejemplo:
	                     * 
	                     * Para una matrix definida de 8x8 de dos capas, se representara en la posicion 0,0 un tile de 1x1. Esto es arriba-izquierda:
	                     * 
	                     * 1 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 
	                     * Si en otra capa tenemos un tile de 4x4 para la misma posicion, se representara en el siguiente espacio. Esto es abajo-izquierda:
	                     * 
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 1 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 0 0 0 0 0 0 0 0
	                     * 
	                     * A si pues, habra que tener en cuenta que se representara la posicion 0,4 cuando se obtenga desde los metodos "geters", 
	                     * pese a que esta en la poscion 0,0 y que podria devolver que esta fuera de la pantalla cuando todavia no sea asi.
	                     * 
	                     * A continuacion se obtendra el modificador en y, la diferencia de representarse arriba-izquierda a abajo-izquierda
	                     */
	                    int moveY = 0;
	                    if (tileH > levelTileHeight) {
	                        moveY = (tileH / levelTileHeight) - 1;
	                        if (moveY > 0) {
	                            moveY = (moveY * levelTileHeight);
	                        }
	                    }
	                    
	                    //Le quito el offset de la imagen del buffer
	                    int modBufferX = 0;
	                    int modBufferY = 0;
	                    /*
	                    if(bufferX !=0){
	                    if(posX +bufferX+ modDrawX< 0){
	                    	modBufferX = extraWidth;
	                    }else if(posX+bufferX+ modDrawX > worldConver.getLayoutX()){
	                    	modBufferX = -extraWidth;
	                    }
	                    }
	                    if(bufferY !=0){
	                    if(posY+bufferY+ modDrawY < 0){
	                    	modBufferY = extraHeight;
	                    }else if(posY+bufferY+ modDrawY > worldConver.getLayoutY()){
	                    	modBufferY = -extraHeight;
	                    }
	                    }
	                    */
	                    if (worldConver.isObjectInGameLayout(cameraX, cameraY,
	                            (levelTileWidth * c) + modBufferX,
	                            (levelTileHeight * f) + modBufferY - moveY,
	                            tileW, tileH)) {

	                        //Dependiendo del ID del tile RELATIVO posicion dentro del .PNG que forma la imagen, hay que aplicar un mod en x e y para pintarla correctamente.
	                        //Partiendo que se empieza a contar de 1 arriba a la izquierda y este se se incremente en +1 hacia la derecha (filas) y abajo (columnas)
	                        //obtengo dicho modificador en x e y a partir del ID de la imagen (El cual corresponde a la posicion de tile en el .PNG)
	                        

	                        int numberImagesX = imgLayers[imgIndex].getWidth() / tileW;
	                        //int numberImagesY = _vImgLayers[imgIndex].getHeight() / tileH;


	                        int modX = ((relativeID - 1) % numberImagesX);

	                        if (modX > numberImagesX) {
	                            modX = modX - numberImagesX;
	                        }

	                        modX = modX * tileW;

	                        int modY = ((relativeID - 1) / numberImagesX);
	                        modY = modY * tileH;
	                        //////////////////////////////////////////////////////////////////////////////////////////////////////

	                        int posX = worldConver.getConversionDrawX((int) cameraX, (levelTileWidth * c));
	                        int posY = worldConver.getConversionDrawY((int) cameraY, (levelTileHeight * f));
	                        
	                        if (moveY > 0) 
	                            posY -= moveY;
	                        
	                        //////////////////////////////////////////////////////////////////////////////////////////////////////
	                        _g.setClip(posX + modDrawX, posY + modDrawY, tileW, tileH);
	                        _g.drawImage(imgLayers[imgIndex], posX - modX + modDrawX, posY - modY + modDrawY, 0);

	                    }
	                }
	            }
	        }

	    }
	
	/**
	 * Doble buffer
	 */
	private Image buffer;
	
	public Image getBuffer() {
		return buffer;
	}
	public void setBuffer(Image buffer) {
		this.buffer = buffer;
	}

	private int extraWidth;
	private int extraHeight;
	
	public int getExtraWidth() {
		return extraWidth;
	}
	public void setExtraWidth(int extraWidth) {
		this.extraWidth = extraWidth;
	}
	public int getExtraHeight() {
		return extraHeight;
	}
	public void setExtraHeight(int extraHeight) {
		this.extraHeight = extraHeight;
	}

	private int offsetX;
	private int offsetY;
	private int bufferX;
	private int bufferY;
	
	public void enableBuffer(WorldConver worldConver, int tileSize, int screenWidth, int screenHeight, 
			float cameraX, float cameraY){
		
		buffer = new Image(screenWidth + tileSize*2, screenHeight + tileSize*2);
		extraWidth = buffer.getWidth() - screenWidth;
		extraHeight = buffer.getHeight() - screenHeight;
		offsetX = (int)cameraX;
		offsetY = (int)cameraY;
		bufferX = 0;
		bufferY = 0;
	}
	
	public boolean updateBuffer(float cameraX, float cameraY, int tileSize){
		boolean draw = false;
		
		if(Math.abs(bufferX) >= tileSize || Math.abs(bufferY) >= tileSize){
			draw = true;
			offsetX = (int)cameraX;
			offsetY = (int)cameraY;
		}
		bufferX = (int)cameraX-offsetX;
		bufferY = (int)cameraY-offsetY;
		
		/*
		if(draw){
			//buffer = new Image(buffer.getWidth(), buffer.getHeight());
		}
		*/
		return draw;
	}
	
	public void drawBuffer(Graphics _g, int screenWidth, int screenHeight){
		 _g.setClip(0, 0, screenWidth, screenHeight);
		 _g.drawImage(buffer, -extraWidth/2 - bufferX, -extraHeight/2 - bufferY, 0);
	}
	
	

}

package com.luis.strategy.map;

import java.util.List;

import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.strategy.GfxManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;


public class GameScene{
	
	private int serverValidator;

	public int getServerValidator() {
		return serverValidator;
	}

	public void setServerValidator(int serverValidator) {
		this.serverValidator = serverValidator;
	}

	private int map;
	private int playerIndex;
	private int turnCount;
	
	private List<Player>playerList;
	private List<Kingdom> kingdomList;
	
	private int numberPartsW;
	private int numberPartsH;
	
	private MapObject mapObject;
	
	public GameScene(
			int map,
			int mapX, int mapY,
			int numberPartsW, int numberPartsH) {
		this.map = map;
		this.numberPartsW = numberPartsW;
		this.numberPartsH = numberPartsH;
		mapObject = new MapObject(
				null,
				mapX, mapY, 
				GfxManager.imgMapList.get(0).getWidth()*numberPartsW, 
				GfxManager.imgMapList.get(0).getHeight()*numberPartsH,
				mapX, mapY, 
				GfxManager.imgMapList.get(0).getWidth()*numberPartsW, 
				GfxManager.imgMapList.get(0).getHeight()*numberPartsH,
				-1, -1) {
		};
	}
	
	public void init(){
		
	}
	
	public void update(
			MultiTouchHandler multiTouchHandler, 
			WorldConver worldConver, 
			GameCamera gameCamera, 
			float delta,
			boolean listenEvents){
		if(alphaFlag){
			alpha-= 60f*delta;
		}else{
			alpha+= 60f*delta;
		}
		if(alpha < 100f || alpha > 200f)
			alphaFlag = !alphaFlag;
		
		alpha = Math.max(100f, alpha);
		alpha = Math.min(200f, alpha);
		
		
		if(alphaFaithFlag){
			alphaFaith-= 120f*delta;
		}else{
			alphaFaith+= 120f*delta;
		}
		if(alphaFaith < 10f || alphaFaith > 250f)
			alphaFaithFlag = !alphaFaithFlag;
		
		alphaFaith = Math.max(10f, alphaFaith);
		alphaFaith= Math.min(250f,  alphaFaith);
		
		//Evemtos touch que chocan contra los de la GUI
		if(listenEvents){
			for (Kingdom k : kingdomList) {
				k.update(multiTouchHandler, worldConver, gameCamera);
				for (Terrain t : k.getTerrainList()) {
					t.update(multiTouchHandler, worldConver, gameCamera);
				}
			}
		}
	}
	
	public void resetKingdoms(){
		//Log.i("Debug", "Clean army");
		for (Kingdom k : kingdomList) {
			k.getButton().reset();
			for (Terrain t : k.getTerrainList()) {
				t.getButton().reset();
			}
		}
	}
	
	public void cleanKingdomTarget(){
		for (Kingdom k : kingdomList) {
			k.setTarget(-1);
		}
	}

	public void removePlayer(Player player){
		for(int i = 0; i < playerList.size(); i++){
			if(playerList.get(i).getId() == player.getId()){
				playerList.remove(i);
				break;
			}
		}
	}
	
	private boolean alphaFlag;
	private float alpha = 255;
	
	private boolean alphaFaithFlag;
	private float alphaFaith = 255;
	
	public void drawMap(Graphics g, WorldConver worldConver, GameCamera gameCamera, List<Player> playerList){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		int pW = GfxManager.imgMapList.get(0).getWidth();
		int pH = GfxManager.imgMapList.get(0).getHeight();
		{
		int i = 0;
		for(int y = 0; y < numberPartsH; y++){
			for(int x = 0; x < numberPartsW; x++){
				
				if(worldConver.isObjectInGameLayout(
						gameCamera.getPosX(), gameCamera.getPosY(), 
						mapObject.getX()+x*pW, mapObject.getY()+y*pH, 
						pW, pH)){
				
					g.drawImage(GfxManager.imgMapList.get(i),
							worldConver.getConversionDrawX(gameCamera.getPosX(), mapObject.getX()+x*pW),
							worldConver.getConversionDrawY(gameCamera.getPosY(), mapObject.getY()+y*pH),
							Graphics.TOP | Graphics.LEFT
							);
				}
				i++;
			}
		}
		}
		for(Kingdom k : kingdomList){
			for(int i = 0; i < k.getTerrainList().size(); i++){
				
				
				if(worldConver.isObjectInGameLayout(
						gameCamera.getPosX(), 
						gameCamera.getPosY(),
						k.getTerrainList().get(i).getAbsoluteX()-k.getTerrainList().get(i).getWidth()/2, 
						k.getTerrainList().get(i).getAbsoluteY()-k.getTerrainList().get(i).getHeight()/2, 
						k.getTerrainList().get(i).getWidth(), k.getTerrainList().get(i).getHeight())){
				
				
				
				
					Image img = null;
					
					switch(k.getTerrainList().get(i).getType()){
						case GameParams.CASTLE : img = null; break;
						case GameParams.CITY : img = GfxManager.imgTerrain.get(GameParams.CITY); break;
						case GameParams.PLAIN : img = GfxManager.imgTerrain.get(GameParams.PLAIN); break;
						case GameParams.FOREST : img = GfxManager.imgTerrain.get(GameParams.FOREST); break;
						case GameParams.MONTAIN : img = GfxManager.imgTerrain.get(GameParams.MONTAIN); break;
					}
					
					float touchMod = 0;
					if(k.getTerrainList().get(i).getButton().isTouching())
						touchMod = 0.25f;
					
					if(i < k.getState()){
						g.setAlpha(100);
					}
					
					
					if(k.getTerrainList().get(i).getType() == GameParams.CITY){
						float total = GameParams.BUILDING_STATE.length * GameParams.BUILDING_STATE[0].length;
						float mod = (k.getCityManagement().getTotalLevel() * 0.5f) / total;
						float size = 0.5f+mod;
						g.setImageSize(size+touchMod, size+touchMod);
					}else{
						g.setImageSize(1f+touchMod, 1f+touchMod);
					}
					
					
					g.drawImage(img, 
						worldConver.getConversionDrawX(gameCamera.getPosX(), k.getTerrainList().get(i).getAbsoluteX()),
						worldConver.getConversionDrawY(gameCamera.getPosY(), k.getTerrainList().get(i).getAbsoluteY()),
						Graphics.VCENTER | Graphics.HCENTER);
					
					g.setAlpha(255);
					g.setImageSize(1f, 1f);
					
					
					
					//Fe
					if(k.getTerrainList().get(i).getType() == GameParams.CITY){
						if(k.isProtectedByFaith()){
							g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
							
							float total = GameParams.BUILDING_STATE.length * GameParams.BUILDING_STATE[0].length;
							float mod = (k.getCityManagement().getTotalLevel() * 0.5f) / total;
							float size = 0.5f+mod;
							
							int modW = (int)(GfxManager.imgTerrain.get(GameParams.CITY).getWidth()*size)/2;
							int modH = (int)(GfxManager.imgTerrain.get(GameParams.CITY).getHeight()*size)/2;
							
							//Efecto resplandor
							g.setAlpha((int)alphaFaith);
							
							g.drawImage(GfxManager.imgProtectionRes, 
									worldConver.getConversionDrawX(gameCamera.getPosX(),
											k.getTerrainList().get(k.getTerrainList().size()-1).getAbsoluteX()+
											modW),
									worldConver.getConversionDrawY(gameCamera.getPosY(),
											k.getTerrainList().get(k.getTerrainList().size()-1).getAbsoluteY()+
											modH),
									Graphics.HCENTER | Graphics.VCENTER);
							g.setAlpha(255);
							
							g.drawImage(GfxManager.imgProtection, 
								worldConver.getConversionDrawX(gameCamera.getPosX(),
										k.getTerrainList().get(k.getTerrainList().size()-1).getAbsoluteX()+
										modW),
								worldConver.getConversionDrawY(gameCamera.getPosY(),
										k.getTerrainList().get(k.getTerrainList().size()-1).getAbsoluteY()+
										modH),
								Graphics.HCENTER | Graphics.VCENTER);
						}
					}
				}
				
			}
			
			//Capitales
			for(Player player : playerList){
				if(player.getCapitalkingdom() != null){
					int modW = player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getWidth()/2-
							GfxManager.imgCrown.getWidth()/3;
					int modH = player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getHeight()/2-
							GfxManager.imgCrown.getHeight()/3;
					g.drawImage(GfxManager.imgCrown, 
						worldConver.getConversionDrawX(gameCamera.getPosX(), 
								player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getAbsoluteX()-
								modW),
						worldConver.getConversionDrawY(gameCamera.getPosY(), 
								player.getCapitalkingdom().getTerrainList().get(player.getCapitalkingdom().getTerrainList().size()-1).getAbsoluteY()-
								modH),
						Graphics.HCENTER | Graphics.VCENTER);
				}
			}
			
			//OK
			for(int i = 0; i < k.getState(); i++){
				g.drawImage(GfxManager.imgTerrainOk,
					worldConver.getConversionDrawX(gameCamera.getPosX(), (k.getTerrainList().get(i).getAbsoluteX()+
							GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth()*0.30f)),
					worldConver.getConversionDrawY(gameCamera.getPosY(), (k.getTerrainList().get(i).getAbsoluteY()+
							GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight()*0.30f)),
					Graphics.VCENTER | Graphics.HCENTER);
			}
		}
	}
	
	public void drawTarget(Graphics g, WorldConver worldConver, GameCamera gameCamera){
		
		g.setClip(0, 0, Define.SIZEX, Define.SIZEX);
		for(Kingdom k : kingdomList){
			
			if(k.getTarget() != -1){
				
				Image imgTarget = null;
				
				switch(k.getTarget()){
				case Kingdom.TARGET_BATTLE:
					if(!k.isProtectedByFaith()){
						imgTarget = GfxManager.imgTargetBattle;
					}
					break;
				case Kingdom.TARGET_DOMAIN:
					imgTarget = GfxManager.imgTargetDomain;
					break;
				case Kingdom.TARGET_AGGREGATION:
					imgTarget = GfxManager.imgTargetAggregation;
					break;
				}
				
				if(imgTarget != null){
					g.setAlpha((int)alpha);
					g.drawImage(imgTarget, k.getTouchX(worldConver, gameCamera), k.getTouchY(worldConver, gameCamera), Graphics.VCENTER | Graphics.HCENTER);
					g.setAlpha(255);
				}
			}
		}
	}
	
	public Kingdom getKingdom(int id){
		Kingdom kingdom = null;
		for(int i = 0; kingdom == null && i < kingdomList.size(); i++){
			if(kingdomList.get(i).getId() == id)
				kingdom= kingdomList.get(i);
		}
		/*
		for(Kingdom k : kingdomList){
			if(k.getId() == id){
				kingdom = k;
				break;
			}
		}
		*/
		return kingdom;
	}

	
	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public List<Kingdom> getKingdomList() {
		return kingdomList;
	}

	public void setKingdomList(List<Kingdom> kingdomList) {
		this.kingdomList = kingdomList;
	}

	public float getAlpha() {
		return alpha;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	public MapObject getMapObject() {
		return mapObject;
	}

	public void setMapObject(MapObject mapObject) {
		this.mapObject = mapObject;
	}

	public int getNumberPartsW() {
		return numberPartsW;
	}

	public void setNumberPartsW(int numberPartsW) {
		this.numberPartsW = numberPartsW;
	}

	public int getNumberPartsH() {
		return numberPartsH;
	}

	public void setNumberPartsH(int numberPartsH) {
		this.numberPartsH = numberPartsH;
	}

	
	
	
	
}
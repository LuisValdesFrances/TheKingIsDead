package com.luis.strategy.map;

import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.Math2D;
import com.luis.lgameengine.gameutils.gameworld.SpriteImage;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GameState;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;

public class Army extends MapObject{
	
	public Player player;
	private static int idCount;
	public static void init(){
		idCount = 0;
	}
	private int id;

	private long seed;
	
	public boolean defeat;
	
	private List<Troop> troopList;
	private Kingdom lastKingdom;
	private Kingdom kingdom;
	
	private int state;
	public static final int STATE_NONE = -1;
	public static final int STATE_ON = 0;
	public static final int STATE_MOVE = 1;
	public static final int STATE_ATACK = 2;
	public static final int STATE_DEAD = 3;
	public static final int STATE_OFF = 4;
	
	private float waitCount;
	private int subState;
	public static final int SUB_STATE_DEAD_ANIM = 0;
	public static final int SUB_STATE_DEAD_WAIT = 1;
	
	
	private boolean flip;
	private int anim;
	public static final int ANIN_IDLE = 0;
	public static final int ANIN_MOVE = 1;
	public static final int ANIN_ATACK = 2;
	public static final int ANIN_DEAD = 3;
	
	private static final float SPEED = 10f;
	
	private float idleCount;
	private float idleWait;
	
	private List<SpriteImage> spriteList;
	
	private int flag;
	
	private Image imgNickBuffer;
	
	//IA
	private IADecision iaDecision;
	
	public Army (MapObject map,
			Player player,
			Kingdom kingdom, int flag, float mapX, float mapY, int mapWidth, int mapHeight) {
		
		super(
			map,
			kingdom.getX(), kingdom.getY(),
			GfxManager.imgArmyIdle.getWidth()/9, GfxManager.imgArmyIdle.getHeight(), 
			mapX, mapY, mapWidth, mapHeight,
			-1, -1);
		this.id = idCount++;
		this.player = player;
		this.troopList = new ArrayList<Troop>();
		this.kingdom = kingdom;
		this.lastKingdom = kingdom;
		this.flag = flag;
		this.state = STATE_OFF;
		spriteList = new ArrayList<SpriteImage>();
		spriteList.add(new SpriteImage(GfxManager.imgArmyIdle.getWidth(), GfxManager.imgArmyIdle.getHeight(), 0.10f, 7));	
		spriteList.add(new SpriteImage(GfxManager.imgArmyRun.getWidth(), GfxManager.imgArmyRun.getHeight(), 0.12f, 8));
		spriteList.add(new SpriteImage(GfxManager.imgArmyAtack.getWidth(), GfxManager.imgArmyRun.getHeight(), 0.09f, 7));
		spriteList.add(new SpriteImage(GfxManager.imgArmyDead.getWidth(), GfxManager.imgArmyDead.getHeight(), 0.12f, 5));
		
		if(getPlayer().getActionIA() != null){
			iaDecision = new IADecision();
		}
		
		imgNickBuffer = Image.createImage(GfxManager.imgNickBox.getWidth(), GfxManager.imgNickBox.getHeight());
		
		anim = ANIN_IDLE;
	}
	
	public void initTroops() {
		// A�ado el minimo de tropas
		for (int i = 0; i < GameParams.TROOP_START.length; i++) {
			for (int j = 0; j < GameParams.TROOP_START[i]; j++) {
				getTroopList().add(new Troop(i, true));
			}
		}
	}
	
	public void update(MultiTouchHandler multiTouchHandler, WorldConver worldConver, GameCamera gameCamera, float delta){
		
		switch(state){
		case STATE_ON:
			super.update(multiTouchHandler, worldConver, gameCamera);
			spriteList.get(anim).updateAnimation(delta);
			if(idleCount < idleWait){
				idleCount+= delta;
				spriteList.get(anim).setFrame(0);
			}else{
				if(spriteList.get(anim).isEndAnimation()){
					idleWait = Main.getRandom(1, 5);
					idleCount = 0;
				}
			}
			break;
		case STATE_OFF:
		case STATE_NONE:
			super.update(multiTouchHandler, worldConver, gameCamera);
			spriteList.get(anim).updateAnimation(delta);
			spriteList.get(anim).setFrame(0);
			//setInPosition();
			break;
		case STATE_MOVE:
			spriteList.get(anim).updateAnimation(delta);
			float angle = Math2D.getAngle360(
				getAbsoluteX(), getAbsoluteY(), 
				kingdom.getAbsoluteX(), kingdom.getAbsoluteY());
			double cos = Math.cos(angle * (Math.PI/180f));
			double sin = Math.sin(angle * (Math.PI/180f));
			
			float speedX = (float)((SPEED*cos)*delta);
			float speedY = (float)((SPEED*sin)*delta);
			setX(getX()+speedX);
			setY(getY()-speedY);
			break;
		case STATE_ATACK:
			spriteList.get(anim).updateAnimation(delta);
			if(spriteList.get(anim).isEndAnimation()){
				changeState(STATE_NONE);//OJO
			}
			break;
		case STATE_DEAD:
			switch(subState){
			case SUB_STATE_DEAD_ANIM:
				spriteList.get(anim).updateAnimation(delta);
				if(spriteList.get(anim).isEndAnimation()){
					spriteList.get(anim).setFrame(4);
					subState = SUB_STATE_DEAD_WAIT;
				}
				break;
			case SUB_STATE_DEAD_WAIT:
				if(waitCount < 1.25){
					waitCount+= delta;
				}else{
					changeState(STATE_NONE);
				}
				break;
			}
			break;
		}
	}
	
	/*
	private void setInPosition(){
		if((int)x > (int)getKingdom().getX()){
			x--;
		}else if((int)x < (int)getKingdom().getX()){
			x++;
		}
		if((int)y > (int)getKingdom().getY()){
			y--;
		}else if((int)y < (int)getKingdom().getY()){
			y++;
		}
	}
	*/
	
	public void draw(
			Graphics g, WorldConver worldConver, GameCamera gameCamera, GameScene gameScene, boolean isSelected, boolean isActive, 
			float distorsionX, float distorsionY, int typeGame){
		
		
		if(worldConver.isObjectInGameLayout(
				gameCamera.getPosX(), 
				gameCamera.getPosY(),
				getAbsoluteX()-getWidth()/2, 
				getAbsoluteY()-getHeight()/2, 
				getWidth(), getHeight())){
		
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			
			int pX = worldConver.getConversionDrawX(gameCamera.getPosX(), getAbsoluteX());
			int pY = worldConver.getConversionDrawY(gameCamera.getPosY(), getAbsoluteY());
			
			
			float modSize = 0;
			/*
			//Modo 3D
			//Size
			float modSize = (((float)pY) * (distorsionY -1f)) / worldConver.getLayoutY();
			g.setImageSize(1+modSize, 1+modSize);
			
			float extraWidth = (distorsionX-1f)*worldConver.getLayoutX();
			float extraHeight = (distorsionY-1f)*worldConver.getLayoutY();
			//Deformacion en la posicion X
			float sepCenter = pX - worldConver.getCentGameLayoutX();
			//Deformacion maxima en x
			float maxX = (sepCenter * extraWidth) / worldConver.getLayoutX()/2;
			//Relativizacion de la deformacion maxima (Menos acusada cuando mas al fondo)
			float relativeModX = (maxX * pY) /  worldConver.getLayoutY();
			
			//Deformacion en la posicion Y
			float relativeModY = (pY * extraHeight) / worldConver.getLayoutY();
			
			
			pX += relativeModX;
			pY += relativeModY;
			*/
			
			if(isSelected){
				g.setAlpha((int)gameScene.getAlpha());
				g.drawImage(
						isActive?GfxManager.imgMapSelectGreen:GfxManager.imgMapSelectRed, 
						pX,
						pY+getHeight()/2,
						Graphics.VCENTER | Graphics.HCENTER);
				g.setAlpha(255);
			}
			
			if(state == STATE_OFF){
				g.setAlpha(140);
			}
			
			
			if(state != STATE_DEAD){
				g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				
				float flagX;
				if(state != STATE_MOVE && state != STATE_ATACK){
					flagX = (float) (pX
						-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.75);
				}else{
					flagX = pX
						+
						(int)(flip?
						-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.25:
						-GfxManager.imgFlagSmallList.get(flag).getWidth()*0.75);
				}
							  
				float flagY = pY-GfxManager.imgFlagSmallList.get(flag).getHeight();
				int angle = flip?15:-15;
				
				g.drawRegion(GfxManager.imgFlagSmallList.get(flag), 
						(int)flagX,
						(int)flagY,
						0, 0, 
						GfxManager.imgFlagSmallList.get(flag).getWidth(), GfxManager.imgFlagSmallList.get(flag).getHeight(), 
						angle, 
						(int)flagX+GfxManager.imgFlagSmallList.get(flag).getWidth()/2, 
						(int)flagY+GfxManager.imgFlagSmallList.get(flag).getHeight()/2);
			}
			
			
			switch(anim){
			case ANIN_IDLE:
				spriteList.get(anim).drawFrame(g, GfxManager.imgArmyIdle, 
						pX,
						pY,
						(1+modSize),(1+modSize),
						flip, Graphics.VCENTER | Graphics.HCENTER);
				break;
			case ANIN_MOVE:
				spriteList.get(anim).drawFrame(g, GfxManager.imgArmyRun,
						pX,
						pY,
						(1+modSize),(1+modSize),
						flip, Graphics.VCENTER | Graphics.HCENTER);
				break;
			case ANIN_ATACK:
				spriteList.get(anim).drawFrame(g, GfxManager.imgArmyAtack,
						pX,
						pY,
						(1+modSize),(1+modSize),
						flip, Graphics.VCENTER | Graphics.HCENTER);
				break;
			case ANIN_DEAD:
				spriteList.get(anim).drawFrame(g, GfxManager.imgArmyDead,
						pX,
						pY,
						(1+modSize),(1+modSize),
						flip, Graphics.VCENTER | Graphics.HCENTER);
				break;
			}
			
			//Test
			/*
			g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolution()]);
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.drawText(""+(relativeModX), pX+30, pY+20, Color.RED);
			g.drawImage(GfxManager.imgCoin, pX, pY,  Graphics.VCENTER | Graphics.HCENTER);
			*/
			g.setImageSize(1, 1);
			g.setAlpha(255);
			/*
			else{
				g.drawImage(GfxManager.imgArmyOff, 
						getAbsoluteX()+GfxManager.imgArmyOff.getWidth()/2, 
						getAbsoluteY()-GfxManager.imgArmyOff.getHeight()/2,
						Graphics.HFLIP);
			}
			*/
			
			if(typeGame == GameState.GAME_MODE_ONLINE && state != STATE_DEAD && getPlayer() != null){
				g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				Graphics _g = imgNickBuffer.getGraphics();
				_g.drawImage(
						GfxManager.imgNickBox, imgNickBuffer.getWidth()/2, 
						imgNickBuffer.getHeight()/2,
						Graphics.VCENTER | Graphics.HCENTER);
				TextManager.drawSimpleText(_g, Font.FONT_SMALL, 
						getPlayer().getName(), 
						imgNickBuffer.getWidth()/2, 
						imgNickBuffer.getHeight()/2, 
						Graphics.VCENTER | Graphics.HCENTER);
				
				float scale=0.65f;
				int nickX = pX + getWidth()/2 + imgNickBuffer.getWidth()/2;
				int nickY = pY - getHeight()/4 - imgNickBuffer.getHeight()/2;
				g.setImageSize(scale, scale);
				g.setAlpha(180);
				g.drawImage(imgNickBuffer, 
						nickX - (int)((GfxManager.imgNickBox.getWidth()*(1f-scale))/2), 
						nickY + (int)((GfxManager.imgNickBox.getHeight()*(1f-scale))/2), 
						Graphics.VCENTER | Graphics.HCENTER);
				g.setAlpha(255);
				g.setImageSize(1f, 1f);
			}
		}
	}
	
	public void changeState(int newState){
		switch(newState){
		case STATE_ON: 
			anim = ANIN_IDLE;
			spriteList.get(anim).resetAnimation(0);
			flip = false;
			break;
		case STATE_MOVE: 
			anim = ANIN_MOVE;
			spriteList.get(anim).resetAnimation(0);
			flip = lastKingdom.getAbsoluteX() > kingdom.getAbsoluteX(); 
			break;
		case STATE_ATACK: 
			anim = ANIN_ATACK;
			spriteList.get(anim).resetAnimation(0);
			SndManager.getInstance().playFX(Main.FX_SWORD, 0);
			break;
		case STATE_DEAD:
			anim = ANIN_DEAD;
			spriteList.get(anim).resetAnimation(0);
			SndManager.getInstance().playFX(Main.FX_DEAD, 0);
			break;
		case STATE_OFF:
		case STATE_NONE:
			subState = 0;
			waitCount = 0;
			if(state != STATE_DEAD){
				anim = ANIN_IDLE;
				spriteList.get(anim).resetAnimation(0);
			}
			flip = lastKingdom.getAbsoluteX() > kingdom.getAbsoluteX();
			break;
		}
		state = newState;
	}
	
	public int getNumberTroops(int type){
		int n = 0;
		for(int i = 0; i < troopList.size(); i++){
			if(troopList.get(i).getType() == type)
				n++;
		}
		return n;
	}
	
	public int getPower(Terrain terrain){
		int power = 0;
		for(int i = 0; i < troopList.size(); i++){
			switch(troopList.get(i).getType()){
			case GameParams.INFANTRY:
				power += GameParams.INFANTRY_COMBAT[terrain.getType()];
				break;
			case GameParams.KNIGHT:
				power += GameParams.KNIGHTS_COMBAT[terrain.getType()];
				break;
			case GameParams.HARASSERES:
				power += GameParams.HARASSERS_COMBAT[terrain.getType()];
				break;
			case GameParams.SIEGE:
				power += GameParams.SIEGE_COMBAT[terrain.getType()];
				break;
			}
		}
		return power;
	}
	
	public int getCost(){
		int cost = 0;
		for(int i = 0; i < troopList.size(); i++){
			cost +=  GameParams.TROOP_COST [troopList.get(i).getType()];
		}
		return cost;
	}
	
	public void setDamage(int costTarget){
		int costCount = 0;
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.SIEGE){
				costCount+= GameParams.TROOP_COST[GameParams.SIEGE];
				troopList.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.HARASSERES){
				costCount+= GameParams.TROOP_COST[GameParams.HARASSERES];
				troopList.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.KNIGHT){
				costCount+= GameParams.TROOP_COST[GameParams.KNIGHT];
				troopList.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < troopList.size() && costCount < costTarget; i++){
			//Maquinas de asedio
			if(!troopList.get(i).isSubject() && troopList.get(i).getType() == GameParams.INFANTRY){
				costCount+= GameParams.TROOP_COST[GameParams.INFANTRY];
				troopList.remove(i);
				i--;
			}
		}
	}

	public int getState() {
		return state;
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}

	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
	}

	
	public Kingdom getLastKingdom() {
		return lastKingdom;
	}

	public void setLastKingdom(Kingdom lastKingdom) {
		this.lastKingdom = lastKingdom;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDefeat() {
		return defeat;
	}

	public void setDefeat(boolean defeat) {
		this.defeat = defeat;
	}

	public List<Troop> getTroopList() {
		return troopList;
	}

	public void setTroopList(List<Troop> troopList) {
		this.troopList = troopList;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public IADecision getIaDecision() {
		return iaDecision;
	}

	public void setIaDecision(IADecision iaDecision) {
		this.iaDecision = iaDecision;
	}

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void discardTroop(int discardNumber){
		int discardCount = 0;
		
		//Obtengo el minimo de tropas
		int min = 0;
		for(int i = 0; i < GameParams.TROOP_START[i]; i++){
			min+=GameParams.TROOP_START[i];
		}
		
		for(int i = 0; 
			troopList.size() > min && discardCount > discardNumber; i++){
		
				if(!troopList.get(i).isSubject()){
				troopList.remove(i);
				discardCount++;
			}
		}
	}
	

	public class IADecision{
		private int decision;
		private Kingdom kingdomDecision;
		
		public int getDecision() {
			return decision;
		}

		public void setDecision(int decision) {
			this.decision = decision;
		}

		public Kingdom getKingdomDecision() {
			return kingdomDecision;
		}

		public void setKingdomDecision(Kingdom kingdomDecision) {
			this.kingdomDecision = kingdomDecision;
		}
		
		
	}
}

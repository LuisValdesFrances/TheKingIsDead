package com.luis.strategy.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gameutils.gameworld.GameCamera;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.input.TouchData;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GameState;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.ModeGame;
import com.luis.strategy.RscManager;
import com.luis.strategy.UserInput;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.gui.ArmyBox;
import com.luis.strategy.gui.BattleBox;
import com.luis.strategy.gui.DialogBox;
import com.luis.strategy.gui.FlagButton;
import com.luis.strategy.gui.MapBox;
import com.luis.strategy.gui.RankingBox;
import com.luis.strategy.gui.SimpleBox;
import com.luis.strategy.gui.CityBox;
import com.luis.strategy.map.ActionIA;
import com.luis.strategy.map.Army;
import com.luis.strategy.map.Kingdom;
import com.luis.strategy.map.GameScene;
import com.luis.strategy.map.Player;
import com.luis.strategy.map.Terrain;
import com.luis.strategy.map.Troop;



public class GameManager {
	
	//
	public static boolean game3D = false;
	private Image gameBuffer;
	private float distorsion = 1.16f;


	private static Button btnDebugPause;
	private static boolean isDebugPaused;

	private static final float IA_WAIT = 1f;

	private static float IAWaitCount;

	//Pad
	private int lastTouchX;
	private int lastTouchY;
	private float cameraTargetX;
	private float cameraTargetY;

	private GameCamera gameCamera;
	private WorldConver worldConver;
	private DataSender dataSender;

	private GameScene gameScene;
	
	private int localTurnCount;
	
	private int state;
	public static final int STATE_INCOME = 0;
	public static final int STATE_ECONOMY = 1;
	public static final int STATE_DISCARD = 2;
	public static final int STATE_ACTION = 3;
	public static final int STATE_END = 4;
	public static final int STATE_WIN = 5;
    public static final int STATE_GAME_OVER = 6;
	public static final int STATE_DEBUG = 7;
	
	
	//SUB-STATE ACTION
	public int subState;
	public int lastSubState;
	public static final int SUB_STATE_ACTION_IA_WAIT_START = 0;
	public static final int SUB_STATE_ACTION_WAIT = 1;
	public static final int SUB_STATE_ACTION_SELECT = 2;
	public static final int SUB_STATE_ACTION_EXCEED = 3;
	public static final int SUB_STATE_ACTION_MOVE = 4;
	public static final int SUB_STATE_ACTION_RESOLVE_MOVE = 5;
	public static final int SUB_STATE_ACTION_ANIM_ATACK = 6;
	public static final int SUB_STATE_ACTION_COMBAT = 7;
	public static final int SUB_STATE_ACTION_RESULT = 8;
	public static final int SUB_STATE_ACTION_ANIM_DEAD = 9;
	public static final int SUB_STATE_ACTION_CONQUEST = 10;
	public static final int SUB_STATE_ACTION_SCAPE = 11;
	public static final int SUB_STATE_ACTION_RESOLVE_SCAPE = 12;
	public static final int SUB_STATE_ARMY_MANAGEMENT = 13;
	public static final int SUB_STATE_CITY_MANAGEMENT = 14;
	public static final int SUB_STATE_MAP_MANAGEMENT = 15;
    public static final int SUB_STATE_RANKING_MANAGEMENT = 16;
	public static final int SUB_STATE_ACTION_IA_WAIT_END = 17;
	
	//GUI
	private Button btnNext;
	private Button btnCancel;
	private Button btnArmy;
    private Button btnChest;
	private FlagButton btnFlagHelmet;
	private FlagButton btnFlagCastle;
	
	private Button btnMap;

	private ArmyBox armyBox;
	private BattleBox battleBox;
	private CityBox cityBox;
	private MapBox mapBox;
    private RankingBox rankingBox;
	private SimpleBox economyBox;
	private SimpleBox resultBox;
	private SimpleBox discardBox;
	private SimpleBox endGameBox;
	private DialogBox troopExceedBox;
	private DialogBox confirmActionBox;
	
	//private Army activeArmy;
	//private Kingdom selectKingdom;
	private List<Mist> mistList;

	private int intertitialCount;
	
	public GameManager(WorldConver wc, GameCamera gc, GameScene gs){
		this.gameBuffer = Image.createImage(
				(int)wc.getGameLayoutX(), 
				(int)wc.getGameLayoutY());
		this.localTurnCount = 0;
		this.worldConver = wc;
		this.gameCamera = gc;
		this.gameScene = gs;
		this.lastTouchX = UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
		this.lastTouchY = UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
		
		this.cameraTargetX=0;//worldConver.getCentlayoutX();
		this.cameraTargetY=0;//=worldConver.getCentlayoutY();

		this.intertitialCount = 0;
		
		this.dataSender = new DataSender();
		MenuElement.bgAlpha = GameParams.BG_BLACK_ALPHA;
		
		if(Main.IS_GAME_DEBUG){
			btnDebugPause = new Button(
					GfxManager.imgButtonDebugPauseRelease, 
					GfxManager.imgButtonDebugPauseFocus, 
					Define.SIZEX64 + GfxManager.imgButtonDebugPauseRelease.getWidth()/2, 
					Define.SIZEY4,
					null, 0){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					
					isDebugPaused = !isDebugPaused;
					if(state == STATE_DEBUG){
						changeState(STATE_END);
					}else{
						btnDebugPause.setDisabled(true);
					}
				}
			};
		}
		
		btnNext = new Button(
				GfxManager.imgButtonNextRelease, 
				GfxManager.imgButtonNextFocus, 
				Define.SIZEX2, 
				Define.SIZEY-GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgGameHud.getHeight()/8,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				SndManager.getInstance().playFX(Main.FX_NEXT, 0);
				changeState(state+1);
				reset();
			}
		};
		
		btnCancel = new Button(
				GfxManager.imgButtonCancelRelease, 
				GfxManager.imgButtonCancelFocus, 
				Define.SIZEX2- GfxManager.imgButtonCancelRelease.getWidth()*2, 
				Define.SIZEY-GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgGameHud.getHeight()/8,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				SndManager.getInstance().playFX(Main.FX_BACK, 0);
				if(state == STATE_ACTION){
					btnCancel.setDisabled(true);
					switch(subState){
					case SUB_STATE_ACTION_WAIT:
					case SUB_STATE_ACTION_SELECT:
						gameScene.resetKingdoms();
						btnFlagHelmet.hide();
						btnFlagCastle.hide();
						//Quito todos lo indicadores de target
						gameScene.cleanKingdomTarget();
						changeSubState(SUB_STATE_ACTION_WAIT);
						break;
					}
				}
				reset();
			}
		};
		
		btnArmy = new Button(
				GfxManager.imgButtonHelmetRelease, 
				GfxManager.imgButtonHelmetFocus, 
				Define.SIZEX2- GfxManager.imgButtonCancelRelease.getWidth()*4,
				Define.SIZEY - GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgButtonHelmetRelease.getHeight()/4,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				SndManager.getInstance().playFX(Main.FX_BACK, 0);
				if(state == STATE_ACTION){
					if(getCurrentPlayer().getActionIA() == null){
						
						Army nextArmy = getNextArmy();
						
						if(nextArmy != null){
							cleanArmyAction();
							setSelectedArmy(nextArmy);
							cameraTargetX = getSelectedArmy().getAbsoluteX();
							cameraTargetY = getSelectedArmy().getAbsoluteY();
						}
					}
				}
				reset();
			}
		};
		
		btnFlagHelmet = new FlagButton(
				GfxManager.imgButtonFlagHelmetRelease, 
				GfxManager.imgButtonFlagHelmetFocus, 
				GfxManager.imgButtonFlagHelmetRelease.getWidth()/2, 
				Define.SIZEY-GfxManager.imgButtonFlagHelmetRelease.getHeight()/2){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				/*
				reset();
				hide();
				btnFlagCastle.hide();
				*/
			}
		};
		
		btnFlagCastle = new FlagButton(
				GfxManager.imgButtonFlagCastleRelease,
				GfxManager.imgButtonFlagCastleFocus, 
				Define.SIZEX-GfxManager.imgButtonFlagCastleRelease.getWidth()/2, 
				Define.SIZEY-GfxManager.imgButtonFlagCastleRelease.getHeight()/2){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				boolean isCurrentPlayer = !isDebugPaused && isSelectedArmyFromCurrentPlayer(getSelectedArmy());
				boolean isDiscardMode = !isDebugPaused && state==STATE_DISCARD;
				armyBox.start(
						getSelectedArmy(), 
						isCurrentPlayer,
						isDiscardMode);
				btnFlagHelmet.hide();
				hide();
				
				
				if(isDebugPaused){
					changeState(STATE_ACTION);
					changeSubState(SUB_STATE_ARMY_MANAGEMENT);
				}else{
					changeSubState(SUB_STATE_ARMY_MANAGEMENT);
				}
			}
		};
		
		btnMap = new Button(
				GfxManager.imgButtonMapRelease,
				GfxManager.imgButtonMapFocus, 
				Define.SIZEX64 + GfxManager.imgButtonMapRelease.getWidth()/2, 
				Define.SIZEY8,
				null, 0){
			@Override
			public void onButtonPressDown(){}
			
			@Override
			public void onButtonPressUp(){
				if(state == STATE_DEBUG){
					mapBox.start(gameScene.getPlayerList());
					btnMap.setDisabled(true);
				}else{
					if(getCurrentPlayer().getActionIA() == null && subState == SUB_STATE_ACTION_WAIT){
						changeSubState(SUB_STATE_MAP_MANAGEMENT);
						mapBox.start(gameScene.getPlayerList());
						setDisabled(true);
					}
				}
			}
		};

        int margin = Define.SIZEY64;
        String text = "" + getCurrentPlayer().getGold();
        int totalWidth = GfxManager.imgButtonChestRelease.getWidth() + margin + Font.getFontWidth(Font.FONT_MEDIUM)*text.length();
        int x = Define.SIZEX - Define.SIZEX4 - totalWidth/2;
        int y = Define.SIZEY - GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgButtonChestRelease.getHeight()/4;
        btnChest = new Button(
                GfxManager.imgButtonChestRelease,
                GfxManager.imgButtonChestFocus,
                x, y,
                null, 0){
            @Override
            public void onButtonPressDown(){}

            @Override
            public void onButtonPressUp(){
                if(state == STATE_DEBUG){
                    rankingBox.start(gameScene.getPlayerList());
                    btnChest.setDisabled(true);
                }else{
                    if(getCurrentPlayer().getActionIA() == null && subState == SUB_STATE_ACTION_WAIT){
                        changeSubState(SUB_STATE_RANKING_MANAGEMENT);
                        rankingBox.start(gameScene.getPlayerList());
                        setDisabled(true);
                    }
                }
            }
        };
		
		armyBox = new ArmyBox(){
			@Override
			public void check(){
				if(state == STATE_DISCARD){
					/*
					int dif = getCurrentPlayer().getTaxes() - getCurrentPlayer().getCost(false);
					if(dif >= 0){
						discardBox.cancel();
					}
					*/
					if(getCurrentPlayer().getGold() >= 0){
						discardBox.cancel();
					}
				}
			}
		};
		
		cityBox = new CityBox(){
			@Override
			public void onBuy(){
				super.onBuy();
				getCurrentPlayer().setGold(getCurrentPlayer().getGold()-GameParams.ARMY_COST);
				
				Army army = new Army(
						gameScene.getMapObject(), 
						getCurrentPlayer(),
						getKingdom(),
						getCurrentPlayer().getFlag(), 
						gameScene.getMapObject().getX(), gameScene.getMapObject().getY(), gameScene.getMapObject().getWidth(), gameScene.getMapObject().getHeight());
				army.initTroops();
				getCurrentPlayer().getArmyList().add(army);
				cancel();
			}
			@Override
			public void onFinish() {
				if(isRecruited())
					NotificationBox.getInstance().addMessage("New army recruited");
			}
		};
		
		mapBox = new MapBox(worldConver, gameScene.getNumberPartsW(), gameScene.getNumberPartsH());
        rankingBox = new RankingBox(worldConver, gameScene.getNumberPartsW(), gameScene.getNumberPartsH());
		
		battleBox = new BattleBox(){
			@Override
			public void onFinish(){
				super.onFinish();
				if(SndManager.getInstance().getCurrentClip() == Main.MUSIC_START_BATTLE){
					SndManager.getInstance().playMusic(Main.MUSIC_MAP, true);
				}
				
				switch(this.getIndexPressed()){
				case 0:
					resolveCombat(battleBox.getResult());
					break;
				case 1:
					if(isScapeOptions()){
						Army defeated = getEnemyAtKingdom(getCurrentPlayer());
						defeated.setDefeat(true);
						NotificationBox.getInstance().addMessage(getCurrentPlayer().getName() + " scape!");
						endBattle();
					}else{
						getSelectedArmy().changeState(Army.STATE_OFF);
						changeSubState(SUB_STATE_ACTION_WAIT);
					}
					break;
				case 2:
					putArmyAtKingdom(getSelectedArmy(), getSelectedArmy().getLastKingdom());
					getSelectedArmy().setX(getSelectedArmy().getKingdom().getX());
					getSelectedArmy().setY(getSelectedArmy().getKingdom().getY());
					getSelectedArmy().changeState(Army.STATE_ON);
					changeSubState(SUB_STATE_ACTION_WAIT);
					break;
				}
			}
		};
		
		resultBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
			@Override
			public void onFinish(){
				if(startConquest){
					changeSubState(SUB_STATE_ACTION_CONQUEST);
				}else if(startAnimDead){
					changeSubState(SUB_STATE_ACTION_ANIM_DEAD);
				}else{
					endBattle();
				}
			}
		};
		
		economyBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
			@Override
			public void onFinish(){
				//Compruebo si estoy en saldo negativo
				if(getCurrentPlayer().getGold() < 0){
					changeState(STATE_DISCARD);
				}else{
					changeState(STATE_ACTION);
				}
			}
		};
		
		endGameBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
			@Override
			public void onFinish() {
				super.onFinish();
				if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){
					
					if(OnlineInputOutput.getInstance().isOnline(Main.getInstance().getContext())){
						dataSender.sendGameScene(gameScene, 2, true);
						//Envio notificaciones
						dataSender.sendGameNotifications();
					}else{
						NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_NO_CONNECTION]);
					}
					
					Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, true);
				}else{
					//Guardo la victoria
					GameState.getInstance().setGameScene(null);
					ModeGame.saveGame();
					Main.changeState(Define.ST_MENU_MAIN, true);
				}
			}
		};
		
		troopExceedBox = new DialogBox(GfxManager.imgSmallBox){
			@Override
			public void onFinish() {
				if(this.getIndexPressed() == 0){
					gameScene.cleanKingdomTarget();
					cleanArmyAction();
					changeSubState(SUB_STATE_ACTION_WAIT);
				}else{
					changeSubState(SUB_STATE_ACTION_MOVE);
				}
			}
		};
		
		confirmActionBox = new DialogBox(GfxManager.imgSmallBox){
			@Override
			public void onFinish() {
				if(this.getIndexPressed() == 0){
					btnCancel.trigger();
				}else{
					startAction();
				}
			}
		};
		
		discardBox = new SimpleBox(GfxManager.imgNotificationBox, false, false){
			@Override
			public void onFinish() {
				super.onFinish();
				armyBox.cancel();
			}
		};
		discardBox.setY(GfxManager.imgNotificationBox.getHeight()/2);
		
		mistList = new ArrayList<Mist>();
		
		int mapW = GfxManager.imgMapList.get(0).getWidth()*gameScene.getNumberPartsW();
		int mapH = GfxManager.imgMapList.get(0).getHeight()*gameScene.getNumberPartsH();
		int numTilesW = mapW / GfxManager.imgMist.getWidth()+1;
		int numTilesH = mapH / GfxManager.imgMist.getHeight()+1;
		 
		for(int i = 0; i < numTilesH; i++){
			for(int j = 0; j < numTilesW; j++){
				 int pY = (GfxManager.imgMist.getHeight()*i) + GfxManager.imgMist.getHeight()/2;
				 int pX = (GfxManager.imgMist.getWidth()*j) + GfxManager.imgMist.getWidth()/2;
				 Mist mist = new Mist(pX, pY, GfxManager.imgMist.getWidth(), GfxManager.imgMist.getHeight());
				 mistList.add(mist);
			}
		}

		Main.getInstance().getActivity().loadInterstitial();
		changeState(STATE_INCOME);
	}
	
	public void update(float delta){
		
		switch(state){
		case STATE_INCOME:
			if(!updatePresentation(delta)){
				changeState(STATE_ECONOMY);
			}
			break;
		case STATE_ECONOMY:
			economyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
			break;
		case STATE_DISCARD:
			discardBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
			
			if(!armyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
				/*
				int dif = getCurrentPlayer().getTaxes() - getCurrentPlayer().getCost(false);
				if(dif >= 0){
					changeState(STATE_ACTION);
				}
				*/
				if(getCurrentPlayer().getGold() >= 0){
					changeState(STATE_ACTION);
				}
			}
				
			for(int i = 0; i < gameScene.getPlayerList().size(); i++){
				for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
					if(army.isSelect()){
						army.getButton().reset();
						cleanArmyAction();
						setSelectedArmy(army);
						btnFlagCastle.start();
					}
				}
			}
		break;
		
		case STATE_ACTION:
			switch(subState){
			
			case SUB_STATE_ACTION_IA_WAIT_START:
				
				//Si ya estaba esperando, espera menos
				float t = lastSubState != SUB_STATE_ACTION_WAIT?IA_WAIT:IA_WAIT/2; 
				
				if(getCurrentPlayer().getActionIA() != null && IAWaitCount < t){
					IAWaitCount+=delta;
				}else{
					IAWaitCount = 0;
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
				
			case SUB_STATE_ACTION_WAIT:
				
				//Actualizar iteracion terreno:
				for(Kingdom kingdom : gameScene.getKingdomList()){
					for(Terrain terrain : kingdom.getTerrainList()){
						if(terrain.isSelect()){
							terrain.getButton().reset();
							
							//Porculeria de los terrenos
							if(terrain.getType() >= GameParams.CITY){
							
								cityBox.start(getCurrentPlayer(), kingdom,
									terrain.getType() >= GameParams.CITY &&
									getArmyAtKingdom(kingdom)== null && 
									getCurrentPlayer().hasKingom(kingdom));
									
								changeSubState(SUB_STATE_CITY_MANAGEMENT);
							}
						}
					}
				}
					
				for(int i = 0; i < gameScene.getPlayerList().size(); i++){
					for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
						if(army.isSelect()){
							SndManager.getInstance().playFX(Main.FX_SELECT_ARMY, 0);
							cleanArmyAction();
							setSelectedArmy(army);
							btnFlagCastle.start();
							//Camara se posiciona en el seleccionado
							cameraTargetX = getSelectedArmy().getAbsoluteX();
							cameraTargetY = getSelectedArmy().getAbsoluteY();
							if(i == gameScene.getPlayerIndex() && army.getState() == Army.STATE_ON){
								//btnFlagHelmet.start();
								insertTargetUnMap(getSelectedArmy());
								changeSubState(SUB_STATE_ACTION_SELECT);
							}else{
								btnFlagHelmet.hide();
							}
						}
					}
				}
			break;
				
			case SUB_STATE_ACTION_SELECT:
				
				if(!confirmActionBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					for(Kingdom kingdom: gameScene.getKingdomList()){
						
						//Chequea si el reino que se ha tocado (isSelect) es valido(target != -1)
						if(
							(getCurrentPlayer().getActionIA() == null && kingdom.getTarget() != -1 && kingdom.isSelect())
							||
							(getCurrentPlayer().getActionIA() != null && getCurrentPlayer().getActionIA().getKingdomToDecision().getId() == kingdom.getId())
						){
							
							if(getCurrentPlayer().getActionIA() == null){
								confirmActionBox.start(null, RscManager.allText[RscManager.TXT_GAME_CONFIRM_ACTION]);
								btnFlagHelmet.hide();
								btnFlagCastle.hide();
								btnCancel.setDisabled(true);
							}else{
								//Si esta protegido por Fe, paso
								kingdom = getSelectedArmy().getPlayer().getActionIA().getKingdomToDecision();
								if(!getCurrentPlayer().hasKingom(kingdom) && kingdom.isProtectedByFaith()){
									getSelectedArmy().changeState(Army.STATE_OFF);
									changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
								}else{
									startAction();
								}
							}
							break;
						}
					}
				}
				break;
				
			case SUB_STATE_ACTION_EXCEED:
				if(troopExceedBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					
				}
				break;
				
			case SUB_STATE_ACTION_MOVE:
				if(getSelectedArmy().getPlayer().getActionIA()!=null){
					cameraTargetX = getSelectedArmy().getAbsoluteX();
					cameraTargetY = getSelectedArmy().getAbsoluteY();
				}
				
				if(GameUtils.getInstance().checkArmyColision(getSelectedArmy())){
					changeSubState(SUB_STATE_ACTION_RESOLVE_MOVE);
				}
				break;
				
			case SUB_STATE_ACTION_ANIM_ATACK:
				if(getSelectedArmy().getState() == Army.STATE_NONE){
					changeSubState(SUB_STATE_ACTION_COMBAT);
				}
				break;
				
			case SUB_STATE_ACTION_COMBAT:
				battleBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
				break;
				
			case SUB_STATE_ACTION_RESULT:
				resultBox.update(UserInput.getInstance().getMultiTouchHandler(), delta);
				break;
				
			case SUB_STATE_ACTION_ANIM_DEAD:
				if(getDefeatArmy().getState() == Army.STATE_NONE){
					removeArmy(getDefeatArmy());
					endBattle();
				}
				break;
				
			case SUB_STATE_ACTION_CONQUEST:
				if(!updateConquest(delta)){
					endBattle();
				}
				break;
				
			case SUB_STATE_ACTION_SCAPE:
				
				if(getDefeatArmy().getPlayer().getActionIA()!=null){
					cameraTargetX = getDefeatArmy().getAbsoluteX();
					cameraTargetY = getDefeatArmy().getAbsoluteY();
				}
				
				//Colision
				if(GameUtils.getInstance().checkArmyColision(getDefeatArmy())){
					changeSubState(SUB_STATE_ACTION_RESOLVE_SCAPE);
				}
				break;
			case SUB_STATE_ARMY_MANAGEMENT:
				//Si no queda ninguna caja en primer plano
				if(!armyBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					if(isDebugPaused){
						changeState(STATE_DEBUG);
					}else{
						changeSubState(SUB_STATE_ACTION_WAIT);
					}
				}
				break;
			case SUB_STATE_CITY_MANAGEMENT:
				if(!cityBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_MAP_MANAGEMENT:
				if(!mapBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_RANKING_MANAGEMENT:
                    if(!rankingBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
                        changeSubState(SUB_STATE_ACTION_WAIT);
                    }
                    break;
			case SUB_STATE_ACTION_IA_WAIT_END:
				if(getCurrentPlayer().getActionIA() != null && IAWaitCount < IA_WAIT){
					IAWaitCount+=delta;
				}else{
					IAWaitCount = 0;
					changeState(STATE_END);
				}
				break;
			}
		break;
		
		
		    case STATE_WIN:
		    case STATE_GAME_OVER:
			if(!endGameBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
				
			}
			break;
		case STATE_DEBUG:
			if(!mapBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
				btnMap.setDisabled(false);
			}
            if(!rankingBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
                btnChest.setDisabled(false);
            }
			
			if(!cityBox.update(UserInput.getInstance().getMultiTouchHandler(), delta)){
			
				//Actualizar iteracion terreno:
				for(Kingdom kingdom : gameScene.getKingdomList()){
					for(Terrain terrain : kingdom.getTerrainList()){
						if(terrain.isSelect()){
							terrain.getButton().reset();
							
							//Porculeria de los terrenos
							if(terrain.getType() >= GameParams.CITY){
							
								cityBox.start(getCurrentPlayer(), kingdom,
									terrain.getType() >= GameParams.CITY &&
									getArmyAtKingdom(kingdom)== null && 
									getCurrentPlayer().hasKingom(kingdom));
							}
						}
					}
				}
			}
			
			for(int i = 0; i < gameScene.getPlayerList().size(); i++){
				for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
					if(army.isSelect()){
						army.getButton().reset();
						cleanArmyAction();
						setSelectedArmy(army);
						btnFlagCastle.start();
						//Camara se posiciona en el seleccionado
						cameraTargetX = getSelectedArmy().getAbsoluteX();
						cameraTargetY = getSelectedArmy().getAbsoluteY();
						if(i == gameScene.getPlayerIndex() && army.getState() == Army.STATE_ON){
							//btnFlagHelmet.start();
							insertTargetUnMap(getSelectedArmy());
							changeSubState(SUB_STATE_ACTION_SELECT);
						}else{
							btnFlagHelmet.hide();
						}
					}
				}
			}
			
			
			break;
		}
		boolean listenEvents = false;
		if(
			!armyBox.isActive() && 
			!battleBox.isActive() && 
			!economyBox.isActive() &&
			!resultBox.isActive() &&
			!endGameBox.isActive() &&
			!troopExceedBox.isActive() &&
			!confirmActionBox.isActive() &&
			!cityBox.isActive()){
			updateCamera();
			if(
				(getCurrentPlayer().getActionIA() == null && state == STATE_ACTION)
				||
				state == STATE_DEBUG){
				listenEvents = true;
			}
		}
		gameScene.update(UserInput.getInstance().getMultiTouchHandler(), worldConver, gameCamera, delta, listenEvents);
		
		updateGUI(UserInput.getInstance().getMultiTouchHandler(), delta);
		
		
		//Actualizar animaciones
		for(Player player : gameScene.getPlayerList())
			player.updateArmies(UserInput.getInstance().getMultiTouchHandler(), worldConver, gameCamera, Main.getDeltaSec());
		
	}
	
	public void draw(Graphics g){
		
		//Pintado en el buffer
		gameScene.drawMap(gameBuffer.getGraphics(), worldConver, gameCamera, gameScene.getPlayerList());
		
		//Flags
		for(Player player : gameScene.getPlayerList()){
			int i=0;
			for(Kingdom kingdom : player.getKingdomList()){
				
				int flagX = kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteX() + 
						GfxManager.imgTerrain.get(GameParams.PLAIN).getWidth()/2;
				int flagY = kingdom.getTerrainList().get(kingdom.getTerrainList().size()-1).getAbsoluteY() + 
						GfxManager.imgTerrain.get(GameParams.PLAIN).getHeight()/2;
				
				if(worldConver.isObjectInGameLayout(
						gameCamera.getPosX(), 
						gameCamera.getPosY(),
						flagX-GfxManager.imgFlagList.get(player.getFlag()).getWidth()/2, 
						flagY-GfxManager.imgFlagList.get(player.getFlag()).getHeight(), 
						GfxManager.imgFlagList.get(player.getFlag()).getWidth(), 
						GfxManager.imgFlagList.get(player.getFlag()).getHeight())){
				
				
					//Controla que no se pinta la ultima bandera mientras se ejecuta el efecto de conquista del jugador en curso
					if(
						player.getId() != getCurrentPlayer().getId() ||
						(!startConquest && subState != SUB_STATE_ACTION_CONQUEST) || 
						i!=player.getKingdomList().size()-1){
						
						gameBuffer.getGraphics().setClip(0, 0, gameBuffer.getWidth(), gameBuffer.getHeight());
						gameBuffer.getGraphics().drawImage(GfxManager.imgFlagList.get(player.getFlag()),
								worldConver.getConversionDrawX(gameCamera.getPosX(), flagX),
								worldConver.getConversionDrawY(gameCamera.getPosY(), flagY),
								Graphics.BOTTOM | Graphics.HCENTER);
						i++;
					}
				}
			}
		}
		
		if(subState == SUB_STATE_ACTION_SELECT && getCurrentPlayer().getActionIA() == null){
			gameScene.drawTarget(gameBuffer.getGraphics(), worldConver, gameCamera);
		}
		
		 
		 //Army
		 for(int i = 0; i < gameScene.getPlayerList().size(); i++){
			 for(Army army: gameScene.getPlayerList().get(i).getArmyList()){
				 boolean isSelected =  subState == SUB_STATE_ACTION_WAIT && 
						 getSelectedArmy() != null && getSelectedArmy().getId() == army.getId();
				 army.draw(gameBuffer.getGraphics(), worldConver, gameCamera, gameScene,
						 getSelectedArmy()!= null && isSelected, i == gameScene.getPlayerIndex() && army.getState() == Army.STATE_ON,
						 distorsion, distorsion, GameState.getInstance().getGameMode());
			 }
		 }
		 
		 //Gold
		 gameBuffer.getGraphics().setClip(0, 0, Define.SIZEX, Define.SIZEY);
		 if(	
				getSelectedArmy() != null && 
				getSelectedArmy().getPlayer()!= null && 
				getSelectedArmy().getPlayer().getActionIA() == null && 
				getSelectedArmy().getState() == Army.STATE_ON){
			 if(Main.isIntervalTwo()){
				 for(Kingdom k : gameScene.getKingdomList()){
					 
					if(!getSelectedArmy().getPlayer().hasKingom(k) && k.getTarget() != -1){
						int index = 0;
						if(k.getTerrainList().get(k.getTerrainList().size()-1).getType()>=GameParams.CITY){
							index = k.getTerrainList().size()-1;
						}
						 
						int goldX = worldConver.getConversionDrawX(gameCamera.getPosX(), 
								k.getTerrainList().get(index).getAbsoluteX()
								+ GfxManager.imgCoinSmall.getWidth()*0.2f);
						int goldY = worldConver.getConversionDrawY(gameCamera.getPosY(), 
								k.getTerrainList().get(index).getAbsoluteY()+ 
								GfxManager.imgTerrain.get(0).getHeight()/2f + GfxManager.imgCoinSmall.getHeight()*0.7f);
						String taxes = ""+k.getTaxes();
						gameBuffer.getGraphics().setAlpha(120);
						gameBuffer.getGraphics().fillRect(
								goldX-GfxManager.imgCoinSmall.getWidth()/2, goldY-Font.getFontHeight(Font.FONT_SMALL)/2, 
								Font.getFontWidth(Font.FONT_SMALL)*taxes.length()+GfxManager.imgCoinSmall.getWidth(), 
								Font.getFontHeight(Font.FONT_SMALL));
						gameBuffer.getGraphics().setAlpha(255);
						gameBuffer.getGraphics().drawImage(GfxManager.imgCoinSmall, 
								goldX, 
								goldY, 
								Graphics.VCENTER | Graphics.HCENTER);
							
						TextManager.drawSimpleText(gameBuffer.getGraphics(), Font.FONT_SMALL, taxes, 
							goldX + GfxManager.imgCoinSmall.getWidth()/2, 
							goldY, Graphics.VCENTER | Graphics.LEFT);
					 }
				 }
			 }
		 }
		 
		 
		 //Mist
		 for(Mist m : mistList){
			 m.clear = false;
		 }
		 g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		 Player humanPlayer = null;
		 if(getCurrentPlayer().getActionIA() == null){
			 humanPlayer = getCurrentPlayer();
		 }else{
			int count = 0;
			int i = gameScene.getPlayerIndex();
			while(humanPlayer == null && count < gameScene.getPlayerList().size()){
				i--;
				if(i < 0){
					i = gameScene.getPlayerList().size()-1;
				}
				count++;
				if(gameScene.getPlayerList().get(i) != null && gameScene.getPlayerList().get(i).getActionIA() == null){
					humanPlayer = gameScene.getPlayerList().get(i);
				}
			}
		}
		 
		
		if(!Main.IS_GAME_DEBUG && humanPlayer != null){//Si p == null significa que todos los jugadores son IA, asi que no pinto niebla
			for(Mist m : mistList){
				
				//Chequeo para despeja niebla
				checkClearMist(humanPlayer, m);
				if(!m.clear){
					if(worldConver.isObjectInGameLayout(
							gameCamera.getPosX(), gameCamera.getPosY(), m.x-m.w/2, m.y-m.h/2, m.w, m.h)){
						gameBuffer.getGraphics().drawImage(
								GfxManager.imgMist, 
								worldConver.getConversionDrawX(gameCamera.getPosX(), m.x), 
								worldConver.getConversionDrawY(gameCamera.getPosY(), m.y), 
								Graphics.VCENTER | Graphics.HCENTER);
					}
				}
			}
		} 
		
		 
		 if(game3D){
			 float totalW = (float)gameBuffer.getWidth()*distorsion;
			 float totalH = (float)gameBuffer.getHeight()*distorsion;
			 g.drawDistorisionImage(
					 gameBuffer, 
					 (int)worldConver.getCentGameLayoutX() - gameBuffer.getWidth()/2 , (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2, //Point corner top-left 
					 (int)worldConver.getCentGameLayoutX() + gameBuffer.getWidth()/2 , (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2, //Point corner top-right
					 (int)worldConver.getCentGameLayoutX() - (int)totalW/2, (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2 + (int)totalH,  //Point corner button-left
					 (int)worldConver.getCentGameLayoutX() + (int)totalW/2, (int)worldConver.getCentGameLayoutY() - gameBuffer.getHeight()/2 + (int)totalH	 //Point corner button-right
					 );
		 }else{
			 g.drawImage(gameBuffer, Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2, Graphics.VCENTER | Graphics.HCENTER);
		 }
		
		
		//Fin buffer
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		
		
		
		drawGUI(g);
		
		if(ModeGame.showDebugInfo){
		    /*
			for(Kingdom k : gameScene.getKingdomList())
				TextManager.drawSimpleText(g, Font.FONT_SMALL, 
						"ST/TA:" + k.getState() + "/" + k.getTarget(),
					worldConver.getConversionDrawX(gameCamera.getPosX(), k.getAbsoluteX()),
					worldConver.getConversionDrawY(gameCamera.getPosY(), k.getAbsoluteY()),
					Graphics.BOTTOM | Graphics.RIGHT);
			*/
			
			for(Player p : gameScene.getPlayerList())
				for(Army a : p.getArmyList())
					TextManager.drawSimpleText(g, Font.FONT_MEDIUM,
                            ""+ a.getId(),
					//""+a.getKingdom().getId() + "-" + a.getId() + " (" + a.getState() + ")",
					worldConver.getConversionDrawX(gameCamera.getPosX(), a.getAbsoluteX()-Define.SIZEX64),
					worldConver.getConversionDrawY(gameCamera.getPosY(), a.getAbsoluteY()-Define.SIZEX64),
					Graphics.BOTTOM | Graphics.RIGHT);
			
			//Margenes
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setColor(Main.COLOR_PURPLE_GALAXY);
			g.fillRect(0, 0, Define.SIZEX, worldConver.getMarginN());
			g.fillRect(0, Define.SIZEY-worldConver.getMarginS(), Define.SIZEX, worldConver.getMarginS());
			g.fillRect(0, 0, worldConver.getMarginW(), Define.SIZEY);
			g.fillRect(Define.SIZEX-worldConver.getMarginE(), 0, worldConver.getMarginE(), Define.SIZEY);
			
			g.fillRect((int)worldConver.getCentGameLayoutX()-2, 0, 4, Define.SIZEY);
			g.fillRect(0, (int)worldConver.getCentGameLayoutY()-2, Define.SIZEX, 4);
			g.setColor(Main.COLOR_BLUE);
			int cameraR = ((Define.SIZEX+Define.SIZEY)/2)/22;
			g.fillCircle(
					(int) gameCamera.getPosX()+worldConver.getMarginW(),
					(int) gameCamera.getPosY()+worldConver.getMarginN(),
					cameraR);
			
			
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setTextSize(32);
			g.setAlpha(160);
			g.setColor(0x88000000);
			g.fillRect(0, 0, Define.SIZEX, g.getTextHeight() * 3);
			g.setAlpha(255);
			
			g.drawText("CameraX: " + gameCamera.getPosX(), 0, g.getTextHeight(), Main.COLOR_WHITE);
			g.drawText("CameraY: " + gameCamera.getPosY(), (int)(Define.SIZEX*0.33), g.getTextHeight(), Main.COLOR_WHITE);
			g.drawText("State: " + state, 0, g.getTextHeight()*2, Main.COLOR_WHITE);
			g.drawText("Sub-State: " + subState, (int)(Define.SIZEX*0.33), g.getTextHeight()*2, Main.COLOR_WHITE);
			g.drawText("Army state: " + (getSelectedArmy() != null?getSelectedArmy().getState():"-"), (int)(Define.SIZEX*0.33), g.getTextHeight()*3, Main.COLOR_WHITE);
			
			String kingdomList = "";
			for(Kingdom kingdom : getCurrentPlayer().getKingdomList()){
				kingdomList += kingdom.getId() + ", ";
			}
			g.drawText("Domains: " + kingdomList, 0, g.getTextHeight()*4, Main.COLOR_WHITE);
		}
		drawConquest(g, getSelectedArmy());
		drawPresentation(g);
	}
	
	private void updateGUI(MultiTouchHandler multiTouchHandler, float delta){
		NotificationBox.getInstance().update(delta);
		
		btnCancel.update(multiTouchHandler);
		btnNext.update(multiTouchHandler);
		btnMap.update(multiTouchHandler);
        btnChest.update(multiTouchHandler);
		btnArmy.update(multiTouchHandler);
		btnFlagHelmet.update(multiTouchHandler, delta);
		btnFlagCastle.update(multiTouchHandler, delta);
		if(Main.IS_GAME_DEBUG){
			btnDebugPause.update(multiTouchHandler);
		}
		
	}
	
	private void drawGUI(Graphics g) {
		if(state == STATE_DEBUG){
			TextManager.drawSimpleText(g, Font.FONT_MEDIUM, "DEBUG PAUSE", 
				GfxManager.imgButtonDebugPauseRelease.getWidth()*2, 
				GfxManager.imgButtonDebugPauseRelease.getHeight(), 
				Graphics.VCENTER | Graphics.LEFT);
		}


		if(state != STATE_END){
			TextManager.drawSimpleText(g, Font.FONT_MEDIUM, getCurrentPlayer().getName(), 
					0, Define.SIZEY-GfxManager.imgGameHud.getHeight(), Graphics.BOTTOM | Graphics.LEFT);
			}
		g.drawImage(GfxManager.imgGameHud, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);

		economyBox.draw(g, GfxManager.imgBlackBG);
		armyBox.draw(g);
		discardBox.draw(g, null);
		troopExceedBox.draw(g, GfxManager.imgBlackBG);
		confirmActionBox.draw(g, GfxManager.imgBlackBG);
		battleBox.draw(g);
		cityBox.draw(g);
		mapBox.draw(g);
		rankingBox.draw(g);
		endGameBox.draw(g, GfxManager.imgBlackBG);
		resultBox.draw(g, GfxManager.imgBlackBG);
		btnCancel.draw(g, 0, 0);
		btnNext.draw(g, 0, 0);
		btnFlagHelmet.draw(g);
		btnFlagCastle.draw(g);
		btnMap.draw(g, 0, 0);
		btnArmy.draw(g, 0, 0);
		btnChest.draw(g, 0, 0);

        //Gold
        TextManager.drawSimpleText(g, Font.FONT_MEDIUM,
                "" + getCurrentPlayer().getGold(),
                btnChest.getX()+ btnChest.getWidth()/2,
                btnChest.getY(),
                Graphics.VCENTER | Graphics.LEFT);


        if(Main.IS_GAME_DEBUG){
			btnDebugPause.draw(g, 0, 0);
		}
		NotificationBox.getInstance().draw(g);
	}

	/*
	private void drawGold(Graphics g){
		int margin = Define.SIZEY64;
		String text = "" + getCurrentPlayer().getGold();
		int totalWidth = GfxManager.imgChest.getWidth() + margin + Font.getFontWidth(Font.FONT_MEDIUM)*text.length();
		int x = Define.SIZEX - Define.SIZEX4 - totalWidth/2; 
		int y = Define.SIZEY - GfxManager.imgGameHud.getHeight()/2 + GfxManager.imgChest.getHeight()/4; 
		g.drawImage(GfxManager.imgChest, x, y, Graphics.VCENTER | Graphics.HCENTER);
		TextManager.drawSimpleText(g, Font.FONT_MEDIUM, text, 
			x+margin + GfxManager.imgChest.getWidth()/2 + Font.getFontWidth(Font.FONT_MEDIUM)*text.length()/2,
			y, 
			Graphics.VCENTER | Graphics.HCENTER);
	}
	*/
	
	private void cleanArmyAction(){
		for(Player player : gameScene.getPlayerList()){
			for(Army army : player.getArmyList()){
				army.setSelected(false);
				army.setDefeat(false);
				army.getButton().reset();
			}
		}
	}
	
	private void prepareArmy(){
		for(Player player : gameScene.getPlayerList()){
			for(Army army : player.getArmyList()){
				army.setLastKingdom(army.getKingdom());
			}
		}
	}
	
	private void insertTargetUnMap(Army army){
		
		//Quito todos lo indicadores de target
		gameScene.cleanKingdomTarget();
		
		for(Kingdom border : getSelectedArmy().getKingdom() .getBorderList()){
			for(Kingdom kingdom: gameScene.getKingdomList()){
				if(kingdom.getTarget()== -1 && kingdom.getId() == border.getId()){
					
					//Si tengo un ejercito en la zona me uno
					if(
						getCurrentPlayer().getArmy(kingdom) != null){
						kingdom.setTarget(Kingdom.TARGET_AGGREGATION);
						kingdom.setTouchX(kingdom.getAbsoluteX());
						kingdom.setTouchY(kingdom.getAbsoluteY());
					}else{
						//Si el territorio es mio
						if(getCurrentPlayer().hasKingom(kingdom)){
							//Busco ejercitos enemigos
							if(getEnemyAtKingdom(getCurrentPlayer(), kingdom) != null){
								kingdom.setTarget(Kingdom.TARGET_BATTLE);
								kingdom.setTouchX(kingdom.getAbsoluteX());
								kingdom.setTouchY(kingdom.getAbsoluteY());
							}else{
								kingdom.setTarget(Kingdom.TARGET_DOMAIN);
								kingdom.setTouchX(kingdom.getAbsoluteX());
								kingdom.setTouchY(kingdom.getAbsoluteY());
							}
						}else{
							if(kingdom.isProtectedByFaith()){
								kingdom.setTarget(-1);
							}else{
                                kingdom.setTarget(Kingdom.TARGET_BATTLE);
                                //Busco ejercitos enemigos
                                if(getEnemyAtKingdom(getCurrentPlayer(), kingdom) != null){
                                    kingdom.setTouchX(kingdom.getAbsoluteX());
                                    kingdom.setTouchY(kingdom.getAbsoluteY());
                                }else{
                                    kingdom.setTouchX(kingdom.getTerrainList().get(0).getAbsoluteX());
                                    kingdom.setTouchY(kingdom.getTerrainList().get(0).getAbsoluteY());
                                }
							}
						}
					}
				}
			}
		}
		//Si estoy en territorio hostil(A midad de una conquista), lo anyado a los seleccionables
		if(!getCurrentPlayer().hasKingom(getSelectedArmy().getKingdom())){
            if(getSelectedArmy().getKingdom().isProtectedByFaith()){
                getSelectedArmy().getKingdom().setTarget(-1);
            }else {
                getSelectedArmy().getKingdom().setTarget(Kingdom.TARGET_BATTLE);
                getSelectedArmy().getKingdom().setTouchX(getSelectedArmy().getKingdom().
                        getTerrainList().get(getSelectedArmy().getKingdom().getState()).getAbsoluteX());
                getSelectedArmy().getKingdom().setTouchY(getSelectedArmy().getKingdom().
                        getTerrainList().get(getSelectedArmy().getKingdom().getState()).getAbsoluteY());
            }
		}
	}

	private void changeState(int newState){
		UserInput.getInstance().getMultiTouchHandler().resetTouch();
		cleanArmyAction();
		prepareArmy();
		gameScene.resetKingdoms();
		btnNext.setDisabled(true);
		btnCancel.setDisabled(true);
		btnMap.setDisabled(true);
        btnChest.setDisabled(true);
		btnArmy.setDisabled(true);
		btnFlagHelmet.hide();
		btnFlagCastle.hide();
		subState = 0;
		state = newState;
		switch(state){
		case STATE_INCOME:

		    //Validaciones
            if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){

                //Validacion del jugador
                if(!getCurrentPlayer().getName().equals(GameState.getInstance().getName())){
					OnlineInputOutput.getInstance().sendIncidence(
							Main.getInstance().getContext(),
							""+GameState.getInstance().getSceneData().getId(),
							GameState.getInstance().getName(), "getCurrentPlayer().getName() No coincide con GameState.getInstance().getName()");
						Main.changeState(Define.ST_MENU_MAIN, true);
                }

                //Validacion de tropas
                List<Integer> idList = new ArrayList<Integer>();
                int numTroop = 0;
                for(Player p : gameScene.getPlayerList()){
                    for(Army a : p.getArmyList()){
                        numTroop++;
                        boolean exist = false;
                        for(Integer id : idList){
                            exist = id == a.getId();
                        }
                        if(!exist){
                            idList.add(a.getId());
                        }
                    }
                }
                if(idList.size() != numTroop){
                    OnlineInputOutput.getInstance().sendIncidence(
                            Main.getInstance().getContext(),
                            ""+GameState.getInstance().getSceneData().getId(),
                            GameState.getInstance().getName(), "Discrepancia con ID tropas");
                    Main.changeState(Define.ST_MENU_MAIN, true);
                }
            }

			if(Main.IS_GAME_DEBUG){
				btnDebugPause.setDisabled(getCurrentPlayer().getActionIA() == null);
			}
			startPresentation(Font.FONT_BIG, RscManager.allText[RscManager.TXT_GAME_TURN] + 
					(gameScene.getTurnCount()+1) + " - " + getCurrentPlayer().getName());
			cameraTargetX = getCurrentPlayer().getCapitalkingdom().getAbsoluteX();
			cameraTargetY = getCurrentPlayer().getCapitalkingdom().getAbsoluteY();



			if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_PLAY_AND_PASS) {
				if (getCurrentPlayer().getActionIA() == null) {
					intertitialCount += 2;
				} else {
					intertitialCount += 1;
				}
				if (getCurrentPlayer().getActionIA() == null && intertitialCount >= 20) {
					Main.getInstance().getActivity().requestInterstitial();
				}
			}
			break;
			
		case STATE_ECONOMY:
			if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_PLAY_AND_PASS) {
				if (getCurrentPlayer().getActionIA() == null) {
					if (getCurrentPlayer().getActionIA() == null && intertitialCount >= 20) {
						Main.getInstance().getActivity().loadInterstitial();
						intertitialCount = 0;
					}
				}
			}

			if(localTurnCount == 0){
				SndManager.getInstance().playMusic(Main.MUSIC_MAP, true);
			}
			
			//City management
			for(Kingdom k : getCurrentPlayer().getKingdomList()){
				if(k.getCityManagement() != null){
					k.getCityManagement().update();
				}
			}
			
			//Activo tropas
			for(Player player : gameScene.getPlayerList())
				for(Army army : player.getArmyList()) {

			        /*
					if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE) {
						long seed =
								GameState.getInstance().getSceneData().getId() * gameScene.getTurnCount() * (army.getId() + 1);
					}
					*/

					army.changeState(Army.STATE_ON);
				}
			
			//Calculo de ganancias:
			int tax = getCurrentPlayer().getTaxes();
			//Calculo de salarios
			int salary = getCurrentPlayer().getCost(false);
			
			getCurrentPlayer().setGold(getCurrentPlayer().getGold()+tax-salary);

			//Quito los marcadores de Fe
			for(Kingdom k : getCurrentPlayer().getKingdomList()){
				k.setProtectedByFaith(false);
			}
			
			if(getCurrentPlayer().getActionIA() == null){
				economyBox.start(
						RscManager.allText[RscManager.TXT_GAME_ECONOMY], 
						RscManager.allText[RscManager.TXT_GAME_EARNING] + " + " + tax + " " + 
						RscManager.allText[RscManager.TXT_GAME_SALARY] + " - " + salary);
			}else{
				if(getCurrentPlayer().getGold() < 0){
					changeState(STATE_DISCARD);
				}else{
					changeState(STATE_ACTION);
				}
			}
			break;
			
		case STATE_DISCARD:
			if(getCurrentPlayer().getActionIA() == null){
				discardBox.start(null, RscManager.allText[RscManager.TXT_GAME_COST_OF_TROOPS]);
			}else{
				getCurrentPlayer().getActionIA().discard();
				changeState(STATE_ACTION);
			}
			break;
			
		case STATE_ACTION:
			/*
			if(getCurrentPlayer().getActionIA() != null){
				changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
			}else{
				changeSubState(SUB_STATE_ACTION_WAIT);
			}
			*/
			changeSubState(SUB_STATE_ACTION_WAIT);
			break;
			
		case STATE_END:
			if(isDebugPaused){
				changeState(STATE_DEBUG);
			}else{
				if(isWinGame()){
					changeState(STATE_WIN);
				}
                else if(isGameOver()){
                    changeState(STATE_GAME_OVER);
                }
				else{
					
					//Faith
					for(Kingdom k : getCurrentPlayer().getKingdomList()){
						//Chequeo de Fe
						if(k.getCityManagement() != null){
							if(k.getCityManagement().getBuildingList().get(GameParams.CHURCH).getActiveLevel() > -1){
								int pro = GameParams.FAITH_CHECK[k.getCityManagement().getBuildingList().get(GameParams.CHURCH).getActiveLevel()];
								int ran = Main.getRandom(0, 100);
								k.setProtectedByFaith(ran <= pro);
							}
						}
					}
					
					setNextPlayer();
					
					if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){
						sendSceneToServer(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, true, true);
					}else if(GameState.getInstance().getGameMode() == GameState.GAME_MODE_PLAY_AND_PASS){
						ModeGame.saveGame();
						localTurnCount++;
						changeState(STATE_INCOME);
					}
				}
			}
			break;
		    case STATE_WIN:
			endGameBox.start(
					RscManager.allText[RscManager.TXT_GAME_WIN],
					RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + getWinner().getName() + " " +
					RscManager.allText[RscManager.TXT_GAME_IS_WINNER]);
			break;
			case STATE_GAME_OVER:
                endGameBox.start(
                        null,
                        RscManager.allText[RscManager.TXT_GAME_YOU_LOST_GAME]);
                break;
		    case STATE_DEBUG:
			    btnDebugPause.setDisabled(false);
			    break;
		}
	}
	
	private void changeSubState(int newSubState){
		
		lastSubState = subState;
		btnNext.setDisabled(true);
		btnCancel.setDisabled(true);
		btnMap.setDisabled(true);
        btnChest.setDisabled(true);
		btnArmy.setDisabled(true);
		subState = newSubState;
		
		switch(state){
		case STATE_INCOME:
		case STATE_ECONOMY:
			gameScene.resetKingdoms();
			break;
		case STATE_ACTION:
			btnNext.setDisabled(true);
			btnCancel.setDisabled(true);
			
			switch(subState){
			case SUB_STATE_ACTION_IA_WAIT_START:
				gameScene.cleanKingdomTarget();
				IAWaitCount = 0;
				//Solo espero si IA va a dar una torta
				if(!(getSelectedArmy() != null && 
						(
						getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_ATACK || 
						getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_MOVE_AND_ATACK
						)
					)){
					changeSubState(SUB_STATE_ACTION_WAIT);
				}
				break;
			case SUB_STATE_ACTION_WAIT:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				btnNext.setDisabled(getCurrentPlayer().getActionIA()!=null);
				btnCancel.setDisabled(true);
				btnMap.setDisabled(false);
                btnChest.setDisabled(false);
				
				//Se deshabilita este boton, si no quedan tropas activas
				boolean disabled = true;
				if(getCurrentPlayer().getActionIA() == null){
					for(int i = 0; i < getCurrentPlayer().getArmyList().size() && disabled; i++){
						if(getCurrentPlayer().getArmyList().get(i).getState() == Army.STATE_ON){
							disabled = false;
						}
					}
				}
				btnArmy.setDisabled(disabled);
				
				cleanArmyAction();
				//
				if(getDefeatArmy() != null){
					getDefeatArmy().setDefeat(false);
				}
				if(getCurrentPlayer().getActionIA() != null){
					
					//Management
					getCurrentPlayer().getActionIA().management(worldConver, gameCamera, gameScene.getMapObject(), gameScene.getPlayerList());
					
					//Activo los ejercitos uno a uno
					Army iaArmy = getCurrentPlayer().getActionIA().getActiveArmy(gameScene.getPlayerList());
					
					if(iaArmy != null){
						setSelectedArmy(iaArmy);
						
						//Camara se posiciona en el seleccionado
						cameraTargetX = getSelectedArmy().getAbsoluteX();
						cameraTargetY = getSelectedArmy().getAbsoluteY();
						insertTargetUnMap(getSelectedArmy());
						changeSubState(SUB_STATE_ACTION_SELECT);
					//Cuando no me quedan mas ejercitos, cambio de estado
					}else{
						changeSubState(SUB_STATE_ACTION_IA_WAIT_END);
					}
				}
				break;
			
			case SUB_STATE_ACTION_SELECT:
				gameScene.resetKingdoms();
				btnNext.setDisabled(true);
				btnCancel.setDisabled(getCurrentPlayer().getActionIA()!=null);
				break;
				
			case SUB_STATE_ACTION_EXCEED:
				gameScene.cleanKingdomTarget();
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				troopExceedBox.start(null, RscManager.allText[RscManager.TXT_GAME_EXCEED_TROOPS]);
			break;
			case SUB_STATE_ACTION_MOVE:
				gameScene.cleanKingdomTarget();
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				SndManager.getInstance().playFX(Main.FX_MARCH, 0);
				
				
				//Ojo con la mierda esta que no quite estados de conquista que no tocan
				getSelectedArmy().getKingdom().setState(0);
				
				//Actualizo el reino del ejercito
				putArmyAtKingdom(getSelectedArmy(), getSelectKingdom());
				
				//Quito todos los otros indicadores de target
				//Quito todos lo indicadores de target
				gameScene.cleanKingdomTarget();
				
				getSelectedArmy().changeState(Army.STATE_MOVE);
				getSelectKingdom().getButton().reset();
				
				break;
			case SUB_STATE_ACTION_RESOLVE_MOVE:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				resolveMovement();
				break;
			case SUB_STATE_ACTION_ANIM_ATACK:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				getSelectedArmy().changeState(Army.STATE_ATACK);
				break;
			case SUB_STATE_ACTION_COMBAT:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				btnFlagHelmet.hide();
				btnFlagCastle.hide();
				//getSelectedArmy().changeState(Army.STATE_OFF);//OJO
				break;
			case SUB_STATE_ACTION_RESULT:
                gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				break;
			case SUB_STATE_ACTION_ANIM_DEAD:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				getDefeatArmy().changeState(Army.STATE_DEAD);
				break;
			case SUB_STATE_ACTION_CONQUEST:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				getSelectedArmy().changeState(Army.STATE_OFF);
				startConquest(getSelectedArmy().getKingdom(), getSelectedArmy().getPlayer().getFlag());
				break;
			case SUB_STATE_ACTION_SCAPE:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				//Si el que huye no es el que ataca
				if(getDefeatArmy().getId() != getSelectedArmy().getId()){
					getSelectedArmy().changeState(Army.STATE_OFF);
					
					getSelectedArmy().getKingdom().setState(0);//control
				}
				Kingdom defeatTarget = getBorderKingdom(getDefeatArmy());
				putArmyAtKingdom(getDefeatArmy(), defeatTarget);
				getDefeatArmy().changeState(Army.STATE_MOVE);
				break;
			case SUB_STATE_ACTION_RESOLVE_SCAPE:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				resolveScape();
				break;
			case SUB_STATE_ARMY_MANAGEMENT:
			case SUB_STATE_CITY_MANAGEMENT:
			case SUB_STATE_MAP_MANAGEMENT:
			case SUB_STATE_RANKING_MANAGEMENT:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				break;
			case SUB_STATE_ACTION_IA_WAIT_END:
				gameScene.cleanKingdomTarget();
				gameScene.resetKingdoms();
				IAWaitCount = 0;
				break;
			}
			break;
		case STATE_END:
			gameScene.resetKingdoms();
			break;
		case STATE_DEBUG:
			gameScene.resetKingdoms();
			btnMap.setDisabled(false);
            btnChest.setDisabled(false);
			break;
		}
	}
	
	public void setNextPlayer(){
		do{
			gameScene.setPlayerIndex((gameScene.getPlayerIndex()+1)%gameScene.getPlayerList().size());
		}
		while(getCurrentPlayer().getNumberCitys() == 0);
		
		if(gameScene.getPlayerIndex()==0){
			gameScene.setTurnCount(gameScene.getTurnCount()+1);
		}
	}
	
	public void sendSceneToServer(int newState, boolean notificationResult, boolean showWait){
		if(OnlineInputOutput.getInstance().isOnline(Main.getInstance().getActivity())){
			//Notification online
			/*
			String message = RscManager.allText[RscManager.TXT_GAME_TURN] + (gameScene.getTurnCount()+1);
			dataSender.addNotification(getCurrentPlayer().getName(), message);
			*/
			String result = dataSender.sendGameScene(gameScene, 1, showWait);
			if(result.equals("Succes")){
				dataSender.sendGameNotifications();
				NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_SEND_DATA]);
			}
			else if(result.equals("Server validation error")){
				NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_SEND_DATA]);
			}
            if(notificationResult){
                NotificationBox.getInstance().addMessage(result);
            }
            Main.changeState(newState, true);
		}else{
			if(notificationResult){
				NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_NO_CONNECTION]);
			}
		}
	}
	
	private int presentationState;
	private int presentationFont;
	private String presentationText;
	private float presentationShowCount;
	private float presentationModX;
	private static final int STATE_PRESENTATION_MOVE_1 = 1;
	private static final int STATE_PRESENTATION_SHOW = 2;
	private static final int STATE_PRESENTATION_MOVE_2 = 3;
	
	private void startPresentation(int font, String text){
		presentationState = STATE_PRESENTATION_MOVE_1;
		presentationFont = font;
		presentationText = text;
		presentationShowCount = 0;
		presentationModX = -Define.SIZEX;
		
		//Sound here
		SndManager.getInstance().playFX(Main.FX_START_GAME, 0);
	}
	
	private boolean updatePresentation(float delta){
		
		switch(presentationState){
		case STATE_PRESENTATION_MOVE_1:
			if(presentationModX < 0){
				presentationModX -= (presentationModX*4f-Define.SIZEX8) * delta; 
			}else{
				presentationModX = 0;
				presentationState = STATE_PRESENTATION_SHOW;
			}
			return true;
		case STATE_PRESENTATION_SHOW:
			if(presentationShowCount >=  0.35f){
				presentationState = STATE_PRESENTATION_MOVE_2;
			}else{
				presentationShowCount+= delta;
			}
			return true;
		case STATE_PRESENTATION_MOVE_2:
			if(presentationModX < Define.SIZEX){
				presentationModX += (presentationModX*4f+Define.SIZEX8) * delta;
			}else{
				presentationState = 0;
			}
			return true;
		}
		
		return false;
	}
	
	private void drawPresentation(Graphics g){
		if(presentationState != 0){
			g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			g.setAlpha(140);
			g.setImageSize(1f, 1f-(Math.abs(presentationModX)/Define.SIZEX));
			g.drawImage(GfxManager.imgTextBG, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
			g.setImageSize(1f, 1f);
			g.setAlpha(255);
			TextManager.drawSimpleText(g, presentationFont, presentationText, 
				(int) (Define.SIZEX2+presentationModX), 
				Define.SIZEY2, Graphics.VCENTER | Graphics.HCENTER);
		}
	}
	
	
	
	
	private boolean startAnimDead;
	
	private boolean startConquest;
	private float modSizeConquest;
	private int modAlphaConquest;
	public static final float MAX_SIZE_CONSQUEST = 16f;
	private void startConquest(Kingdom kingdom, int flag){
		this.startConquest = false;
		this.modAlphaConquest = 255;
		this.modSizeConquest = MAX_SIZE_CONSQUEST;
		
		if(getCurrentPlayer().getActionIA() != null){
			SndManager.getInstance().playFX(Main.FX_FANFARRIA_START, 0);
		}else{
			SndManager.getInstance().playFX(Main.FX_FANFARRIA_END, 0);
		}
		
	}
	
	private boolean updateConquest(float delta){
		if(modSizeConquest > 0.01f){
			modSizeConquest -= (modSizeConquest*8f)*delta;
			modAlphaConquest = (int)((modSizeConquest*255f)/MAX_SIZE_CONSQUEST);
			return true;
		}
		if(modAlphaConquest >= 255){
			modSizeConquest = 0f;
			modAlphaConquest = 255;
		}
		//Log.i("INFO", "modSizeConquest: " + modSizeConquest);
		return false;	
	}
	
	private void drawConquest(Graphics g, Army army){
		if(subState == SUB_STATE_ACTION_CONQUEST){
			g.setAlpha(255-modAlphaConquest);
			g.setImageSize(
					1f+modSizeConquest, 
					1f+modSizeConquest);
			
			int flagX = worldConver.getConversionDrawX(gameCamera.getPosX(), 
					army.getKingdom().getTerrainList().get(army.getKingdom().getTerrainList().size()-1).getAbsoluteX())
					+GfxManager.imgTerrain.get(0).getWidth()/2;
			int flagY = worldConver.getConversionDrawX(gameCamera.getPosY(), 
					army.getKingdom().getTerrainList().get(army.getKingdom().getTerrainList().size()-1).getAbsoluteY())
					-GfxManager.imgTerrain.get(0).getHeight()/2;
			
			int modX = (int)(((Define.SIZEX2-flagX)*modSizeConquest)/MAX_SIZE_CONSQUEST);
			int modY = (int)(((Define.SIZEY2-flagY)*modSizeConquest)/MAX_SIZE_CONSQUEST);
			
			g.drawImage(GfxManager.imgFlagList.get(army.getPlayer().getFlag()), 
					flagX+modX,
					flagY+modY,
					Graphics.VCENTER | Graphics.HCENTER);
			g.setAlpha(255);
			g.setImageSize(1f, 1f);
		}
	}
	
	/**
	 * Devuelve el ejercito seleccionado
	 * @return
	 */
	private Army getSelectedArmy(){
		Army selected = null;
		for(Player player : gameScene.getPlayerList()){
			for(Army army : player.getArmyList()){
				if(army.isSelected()){
					selected = army;
                    return selected;
				}
			}
		}
		return selected;
	}
	
	private Kingdom getSelectKingdom(){
		Kingdom selected = null;
		for(Kingdom kingdom: gameScene.getKingdomList()){
			if(kingdom.isSelect()){
				selected = kingdom;
			}
		}
		return selected;
	}
	
	private void setSelectedArmy(Army army){
		if(getSelectedArmy() != null){
			getSelectedArmy().setSelected(false);
		}
		for(Player player : gameScene.getPlayerList()){
			for(Army a : player.getArmyList()){
				if(a.getId() == army.getId()){
					a.setSelected(true);
					return;
				}
			}
		}
	}

    private Player getWinner(){
		Player winner = null;
		if(isWinGame()){
			for(int i = 0; i < gameScene.getPlayerList().size() && winner == null; i++){
				if(gameScene.getPlayerList().get(i).getCapitalkingdom() != null){
					winner = gameScene.getPlayerList().get(i);
				}
			}
		}
		return winner;
	}
	
	private Player getPlayerByKingdom(Kingdom kingdom){
		Player player = null;
		for(int i = 0; i < gameScene.getPlayerList().size() && player == null; i++){
			for(int j = 0; j < gameScene.getPlayerList().get(i).getKingdomList().size() && player == null; j++){
				if(gameScene.getPlayerList().get(i).getKingdomList().get(j).getId() == kingdom.getId()){
					player = gameScene.getPlayerList().get(i);
				}
			}
		}
		return player;
	}
	
	private boolean isWinGame(){
		boolean end = true;
		for(int i = 0; i < gameScene.getPlayerList().size() && end; i++){
			if(gameScene.getPlayerList().get(i).getId() != getCurrentPlayer().getId()){
				if(gameScene.getPlayerList().get(i).getCapitalkingdom() != null){
					end = false;
				}
			}
		}
		return end;
	}


    private boolean isGameOver(){
	    if(GameState.getInstance().getGameMode() != GameState.GAME_MODE_ONLINE && !Main.IS_GAME_DEBUG){
            boolean end = true;

            for(int i = 0; i < gameScene.getPlayerList().size() && end; i++){
                if(gameScene.getPlayerList().get(i).getActionIA() == null){
                    end = false;
                }
            }
            return end;
        }else{
	        return false;
        }
    }
	
	/**
	 * Devuelve true si el ejercito que se pasa por parametro pertenece al jugador en curso.
	 * @param army
	 * @return
	 */
	private boolean isSelectedArmyFromCurrentPlayer(Army army){
		boolean current = false;
		for(Army a : getCurrentPlayer().getArmyList()){
			if(a.getId() == army.getId()){
				current = true;
				break;
			}
		}
		return current;
	}
	
	private Army getDefeatArmy(){
		Army army = null;
		for(Player player: gameScene.getPlayerList()){
			for(Army a : player.getArmyList()){
				if(a.isDefeat()){
					army = a;
					break;
				}
			}
		}
		return army;
	}
	
	private Army getArmyAtKingdom(Kingdom kingdom){
		Army army = null;
		for(Player player : gameScene.getPlayerList()){
			army = player.getArmy(kingdom);
			if(army != null)
				break;
		}
		return army;
	}
	
	/**
	 * Devuelve true si el ejercito del reino que se pasa por parametro contiene un ejercito enemigo (Explorar fronteras)
	 */
	private Army getEnemyAtKingdom(Player player, Kingdom kingdom){
		Army enemy = null;
		for(Player p : gameScene.getPlayerList()){
			if(p.getId() != player.getId()){
				for(Army a : p.getArmyList()){
					if(a.getKingdom().getId() == kingdom.getId()){
						enemy = a;
						break;
					}
				}
			}
		}
		return enemy;
	}
	
	/**
	 * Devuelve true hay un ejercito enemigo en el reino donde se encuentra el ejercito seleccionado
	 */
	private Army getEnemyAtKingdom(Player player){
		if(player.getSelectedArmy() != null){
			Kingdom kingdom = player.getSelectedArmy().getKingdom();
			return getEnemyAtKingdom(player, kingdom);
		}else{
			return null;
		}
	}
	
	private void removeArmy(Army army){
		for(Player player: gameScene.getPlayerList()){
			for(int i = 0; i < player.getArmyList().size(); i++){
				if(player.getArmyList().get(i).getId() == army.getId()){
					player.getArmyList().remove(i);
					break;
				}
			}
		}
	}
	
	private void endBattle(){
		
		Army defeat = getDefeatArmy();
		if(getSelectedArmy() != null){
			getSelectedArmy().changeState(Army.STATE_OFF);//OJO
		}
		if(defeat != null){
			changeSubState(SUB_STATE_ACTION_SCAPE);
		}else{
			if(getCurrentPlayer().getActionIA() != null){
				changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
			}else{
				changeSubState(SUB_STATE_ACTION_WAIT);
			}
		}
	}
	
	private int join(Army army1, Army army2){
		int cost = 0;
		for(Troop troop : army2.getTroopList()){
			if(army1.getTroopList().size() < GameParams.MAX_NUMBER_OF_TROOPS){
				troop.setSubject(false);
				army1.getTroopList().add(troop);
			}else{
				cost += GameParams.TROOP_COST[troop.getType()]/2;
			}
		}
		army1.setDefeat(army1.isDefeat() || army2.isDefeat());
		//Si cualquiera de los ejercitos aun no ha actuado, mantengo el estado
		if(army1.getState()==Army.STATE_ON || army2.getState()==Army.STATE_ON){
			army1.changeState(Army.STATE_ON);
		}
		removeArmy(army2);
		
		return cost;
	}
	
	private void addNewConquest(Player player, Kingdom kingdom){
		//Elimino el territorio del domino de cualquier jugador
		for(Player p : gameScene.getPlayerList()){
			p.removeKingdom(kingdom);
		}
		
		player.getKingdomList().add(kingdom);
	}

	private void putArmyAtKingdom(Army army, Kingdom newKingdom){
		army.setLastKingdom(army.getKingdom());
		army.setKingdom(newKingdom);
		//Actulizo su zona de touch (Se altera al moverse)
		army.setTouchX(newKingdom.getAbsoluteX());
		army.setTouchY(newKingdom.getAbsoluteY());
	}
	
	private void combat(Kingdom kingdom, Army armyAtack, Army armyDefense){
		
		int diceDifficult = 
				GameUtils.getInstance().calculateDifficult(
				kingdom, armyAtack, armyDefense);
		
		int result = 0;
		int[] diceValue = new int[]{
				Main.getRandom(1, GameParams.ROLL_SYSTEM), 
				Main.getRandom(1, GameParams.ROLL_SYSTEM), 
				Main.getRandom(1, GameParams.ROLL_SYSTEM)};
		
		for(int i = 0;i < 3; i++){
			if(diceValue[i] == GameParams.ROLL_SYSTEM || diceValue[i] >= diceDifficult){
				result++;
			}
		}
		resolveCombat(result);
		
		if(armyAtack != null && !armyAtack.isDefeat()){
			armyAtack.changeState(Army.STATE_ATACK);
		}else if(armyDefense != null && !armyDefense.isDefeat()){
			armyDefense.changeState(Army.STATE_ATACK);
		}
	}
	
	private void startAction(){
		
		Kingdom kingdom = getSelectKingdom(); 
		if(getCurrentPlayer().getActionIA() != null){
			kingdom = getSelectedArmy().getPlayer().getActionIA().getKingdomToDecision();
		}else{
			kingdom = getSelectKingdom(); 
		}
		
		boolean move = kingdom.getId() != getSelectedArmy().getKingdom().getId();
		
		if(move){
			
			if(getCurrentPlayer().getActionIA() != null){
				kingdom.setSelect(true);
				changeSubState(SUB_STATE_ACTION_MOVE);
			}else{
				//Chequeo si en el target existe un colegui
				Army neighbour = getArmyAtKingdom(kingdom);
				if(neighbour != null && neighbour.getPlayer().getId() == getSelectedArmy().getPlayer().getId()){
					
					//Chequeo si se excede el maximo de tropas
					if(neighbour.getTroopList().size()+getSelectedArmy().getTroopList().size() > GameParams.MAX_NUMBER_OF_TROOPS){
						changeSubState(SUB_STATE_ACTION_EXCEED);
					}else{
						changeSubState(SUB_STATE_ACTION_MOVE);
					}
				}else{
					changeSubState(SUB_STATE_ACTION_MOVE);
				}
			}
		}else{
			//Quito todos lo indicadores de target
			gameScene.cleanKingdomTarget();
			changeSubState(SUB_STATE_ACTION_RESOLVE_MOVE);
		}
	}
	
	private void resolveCombat(int result){
		startAnimDead = false;
		Player defeatPlayer = null;
		Army enemy = getEnemyAtKingdom(getCurrentPlayer());
		
		/*
		 * El ejercito ganador genera la mitad de su valor en da�o al ejercito perdedor
		 * El ejercito perdedor genera la la cuarta parte de su da�o al ejercito ganador
		 */
		boolean showResultBox = 
				getSelectedArmy().getPlayer().getActionIA() == null ||
				(enemy != null && enemy.getPlayer().getActionIA() == null);
		
		boolean changeCapital = false;
		boolean deletePlayer = false;
		
		String textH = "";
		String textB = "";
		
		boolean attackerWins=false;
		boolean attackerLost=false;
		boolean attackerHasDestroyed=false;
		boolean arrackerHasBeendestroyed=false;
		
		textH=RscManager.allText[RscManager.TXT_GAME_RESULT];
		switch(result){
			case 0: 
				textH=RscManager.allText[RscManager.TXT_GAME_BIG_DEFEAT];
				break;
			case 1: 
				textH=RscManager.allText[RscManager.TXT_GAME_DEFEAT];
				break;
			case 2: 
				textH=RscManager.allText[RscManager.TXT_GAME_WIN];
				break;
			case 3: 
				textH=RscManager.allText[RscManager.TXT_GAME_BIG_VICTORY];
				break;
		}
		//Nyapa
		//result = 3;
		
		//Hay ejercito enemigo
		if(enemy != null){
			//Resolucion del combate
			Army defeated = null;
			if(result > 1)
				defeated = getEnemyAtKingdom(getCurrentPlayer());
			else
				defeated = getSelectedArmy();
			
			//Comparo si alguno de los territorios adyacentes pertenece al derrotado
			Kingdom defeatTarget = getBorderKingdom(defeated);
			
			
			boolean aniquilation = 
				(result == 3 && defeated.getPlayer().getId() != getCurrentPlayer().getId())
				||
				(result == 0 && defeated.getPlayer().getId() == getCurrentPlayer().getId());
			
			if(defeatTarget == null || aniquilation){
				
				startAnimDead = true;
				defeated.setDefeat(true);
				//Masacre al defensor
				if(defeated.getPlayer().getId() != getCurrentPlayer().getId()){
					attackerHasDestroyed = true;
					textB = RscManager.allText[RscManager.TXT_GAME_ATTACKER_HAS_DESTROYED];
				}
				//Masacrea al atacante
				else{
					arrackerHasBeendestroyed = true;
					textB = RscManager.allText[RscManager.TXT_GAME_ATTACKER_HAS_BEEN_DESTROYED];
				}
			}else{
				defeated.setDefeat(true);
				//Danyo
				int casualtiesFromArmy = 0;
				int casualtiesFromEnemy = 0;
				if(result == 1){//Ejercito selecionado pierde
					casualtiesFromArmy = (getSelectedArmy().getCost() * 25) / 100; 
					casualtiesFromEnemy = (enemy.getCost() * 50) / 100; 
				}else if(result == 2){//Ejercito selecionado gana
					casualtiesFromArmy = (getSelectedArmy().getCost() * 50) / 100; 
					casualtiesFromEnemy = (enemy.getCost() * 25) / 100;
				}
				getSelectedArmy().setDamage(casualtiesFromEnemy);
				enemy.setDamage(casualtiesFromArmy);
				textB = 
						RscManager.allText[RscManager.TXT_GAME_ATTACKER_LOST] + " " +
						casualtiesFromEnemy + " " + RscManager.allText[RscManager.TXT_GAME_LOSSES] + " " +
						RscManager.allText[RscManager.TXT_GAME_DEFENSER_LOST] + " " + 
						casualtiesFromArmy + " " + RscManager.allText[RscManager.TXT_GAME_LOSSES];
				
				if(defeated.getPlayer().getId() != getCurrentPlayer().getId()){
					attackerWins = true;
				}else{
					attackerLost = true;
				}
				
			}
			//Si el atacante gana, le quita el territorio al defensor
			if(getSelectedArmy() != null && result > 1){
				getSelectedArmy().getKingdom().setState(0);//control
				//getSelectedArmy().getKingdom().setTarget(-1);
			}
		}
		
		//No hay ejercito enemigo pues se combate en un territorio vacio
		else{
			if(result > 1){
				//Resolucion del combate
				int newState = getSelectedArmy().getKingdom().getState()+1;
				int totalStates = getSelectedArmy().getKingdom().getTotalStates();
				if(newState < totalStates){
					getSelectedArmy().getKingdom().setState(newState);
					//getSelectedArmy().getKingdom().setTarget(-1);
				}else{//Conquista
					startConquest = true;
					//Obtengo al perdedor antes de cambiar el reino de dueño
					defeatPlayer = getPlayerByKingdom(getSelectedArmy().getKingdom());

					getSelectedArmy().getKingdom().setState(0);
					//getSelectedArmy().getKingdom().setTarget(-1);
					addNewConquest(getCurrentPlayer(), getSelectedArmy().getKingdom());

					if(getSelectedArmy().getKingdom().isACity()) {
						//Cambio de capital
						changeCapital = defeatPlayer != null && defeatPlayer.changeCapital();

						//Eliminacion jugador
						deletePlayer = defeatPlayer != null && defeatPlayer.getCapitalkingdom() == null;
					}
				}
			}
		}
		
		if(changeCapital){
			cameraTargetX = defeatPlayer.getCapitalkingdom().getAbsoluteX();
			cameraTargetY = defeatPlayer.getCapitalkingdom().getAbsoluteY();
		}
		
		//Notificaciones:
		if(attackerWins){
			String message = RscManager.allText[RscManager.TXT_GAME_ATTACKER_WINS];
			if(!showResultBox){
				NotificationBox.getInstance().addMessage(message);
			}
			if(enemy != null){
				dataSender.addNotification(
						getCurrentPlayer().getName(), enemy.getPlayer().getName(), 1, 
						OnlineInputOutput.CODE_NOTIFICATION_YOUR_ARMY_DEFEATED);
			}
		}
		
		if(attackerLost){
			String message = RscManager.allText[RscManager.TXT_GAME_ATTACKER_LOSES];
			if(!showResultBox){
				NotificationBox.getInstance().addMessage(message);
			}
			if(enemy != null){
				dataSender.addNotification(
						getCurrentPlayer().getName(), enemy.getPlayer().getName(), 1, 
						OnlineInputOutput.CODE_NOTIFICATION_YOUR_ARMY_WON);
			}
		}
		
		if(attackerHasDestroyed){
			String message = RscManager.allText[RscManager.TXT_GAME_ATTACKER_HAS_DESTROYED];
			if(!showResultBox){
				NotificationBox.getInstance().addMessage(message);
			}
			if(enemy != null){
				dataSender.addNotification(
						getCurrentPlayer().getName(), enemy.getPlayer().getName(), 1, 
						OnlineInputOutput.CODE_NOTIFICATION_YOUR_ARMY_DESTROYED);
			}
		}
		
		if(arrackerHasBeendestroyed){
			String message = RscManager.allText[RscManager.TXT_GAME_ATTACKER_HAS_BEEN_DESTROYED];
			if(!showResultBox){
				NotificationBox.getInstance().addMessage(message);
			}
			if(enemy != null){
				dataSender.addNotification(
						getCurrentPlayer().getName(), enemy.getPlayer().getName(), 1,
						OnlineInputOutput.CODE_NOTIFICATION_YOUR_ARMY_DESTROYED_ENEMY);
			}
		}
		
		if(changeCapital){
			String message = 
					RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + defeatPlayer.getName() +
					RscManager.allText[RscManager.TXT_GAME_CHANGE_HIS_CAPITAL];
			NotificationBox.getInstance().addMessage(message);
			
			//Notifico al resto de jugadores que uno ha cambia de capital (Pongo como remitente al player afectado)
			if(enemy != null){
				for(Player player : gameScene.getPlayerList()){
					if(player != null && player.getId() != getCurrentPlayer().getId()){
						dataSender.addNotification(
							enemy.getPlayer().getName(), player.getName(), 1, 
							OnlineInputOutput.CODE_NOTIFICATION_CHANGE_CAPITAL);
					}
				}
			}
		}
		
		if(deletePlayer){
			String message = 
					RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + defeatPlayer.getName() +
					RscManager.allText[RscManager.TXT_GAME_LOST_GAME];
			NotificationBox.getInstance().addMessage(message);
			
			dataSender.addNotification(
					getCurrentPlayer().getName(), defeatPlayer.getName(), 0, 
					OnlineInputOutput.CODE_NOTIFICATION_YOU_LOST_GAME);
			
			//Notifico al resto de jugadores que uno ha perdido (Pongo como remitente al player afectado)
			if(enemy != null){
				for(Player player : gameScene.getPlayerList()){
					if(player != null && player.getId() != getCurrentPlayer().getId()){
						dataSender.addNotification(
								enemy.getPlayer().getName(), player.getName(), 1, 
								OnlineInputOutput.CODE_NOTIFICATION_LOST_GAME);
					}
				}
			}
		}
        ///*
		if(deletePlayer){
			gameScene.removePlayerKingdoms(defeatPlayer);
		}
		//*/
		if(showResultBox){
			resultBox.start(textB.length() > 0 ?textH:null, textB.length() > 0 ?textB:textH);
			
			if(result == 0 || result == 1){
				SndManager.getInstance().playFX(Main.FX_DEFEAT, 0);
			}else{
				SndManager.getInstance().playFX(Main.FX_VICTORY, 0);
			}
			
			changeSubState(SUB_STATE_ACTION_RESULT);
		}else{
			if(startConquest){
				changeSubState(SUB_STATE_ACTION_CONQUEST);
			}else if(startAnimDead){
				changeSubState(SUB_STATE_ACTION_ANIM_DEAD);
			}else{
				endBattle();
			}
		}
	}
	
	private Kingdom getBorderKingdom(Army army) {
		//Comparo si alguno de los territorios adyacentes pertenece al derrotado
		Kingdom defeatTarget = null;
		for(Kingdom domain : army.getPlayer().getKingdomList()){
			for(Kingdom border : army.getKingdom().getBorderList()){
				if(domain.getId() == border.getId() 
						&& getEnemyAtKingdom(army.getPlayer(), border) == null){
					defeatTarget = domain;
					break;
				}
			}
		}
		return defeatTarget;
	}

	private void updateCamera(){
		if(
				(getCurrentPlayer().getActionIA() == null && (state == STATE_ACTION || state == STATE_DISCARD))
				||
				state == STATE_DEBUG){
			if(
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(0) == TouchData.ACTION_MOVE
					&& UserInput.getInstance().getMultiTouchHandler().getTouchFrames(0) > 1){
				if(lastTouchX != UserInput.getInstance().getMultiTouchHandler().getTouchX(0)){
					cameraTargetX = cameraTargetX + lastTouchX - UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
				}
				if(lastTouchY != UserInput.getInstance().getMultiTouchHandler().getTouchY(0)){
					cameraTargetY = cameraTargetY + lastTouchY - UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
				}
			}
		}
		
		lastTouchX = UserInput.getInstance().getMultiTouchHandler().getTouchX(0);
		lastTouchY = UserInput.getInstance().getMultiTouchHandler().getTouchY(0);
		cameraTargetX = Math.max(cameraTargetX, worldConver.getLayoutX() / 2f);
		cameraTargetX = Math.min(cameraTargetX, worldConver.getWorldWidth() - worldConver.getLayoutX() / 2f);
		cameraTargetY = Math.max(cameraTargetY, worldConver.getLayoutY() / 2f);
		cameraTargetY = Math.min(cameraTargetY, worldConver.getWorldHeight() - worldConver.getLayoutY() / 2f);
		
		gameCamera.updateCamera(
				(int)cameraTargetX-worldConver.getLayoutX()/2, 
				(int)cameraTargetY-worldConver.getLayoutY()/2);
	}
	
	private void resolveMovement(){
		//getSelectedArmy().setX(getSelectedArmy().getKingdom().getX());
		//getSelectedArmy().setY(getSelectedArmy().getKingdom().getY());
		
		//getSelectedArmy().getKingdom().setTarget(-1);
		getSelectedArmy().changeState(Army.STATE_OFF);
		
		
		//Ia ataca libre
		
		/**
		Para ver la opcion de escapar:
		-No es una partida on line
		-Debe de haber un defensor
		-Territorio al que escapar
		-El defensor no debe de ser la IA
		*/
		boolean scapeOption =
				GameState.getInstance().getGameMode() != GameState.GAME_MODE_ONLINE
				&&
				getEnemyAtKingdom(getCurrentPlayer()) != null  //Hay un defensor
				&&
				getBorderKingdom(getEnemyAtKingdom(getCurrentPlayer())) != null //Hay espacio para el defensor
				&&
				getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() == null; //El defensor NO es IA
		
		boolean waitOption = 
				getCurrentPlayer().getActionIA() == null && 
				getEnemyAtKingdom(getCurrentPlayer()) == null &&
				getSelectedArmy().getKingdom() != getSelectedArmy().getLastKingdom();//Si hay movimiento
				
		boolean cancelOption = false;//getSelectedArmy().getPlayer().getId() == getCurrentPlayer().getId();
		
		
		
		//Si tengo un ejercito en la zona y no soy yo ese ejercito me uno
		List <Army> armyFiendList = new ArrayList<Army>();
		for(int i = 0; i < getCurrentPlayer().getArmyList().size(); i++){
			if(getCurrentPlayer().getArmyList().get(i).getKingdom().getId() == getSelectedArmy().getKingdom().getId()){
				armyFiendList.add(getCurrentPlayer().getArmyList().get(i));
			}
		}
		if(armyFiendList.size() > 1){
			int cost = join(armyFiendList.get(0), armyFiendList.get(1));
			//activeArmy = armyList.get(0);
			NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_GAME_ARMY_JOIN]);
			if(cost > 0){
				getCurrentPlayer().setGold(getCurrentPlayer().getGold()+cost);
				NotificationBox.getInstance().addMessage("+" + cost + " coins");
			}
			if(getCurrentPlayer().getActionIA() != null){
				changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
			}else{
				changeSubState(SUB_STATE_ACTION_WAIT);
			}
			
		}else{
			//Si hay un ejercito enemigo
			if(getEnemyAtKingdom(getCurrentPlayer()) != null){
				
				//Si hay un efercito de la IA y decide huir
				if(
						getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null //Es un ejercito de la IA
						&& getBorderKingdom(getEnemyAtKingdom(getCurrentPlayer())) != null //Tiene territorios adyancentes libres
						&& getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA().
						scape(getSelectedArmy(), getEnemyAtKingdom(getCurrentPlayer()))){//Ha elegido huir
					
					Army defeated = getEnemyAtKingdom(getCurrentPlayer());
					defeated.setDefeat(true);
					NotificationBox.getInstance().addMessage(getDefeatArmy().getPlayer().getName() + " scape!");
					endBattle();
				
				}else{
					//Para batallas, el tipo de box es el primer elemento del territorio
					if(
							getSelectedArmy().getPlayer().getActionIA() != null && 
							getEnemyAtKingdom(getCurrentPlayer()).getPlayer().getActionIA() != null){
						
						combat(
								getSelectedArmy().getKingdom(), 
								getSelectedArmy(),
								getEnemyAtKingdom(getCurrentPlayer()));
						
					}else{
						battleBox.start(
								getSelectedArmy().getKingdom(),
								getSelectedArmy(),
								getEnemyAtKingdom(getCurrentPlayer()),
								-1,
								waitOption, scapeOption, cancelOption,
								getCurrentPlayer().getActionIA() != null);
						changeSubState(SUB_STATE_ACTION_ANIM_ATACK);
						SndManager.getInstance().playMusic(Main.MUSIC_START_BATTLE, true);
					}
				}
			}else{
				//Si el territorio es mio
				if(getCurrentPlayer().hasKingom(getSelectedArmy().getKingdom())){
					//getSelectedArmy().getKingdom().setTarget(-1);
					getSelectedArmy().changeState(Army.STATE_OFF);
					if(getCurrentPlayer().getActionIA() != null){
						changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
					}else{
						changeSubState(SUB_STATE_ACTION_WAIT);
					}
				}else{
					//Si es la IA, solo se muestra la ventana de combate si se va a producir un combate
					if(getCurrentPlayer().getActionIA() != null){
						if(
							getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_ATACK
							||
							getSelectedArmy().getIaDecision().getDecision() == ActionIA.DECISION_MOVE_AND_ATACK
						){
							combat(
									getSelectedArmy().getKingdom(),
									getSelectedArmy(),
									null);
						}else{
							//getSelectedArmy().getKingdom().setTarget(-1);
							getSelectedArmy().changeState(Army.STATE_OFF);
							changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
						}
					}else{
						battleBox.start(
								getSelectedArmy().getKingdom(), 
								getSelectedArmy(),
								null,
								getPlayerByKingdom(getSelectedArmy().getKingdom()) != null?
								getPlayerByKingdom(getSelectedArmy().getKingdom()).getFlag():GfxManager.imgFlagBigList.size()-1,
								waitOption, scapeOption, cancelOption, 
								getCurrentPlayer().getActionIA() != null);
						changeSubState(SUB_STATE_ACTION_ANIM_ATACK);
					}
				}
			}
		}
	}
	
	private void resolveScape(){
		//getDefeatArmy().getKingdom().setTarget(-1);
		
		//Si hay un ejercito amigo, se unen
		//Si tengo un ejercito en la zona y no soy yo ese ejercito me uno
		List <Army> armyFiendList = new ArrayList<Army>();
		for(int i = 0; i < getDefeatArmy().getPlayer().getArmyList().size(); i++){
			if(getDefeatArmy().getPlayer().getArmyList().get(i).getKingdom().getId() == getDefeatArmy().getKingdom().getId()){
				armyFiendList.add(getDefeatArmy().getPlayer().getArmyList().get(i));
			}
		}
		if(armyFiendList.size() > 1){
			int cost = join(armyFiendList.get(0), armyFiendList.get(1));
			getDefeatArmy().getPlayer().setGold(getCurrentPlayer().getGold()+cost);

			NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_GAME_ARMY_JOIN]);
			if(cost > 0){
				NotificationBox.getInstance().addMessage("+" + cost + " coins");
			}
		}
		
		try{
			if(getDefeatArmy().getPlayer() != null && getDefeatArmy().getPlayer().getId() == getCurrentPlayer().getId()){
				getDefeatArmy().changeState(Army.STATE_OFF);
			}else{
				getDefeatArmy().changeState(Army.STATE_ON);
			}
		}catch(Exception e){
			Log.i("Debug", getDefeatArmy().toString());
			Log.i("Debug", getDefeatArmy().getPlayer().toString());
			Log.i("Debug", getCurrentPlayer().toString());
			changeState(STATE_DEBUG);
		}
			
		if(getCurrentPlayer().getActionIA() != null){
			changeSubState(SUB_STATE_ACTION_IA_WAIT_START);
		}else{
			changeSubState(SUB_STATE_ACTION_WAIT);
		}
	}
	
	private Player getCurrentPlayer() {
		return gameScene.getPlayerList().get(gameScene.getPlayerIndex());
	}
	
	private void checkClearMist(Player player, Mist m){
		int kW = (int)(GfxManager.imgMist.getWidth());
		int kH = (int)(GfxManager.imgMist.getHeight());
		
		for(Kingdom k : player.getKingdomList()){
			
			kW = (int)(GfxManager.imgMist.getWidth());
			kH = (int)(GfxManager.imgMist.getHeight());
			if(GameUtils.getInstance().checkColision(
				m.x, m.y, m.w, m.h, 
				k.getAbsoluteX(), k.getAbsoluteY(), kW, kH)){
				m.clear = true;
			}
			if(!m.clear){
				for(Terrain t : k.getTerrainList()){
					if(GameUtils.getInstance().checkColision(
						m.x, m.y, m.w, m.h, 
						t.getAbsoluteX(), t.getAbsoluteY(), kW, kH)){
						m.clear = true;
					}
				}
			}
			
			//Vecinos
			kW = (int)(GfxManager.imgMist.getWidth()*0.65);
			kH = (int)(GfxManager.imgMist.getHeight()*0.65);
			if(!m.clear){
				for(Kingdom k2 : k.getBorderList()){
					if(GameUtils.getInstance().checkColision(
							m.x, m.y, m.w, m.h,  
							k2.getAbsoluteX(), k2.getAbsoluteY(), kW, kH)){
						m.clear = true;
					}
					if(!m.clear){
						for(Terrain t : k2.getTerrainList()){
							if(GameUtils.getInstance().checkColision(
								m.x, m.y, m.w, m.h, 
								t.getAbsoluteX(), t.getAbsoluteY(), kW, kH)){
								m.clear = true;
							}
						}
					}
				}
			}
		}
		 
		//Army
		if(!m.clear){
			 for(Army a : player.getArmyList()){
				 
				kW = (int)(GfxManager.imgMist.getWidth());
				kH = (int)(GfxManager.imgMist.getHeight());
				if(GameUtils.getInstance().checkColision(
					m.x, m.y, m.w, m.h, 
					a.getKingdom().getAbsoluteX(), a.getKingdom().getAbsoluteY(), kW, kH)){
					m.clear = true;
				}
				if(!m.clear){
					for(Terrain t : a.getKingdom().getTerrainList()){
						if(GameUtils.getInstance().checkColision(
							m.x, m.y, m.w, m.h, 
							t.getAbsoluteX(), t.getAbsoluteY(), kW, kH)){
							m.clear = true;
						}
					}
				}
				
				//Vecinos
				kW = (int)(GfxManager.imgMist.getWidth()*0.65);
				kH = (int)(GfxManager.imgMist.getHeight()*0.65);
				if(!m.clear){
					for(Kingdom k2 : a.getKingdom().getBorderList()){
						if(GameUtils.getInstance().checkColision(
								m.x, m.y, m.w, m.h, 
								k2.getAbsoluteX(), k2.getAbsoluteY(), kW, kH)){
							m.clear = true;
						}
						if(!m.clear){
							for(Terrain t : k2.getTerrainList()){
								if(GameUtils.getInstance().checkColision(
										m.x, m.y, m.w, m.h, 
										t.getAbsoluteX(), t.getAbsoluteY(), kW, kH)){
									m.clear = true;
								}
							}
						}
					}
				}
			}
		}
	}
	
	private Army getNextArmy(){
		Army army = null;





		if(getCurrentPlayer().getArmyList().size() > 0){

		    //Si solo tento un ejercito y esta activo, devuelvo ese
			if(getCurrentPlayer().getArmyList().size() == 1 && getCurrentPlayer().getArmyList().get(0).getState() == Army.STATE_ON){
				army = getCurrentPlayer().getArmyList().get(0);
			}
			//Si no tengo seleccionado ninguno o tengo seleccionado uno del enemigo, devuelvo el primero activo
			else if(getSelectedArmy() == null || (getSelectedArmy() != null && !isSelectedArmyFromCurrentPlayer(getSelectedArmy()))){

                for (int i = 0; i < getCurrentPlayer().getArmyList().size() && army == null; i++) {
                    {
                        if ( getCurrentPlayer().getArmyList().get(i).getState() == Army.STATE_ON) {
                            army = getCurrentPlayer().getArmyList().get(i);
                        }
                    }
                }
			}
			else{
			
				//Ordeno segun id
                /*
				for(int i = 0; i < getCurrentPlayer().getArmyList().size(); i++){
					for(int j = 1; j < (getCurrentPlayer().getArmyList().size()-i); j++){
						if(getCurrentPlayer().getArmyList().get(j-1).getId() > getCurrentPlayer().getArmyList().get(j).getId()){
							Army sav = getCurrentPlayer().getArmyList().get(j-1);
							getCurrentPlayer().getArmyList().set(j-1, getCurrentPlayer().getArmyList().get(j));
							getCurrentPlayer().getArmyList().set(j, sav);
							
						}
					}
				}
				*/

                int numActive = 0;
                for(Army a : getCurrentPlayer().getArmyList()){
                    if(a.getState() == Army.STATE_ON){
                        numActive++;
                    }
                }
				
				int currentIndex = 0;
				if(getSelectedArmy() != null){
					for(int i = 0; i < getCurrentPlayer().getArmyList().size() && currentIndex == 0; i++){
						if(getCurrentPlayer().getArmyList().get(i).isSelected()){
							currentIndex = i;
						}
					}
				}
                //Si solo queda uno activo, devuelvo ese:
				if(numActive == 1){
                    army = getCurrentPlayer().getArmyList().get(currentIndex);
                }else {

                    //Busco al siguiente
                    int nextIndex;

                    if (currentIndex == getCurrentPlayer().getArmyList().size() - 1) {
                        currentIndex = 0;
                        nextIndex = 0;
                    } else {
                        nextIndex = currentIndex + 1;
                    }


                    for (int i = 0; i < getCurrentPlayer().getArmyList().size() && army == null; i++) {
                        {
                            if (
                                    getCurrentPlayer().getArmyList().get(nextIndex).getState() == Army.STATE_ON &&
                                            getCurrentPlayer().getArmyList().get(nextIndex).getId() >= getCurrentPlayer().getArmyList().get(currentIndex).getId()) {
                                army = getCurrentPlayer().getArmyList().get(nextIndex);
                            }
                            nextIndex++;

                            if(nextIndex >= getCurrentPlayer().getArmyList().size()){
                                nextIndex=0;
                                currentIndex=0;
                            }
                        }
                    }
                }
			}
		}
		return army;
	}
	
	private class Mist{
		
		public Mist(int x, int y, int w, int h) {
			super();
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			this.clear = false;
		}
		int x;
		int y;
		int w;
		int h;
		boolean clear;
	}
		
}
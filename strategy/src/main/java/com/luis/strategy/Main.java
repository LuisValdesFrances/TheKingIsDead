package com.luis.strategy;

import java.util.Random;

import android.util.Log;

import com.luis.lgameengine.gameutils.GamePerformance;
import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.graphics.Screen;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.constants.Define;

/**
 * @author Luis Valdes Frances
 */
public class Main extends Screen implements Runnable {
	
	public static Main instance;
	public static Main getInstance(){
		return instance;
	}
	public static boolean isTouchDevice = true;
	
	public static final boolean IS_MOVE_SOFT_BANNER = false;

	public static int targetFPS;
	public static int iFrame;
	public static long lInitTime;
	private static int iAcumulativeTicks;
	private static long lAcumulativeTime;
	public static int iFramesXSecond;

	private static long deltaTime;
	public static int getDeltaMilis(){
		return (int)deltaTime;
	}
	public static float getDeltaSec(){
		float d = Math.min(((float)deltaTime / 1000f), 0.1f);
		if(IS_GAME_DEBUG){
			if(d > 0.1f){
				d = 0.1f;
			}
		}
		return d;
	}
	public static long lastTime;

	private static long minDurationFrame;
	private static boolean isGameRun;
    public void finishGame(){
        isGameRun = false;
    }

	public static int state;
	public static int lastState;
	private boolean isPaused;
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public static final int COLOR_BLACK = 0x00000000;
	public static final int COLOR_GREEN    = 0xff0ee50e;
	public static final int COLOR_RED      = 0xffA02020;
	public static final int COLOR_ORANGE   = 0xffff7800;
	public static final int COLOR_YELLOW   = 0xfffcff00;
	public static final int COLOR_PURPLE   = 0xffcc00ff;
	public static final int COLOR_PURPLE_GALAXY = 0xff2f0947;
    public static final int COLOR_BLUE     = 0xff202080;
	public static final int COLOR_WHITE    = 0xffffffff;
	
	public static final int COLOR_LILA_BG = 0xffe48bfe;
	public static final int COLOR_BLUE_BG = 0xff88c7fc;
	public static final int COLOR_GREEN_BG = 0xff8bfc88;
	public static final int COLOR_YELOW_BG = 0xfffcf659;

	public static boolean IS_FPS = false;
	public static boolean IS_DEBUG = false;
	public static boolean IS_TOUCH_INPUT_DEBUG = false;
	public static boolean IS_KEY_INPUT_DEBUG = false;
    public static boolean IS_GAME_DEBUG = false;

	public static final int INDEX_DATA_LANGUAGE = 0;
	public static final int INDEX_DATA_RECORD = 1;
	
	
	//Sound
	public static final byte MUSIC_INTRO = 0;
    public static final byte MUSIC_MAIN = 1;
	public static final byte MUSIC_MAP = 2;
	public static final byte MUSIC_START_BATTLE = 3;
    public static final byte MUSIC_SIR_ROBIN = 4;
	
	//FX
	public static final byte FX_HIT = 0;
	public static final byte FX_NEXT = 1;
	public static final byte FX_BACK = 2;
	public static final byte FX_SELECT = 3;
	public static final byte FX_SWORD = 4;
	public static final byte FX_SWORD_BLOOD = 5;
	public static final byte FX_SWORD_STRONG = 6;
	public static final byte FX_FAIL = 7;
	public static final byte FX_FANFARRIA_START = 8;
	public static final byte FX_FANFARRIA_END = 9;
	public static final byte FX_BATTLE = 10;
	public static final byte FX_SELECT_ARMY = 11;
	public static final byte FX_MARCH = 12;
	public static final byte FX_COINS = 13;
	public static final byte FX_START_GAME = 14;
	public static final byte FX_VICTORY = 15;
	public static final byte FX_DEFEAT = 16;
	public static final byte FX_DEAD = 17;
	 
	private static final int MUSIC_LIST [] = {
			R.raw.intro,
			R.raw.main,
			R.raw.map,
			R.raw.battle_start,
			R.raw.sir_robin
	 };
	
	private static final int FX_FILE [] = {
			R.raw.fx_hit,
			R.raw.fx_next,
			R.raw.fx_back,
			R.raw.fx_select,
			R.raw.fx_sword,
			R.raw.fx_sword_blood,
			R.raw.fx_sword_strong,
			R.raw.fx_fail,
			R.raw.fx_fanfarria_start,
			R.raw.fx_fanfarria_end,
			R.raw.fx_battle,
			R.raw.fx_select_army,
			R.raw.fx_march,
			R.raw.fx_coins,
			R.raw.fx_start_game,
			R.raw.fx_victory,
			R.raw.fx_defeat,
			R.raw.fx_dead,
	    
	};
	 
	private MainActivity activity;
	public MainActivity getActivity() {
		return activity;
	}
	
	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}

	private boolean notification;
	public boolean isNotification(){
		return  notification;
	}

	public Main(MainActivity activity, Settings settings, boolean notification) {
		super(activity, settings.getScreenWidth(), settings.getScreenHeight());
		this.activity = activity;
		this.notification = notification;
		Log.i("Debug", "Screen size " + settings.getScreenWidth() + "x" + settings.getScreenHeight());
        Log.i("Debug", "Real size " + Settings.getInstance().getRealWidth() + "x" + Settings.getInstance().getRealHeight());
		instance = this;

		Define.init(settings.getScreenWidth(), settings.getScreenHeight());

		UserInput.getInstance().init(multiTouchHandler, keyboardHandler);
		SndManager.getInstance().inicialize(activity, MUSIC_LIST, FX_FILE);
		isGameRun = true;
	}

	private void initGame() {
		Log.i("Debug", "Game initialized");
		targetFPS = GamePerformance.getInstance().getOptimalFrames();//30;
		minDurationFrame = 1000 / targetFPS;
		changeState(Define.ST_MENU_START, true);
	}

	@Override
	public void run() {

		initGame();

		while (isGameRun) {

			deltaTime = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();

			if(orderToChangeState){
				updateChangeState();
			}else{
				switch (state) {
					case Define.ST_MENU_START:
					case Define.ST_MENU_LOGO:
					case Define.ST_MENU_ASK_LANGUAGE:
					case Define.ST_MENU_ASK_SOUND:
					case Define.ST_MENU_MAIN:
					case Define.ST_MENU_OPTIONS:
					case Define.ST_MENU_INFO:
					case Define.ST_MENU_EXIT:
					case Define.ST_MENU_HELP:
					case Define.ST_MENU_ABOUT:
					case Define.ST_MENU_SELECT_GAME:
					case Define.ST_MENU_SELECT_MAP:
					case Define.ST_MENU_CONFIG_MAP:
					case Define.ST_MENU_CAMPAING:

					case Define.ST_MENU_ON_LINE_START:
					case Define.ST_MENU_ON_LINE_CREATE_USER:
					case Define.ST_MENU_ON_LINE_LOGIN:
					case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
					case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
					case Define.ST_MENU_ON_LINE_CREATE_SCENE:

					case Define.ST_TEST:
					if (!isLoading) {
						ModeMenu.update();
					}
					break;

				case Define.ST_GAME_INIT_PASS_AND_PLAY:
				case Define.ST_GAME_INIT_ON_LINE:
				case Define.ST_GAME_NOTIFICATION:
				case Define.ST_GAME_RUN:
				case Define.ST_GAME_PAUSE:
				case Define.ST_GAME_OPTIONS:
				case Define.ST_GAME_CONFIRMATION_QUIT:
					if (!isLoading) {
						ModeGame.update(state);
					}
					break;
				}
			}
			repaint();

			multiTouchHandler.update();
			keyboardHandler.update();

			while (System.currentTimeMillis() - lInitTime < minDurationFrame)
				Thread.yield();

			// New loop:
			lAcumulativeTime += (System.currentTimeMillis() - lInitTime);
			if (lAcumulativeTime < 1000) {
				iAcumulativeTicks++;
			} else {
				iFramesXSecond = iAcumulativeTicks;
				lAcumulativeTime = 0;
				iAcumulativeTicks = 0;
			}

			lInitTime = System.currentTimeMillis();
			iFrame++;

			/*
			 * if(!vHolder.getSurface().isValid()) continue; paint(vGraphics);
			 * Canvas canvas = vHolder.lockCanvas();
			 * canvas.getClipBounds(vDstRect);//Obtiene la totalidad de la
			 * pantalla. canvas.drawBitmap(vGraphics.mFrameBuffer, null,
			 * vDstRect, null); vHolder.unlockCanvasAndPost(canvas);
			 */

		}
		stop();
	}

	@Override
	protected void paint(Graphics _g) {
		if (!isClock) {
			switch (state) {
				 case Define.ST_MENU_START:
				 case Define.ST_MENU_LOGO:
		         case Define.ST_MENU_ASK_LANGUAGE:
		         case Define.ST_MENU_ASK_SOUND:
		         case Define.ST_MENU_MAIN:
		         case Define.ST_MENU_OPTIONS:
		         case Define.ST_MENU_INFO:
		         case Define.ST_MENU_EXIT:
		         case Define.ST_MENU_HELP:
		         case Define.ST_MENU_ABOUT:
		         case Define.ST_MENU_SELECT_GAME:
		         case Define.ST_MENU_SELECT_MAP:
		         case Define.ST_MENU_CONFIG_MAP:
		         case Define.ST_MENU_CAMPAING:
		        	 
		         case Define.ST_MENU_ON_LINE_START:
				 case Define.ST_MENU_ON_LINE_CREATE_USER:
				 case Define.ST_MENU_ON_LINE_LOGIN:
				 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
				 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
				 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
		        	 
		         case Define.ST_TEST:
					ModeMenu.draw(_g);
					break;
		         case Define.ST_GAME_INIT_PASS_AND_PLAY:
		         case Define.ST_GAME_INIT_ON_LINE:
		         case Define.ST_GAME_NOTIFICATION:
		         case Define.ST_GAME_RUN:
		         case Define.ST_GAME_PAUSE:
		         case Define.ST_GAME_OPTIONS:
		         case Define.ST_GAME_CONFIRMATION_QUIT:
		        	 ModeGame.draw(_g, state);
					break;
			}
			
			if (Main.IS_FPS) {
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolutionSet()]);
				_g.drawText("" + iFramesXSecond + "/" + targetFPS,  0, _g.getTextHeight(), COLOR_RED);
			}
			else if (Main.IS_DEBUG) {
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolutionSet()]);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 4);
				_g.setAlpha(255);
				_g.drawText("LGameEngine v.: " + Settings.LGAME_ENGINE_VERSION, 0, _g.getTextHeight(), COLOR_WHITE);
				_g.drawText("FPS: " + iFramesXSecond + "/" + targetFPS, 0, _g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("DT Real/Trans: " + ((float)deltaTime / 1000f) + "/" + getDeltaSec(), Define.SIZEX4, _g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("SizeX: " + Define.SIZEX, 0, _g.getTextHeight() * 3,COLOR_WHITE);
				_g.drawText("SizeY: " + Define.SIZEY, Define.SIZEX2, _g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("RealW: " + Settings.getInstance().getRealWidth(), 0, _g.getTextHeight() * 4, COLOR_WHITE);
				_g.drawText("RealH: " + Settings.getInstance().getRealHeight(), Define.SIZEX2, _g.getTextHeight() * 4, COLOR_WHITE);
				
				_g.setColor(Main.COLOR_GREEN);
				_g.fillRect(0, 0, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
				_g.fillRect(0, Define.SIZEY-Define.SCR_MIDLE/64, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
				_g.fillRect(Define.SIZEX-Define.SCR_MIDLE/64, 0, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
				_g.fillRect(Define.SIZEX-Define.SCR_MIDLE/64, Define.SIZEY-Define.SCR_MIDLE/64, Define.SCR_MIDLE/64, Define.SCR_MIDLE/64);
			
			}else if (Main.IS_TOUCH_INPUT_DEBUG){
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolutionSet()]);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 7);
				_g.setAlpha(255);
				_g.drawText("TouchAction: " + 
				multiTouchHandler.getTouchAction(0), 0, _g.getTextHeight(),COLOR_WHITE);
				_g.drawText("TouchFrame: " + 
				multiTouchHandler.getTouchFrames(0), Define.SIZEX2-Define.SIZEX4, _g.getTextHeight(), COLOR_WHITE);
				/*
				_g.drawText("Buffer size: " + 
						UserInput.getInstance().getMultiTouchHandler().getBufferSize(), Define.SIZEX2+Define.SIZEX4,_g.getTextHeight() * 5, 
						UserInput.getInstance().getMultiTouchHandler().getBufferSize() <= MultiTouchHandler.BUFFER_SIZE ? COLOR_WHITE : COLOR_RED);
				*/
				_g.drawText("Orin_X: " + 
				multiTouchHandler.getTouchOriginX(0), 0, _g.getTextHeight()*2,COLOR_WHITE);
				_g.drawText("Orin_Y: " + 
				multiTouchHandler.getTouchOriginY(0), Define.SIZEX2-Define.SIZEX4,_g.getTextHeight()*2, COLOR_WHITE);
				_g.drawText("Current_X: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchX(0), 0, _g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("Current_Y: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchY(0), Define.SIZEX2-Define.SIZEX4,_g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("Dist_X: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchDistanceX(0), 0, _g.getTextHeight() * 4, COLOR_WHITE);
				_g.drawText("Dist_Y: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchDistanceY(0), Define.SIZEX2-Define.SIZEX4,_g.getTextHeight() * 4, COLOR_WHITE);
				
				_g.drawText("Pointer 2: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(1), 0,_g.getTextHeight() * 6, COLOR_WHITE);
				_g.drawText("Pointer 3: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(2), Define.SIZEX2-Define.SIZEX4, _g.getTextHeight() * 6, COLOR_WHITE);
				_g.drawText("Pointer 4: " +
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(3), 0,_g.getTextHeight() * 7, COLOR_WHITE);
				_g.drawText("Pointer 5: " + 
					UserInput.getInstance().getMultiTouchHandler().getTouchAction(4), Define.SIZEX2-Define.SIZEX4, _g.getTextHeight() * 7, COLOR_WHITE);

			}else if (Main.IS_KEY_INPUT_DEBUG){
				_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
				_g.setTextSize(Font.SYSTEM_SIZE[Settings.getInstance().getNativeResolutionSet()]);
				_g.setAlpha(160);
				_g.setColor(0x88000000);
				_g.fillRect(0, 0, Define.SIZEX, _g.getTextHeight() * 3);
				_g.setAlpha(255);
				_g.drawText("Key UP: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_UP).getAction()), 0, _g.getTextHeight(),COLOR_WHITE);
				_g.drawText("Key DOWN: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_DOWN).getAction()), Define.SIZEX2,_g.getTextHeight(), COLOR_WHITE);
				_g.drawText("Key LEFT: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_LEFT).getAction()), 0, _g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("Key RIGHT: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_RIGHT).getAction()), Define.SIZEX2,_g.getTextHeight() * 2, COLOR_WHITE);
				_g.drawText("Key A: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_SHIELD_A).getAction()), 0, _g.getTextHeight() * 3, COLOR_WHITE);
				_g.drawText("Key B: " + 
					(UserInput.getInstance().getKeyboardHandler().getPressedKeys(UserInput.KEYCODE_SHIELD_B).getAction()), Define.SIZEX2,_g.getTextHeight() * 3, COLOR_WHITE);
			}if(IS_GAME_DEBUG){
				//Margenes
				if(isIntervalTwo()) {
					_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
					_g.setColor(Main.COLOR_GREEN);
					_g.fillRect(0, 0, Define.SCR_MIDLE/32, Define.SCR_MIDLE/32);
					_g.fillRect(0, Define.SIZEY-Define.SCR_MIDLE/32, Define.SCR_MIDLE/32, Define.SCR_MIDLE/32);
					_g.fillRect(Define.SIZEX-Define.SCR_MIDLE/32, 0, Define.SCR_MIDLE/32, Define.SCR_MIDLE/32);
					_g.fillRect(Define.SIZEX-Define.SCR_MIDLE/32, Define.SIZEY-Define.SCR_MIDLE/32, Define.SCR_MIDLE/32, Define.SCR_MIDLE/32);
				}
			}
			_g.setAlpha(255);
		} else {
			drawClock(_g);
		}
	}
	
	//Resources:
    //Obtiene un randon entre el primer parametro(Numero menor) y el segundo parametro(numero mayor).
    //Ambos incluidos.
    public static int getRandom(int _i0, int _i1) {
        Random random = new Random();
        return _i0 + Math.abs(random.nextInt() % (1 + _i1 - _i0));
    }
    
    public static int getRandom(int _iNumber) {
        Random random = new Random();
        if (_iNumber < 0) {
            return (random.nextInt() % -_iNumber);
        }
        try {
            return Math.abs(random.nextInt()) % _iNumber;
        } catch (Exception e) {
            e.printStackTrace();
           return 0;
        }
    }

    public static int getRandom(long seed, int _i0, int _i1) {
        Random random = new Random(seed);
        return _i0 + Math.abs(random.nextInt() % (1 + _i1 - _i0));
    }


    
    public static boolean isModule(int number) {
        if ((number % 2) == 1) {
            return false;//impar
        } else {
            return true;//par
        }
    }
    
    public static boolean isIntervalTwo() {
        if (
        	iFrame % (50 * GamePerformance.getInstance().getFrameMult(targetFPS)) < 5 || 
        	(
        	iFrame % (50 * GamePerformance.getInstance().getFrameMult(targetFPS)) > 10
            && 
            iFrame % (50 * GamePerformance.getInstance().getFrameMult(targetFPS)) < 15)
           ) {
            return false;
        } else {
            return true;
        }
    }
    
    public static boolean isDispareCount(float deltaTime, float currentCount, float totalCount){
        return (currentCount%totalCount < totalCount) && ((currentCount + deltaTime)%totalCount < currentCount%totalCount);
        
    }

	public void pause() {
		Log.i("INFO", "Llamada a pause()");
		isPaused = true;
		
		if(state == Define.ST_GAME_RUN){
			changeState(Define.ST_GAME_PAUSE, false);
		}
		
		SndManager.getInstance().pauseMusic();
		SndManager.getInstance().pauseFX();
	}

	/*
	public void saveAndSend(){
		if(state >= Define.ST_GAME_RUN && GameState.getInstance().getGameMode() == GameState.GAME_MODE_ONLINE){
			ModeGame.sendSceneToServerAsin(Define.ST_MENU_MAIN);
			changeState(Define.ST_MENU_MAIN, true);
		}
	}
	*/

	public void unPause() {
		//MyCanvas.isPause = false;
		Log.i("INFO", "Llamada a unPause()");
		isPaused = false;
		SndManager.getInstance().unpauseMusic();
	}

	public void stop() {
		SndManager.getInstance().stopMusic();
		SndManager.getInstance().flushSndManager();
	}

	private static boolean isLoading;
	private static int tripleBufferWait;
	private static boolean orderToChangeState;
	private static int newState;
	private static boolean isLoadGraphics;
	
	public static void changeState(int _iNewState, boolean _isLoadGraphics) {
		if(!orderToChangeState){
			tripleBufferWait = 0;
			orderToChangeState = true;
			newState = _iNewState;
			isLoadGraphics = _isLoadGraphics;
		}
	}
	private static void updateChangeState(){
		if(tripleBufferWait > 3 && orderToChangeState){
			executeChageState(newState, isLoadGraphics);
			tripleBufferWait = 0;
			orderToChangeState = false;
		}else{
			tripleBufferWait++;
		}
	}
	private static void executeChageState(int _iNewState, boolean _isLoadGraphics){
		
		isLoading = true;
		
		UserInput.getInstance().getMultiTouchHandler().resetTouch();
		UserInput.getInstance().getKeyboardHandler().resetKeys();

		lastState = state;
		state = _iNewState;
		///*
		switch(state){
			case Define.ST_GAME_INIT_PASS_AND_PLAY:
			case Define.ST_GAME_INIT_ON_LINE:
				GfxManager.deleteMenuGFX();
				break;
			case Define.ST_MENU_MAIN:
				if(lastState >= Define.ST_GAME_INIT_PASS_AND_PLAY ){
					GfxManager.deleteGameGFX();
				}
				break;
			case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
				if(lastState >= Define.ST_GAME_INIT_PASS_AND_PLAY ){
					GfxManager.deleteGameGFX();
				}
				break;
		}
		//*/
		if(_isLoadGraphics){
			instance.startClock(TYPE_CLOCK);
			GfxManager.loadGFX(_iNewState);
		}

		Log.i("INFO", "Estado cambiado a: " + _iNewState);
		
		
		if (_iNewState < Define.ST_GAME_INIT_PASS_AND_PLAY)
			ModeMenu.init(state);
		else
			ModeGame.init(state);
		
		instance.stopClock();
		isLoading = false;
	}
	

	/*
	 * @Override public boolean onTouch(View _v, MotionEvent _event) { // switch
	 * (_event.getAction()) { // case MotionEvent.ACTION_DOWN: // case
	 * MotionEvent.ACTION_MOVE: // iTouchX = (int)(_event.getX() *
	 * Settings.getScaleX()); // iTouchY = (int)(_event.getX() *
	 * Settings.getScaleY()); // break; // case MotionEvent.ACTION_UP: //
	 * iTouchX = 0; // iTouchY = 0; // break; // } iTouchX =
	 * (int)(_event.getX()); iTouchY = (int)(_event.getX()); return true; }
	 */
	
	
	// Show clock variables:
    private static final int SPEED_TIME_ANIMATION = 200;
	private static final int FRAMES = 4;
	
	public static final int TYPE_CLOCK = 0;
	public static final int TYPE_EARTH = 1;
	
	private long lClCurrentTime;
	private long lClLastCurrentTime;
	public int iFrameClock;
	public boolean isClock = false;
	public static Image imgClock;
	public static Image imgEarth;
	private Thread tClockThread;
	private int clockType;

	public void startClock(int type) {

		System.out.println("Start clock run");
		this.isClock = true;
		this.iFrameClock = 0;
		this.lClCurrentTime = 0;
		this.clockType = type;
		tClockThread = new Thread() {

			public void run() {
				while (isClock) {
					lClLastCurrentTime = System.currentTimeMillis();
					repaint();
					updateClock();
				}
			}
		};
		tClockThread.start();
	}

	private void updateClock() {
		long time = System.currentTimeMillis();
		lClCurrentTime += time - lClLastCurrentTime;
		lClLastCurrentTime = time;

		if (lClCurrentTime > SPEED_TIME_ANIMATION) {
			lClCurrentTime = 0;
			iFrameClock = iFrameClock + 1 == FRAMES ?0:iFrameClock + 1;
			repaint();
		}
	}

	public void stopClock() {
		System.out.println("Stop clock run");
		if (isClock) {
			isClock = false;
			tClockThread = null;
		}
	}

	private void drawClock(Graphics _g) {

		if (isClock) {
			if (clockType == TYPE_CLOCK && imgClock == null){
				try{
					imgClock = Image.createImage("/clock.png");
					}catch(Exception e){
						Log.e("error", "No se encuentra la imagen de carga");
					}
			}else if (clockType == TYPE_EARTH && imgEarth == null){
				try{
					imgEarth = Image.createImage("/earth.png");
					}catch(Exception e){
						Log.e("error", "No se encuentra la imagen de carga");
					}
			}
			
			Image img = clockType == TYPE_CLOCK? imgClock : imgEarth;
			
			if(img != null){
			_g.setColor(COLOR_BLACK);
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.fillRect(0, Define.SIZEY2 - img.getHeight(), Define.SIZEX, img.getHeight());
			_g.setClip(
					Define.SIZEX2 - ((img.getWidth() / FRAMES) >> 1),
					Define.SIZEY2 - img.getHeight(),
					img.getWidth() / FRAMES,
					img.getHeight());
			_g.drawImage(
					img,
					Define.SIZEX2 - ((img.getWidth() / FRAMES) >> 1)
							- ((img.getWidth() / FRAMES) * iFrameClock),
					Define.SIZEY2 - img.getHeight(), 0);

			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		}
		}else{
			Log.e("error", "No se existe la imagen del reloj de carga");
		}
	}
}

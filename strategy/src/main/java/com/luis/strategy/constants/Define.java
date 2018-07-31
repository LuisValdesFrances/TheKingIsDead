package com.luis.strategy.constants;

import com.luis.lgameengine.gameutils.Settings;

/**
*
* @author Luis Valdes Frances
*/
public class Define {
	
	public static void init(int screenW, int screenH){

		SIZEX = screenW;
		SIZEY = screenH;
		SIZEX2 = SIZEX >> 1;
		SIZEY2 = SIZEY >> 1;
		SIZEX4 = SIZEX >> 2;
		SIZEY4 = SIZEY >> 2;
		SIZEX8 = SIZEX >> 3;
		SIZEY8 = SIZEY >> 3;
		SIZEX12 = SIZEX / 12;
		SIZEY12 = SIZEY / 12;
		SIZEX16 = SIZEX >> 4;
		SIZEY16 = SIZEY >> 4;
		SIZEX24 = SIZEX / 24;
		SIZEY24 = SIZEY / 24;
		SIZEX32 = SIZEX >> 5;
		SIZEY32 = SIZEY >> 5;
		SIZEX64 = SIZEX >> 6;
		SIZEY64 = SIZEY >> 6;
        SCR_MIDLE = (SIZEX+SIZEY)/2;
	}

	public static int SIZEX;
	public static int SIZEY;
	public static int SIZEX2;
	public static int SIZEY2;
	public static int SIZEX4;
	public static int SIZEY4;
	public static int SIZEX8;
	public static int SIZEY8;
	public static int SIZEX12;
	public static int SIZEY12;
	public static int SIZEX16;
	public static int SIZEY16;
	public static int SIZEX24;
	public static int SIZEY24;
	public static int SIZEX32;
	public static int SIZEY32;
	public static int SIZEX64;
	public static int SIZEY64;
    public static int SCR_MIDLE;

	// Menu States:
	public static final int ST_MENU_START = 0;
	public static final int ST_MENU_LOGO = 1;
	public static final int ST_MENU_ASK_LANGUAGE = 2;
	public static final int ST_MENU_ASK_SOUND = 3;

	public static final int ST_MENU_MAIN = 4;
	public static final int ST_MENU_OPTIONS = 5;
	public static final int ST_MENU_INFO = 6;
	public static final int ST_MENU_EXIT = 7;
	public static final int ST_MENU_HELP = 8;
	public static final int ST_MENU_ABOUT = 9;
	public static final int ST_MENU_SELECT_GAME = 10;
	public static final int ST_MENU_SELECT_MAP = 11;
	public static final int ST_MENU_CONFIG_MAP = 12;
	public static final int ST_MENU_CAMPAING = 13;
	
	public static final int ST_MENU_ON_LINE_START = 14;
	public static final int ST_MENU_ON_LINE_CREATE_USER = 15;
	public static final int ST_MENU_ON_LINE_LOGIN = 16;
	public static final int ST_MENU_ON_LINE_LIST_ALL_GAME = 17;
	public static final int ST_MENU_ON_LINE_LIST_JOIN_GAME = 18;
	public static final int ST_MENU_ON_LINE_CREATE_SCENE = 19;
	
	public static final int ST_TEST = 50;

	// Game states:
	public static final int ST_GAME_INIT_PASS_AND_PLAY = 100;
	public static final int ST_GAME_INIT_ON_LINE = 101;
	public static final int ST_GAME_NOTIFICATION = 102;
	public static final int ST_GAME_RUN = 103;
	public static final int ST_GAME_PAUSE = 104;
	public static final int ST_GAME_OPTIONS = 105;
	public static final int ST_GAME_CONFIRMATION_QUIT = 106;
	
	// Nombre del fichero donde se guardaran y cargaran los datos:
	//language\nsound\enableAlerts\game3D
	//0\ntrue\ntrue\ntrue
	public static final String DATA_CONFIG = "strategyDataConfig";
	/*
	 * Primera linea: English, Spanish, Catalan
	 */
	public static final String DATA_USER = "strategyDataUser";
	public static final String DATA_PASS_AND_PLAY = "strategyDataPassAndPlay";
	public static final String DATA_CAMPAING = "strategyDataCampaing";
	public static final String DATA_NOTIFICATION = "strategyDataNotification";
	public static final int MAX_NAME_CHAR = 14;
	public static final int MIN_NAME_CHAR = 4;
	public static final int MIN_PASSWORD_CHAR = 8;
	  
	 
	

}

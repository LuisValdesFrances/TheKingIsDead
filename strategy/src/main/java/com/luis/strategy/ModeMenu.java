package com.luis.strategy;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.luis.lgameengine.gameutils.Settings;
import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.ListBox;
import com.luis.lgameengine.gui.MenuElement;
import com.luis.lgameengine.gui.NotificationBox;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GameState.PlayerConf;
import com.luis.strategy.connection.OnlineInputOutput;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.data.DataKingdom;
import com.luis.strategy.data.GameBuilder;
import com.luis.strategy.datapackage.scene.NotificationListData;
import com.luis.strategy.datapackage.scene.PreSceneData;
import com.luis.strategy.datapackage.scene.PreSceneListData;
import com.luis.strategy.datapackage.scene.SceneData;
import com.luis.strategy.datapackage.scene.SceneListData;
import com.luis.strategy.game.GameManager;
import com.luis.strategy.gui.ConfigMapBox;
import com.luis.strategy.gui.CreateUserBox;
import com.luis.strategy.gui.DialogBox;
import com.luis.strategy.gui.LoginBox;
import com.luis.strategy.gui.ConfigurationBox;
import com.luis.strategy.gui.RankingBox;
import com.luis.strategy.gui.SceneDataListBox;
import com.luis.strategy.gui.SimpleBox;
import com.luis.strategy.map.GameScene;

public class ModeMenu {

	private static int cita;
    private static int author;
	
	private static Button btnCancel;
	private static Button btnBack;
	private static Button btnNext;
	private static Button btnMultiPlayer;
	private static Button btnOnLine;
	private static Button btnPassAndPlay;
	private static Button btnCampaign;
	private static Button btnContinuePassAndPlay;
	
	private static Button btnNewAccount;
	private static Button btnLogin;
	private static Button btnSearchGame;
	private static Button btnCreateScene;
	private static Button btnRanking;
	private static Button btnInfo;
	private static Button btnConfiguration;
	
	private static Button btnAbout;
	private static Button btnHelp;

    private static Button btnEasterEgg;
	private static Button btnDebug;
	
	
	private static ListBox createSceneListBox;
	
	private static ListBox selectSceneListBox;
	private static ListBox joinPreSceneListBox;
    private static ListBox rankingSceneListBox;
	
	private static ConfigMapBox configMapBox;
	private static CreateUserBox createUserBox;
	private static LoginBox loginBox;
	private static SimpleBox gameVersionBox;
	private static ListBox notificationBox;
	private static DialogBox dialogBox;
    private static SimpleBox simpleBox;
    private static RankingBox rankingBox;
	public static ConfigurationBox configurationBox;
	
	private static boolean createUsserSucces;
	private static boolean loginUsserSucces;
	
	private static SceneData sceneData;
	
	private static PreSceneData preSceneDataToJoin;
	
	private static String onlineCreateMap;
	private static String onlineCreateHost;
	private static String onLineGameName;
	
	private static int numLetters;

	private static int debugCount;
    private static int easterEggCount;
	
	public static void init(int _iMenuState){
		Log.i("Info", "Init State: "+ _iMenuState);
		switch (_iMenuState) {
		
		case Define.ST_MENU_START:
			Font.init(GfxManager.vImgFontSmall, GfxManager.vImgFontMedium, GfxManager.vImgFontBig);
			
			//Miro si hay datos guardados
			String dataConfig = FileIO.getInstance().loadData(Define.DATA_CONFIG, Main.getInstance().getActivity());
			
			if(dataConfig == null){
				
				String language = Locale.getDefault().getDisplayLanguage();
				
				language = language.toLowerCase();
				//Log.i("Debug", language);
				int lang=0;
				if(language.equals("english")){
					lang=0;
				}else if(language.equals("español")){
					lang=1;
				}else if(language.equals("catala")){
					lang=2;
				}
				
				dataConfig = ""+lang+"\ntrue\ntrue\nfalse";//A�adir resto de configuraciones;
				FileIO.getInstance().saveData(dataConfig, Define.DATA_CONFIG, Main.getInstance().getActivity());
			}
			
			int language = Integer.parseInt(dataConfig.split("\n")[0]);
			if(language == 0){
				RscManager.loadLanguage(0);
			}
			else if(language == 1){
				RscManager.loadLanguage(1);
			}else{
				RscManager.loadLanguage(2);
			}
			
			//Sonido
			SndManager.getInstance().setSound(dataConfig.split("\n")[1].equals("true"));
			
			//Modo 3D
			GameManager.game3D = dataConfig.split("\n")[3].equals("true");
			
			MenuElement.bgAlpha = (int)(GameParams.BG_BLACK_ALPHA*0.5);
			
			alpha = 255;
        	startTime = System.currentTimeMillis();
        	cita = Main.getRandom(RscManager.TXT_CITA_1, RscManager.TXT_CITA_7);
			author = RscManager.TXT_CITA_7+cita;
			
			configurationBox = new ConfigurationBox(){
				@Override
				public void onFinish(){}
			};
			
			btnBack = new Button(GfxManager.imgButtonArrowBackRelease, GfxManager.imgButtonArrowBackFocus,
					Define.SIZEX32+GfxManager.imgButtonArrowBackRelease.getWidth()/2,
					Define.SIZEY32+GfxManager.imgButtonArrowBackRelease.getHeight()/2,
					null, -1){
				@Override
				public void onButtonPressDown(){}
				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_BACK, 0);
					reset();
					switch(Main.state){
					case Define.ST_MENU_SELECT_GAME:
					case Define.ST_MENU_CAMPAING:
					case Define.ST_MENU_ON_LINE_START:
					case Define.ST_MENU_INFO:
					case Define.ST_MENU_OPTIONS:
						Main.changeState(Define.ST_MENU_MAIN, false);
						break;
					case Define.ST_MENU_ABOUT:
						Main.changeState(Define.ST_MENU_INFO, false);
						break;
					case Define.ST_MENU_SELECT_MAP:
						createSceneListBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_CREATE_SCENE:
						createSceneListBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
						selectSceneListBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
						joinPreSceneListBox.cancel();
						break;
					case Define.ST_MENU_CONFIG_MAP:
						configMapBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_CREATE_USER:
						createUserBox.cancel();
						break;
					case Define.ST_MENU_ON_LINE_LOGIN:
						loginBox.cancel();
						break;
						case Define.ST_MENU_ON_LINE_RANKING:
                            rankingSceneListBox.cancel();
                            break;
					}
				};
			};
			
			btnNext = new Button(GfxManager.imgButtonArrowNextRelease, GfxManager.imgButtonArrowNextFocus,
					Define.SIZEX-Define.SIZEX32-GfxManager.imgButtonArrowBackRelease.getWidth()/2,
					Define.SIZEY-Define.SIZEY32-GfxManager.imgButtonArrowBackRelease.getHeight()/2,
					null, -1){
				
				@Override
				public void onButtonPressDown(){}
				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					switch(Main.state){
					case Define.ST_MENU_CONFIG_MAP:
						configMapBox.setIndexPressed(0);
						configMapBox.cancel();
						break;
					}
				};
			};
			
			btnInfo = new Button(GfxManager.imgButtonInfoRelease, GfxManager.imgButtonInfoFocus,
					Define.SIZEY64 + GfxManager.imgButtonInfoRelease.getWidth()/2,
					Define.SIZEY64 + GfxManager.imgButtonInfoRelease.getHeight()/2,
					null, -1){
				
				@Override
				public void onButtonPressDown(){}
				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					Main.changeState(Define.ST_MENU_INFO, false);
				};
			};
			
			btnConfiguration = new Button(
					GfxManager.imgButtonOptionsRelease, GfxManager.imgButtonOptionsFocus,
					Define.SIZEY64 + GfxManager.imgButtonInfoRelease.getWidth()/2,
					Define.SIZEY32 + (int)(GfxManager.imgButtonInfoRelease.getHeight()*1.5f),
					null, -1){
				
				@Override
				public void onButtonPressDown(){}
				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					configurationBox.start();
				};
			};
			
			NotificationBox.getInstance().init(
					Define.SIZEX, Define.SIZEY, 
					null, NotificationBox.DURATION_LONG);
			
			break;
        case Define.ST_MENU_LOGO:
        	startTime = System.currentTimeMillis();
    		statePresentation = ST_PRESENTATION_1;
			alpha = 255;
			logoAlpha = 255;
			break;
		case Define.ST_MENU_ASK_SOUND:
		case Define.ST_MENU_ASK_LANGUAGE:
			
			break;
		case Define.ST_MENU_MAIN:

			if(Main.lastState < Define.ST_MENU_MAIN){
				alpha = 255;
				startTime = System.currentTimeMillis();
				
				cloudFarBGX = Define.SIZEX2;
				cloudNearBGX = Define.SIZEX;
				cloudNear2BGX = Define.SIZEX+Define.SIZEX2;
				
				cloudFarBGY = Define.SIZEY2;
				cloudNearBGY = Define.SIZEY2+Define.SIZEY4;
				cloudNear2BGY = Define.SIZEY;
				
				btnCampaign = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5)-Define.SIZEY64,
						RscManager.allText[RscManager.TXT_CAMPAING], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						SndManager.getInstance().playFX(Main.FX_NEXT, 0);
						Main.changeState(Define.ST_MENU_CAMPAING, false);
						reset();
					}
				};
				btnMultiPlayer = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2)-Define.SIZEY64,
						RscManager.allText[RscManager.TXT_MULTI_PLAYER], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						SndManager.getInstance().playFX(Main.FX_NEXT, 0);
						reset();
						Main.changeState(Define.ST_MENU_SELECT_GAME, false);
					}
				};

                debugCount = 0;
                btnDebug = new Button(Define.SIZEX8, Define.SIZEY8, 0, Define.SIZEY-Define.SIZEY8){
                    @Override
                    public void onButtonPressDown(){}

                    @Override
                    public void onButtonPressUp(){
                        if(debugCount == 8){
                            debugCount = 0;
                            Main.IS_GAME_DEBUG = !Main.IS_GAME_DEBUG;
                            NotificationBox.getInstance().addMessage(Main.IS_GAME_DEBUG?"DEBUG ON":"DEBUG OFF");
                        }else{
                            debugCount++;
                        }
                        reset();
                    }
                };


                btnEasterEgg = new Button(
                        GfxManager.imgTitle.getWidth(),GfxManager.imgTitle.getHeight(),
                        Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64,
                        GfxManager.imgTitle.getHeight()/2 + Define.SIZEY32){
                    @Override
                    public void onButtonPressDown(){}

                    @Override
                    public void onButtonPressUp(){
                        if(easterEggCount == 8){
                            easterEggCount = 0;

                            SndManager.getInstance().stopMusic();
                            NotificationBox.getInstance().addMessage("SOUND ON");
                            String dataConfig = FileIO.getInstance().loadData(Define.DATA_CONFIG, Main.getInstance().getActivity());
                            int language = Integer.parseInt(dataConfig.split("\n")[0]);
                            boolean notifications = dataConfig.split("\n")[2].equals("true");
                            boolean game3D = dataConfig.split("\n")[3].equals("true");
                            String data = "" + language + "\n" + true + "\n" + notifications + "\n" + game3D;
                            FileIO.getInstance().saveData(dataConfig, Define.DATA_CONFIG, Main.getInstance().getActivity());


                            SndManager.getInstance().playMusic(Main.MUSIC_SIR_ROBIN, false);
                        }else{
                            easterEggCount++;
                        }
                        SndManager.getInstance().playFX(Main.FX_COINS, 0);
                        reset();
                    }
                };
				
				btnCampaign.setDisabled(true);


				//firebase (Temporal)
                //Chequeo si la version del juego es la ultima
                String result = OnlineInputOutput.getInstance().checkGameVersion(Main.getInstance().getActivity());

                if(result.equals("Succes")){
                    String data = FileIO.getInstance().loadData(Define.DATA_USER,
                            Settings.getInstance().getActiviy().getApplicationContext());

                    if (data != null && data.length() > 0) {
                        String[] d = data.split("\n");
                        final String userName = d[0];
                        final String firebaseIdDeviceToken = Main.getInstance().getActivity().getFirebaseDeviceToken();

                        Thread t = new Thread(){
                            @Override
                            public void run(){
                                OnlineInputOutput.getInstance().sendFirebaseIdDeviceToken(
                                        Main.getInstance().getActivity(), userName, firebaseIdDeviceToken
                                );
                            }
                        };
                        t.start();

                    }
                }
                else if(result.equals("Server error")){
                    NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_SERVER_ERROR]);
                }

            }
			else if(Main.lastState > Define.ST_GAME_INIT_PASS_AND_PLAY){
				MenuElement.bgAlpha = (int)(GameParams.BG_BLACK_ALPHA*0.5);
				SndManager.getInstance().playMusic(Main.MUSIC_MAIN, true);
				Main.getInstance().getActivity().requestInterstitial();
			}
			
			break;
		case Define. ST_MENU_OPTIONS:
			break;
		case Define.ST_MENU_INFO:
			
			btnAbout = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-Define.SIZEY64 -(int)GfxManager.imgButtonMenuBigRelease.getHeight()/2,
					RscManager.allText[RscManager.TXT_ABOUT], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					Main.changeState(Define.ST_MENU_ABOUT, false);
				}
			};
			
			btnHelp = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-Define.SIZEY64 -(int)(GfxManager.imgButtonMenuBigRelease.getHeight()*1.5),
					RscManager.allText[RscManager.TXT_HELP], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					
					String url = "https://www.youtube.com/watch?v=CpSBnGPn0r0";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					Main.getInstance().getActivity().startActivity(i);
				}
			};
            break;
		case Define. ST_MENU_EXIT:
		case Define. ST_MENU_HELP:
		case Define. ST_MENU_ABOUT:
			numLetters = 0;
			break;
		
		case Define. ST_MENU_CAMPAING:
			break;
			
		case Define. ST_MENU_SELECT_GAME://porque carga
			
			//Miro si hay partida guardada
			sceneData = null;
			try{
				FileInputStream fis = Main.getInstance().getActivity().openFileInput(Define.DATA_PASS_AND_PLAY);
				ObjectInputStream is = new ObjectInputStream(fis);
				sceneData = (SceneData) is.readObject();
				is.close();
				fis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			btnOnLine = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-Define.SIZEY64
					-(int)(sceneData != null ? GfxManager.imgButtonMenuBigRelease.getHeight()*2.5 : GfxManager.imgButtonMenuBigRelease.getHeight()*1.5),
					RscManager.allText[RscManager.TXT_ON_LINE], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					reset();
					
					//Chequeo si la version del juego es la ultima
					String result = OnlineInputOutput.getInstance().checkGameVersion(Main.getInstance().getActivity());
					
					if(result.equals("Succes")){
						String data = FileIO.getInstance().loadData(Define.DATA_USER, 
								Settings.getInstance().getActiviy().getApplicationContext());
	
						if (data != null && data.length() > 0) {
							String[] d = data.split("\n");
							GameState.getInstance().setName(d[0]);
							GameState.getInstance().setPassword(d[1]);
							NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_CONNECTED_BY] + " " + d[0]);
							Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
						} else{
							Main.changeState(Define.ST_MENU_ON_LINE_START, false);
						}
					}
					else if(result.equals("Server error")){
						NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_SERVER_ERROR]);
					} 
					else if(result.equals(OnlineInputOutput.MSG_NO_CONNECTION)){
						NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_NO_CONNECTION]);
					}
					else if(result.equals("Version error")){
						gameVersionBox = new SimpleBox(GfxManager.imgSmallBox, true, false){
							@Override
							public void onFinish(){
								String url = "https://play.google.com/store/apps/details?id=com.luis.strategy";
								Intent i = new Intent(Intent.ACTION_VIEW);
								i.setData(Uri.parse(url));
								Main.getInstance().getActivity().startActivity(i);
							}
						};
						gameVersionBox.start(null, RscManager.allText[RscManager.TXT_UPDATE_GAME]);
					}
				}
			};
			
			btnPassAndPlay = new Button(
					GfxManager.imgButtonMenuBigRelease, 
					GfxManager.imgButtonMenuBigFocus, 
					Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
					Define.SIZEY-Define.SIZEY64
					-(int)(sceneData != null ? GfxManager.imgButtonMenuBigRelease.getHeight()*1.5 : GfxManager.imgButtonMenuBigRelease.getHeight()/2),
					RscManager.allText[RscManager.TXT_PASS_AND_PLAY], Font.FONT_MEDIUM){
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp(){
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					Main.changeState(Define.ST_MENU_SELECT_MAP, false);
					reset();
				}
			};
			
			if(sceneData != null){
				btnContinuePassAndPlay = new Button(
						GfxManager.imgButtonMenuBigRelease, 
						GfxManager.imgButtonMenuBigFocus, 
						Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
						Define.SIZEY-Define.SIZEY64
						-(int)(GfxManager.imgButtonMenuBigRelease.getHeight()/2),
						RscManager.allText[RscManager.TXT_CONTINUE], Font.FONT_MEDIUM){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						SndManager.getInstance().playFX(Main.FX_NEXT, 0);
						
						GameState.getInstance().init(GameState.GAME_MODE_PLAY_AND_PASS, sceneData);
						Main.changeState(Define.ST_GAME_INIT_PASS_AND_PLAY, true);
						//Main.changeState(Define.ST_GAME_CONTINUE, true);
						reset();
					}
				};
			}
			break;
		case Define.ST_MENU_SELECT_MAP:
			createSceneListBox = new ListBox(
					Define.SIZEX, Define.SIZEY, 
					null, GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
					Define.SIZEX2, Define.SIZEY2, 
					RscManager.allText[RscManager.TXT_SELECT_GAME],
					DataKingdom.SCENARY_LIST,
					Font.FONT_BIG, Font.FONT_SMALL,
					-1, Main.FX_NEXT){
				
				@Override
				public void onFinish(){
					
					if(getIndexPressed() != -1){
						GameState.getInstance().init(
								GameState.GAME_MODE_PLAY_AND_PASS,
								getIndexPressed(), DataKingdom.INIT_MAP_DATA[getIndexPressed()].length);
						
						int i = 0;
						for(PlayerConf pc : GameState.getInstance().getPlayerConfList()){
							pc.name="Player " + (i+1);
							pc.flag=i;
							pc.IA = false;
							i++;
						}
						Main.changeState(Define.ST_MENU_CONFIG_MAP, false);
					}else{
						Main.changeState(Define.ST_MENU_SELECT_GAME, false);
					}
				}
			};
			createSceneListBox.start();
			break;
		case Define.ST_MENU_CONFIG_MAP:

            simpleBox = new SimpleBox(GfxManager.imgSmallBox, true, false) {
                @Override
                public void onFinish() {
                    Main.changeState(Define.ST_MENU_CONFIG_MAP, false);
                }
            };

			configMapBox = new ConfigMapBox(GameState.getInstance().getPlayerConfList()){
				@Override
				public void onFinish(){
					if(getIndexPressed() != -1){
                        //Chequeo de que se haya seleccionado almenos un jugador real
						boolean onlyIA = true;
						for(int i = 0; i < GameState.getInstance().getPlayerConfList().length && onlyIA; i++){
						    if(!GameState.getInstance().getPlayerConfList()[i].IA){
                                onlyIA = false;
                            }
                        }
                        if(onlyIA && !Main.IS_GAME_DEBUG){
                            simpleBox.start(null, RscManager.allText[RscManager.TXT_SELECT_ONE_HUMAN]);
						}else{
							//Borro los datos guardados en caso de haberlos
							GameState.getInstance().setSceneData(null);
							Main.changeState(Define.ST_GAME_INIT_PASS_AND_PLAY, true);
						}
					}else{
						Main.changeState(Define.ST_MENU_SELECT_MAP, false);
					}
				}
			};
			configMapBox.start();
			break;
			
			
			
		case Define.ST_MENU_ON_LINE_START:
			
			btnLogin = new Button(GfxManager.imgButtonMenuBigRelease,
					GfxManager.imgButtonMenuBigFocus,
					Define.SIZEX - (int) (GfxManager.imgButtonMenuBigRelease.getWidth() / 2) - Define.SIZEY64,
					Define.SIZEY - (int) (GfxManager.imgButtonMenuBigRelease.getHeight() * 1.5) - Define.SIZEY64,
					RscManager.allText[RscManager.TXT_LOGIN], Font.FONT_MEDIUM) {
				@Override
				public void onButtonPressDown(){}
				
				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					Main.changeState(Define.ST_MENU_ON_LINE_LOGIN, false);
					reset();
				}
			};
			
			btnNewAccount = new Button(GfxManager.imgButtonMenuBigRelease,
					GfxManager.imgButtonMenuBigFocus,
					Define.SIZEX - (int) (GfxManager.imgButtonMenuBigRelease.getWidth() / 2) - Define.SIZEY64,
					Define.SIZEY - (int) (GfxManager.imgButtonMenuBigRelease.getHeight() / 2) - Define.SIZEY64,
					RscManager.allText[RscManager.TXT_NEW_ACOUNT], Font.FONT_MEDIUM) {
				@Override
				public void onButtonPressDown(){}

				@Override
				public void onButtonPressUp() {
					SndManager.getInstance().playFX(Main.FX_NEXT, 0);
					Main.changeState(Define.ST_MENU_ON_LINE_CREATE_USER, false);
					reset();
				}
			};

			Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
			break;
			
		 case Define.ST_MENU_ON_LINE_CREATE_USER:
			 createUsserSucces = false;
			 createUserBox = new CreateUserBox(){
				 @Override
				 public void onSendForm() {
					 super.onSendForm();
					 
					  if(!getTextPassword().equals(getTextRepPassword())){
						 NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_PASS_NO_MATCH]);
					  }
					  else{
						 //Escrutura online
						 String msg;
						 Main.getInstance().startClock(Main.TYPE_EARTH);
						 String firebaseIdDeviceToken = Main.getInstance().getActivity().
								 getFirebaseDeviceToken()!=null?Main.getInstance().getActivity().getFirebaseDeviceToken():"";
						 String result = OnlineInputOutput.getInstance().sendUser(
								 Main.getInstance().getActivity(), OnlineInputOutput.URL_CREATE_USER,
								 getTextName(), getTextPassword(), firebaseIdDeviceToken);//check
						 Main.getInstance().stopClock();
						 
						 if(result.equals("Server error")){
							msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
						 }
						 else if(result.equals("Query error")){
							msg = RscManager.allText[RscManager.TXT_TRY_ANOTHER_NAME];
						 }
						 else if(result.equals(OnlineInputOutput.MSG_NO_CONNECTION)){
							msg = RscManager.allText[RscManager.TXT_NO_CONNECTION];
						 }
						 else if(result.equals("Succes")){
							msg = RscManager.allText[RscManager.TXT_ACCOUNT_CREATED];
							 
							GameState.getInstance().setName(getTextName());
							GameState.getInstance().setPassword(getTextPassword());
							
							//Guardo los datos en la memoria local
							String d = getTextName() + "\n" + getTextPassword();
							FileIO.getInstance().saveData(d, Define.DATA_USER, 
									Settings.getInstance().getActiviy().getApplicationContext());
							
							createUsserSucces = true;
							cancel();
						}else{
							msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
						}
						
						NotificationBox.getInstance().addMessage(msg);
					 }
				 }
				 
				 @Override
				 public void onFinish(){
					 if(createUsserSucces){
						 Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
						 
						 //Valido los datos guardados contra el servidor(De momento no es necesario porque si el nombre es incorrecto)
						 
					 }else{
						 Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					 }
				}
			 };
			 createUserBox.start();
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 loginUsserSucces = false;
			 loginBox = new LoginBox(){
				 @Override
				 public void onSendForm() {
					 super.onSendForm();
					 String msg = "";
					 Main.getInstance().startClock(Main.TYPE_EARTH);
					 String firebaseIdDeviceToken = Main.getInstance().getActivity().
							 getFirebaseDeviceToken()!=null?Main.getInstance().getActivity().getFirebaseDeviceToken():"";
					 String result = OnlineInputOutput.getInstance().sendUser(
							 Main.getInstance().getActivity(), OnlineInputOutput.URL_LOGIN_USER,
							 getTextName(), getTextPassword(), firebaseIdDeviceToken);//check
					 Main.getInstance().stopClock();
					 
					if(result.equals("Server error")){
						 msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
					 }
					 else if(result.equals("Query error")){
						 msg = RscManager.allText[RscManager.TXT_INCORRECT_USER_NAME];
					 }
					 else if(result.equals(OnlineInputOutput.MSG_NO_CONNECTION)){
						msg = RscManager.allText[RscManager.TXT_NO_CONNECTION];
					 }
					 else if(result.equals("Succes")){
						 msg = RscManager.allText[RscManager.TXT_CONNECTED_BY] + " " + getTextName();
						
						 GameState.getInstance().setName(getTextName());
						 GameState.getInstance().setPassword(getTextPassword());
						
						 //Guardo los datos en la memoria local
						 String d = getTextName() + "\n" + getTextPassword();
						 FileIO.getInstance().saveData(d, Define.DATA_USER, 
								Settings.getInstance().getActiviy().getApplicationContext());
						
						 loginUsserSucces = true;
						 cancel();
					}else{
						msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
					}
					NotificationBox.getInstance().addMessage(msg);
				 }
				 
				 @Override
				 public void onFinish(){
					 if(loginUsserSucces){
						 Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
					 }else{
						 Main.changeState(Define.ST_MENU_ON_LINE_START, false);
					 }
				}
			 };
			 loginBox.start();
			 break;
		 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:

		     btnSearchGame = new Button(
						GfxManager.imgButtonSearchBigRelease, 
						GfxManager.imgButtonSearchBigFocus, 
						Define.SIZEX - (GfxManager.imgButtonSearchBigRelease.getWidth()/2) - Define.SIZEY64, 
						(GfxManager.imgButtonSearchBigRelease.getHeight()/2) + Define.SIZEY64,
						null, -1){
				 	@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						SndManager.getInstance().playFX(Main.FX_NEXT, 0);
						reset();
						Main.changeState(Define.ST_MENU_ON_LINE_LIST_JOIN_GAME, false);
					}
				};
				
				btnCreateScene = new Button(
						GfxManager.imgButtonCrossBigRelease, 
						GfxManager.imgButtonCrossBigFocus, 
						Define.SIZEX - (GfxManager.imgButtonCrossBigRelease.getWidth()/2)-Define.SIZEY64,
                        (GfxManager.imgButtonSearchBigRelease.getHeight()) + (GfxManager.imgButtonSearchBigRelease.getHeight()/2) + Define.SIZEY32,
						null, -1){
					@Override
					public void onButtonPressDown(){}
					
					@Override
					public void onButtonPressUp(){
						SndManager.getInstance().playFX(Main.FX_NEXT, 0);
						reset();
						Main.changeState(Define.ST_MENU_ON_LINE_CREATE_SCENE, false);
					}
				};

             btnRanking = new Button(
                     GfxManager.imgButtonRankingRelease,
                     GfxManager.imgButtonRankingFocus,
                     Define.SIZEX - (GfxManager.imgButtonCrossBigRelease.getWidth()/2)-Define.SIZEY64,
                     (GfxManager.imgButtonSearchBigRelease.getHeight()*2) + (GfxManager.imgButtonSearchBigRelease.getHeight()/2) + Define.SIZEY32 + Define.SIZEY64,
                     null, -1){
                 @Override
                 public void onButtonPressDown(){}

                 @Override
                 public void onButtonPressUp(){
                     SndManager.getInstance().playFX(Main.FX_NEXT, 0);
                     reset();
                     Main.changeState(Define.ST_MENU_ON_LINE_RANKING, false);
                 }
             };
				
				
			Main.getInstance().startClock(Main.TYPE_EARTH);
			SceneListData sceneListData = OnlineInputOutput.getInstance().reviceSceneListData(
					Main.getInstance().getActivity(),
                    Main.IS_GAME_DEBUG?"-1":GameState.getInstance().getName(), "active");//check
			Main.getInstance().stopClock();

			if (sceneListData != null) {

				String[] textList = new String[sceneListData.getSceneDataList().size()];
				
				boolean[] disableList = new boolean[sceneListData.getSceneDataList().size()];
				for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
					disableList[i] = 
							!sceneListData.getSceneDataList().get(i).getNextPlayer().equals(GameState.getInstance().getName());
				}
				
				for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
					textList[i] = ""+
							sceneListData.getSceneDataList().get(i).getId() + " - " +
							DataKingdom.SCENARY_NAME_LIST[sceneListData.getSceneDataList().get(i).getMap()] +
							" - ";
					textList[i] += RscManager.allText[RscManager.TXT_NEXT] + " " +
                            (sceneListData.getSceneDataList().get(i).getTurnCount()+1) + "/" + GameParams.MAX_TURNS + " " +
							sceneListData.getSceneDataList().get(i).getNextPlayer();
				}

				selectSceneListBox = new SceneDataListBox(
						sceneListData, RscManager.allText[RscManager.TXT_SELECT_GAME], textList) {
					@Override
					public void onFinish() {
						if (getIndexPressed() != -1) {
							
							SceneListData sceneListData = (SceneListData)getSceneListData();
							SceneData sd = sceneListData.getSceneDataList().get(getIndexPressed());
							
							String msg = "";
							Main.getInstance().startClock(Main.TYPE_EARTH);
							
							//El escenario es nuevo
							SceneData sceneData = null;
							if(sd.getState() == 0){
								sceneData = 
										OnlineInputOutput.getInstance().reviceSceneData(
												Main.getInstance().getActivity(), OnlineInputOutput.URL_GET_START_SCENE, ""+ sd.getId());//check
							}
							//El escenario NO es nuevo
							else{
								sceneData = 
										OnlineInputOutput.getInstance().reviceSceneData(
												Main.getInstance().getActivity(), OnlineInputOutput.URL_GET_SCENE, ""+ sd.getId());//check
							}
							
							Main.getInstance().stopClock();
							
							if(sceneData != null){
								GameState.getInstance().init(GameState.GAME_MODE_ONLINE, sceneData);
								Main.changeState(Define.ST_GAME_INIT_ON_LINE, true);
							}else{
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
								NotificationBox.getInstance().addMessage(msg);
								Main.changeState(Define.ST_MENU_SELECT_GAME, false);
							}
							
						}else{
							Main.changeState(Define.ST_MENU_SELECT_GAME, false);
						}
					}
				};
				if(!Main.IS_GAME_DEBUG) {
					selectSceneListBox.setDisabledList(disableList);
				}
				selectSceneListBox.start();
				
				updateScenes();
			
			} else {
				NotificationBox.getInstance().
					addMessage(RscManager.allText[RscManager.TXT_CONNECTION_ERROR]);
				Main.changeState(Define.ST_MENU_ON_LINE_START, false);
			}
			
			if(Main.lastState > Define.ST_GAME_INIT_PASS_AND_PLAY){
				SndManager.getInstance().playMusic(Main.MUSIC_MAIN, true);
                Main.getInstance().getActivity().requestInterstitial();
			}
			
			break;
		 
		 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
			 Main.getInstance().startClock(Main.TYPE_EARTH);
			 PreSceneListData preSceneListData =  
					 OnlineInputOutput.getInstance().revicePreSceneListData(
							 Main.getInstance().getActivity(),
                             OnlineInputOutput.URL_GET_PRE_SCENE_LIST,
                             Main.IS_GAME_DEBUG?"-1":GameState.getInstance().getName());//check
			 Main.getInstance().stopClock();
			 
			 dialogBox = new DialogBox(GfxManager.imgMediumBox){
					@Override
					public void onFinish() {
						btnBack.setDisabled(false);
						if(this.getIndexPressed() != 0){
							
							String scene = "" + preSceneDataToJoin.getId();
							String user = GameState.getInstance().getName();
							
							String create = null;
							
							int insCount = (preSceneDataToJoin.getPlayerList().size()+1);
							if(insCount ==  DataKingdom.INIT_MAP_DATA[preSceneDataToJoin.getMap()].length){
								create = "create";
								Log.i("Debug", "Scene " + preSceneDataToJoin.getId() + " ya contiene el total de jugadores");
							}
							
							String msg = "";
							Main.getInstance().startClock(Main.TYPE_EARTH);
							String result = 
									OnlineInputOutput.getInstance().sendInscription(Main.getInstance().getActivity(), 
											scene, user, create);//check
							Main.getInstance().stopClock();
							 
							if(result.equals("Server error")){
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
							}
							else if(result.equals("Query error")){
								msg = RscManager.allText[RscManager.TXT_INCORRECT_USER_NAME];
							}
							else if(result.equals(OnlineInputOutput.MSG_NO_CONNECTION)){
								msg = RscManager.allText[RscManager.TXT_NO_CONNECTION];
							 }
							else if(result.equals("Succes")){
								msg = RscManager.allText[RscManager.TXT_HAVE_JOINED];
							}
							else{
								msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
							}
							NotificationBox.getInstance().addMessage(msg);
							Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
						}else{
							joinPreSceneListBox.start();
						}
					}
				};
			
			 if (preSceneListData != null) {
				 
				String[] textList = new String[preSceneListData.getPreSceneDataList().size()];
				for(int i = 0; i < preSceneListData.getPreSceneDataList().size(); i++){
					
					int numPlayer = DataKingdom.INIT_MAP_DATA[preSceneListData.getPreSceneDataList().get(i).getMap()].length;
					
					textList[i] = ""+
							preSceneListData.getPreSceneDataList().get(i).getId() + "-" +
							preSceneListData.getPreSceneDataList().get(i).getHost() + " " +
							DataKingdom.SCENARY_NAME_LIST[preSceneListData.getPreSceneDataList().get(i).getMap()] +
							" - " +
							preSceneListData.getPreSceneDataList().get(i).getPlayerList().size() + 
							"/" + numPlayer;
				}
				
				joinPreSceneListBox = new SceneDataListBox(
						preSceneListData, RscManager.allText[RscManager.TXT_JOIN_GAME], textList){
					@Override
					public void onFinish(){
						if(getIndexPressed() != -1){
							
							PreSceneListData preSceneListData = (PreSceneListData)getSceneListData();
							preSceneDataToJoin = preSceneListData.getPreSceneDataList().get(getIndexPressed());
							btnBack.setDisabled(true);
							int numPlayer = DataKingdom.INIT_MAP_DATA[preSceneDataToJoin.getMap()].length;
							
							String playerList = "";
							for(int i = 0; i < preSceneDataToJoin.getPlayerList().size(); i++){
								playerList += preSceneDataToJoin.getPlayerList().get(i).getName();
								if(i < preSceneDataToJoin.getPlayerList().size()-1){
									playerList += " - ";
								}
							}
							
							dialogBox.start(
									preSceneDataToJoin.getId() + "-" +
									DataKingdom.SCENARY_NAME_LIST[preSceneDataToJoin.getMap()] + " " +
									preSceneDataToJoin.getPlayerList().size() + "/" + numPlayer,
									
									RscManager.allText[RscManager.TXT_DO_YOU_WANT_JOIN] + " " + preSceneDataToJoin.getHost() + 
									RscManager.allText[RscManager.TXT_GAME_INTERROGATION_ICON] + " " +
									RscManager.allText[RscManager.TXT_PLAYERS] + " " +
									playerList);
						}else{
							Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
						}
					}
				};
				joinPreSceneListBox.start();
				updatePreScenes();
				
				
			}else{
				NotificationBox.getInstance().addMessage(RscManager.allText[RscManager.TXT_CONNECTION_ERROR]);
				Main.changeState(Define.ST_MENU_ON_LINE_START, false);
			}
			
			break;
		 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
			 dialogBox = new DialogBox(GfxManager.imgMediumBox){
					@Override
					public void onFinish() {
						btnBack.setDisabled(false);
						if(this.getIndexPressed() != 0){
							
							String msg = null;
							Main.getInstance().startClock(Main.TYPE_EARTH);
							String result =  
								 OnlineInputOutput.getInstance().sendPreScene(
											 Main.getInstance().getActivity(), 
											 OnlineInputOutput.URL_CREATE_PRE_SCENE, onlineCreateMap, onlineCreateHost, onLineGameName);//check
							Main.getInstance().stopClock();
							if(result.equals("Server error")){
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
							}
							else if(result.equals("Query error")){
								msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
							}
							else if(result.equals("Succes")){
								msg = RscManager.allText[RscManager.TXT_GAME_CREATED];
							}
							else if(result.equals(OnlineInputOutput.MSG_NO_CONNECTION)){
								msg = RscManager.allText[RscManager.TXT_NO_CONNECTION];
							}else{
								msg = RscManager.allText[RscManager.TXT_CONNECTION_ERROR];
							}
							Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
							NotificationBox.getInstance().addMessage(msg);
							
						}else{
							createSceneListBox.start();
						}
					}
				};
			 
			 createSceneListBox = new ListBox(
						Define.SIZEX, Define.SIZEY, 
						null, GfxManager.imgNotificationBox, GfxManager.imgNotificationBox, 
						Define.SIZEX2, Define.SIZEY2, 
						RscManager.allText[RscManager.TXT_CREATE_GAME],
						DataKingdom.SCENARY_LIST,
						Font.FONT_BIG, Font.FONT_SMALL,
						-1, Main.FX_NEXT){
					
					@Override
					public void onFinish(){
						
						if(getIndexPressed() != -1){
							
							onlineCreateMap = "" + getIndexPressed();
							onlineCreateHost = GameState.getInstance().getName();
							onLineGameName = "TEST";
							
							btnBack.setDisabled(true);
							
							dialogBox.start(
									DataKingdom.SCENARY_NAME_LIST[getIndexPressed()] + " - " +
									RscManager.allText[RscManager.TXT_PLAYERS] + " " +
									DataKingdom.INIT_MAP_DATA[getIndexPressed()].length,
									RscManager.allText[RscManager.TXT_GAME_CREATE_CONF]);
						}else{
							Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
						}
					};
				};
				createSceneListBox.start();
			 break;

            case Define.ST_MENU_ON_LINE_RANKING:

                rankingBox = new RankingBox(){
                    public void onFinish() {
                        btnBack.setDisabled(false);
                        rankingSceneListBox.start();
                    }
                };

                Main.getInstance().startClock(Main.TYPE_EARTH);
                SceneListData rankingListData = OnlineInputOutput.getInstance().reviceSceneListData(
                        Main.getInstance().getActivity(),
                        Main.IS_GAME_DEBUG?"-1":GameState.getInstance().getName(), "unactive");//check
                Main.getInstance().stopClock();

                if (rankingListData != null) {

                    String[] textList = new String[rankingListData.getSceneDataList().size()];
                    for(int i = 0; i < rankingListData.getSceneDataList().size(); i++){

                        textList[i] = ""+
                                rankingListData.getSceneDataList().get(i).getId() + " - " +
                                DataKingdom.SCENARY_NAME_LIST[rankingListData.getSceneDataList().get(i).getMap()];
                    }

                    rankingSceneListBox = new SceneDataListBox(
                            rankingListData, RscManager.allText[RscManager.TXT_GAME_RANKING], textList) {
                        @Override
                        public void onFinish() {
                            if (getIndexPressed() != -1) {

                                SceneListData sceneListData = (SceneListData)getSceneListData();
                                SceneData sd = sceneListData.getSceneDataList().get(getIndexPressed());

                                String msg = "";
                                Main.getInstance().startClock(Main.TYPE_EARTH);

                                SceneData sceneData = OnlineInputOutput.getInstance().reviceSceneData(
                                        Main.getInstance().getActivity(),
                                        OnlineInputOutput.URL_GET_SCENE, ""+ sd.getId());//check;

                                Main.getInstance().stopClock();

                                if(sceneData != null){
                                    GameState.getInstance().init(GameState.GAME_MODE_ONLINE, sceneData);
                                    GameScene gameScene = GameBuilder.getInstance().buildGameScene();
                                    btnBack.setDisabled(true);
                                    rankingBox.start(gameScene.getPlayerList());
                                }else{
                                    msg = RscManager.allText[RscManager.TXT_SERVER_ERROR];
                                    NotificationBox.getInstance().addMessage(msg);
                                    Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
                                }

                            }else{
                                Main.changeState(Define.ST_MENU_ON_LINE_LIST_ALL_GAME, false);
                            }
                        }
                    };

                    rankingSceneListBox.start();

                } else {
                    NotificationBox.getInstance().
                            addMessage(RscManager.allText[RscManager.TXT_CONNECTION_ERROR]);
                    Main.changeState(Define.ST_MENU_ON_LINE_START, false);
                }

                break;
			 
		 case Define.ST_TEST:
			break;
		}
	}
	
	public static void update(){
		
		switch (Main.state) {
		case Define.ST_MENU_START:
			if(!runPresentation(ST_TIME_CITA_1, ST_TIME_CITA_2, ST_TIME_CITA_3) || Main.IS_GAME_DEBUG || Main.getInstance().isNotification()){
				Main.changeState(Define.ST_MENU_LOGO, false);
			}
			break;
		case Define.ST_MENU_LOGO:
			if(!runPresentation(ST_TIME_LOGO_1, ST_TIME_LOGO_2, ST_TIME_LOGO_3) || Main.IS_GAME_DEBUG || Main.getInstance().isNotification()){
				Main.changeState(Define.ST_MENU_MAIN, false);
			}
			break;
		case Define.ST_MENU_ASK_LANGUAGE:
			break;
		
		case Define.ST_MENU_ASK_SOUND:
			break;
		
		case Define.ST_MENU_MAIN:
			runMenuBG(Main.getDeltaSec());
			NotificationBox.getInstance().update(Main.getDeltaSec());
			configurationBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());

			btnConfiguration.setDisabled(configurationBox.isActive());
			btnConfiguration.update(UserInput.getInstance().getMultiTouchHandler());
			btnCampaign.update(UserInput.getInstance().getMultiTouchHandler());
			btnMultiPlayer.setDisabled(configurationBox.isActive());
			btnMultiPlayer.update(UserInput.getInstance().getMultiTouchHandler());
			btnInfo.setDisabled(configurationBox.isActive());
			btnInfo.update(UserInput.getInstance().getMultiTouchHandler());
			btnDebug.update(UserInput.getInstance().getMultiTouchHandler());
            btnEasterEgg.update(UserInput.getInstance().getMultiTouchHandler());

			break;
			
		case Define.ST_MENU_INFO:
			runMenuBG(Main.getDeltaSec());
			btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			btnHelp.update(UserInput.getInstance().getMultiTouchHandler());
			btnAbout.update(UserInput.getInstance().getMultiTouchHandler());
			break;
			
		case Define.ST_MENU_OPTIONS:
			runMenuBG(Main.getDeltaSec());
			btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			break;
			
		case Define.ST_MENU_HELP:
			runMenuBG(Main.getDeltaSec());
			btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			break;
			
		case Define.ST_MENU_ABOUT:
			runMenuBG(Main.getDeltaSec());
			btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			if(numLetters < RscManager.allText[RscManager.TXT_ABOUT_DESCRIP].length()){
				while(RscManager.allText[RscManager.TXT_ABOUT_DESCRIP].charAt(numLetters) == '@'){
					numLetters++;	
				}
				numLetters++;
			}
			break;
			
		case Define.ST_MENU_EXIT:
        	break;
        	
		case Define.ST_MENU_CAMPAING:
	        	runMenuBG(Main.getDeltaSec());
	        	btnBack.update(UserInput.getInstance().getMultiTouchHandler());
				break;
				
        case Define.ST_MENU_SELECT_GAME:
        	runMenuBG(Main.getDeltaSec());
        	btnBack.update(UserInput.getInstance().getMultiTouchHandler());
        	
        	if(gameVersionBox != null){
        		gameVersionBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
        	}
        	
        	if(gameVersionBox == null || (gameVersionBox != null && !gameVersionBox.isActive())){
        		btnOnLine.update(UserInput.getInstance().getMultiTouchHandler());
    			btnPassAndPlay.update(UserInput.getInstance().getMultiTouchHandler());
    			if(btnContinuePassAndPlay != null){
    				btnContinuePassAndPlay.update(UserInput.getInstance().getMultiTouchHandler());
    			}
        	}
        	break;
        case Define.ST_MENU_SELECT_MAP:
        	runMenuBG(Main.getDeltaSec());
        	btnBack.update(UserInput.getInstance().getMultiTouchHandler());
        	createSceneListBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			break;
			
        case Define.ST_MENU_CONFIG_MAP:
			runMenuBG(Main.getDeltaSec());
			btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			btnNext.update(UserInput.getInstance().getMultiTouchHandler());
			configMapBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
            simpleBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			break;
			
        case Define.ST_MENU_ON_LINE_START:
        	 runMenuBG(Main.getDeltaSec());
        	 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
        	 btnNewAccount.update(UserInput.getInstance().getMultiTouchHandler());
        	 btnLogin.update(UserInput.getInstance().getMultiTouchHandler());
			 break;
			 
		 case Define.ST_MENU_ON_LINE_CREATE_USER:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 createUserBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 break;
			 
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 loginBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 break;
			 
		 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
			 runMenuBG(Main.getDeltaSec());
			 
			 if(notificationBox != null){
				 btnCancel.update(UserInput.getInstance().getMultiTouchHandler());
				 notificationBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 }
			 
			 if(notificationBox == null || (notificationBox != null && !notificationBox.isActive())){
				 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
				 btnSearchGame.update(UserInput.getInstance().getMultiTouchHandler());
				 btnCreateScene.update(UserInput.getInstance().getMultiTouchHandler());
                 btnRanking.update(UserInput.getInstance().getMultiTouchHandler());
				 if(selectSceneListBox != null){
					 selectSceneListBox .update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
				 }
			 }
			 break;
			 
		 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 dialogBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 
			 if(joinPreSceneListBox != null){
				 joinPreSceneListBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 }
			 break;
			 
		 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
			 runMenuBG(Main.getDeltaSec());
			 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
			 dialogBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 if(createSceneListBox != null)
				 createSceneListBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
			 break;

			 case Define.ST_MENU_ON_LINE_RANKING:
                 runMenuBG(Main.getDeltaSec());
                 btnBack.update(UserInput.getInstance().getMultiTouchHandler());
                 rankingBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
                 if(rankingSceneListBox != null){
                     rankingSceneListBox.update(UserInput.getInstance().getMultiTouchHandler(), Main.getDeltaSec());
                 }
                break;

		case Define.ST_TEST:
			break;
		}
		
		NotificationBox.getInstance().update(Main.getDeltaSec());
	}
	
	public static void draw(Graphics _g){
		
		switch (Main.state) {
		case Define.ST_MENU_START:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setColor(Main.COLOR_BLACK);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			
			TextManager.draw(_g, Font.FONT_SMALL, RscManager.allText[cita], 
					Define.SIZEX2, Define.SIZEY2-Define.SIZEY8, 
					(int)(Define.SIZEX*0.75), TextManager.ALING_CENTER, -1);
			
			TextManager.draw(_g, Font.FONT_SMALL, RscManager.allText[author], 
					Define.SIZEX2, Define.SIZEY2+Define.SIZEY8, 
					Define.SIZEX, TextManager.ALING_CENTER, -1);
			
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
			_g.setAlpha(255);
			break;
			
		case Define.ST_MENU_LOGO:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.setColor(Main.COLOR_BLACK);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			
			_g.drawImage(GfxManager.vImgLogo, Define.SIZEX2, Define.SIZEY2, Graphics.VCENTER|Graphics.HCENTER);
			
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
			_g.setAlpha(255);
             
			break;
		case Define.ST_MENU_ASK_LANGUAGE:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_MENU_ASK_SOUND:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
		
		case Define.ST_MENU_MAIN:
			drawMenuBG(_g);
			btnCampaign.draw(_g, 0, 0);
			btnMultiPlayer.draw(_g, 0, 0);
			btnInfo.draw(_g, 0, 0);
			btnConfiguration.draw(_g, 0, 0);
			_g.setAlpha(alpha);
			_g.drawImage(GfxManager.imgBlackBG, 0, 0, Graphics.TOP | Graphics.LEFT);
			_g.setAlpha(255);
			configurationBox.draw(_g, GfxManager.imgBlackBG);
            NotificationBox.getInstance().draw(_g);
			break;
			
		case Define.ST_MENU_OPTIONS:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_MENU_INFO:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			btnHelp.draw(_g, 0, 0);
			btnAbout.draw(_g, 0, 0);
			break;
			
		case Define.ST_MENU_HELP:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			break;
			
		case Define.ST_MENU_ABOUT:
			drawMenuBG(_g);
			TextManager.draw(_g, Font.FONT_BIG, RscManager.allText[RscManager.TXT_ABOUT_DESCRIP],
					Define.SIZEX2, Define.SIZEY2,
					Define.SIZEX-Define.SIZEX8, TextManager.ALING_CENTER, numLetters);
			btnBack.draw(_g, 0, 0);
			break;
			
		case Define. ST_MENU_CAMPAING:
			drawMenuBG(_g);
			btnBack.draw(_g, 0, 0);
			break;
			
		case Define.ST_MENU_SELECT_GAME:
			drawMenuBG(_g);
			if(gameVersionBox != null){
				gameVersionBox.draw(_g, GfxManager.imgBlackBG);
			}
			btnBack.draw(_g, 0, 0);
			btnOnLine.draw(_g, 0, 0);
			btnPassAndPlay.draw(_g, 0, 0);
			if(btnContinuePassAndPlay != null){
				btnContinuePassAndPlay.draw(_g, 0, 0);
			}
			break;
			
		case Define.ST_MENU_SELECT_MAP:
			drawMenuBG(_g);
			createSceneListBox.draw(_g, GfxManager.imgBlackBG);
			btnBack.draw(_g, 0, 0);
			break;

		case Define.ST_MENU_CONFIG_MAP:
			drawMenuBG(_g);
			configMapBox.draw(_g);
            simpleBox.draw(_g, GfxManager.imgBlackBG);
			btnBack.draw(_g, 0, 0);
			btnNext.draw(_g, 0, 0);
			break;
			
		 case Define.ST_MENU_ON_LINE_START:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 btnNewAccount.draw(_g, 0, 0);
			 btnLogin.draw(_g, 0, 0);
			 break;
			 
		 case Define.ST_MENU_ON_LINE_CREATE_USER:
			 drawMenuBG(_g);
			 btnBack.draw(_g, 0, 0);
			 createUserBox.draw(_g);
			 break;
		 case Define.ST_MENU_ON_LINE_LOGIN:
			 drawMenuBG(_g);
			 loginBox.draw(_g);
			 btnBack.draw(_g, 0, 0);
			 break;
			 
		 case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
			 drawMenuBG(_g);
			 TextManager.drawSimpleText(
					 _g, Font.FONT_SMALL,
					 RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + GameState.getInstance().getName(),
					 Define.SIZEX64,
					 Define.SIZEY-Define.SIZEY64, Graphics.BOTTOM | Graphics.LEFT);
			 if(selectSceneListBox != null){
				 selectSceneListBox.draw(_g, GfxManager.imgBlackBG);
			 }

			 if(notificationBox != null){
				 notificationBox.draw(_g, GfxManager.imgBlackBG);
				 btnCancel.draw(_g, (int)notificationBox.getModPosX(), 0);
			 }
			 btnBack.draw(_g, 0, 0);
			 btnSearchGame.draw(_g, 0, 0);
			 btnCreateScene.draw(_g,0, 0);
             btnRanking.draw(_g,0, 0);
			 break;
			 
		 case Define.ST_MENU_ON_LINE_LIST_JOIN_GAME:
			 drawMenuBG(_g);
			 dialogBox.draw(_g, GfxManager.imgBlackBG);
			 TextManager.drawSimpleText(
					 _g, Font.FONT_SMALL,
					 RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + GameState.getInstance().getName(),
					 Define.SIZEX64,
					 Define.SIZEY-Define.SIZEY64, Graphics.BOTTOM | Graphics.LEFT);
			 if(joinPreSceneListBox != null){
				 joinPreSceneListBox.draw(_g, GfxManager.imgBlackBG);
			 }
			 btnBack.draw(_g, 0, 0);
			 break;
			 
		 case Define.ST_MENU_ON_LINE_CREATE_SCENE:
			 drawMenuBG(_g);
			 dialogBox.draw(_g, GfxManager.imgBlackBG);
			 if(createSceneListBox != null){
				 createSceneListBox.draw(_g, GfxManager.imgBlackBG);
			 }
			 btnBack.draw(_g, 0, 0);
			 break;

			 case Define.ST_MENU_ON_LINE_RANKING:
                 drawMenuBG(_g);
                 rankingBox.draw(_g);
                 TextManager.drawSimpleText(
                         _g, Font.FONT_SMALL,
                         RscManager.allText[RscManager.TXT_GAME_PLAYER] + " " + GameState.getInstance().getName(),
                         Define.SIZEX64,
                         Define.SIZEY-Define.SIZEY64, Graphics.BOTTOM | Graphics.LEFT);
                 if(rankingSceneListBox != null){
                     rankingSceneListBox.draw(_g, GfxManager.imgBlackBG);
                 }
                 btnBack.draw(_g, 0, 0);
		     break;
		 case Define.ST_MENU_EXIT:
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			break;
			
		case Define.ST_TEST:
			
			_g.setColor(0x000000);
			_g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
			_g.fillRect(0, 0, Define.SIZEX, Define.SIZEY);
			break;
		}
		
		NotificationBox.getInstance().draw(_g);
	}
	
	public static void drawBackground(Graphics _g, Image _vGB) {
        if (_vGB != null) {
            _g.drawImage(_vGB, 0, 0, 0);
        } else {
            gradientBackground(_g, Main.COLOR_BLUE_BG, Main.COLOR_BLACK, 0, 0, Define.SIZEX, Define.SIZEY, 32);
        }
    }
	
	public static int[] iColor = new int[3];
    public static boolean isVibrate;

    public static void gradientBackground(Graphics g, int color1, int color2, int x, int y, int width, int height, int steps) {

        int stepSize = height / steps;

        int color1RGB[] = new int[]{(color1 >> 16) & 0xff, (color1 >> 8) & 0xff, color1 & 0xff};
        int color2RGB[] = new int[]{(color2 >> 16) & 0xff, (color2 >> 8) & 0xff, color2 & 0xff};

        int colorCalc[] = new int[]{
            ((color2RGB[0] - color1RGB[0]) << 16) / steps,
            ((color2RGB[1] - color1RGB[1]) << 16) / steps,
            ((color2RGB[2] - color1RGB[2]) << 16) / steps
        };

        for (int i = 0; i < steps; i++) {
            g.setColor(color1RGB[0] + ((i * colorCalc[0] >> 16)) << 16
                    | color1RGB[1] + ((i * colorCalc[1] >> 16)) << 8
                    | color1RGB[2] + ((i * colorCalc[2] >> 16)));
            if (i != steps - 1) {
                g.fillRect(x, y + i * stepSize, width, stepSize);
            } else {
                g.fillRect(x, y + i * stepSize, width, stepSize + 30); //+20 corrects presicion los due to divisions
            }
        }
    }
	
	private static long startTime;
	public static final long ST_TIME_CITA_1 = 1000;
	public static final long ST_TIME_CITA_2 = 6000;
	public static final long ST_TIME_CITA_3 = 2000;


	public static final long ST_TIME_LOGO_1 = 1000;
	public static final long ST_TIME_LOGO_2 = 800;
	public static final long ST_TIME_LOGO_3 = 1000;

	public static final long ST_TIME_MAIN = 1200;

    private static int statePresentation;
	public static final int ST_PRESENTATION_1 = 0;
	public static final int ST_PRESENTATION_2 = 2;
	public static final int ST_PRESENTATION_3 = 3;
	public static int alpha;
	
	public static boolean runPresentation(long time1, long time2, long time3){
		switch(statePresentation){
		case ST_PRESENTATION_1:
			alpha = 255-(int)(((System.currentTimeMillis() - startTime)*255)/time1);
			if(alpha <= 0){
				alpha = 0;
				statePresentation = ST_PRESENTATION_2;
				startTime = System.currentTimeMillis();
			}
			break;
		case ST_PRESENTATION_2:
			if(System.currentTimeMillis()>startTime+time2){
				if(Main.state == Define.ST_MENU_START){
					SndManager.getInstance().playMusic(Main.MUSIC_INTRO, true);
				}
				statePresentation = ST_PRESENTATION_3;
				startTime = System.currentTimeMillis();
				
			}
			break;
		case ST_PRESENTATION_3:
			alpha = (int)(((System.currentTimeMillis() - startTime)*255)/time3);
			if(alpha >= 255){
				alpha = 255;
				return false;
			}
		}
		return true;
	}
	
	public static float cloudFarBGX;
	public static float cloudFarBGY;
	public static float cloudNearBGX;
	public static float cloudNearBGY;
	public static float cloudNear2BGX;
	public static float cloudNear2BGY;
	public static int logoAlpha;
	public static void runMenuBG(float delta){
		
		alpha = 255-((int)(((System.currentTimeMillis() - startTime)*255)/ST_TIME_MAIN));
		if(alpha < 0){
			alpha = 0;
		}
		if(cloudFarBGX < -GfxManager.imgCloudBG.getWidth()/2){
			cloudFarBGX = Define.SIZEX + (GfxManager.imgCloudBG.getWidth()/2);
		}
		if(cloudNearBGX < -(GfxManager.imgCloudBG.getWidth()*1.2f)/2){
			cloudNearBGX = Define.SIZEX + ((GfxManager.imgCloudBG.getWidth()*1.2f)/2);
		}
		if(cloudNear2BGX < -(GfxManager.imgCloudBG.getWidth()*1.4f)/2){
			cloudNear2BGX = Define.SIZEX + ((GfxManager.imgCloudBG.getWidth()*1.4f)/2);
		}
		cloudFarBGX-=10f*delta;
		cloudNearBGX-=20f*delta;
		cloudNear2BGX-=40f*delta;
		
		if(
				!configurationBox.isActive() &&
				(
				Main.state == Define.ST_MENU_MAIN || 
				Main.state == Define.ST_MENU_SELECT_GAME || 
				Main.state == Define.ST_MENU_ON_LINE_START)
				
		){
			logoAlpha +=delta*255;
			logoAlpha = Math.min(255, logoAlpha);
		}else{
			logoAlpha -=delta*(255);
			logoAlpha = Math.max(80, logoAlpha);
		}
	}
	
	private static void drawMenuBG(Graphics g){
		g.setClip(0, 0, Define.SIZEX, Define.SIZEY);
		g.drawImage(GfxManager.imgMainBG, 0, 0, Graphics.TOP | Graphics.LEFT);
		
		g.setAlpha(logoAlpha);
		g.drawImage(GfxManager.imgTitle, 
				Define.SIZEX-(int)(GfxManager.imgButtonMenuBigRelease.getWidth()/2)-Define.SIZEY64, 
				GfxManager.imgTitle.getHeight()/2 + Define.SIZEY32, 
				Graphics.VCENTER | Graphics.HCENTER);
		
		g.setAlpha(255);
		
		g.drawImage(GfxManager.imgCloudBG, (int)cloudFarBGX, (int)cloudFarBGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1.2f, 1.2f);
		g.drawImage(GfxManager.imgCloudBG, (int)cloudNearBGX, (int)cloudNearBGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1f, 1f);
		g.drawImage(GfxManager.imgSwordBG, 0, Define.SIZEY, Graphics.BOTTOM | Graphics.LEFT);
		g.setImageSize(1.4f, 1.4f);
		g.drawImage(GfxManager.imgCloudBG, (int)cloudNear2BGX, (int)cloudNear2BGY, Graphics.VCENTER | Graphics.HCENTER);
		g.setImageSize(1f, 1f);
	}
	
	private static Thread sceneUpdate;
	private static Thread preSceneUpdate;
	//private static Thread notificationsUpdate;
	
	private static void updateNotificatons(){
		
		if(notificationBox == null || notificationBox != null && !notificationBox.isActive()){
		
			NotificationListData notificationListData = 
					OnlineInputOutput.getInstance().reviceNotificationListData(
							Main.getInstance().getActivity(),
							"-1", GameState.getInstance().getName(), "0");
			
			if (notificationListData != null && 
					notificationListData.getNotificationDataList().size() > 0) {
				
				
				String[] notificationList = new String[notificationListData.getNotificationDataList().size()];
				for (int i = 0; i < notificationListData.getNotificationDataList().size(); i++) {
					
					String msg = "";
					switch(notificationListData.getNotificationDataList().get(i).getMessage()){
					case OnlineInputOutput.CODE_NOTIFICATION_YOU_LOST_GAME:
						msg = 
							notificationListData.getNotificationDataList().get(i).getSceneId() + "-" +
							RscManager.allText[RscManager.TXT_NOTIFICATION_YOU_LOST_GAME];
						break;
					default:
						msg = "Notification error default";
						break;
					}
					notificationList[i] = msg;
				}
				notificationBox = new ListBox(Define.SIZEX, Define.SIZEY,
						GfxManager.imgBigBox,
						GfxManager.imgButtonInvisible,
						GfxManager.imgButtonInvisible, Define.SIZEX2,
						Define.SIZEY2,
						RscManager.allText[RscManager.TXT_NOTIFICATIONS],
						notificationList, Font.FONT_MEDIUM,
						Font.FONT_SMALL, -1, Main.FX_NEXT);
				notificationBox.setDisabledList();
				for (Button button : notificationBox.getBtnList()) {
					button.setIgnoreAlpha(true);
				}
				notificationBox.start();
	
				btnCancel = new Button(
						GfxManager.imgButtonCancelRelease,
						GfxManager.imgButtonCancelFocus,
						notificationBox.getX() - notificationBox.getWidth()/ 2, 
						notificationBox.getY()- notificationBox.getHeight() / 2, 
						null, -1) {
					@Override
					public void onButtonPressUp() {
						setDisabled(true);
						notificationBox.cancel();
					}
				};
	
				// Enviar notificaciones leidas
				OnlineInputOutput.getInstance().sendDataPackage(
						 Main.getInstance().getActivity(),
						 OnlineInputOutput.URL_UPDATE_NOTIFICATION, notificationListData);
				
			}
		}
	}
	
	private static void updateScenes() {
		sceneUpdate = new Thread() {
			@Override
			public void run() {
				super.run();
				
				while(Main.state == Define.ST_MENU_ON_LINE_LIST_ALL_GAME){
					try {
						Thread.sleep(5000);
						//Log.i("Debug", "Actualizando lista de scenes...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if(
							!Main.getInstance().isPaused() &&
							Main.state == Define.ST_MENU_ON_LINE_LIST_ALL_GAME &&
									selectSceneListBox != null){
						SceneListData sceneListData = 
								OnlineInputOutput.getInstance().reviceSceneListData(
										Main.getInstance().getActivity(),
                                        Main.IS_GAME_DEBUG?"-1":GameState.getInstance().getName(), "active");
						
						if (sceneListData != null) {
							Log.i("Debug", "Actualizando selectSceneBox " + Main.iFrame);
							String[] textList = new String[sceneListData.getSceneDataList().size()];
							
							boolean[] disableList = new boolean[sceneListData.getSceneDataList().size()];
							for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
								disableList[i] = 
										!sceneListData.getSceneDataList().get(i).getNextPlayer().equals(GameState.getInstance().getName());
							}
							
							for (int i = 0; i < sceneListData.getSceneDataList().size(); i++) {
								textList[i] = ""+
										sceneListData.getSceneDataList().get(i).getId() + " - " +
										DataKingdom.SCENARY_NAME_LIST[sceneListData.getSceneDataList().get(i).getMap()] +
										" - ";
								textList[i] += RscManager.allText[RscManager.TXT_NEXT] + " " +
                                        (sceneListData.getSceneDataList().get(i).getTurnCount()+1) + "/" + GameParams.MAX_TURNS + " " +
										sceneListData.getSceneDataList().get(i).getNextPlayer();
							}
							selectSceneListBox.refresh(sceneListData, RscManager.allText[RscManager.TXT_SELECT_GAME], textList);
                            if(!Main.IS_GAME_DEBUG) {
								selectSceneListBox.setDisabledList(disableList);
                            }
						}
						
						updateNotificatons();
					}
				}
			}
		};
		sceneUpdate.start();
	}
	
	private static PreSceneListData preSceneListData;
	private static void updatePreScenes() {
		preSceneUpdate = new Thread() {
			@Override
			public void run() {
				super.run();
					
				while(Main.state == Define.ST_MENU_ON_LINE_LIST_JOIN_GAME){
					
					try {
						Thread.sleep(5000);
						//Log.i("Debug", "Actualizando lista de scenes...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if(
							!Main.getInstance().isPaused()  &&
							Main.state == Define.ST_MENU_ON_LINE_LIST_JOIN_GAME && 
							joinPreSceneListBox != null){
						
						preSceneListData =  
								OnlineInputOutput.getInstance().revicePreSceneListData(
										Main.getInstance().getActivity(),
                                        OnlineInputOutput.URL_GET_PRE_SCENE_LIST,
                                        Main.IS_GAME_DEBUG?"-1":GameState.getInstance().getName());
						if (preSceneListData != null) {
							Log.i("Debug", "Actualizando selectPreSceneBox " + Main.iFrame);
							String[] textList = new String[preSceneListData.getPreSceneDataList().size()];
							for(int i = 0; i < preSceneListData.getPreSceneDataList().size(); i++){
								int numPlayer = DataKingdom.INIT_MAP_DATA[preSceneListData.getPreSceneDataList().get(i).getMap()].length;
								textList[i] = ""+
										preSceneListData.getPreSceneDataList().get(i).getId() + "-" +
										preSceneListData.getPreSceneDataList().get(i).getHost() + " " +
										DataKingdom.SCENARY_NAME_LIST[preSceneListData.getPreSceneDataList().get(i).getMap()] +
										" - " +
										preSceneListData.getPreSceneDataList().get(i).getPlayerList().size() + 
										"/" + numPlayer;
								}
							joinPreSceneListBox.refresh(preSceneListData, RscManager.allText[RscManager.TXT_JOIN_GAME], textList);
						}
					}
				}
			}
		};
		preSceneUpdate.start();
	}
	
	
}

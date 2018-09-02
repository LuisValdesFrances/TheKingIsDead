package com.luis.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.strategy.constants.Define;

public class GfxManager {

	public static Image vImgFontSmall;
	public static Image vImgFontMedium;
	public static Image vImgFontBig;

	public static Image imgBlackBG;
	public static Image vImgLogo;
	public static Image imgMainBG;
	public static Image imgSwordBG;
	public static Image imgCloudBG;
	public static Image imgTitle;

	public static Image imgButtonMenuBigRelease;
	public static Image imgButtonMenuBigFocus;
	public static Image imgButtonMenuMediumRelease;
	public static Image imgButtonMenuMediumFocus;
	public static Image imgButtonMenuSmallRelease;
	public static Image imgButtonMenuSmallFocus;

	public static Image imgButtonArrowBackRelease;
	public static Image imgButtonArrowBackFocus;
	public static Image imgButtonArrowNextRelease;
	public static Image imgButtonArrowNextFocus;

	public static Image imgButtonInvisible;

	public static Image imgButtonKeyboardRelease;
	public static Image imgButtonKeyboardFocus;
	public static Image imgButtonKeyboardReleaseSp;
	public static Image imgButtonKeyboardFocusSp;

	public static Image imgInputBox;
	public static Image imgTextPointer;

	public static Image imgCheckFocus;
	public static Image imgCheckRelease;
	public static Image imgUncheckFocus;
	public static Image imgUncheckRelease;

	public static Image imgButtonOptionsRelease;
	public static Image imgButtonOptionsFocus;
	public static Image imgButtonRankingRelease;
	public static Image imgButtonRankingFocus;

	public static Image imgEnglishRelease;
	public static Image imgEnglishFocus;
	public static Image imgSpanishRelease;
	public static Image imgSpanishFocus;
	public static Image imgCatalaRelease;
	public static Image imgCatalaFocus;

	// Game images:
	public static Image imgTextBG;

	public static Image imgPadWest;
	public static Image imgPadEast;

	/*
	 * public static Image imgPadNorth; public static Image imgPadSouth; public
	 * static Image imgPadAux;
	 */
	public static Image imgCityRanking;
    public static Image imgSwordRanking;

	public static Image imgProtection;
	public static Image imgProtectionRes;
	public static Image imgGameHud;
	public static Image imgCoin;
	public static Image imgCoinSmall;
	public static Image imgCrown;
	public static Image imgButtonPauseRelease;
	public static Image imgButtonPauseFocus;
	public static Image imgButtonDebugPauseRelease;
	public static Image imgButtonDebugPauseFocus;
	public static Image imgButtonNextRelease;
	public static Image imgButtonNextFocus;
	public static Image imgButtonCancelRelease;
	public static Image imgButtonCancelFocus;
	public static Image imgButtonDeleteRelease;
	public static Image imgButtonDeleteFocus;
	public static Image imgButtonNewArmyRelease;
	public static Image imgButtonNewArmyFocus;
	public static Image imgButtonInfoRelease;
	public static Image imgButtonInfoFocus;
	public static Image imgButtonMapRelease;
	public static Image imgButtonMapFocus;
	public static Image imgButtonCrownRelease;
	public static Image imgButtonCrownFocus;
	public static Image imgButtonChestRelease;
	public static Image imgButtonChestFocus;

	public static Image imgButtonCombatRelease;
	public static Image imgButtonCombatFocus;
	public static Image imgButtonCardRelease;
	public static Image imgButtonCardFocus;
	public static Image imgButtonOkRelease;
	public static Image imgButtonOkFocus;
	public static Image imgButtonCrossRelease;
	public static Image imgButtonCrossFocus;
	public static Image imgButtonCrossBigRelease;
	public static Image imgButtonCrossBigFocus;
	public static Image imgButtonSearchBigRelease;
	public static Image imgButtonSearchBigFocus;
	public static Image imgButtonFlagHelmetRelease;
	public static Image imgButtonFlagHelmetFocus;
	public static Image imgButtonFlagCastleRelease;
	public static Image imgButtonFlagCastleFocus;
	public static Image imgButtonHelmetRelease;
	public static Image imgButtonHelmetFocus;

	public static Image imgButtonYellowRelease;
	public static Image imgButtonYellowFocus;
	public static Image imgButtonGreenRelease;
	public static Image imgButtonGreenFocus;
	public static Image imgButtonRedRelease;
	public static Image imgButtonRedFocus;

	public static Image imgNickBox;

	public static Image imgTextBox;
	public static Image imgBigBox;
	public static Image imgMediumBox;
	public static Image imgSmallBox;
	public static Image imgNotificationBox;

	public static List<Image> imgMapList;
	public static Image imgMist;
	public static Image imgTerrainOk;

	public static Image imgTargetDomain;
	public static Image imgTargetBattle;
	public static Image imgTargetAggregation;
	public static Image imgMapSelectGreen;
	public static Image imgMapSelectRed;

	public static List<Image> imgTerrain;
	public static List<Image> imgTerrainBox;

	public static Image imgLevelUpRelease;
	public static Image imgLevelUpFocus;
	public static Image imgLevelUpDisabled;

	public static List<Image> imgTowerList;
	public static List<Image> imgTowerBNList;
	public static List<Image> imgMarketList;
	public static List<Image> imgMarketBNList;
	public static List<Image> imgChurchList;
	public static List<Image> imgChurchBNList;

	public static List<Image> imgBigTroop;
	public static List<Image> imgSmallTroop;
	public static List<Image> imgIconTroop;
	public static Image imgVillagers;

	public static Image imgArmyIdle;
	public static Image imgArmyRun;
	public static Image imgArmyAtack;
	public static Image imgArmyDead;

	public static List<Image> imgFlagList;
	public static List<Image> imgFlagSmallList;
	public static List<Image> imgFlagBigList;

	public static Image imgShieldIcon;
	public static Image imgCrossIcon;
	public static Image imgOkIcon;
	public static Image imgShield;
	public static List<Image> imgRollList;

	public static void loadGFX(int _iNewState) {

		switch (_iNewState) {
		case Define.ST_MENU_START:
			try {
				// Load fonts
				vImgFontSmall = Image.createImage("/font_small.png");
				vImgFontMedium = Image.createImage("/font_medium.png");
				vImgFontBig = Image.createImage("/font_big.png");

				imgBlackBG = Image.createImage("/black_bg.png");
				vImgLogo = Image.createImage("/4away.png");

				loadMenuGfx();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		case Define.ST_MENU_ASK_SOUND:
		case Define.ST_MENU_ASK_LANGUAGE:

			break;

		case Define.ST_MENU_MAIN:
		case Define.ST_MENU_ON_LINE_LIST_ALL_GAME:
			if (Main.lastState >= Define.ST_GAME_INIT_PASS_AND_PLAY) {
				try {
					loadMenuGfx();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			break;
		// Test
		case Define.ST_MENU_SELECT_GAME:

			break;

		case Define.ST_GAME_INIT_PASS_AND_PLAY:
		case Define.ST_GAME_INIT_ON_LINE:
			try {
				// Menu commons
				imgButtonMenuBigRelease = Image
						.createImage("/button_menu_big_release.png");
				imgButtonMenuBigFocus = Image
						.createImage("/button_menu_big_focus.png");

				imgButtonInvisible = Image.createImage("/button_invisible.png");

				imgMapList = new ArrayList<Image>();
				switch (GameState.getInstance().getMap()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					imgMapList.add(Image.createImage("/maps/map_1.png"));
					imgMapList.add(Image.createImage("/maps/map_2.png"));
					imgMapList.add(Image.createImage("/maps/map_3.png"));
					imgMapList.add(Image.createImage("/maps/map_4.png"));
					imgMapList.add(Image.createImage("/maps/map_5.png"));
					imgMapList.add(Image.createImage("/maps/map_6.png"));
					imgMapList.add(Image.createImage("/maps/map_7.png"));
					imgMapList.add(Image.createImage("/maps/map_8.png"));
					imgMapList.add(Image.createImage("/maps/map_9.png"));
					imgMapList.add(Image.createImage("/maps/map_10.png"));
					imgMapList.add(Image.createImage("/maps/map_11.png"));
					imgMapList.add(Image.createImage("/maps/map_12.png"));
					imgMapList.add(Image.createImage("/maps/map_13.png"));
					imgMapList.add(Image.createImage("/maps/map_14.png"));
					imgMapList.add(Image.createImage("/maps/map_15.png"));
					imgMapList.add(Image.createImage("/maps/map_16.png"));
					
					imgMist = Image.createImage("/maps/mist.png");
					break;
				}

				imgTowerList = new ArrayList<Image>();
				imgTowerBNList = new ArrayList<Image>();
				imgMarketList = new ArrayList<Image>();
				imgMarketBNList = new ArrayList<Image>();
				imgChurchList = new ArrayList<Image>();
				imgChurchBNList = new ArrayList<Image>();

				imgTowerList.add(Image.createImage("/tower1.png"));
				imgTowerList.add(Image.createImage("/tower2.png"));
				imgTowerList.add(Image.createImage("/tower3.png"));
				imgTowerBNList.add(Image.createImage("/tower1_bn.png"));
				imgTowerBNList.add(Image.createImage("/tower2_bn.png"));
				imgTowerBNList.add(Image.createImage("/tower3_bn.png"));
				imgMarketList.add(Image.createImage("/market1.png"));
				imgMarketList.add(Image.createImage("/market2.png"));
				imgMarketList.add(Image.createImage("/market3.png"));
				imgMarketBNList.add(Image.createImage("/market1_bn.png"));
				imgMarketBNList.add(Image.createImage("/market2_bn.png"));
				imgMarketBNList.add(Image.createImage("/market3_bn.png"));
				imgChurchList.add(Image.createImage("/church1.png"));
				imgChurchList.add(Image.createImage("/church2.png"));
				imgChurchList.add(Image.createImage("/church3.png"));
				imgChurchBNList.add(Image.createImage("/church1_bn.png"));
				imgChurchBNList.add(Image.createImage("/church2_bn.png"));
				imgChurchBNList.add(Image.createImage("/church3_bn.png"));

				imgProtection = Image.createImage("/protection.png");
				imgProtectionRes = Image.createImage("/protection_res.png");
				
				imgLevelUpRelease = Image.createImage("/level_up_release.png");
				imgLevelUpFocus = Image.createImage("/level_up_focus.png");
				imgLevelUpDisabled = Image.createImage("/level_up_disabled.png");

				imgTextBG = Image.createImage("/text_bg.png");
				imgNickBox = Image.createImage("/nick_box.png");

				imgPadWest = Image.createImage("/pad_left.png");
				imgPadEast = Image.createImage("/pad_right.png");

				imgTerrainOk = Image.createImage("/terrain_ok.png");

				imgTargetDomain = Image.createImage("/target_domain.png");
				imgTargetAggregation = Image
						.createImage("/target_aggregation.png");
				imgTargetBattle = Image.createImage("/target_battle.png");

				imgMapSelectGreen = Image.createImage("/map_select_green.png");
				imgMapSelectRed = Image.createImage("/map_select_red.png");

				imgArmyIdle = Image.createImage("/army_idle.png");
				imgArmyRun = Image.createImage("/army_run.png");
				imgArmyAtack = Image.createImage("/army_atack.png");
				imgArmyDead = Image.createImage("/army_dead.png");


				imgGameHud = Image.createImage("/game_hud.png");
				imgCoinSmall = Image.createImage("/coin_small.png");
				imgCoin = Image.createImage("/coin.png");
                imgSwordRanking = Image.createImage("/sword_ranking.png");
                imgCityRanking = Image.createImage("/city_ranking.png");
				imgCrown = Image.createImage("/crown.png");
				imgButtonPauseRelease = Image.createImage("/pause_release.png");
				imgButtonPauseFocus = Image.createImage("/pause_focus.png");
				imgButtonDebugPauseRelease = Image
						.createImage("/debug_pause_release.png");
				imgButtonDebugPauseFocus = Image
						.createImage("/debug_pause_focus.png");
				imgButtonCancelRelease = Image
						.createImage("/cancel_release.png");
				imgButtonCancelFocus = Image.createImage("/cancel_focus.png");
				imgButtonNextRelease = Image.createImage("/next_release.png");
				imgButtonNextFocus = Image.createImage("/next_focus.png");
				imgButtonOkRelease = Image.createImage("/ok_release.png");
				imgButtonOkFocus = Image.createImage("/ok_focus.png");
				imgButtonCrossRelease = Image.createImage("/cross_release.png");
				imgButtonCrossFocus = Image.createImage("/cross_focus.png");
				imgButtonCrossBigRelease = Image
						.createImage("/cross_big_release.png");
				imgButtonCrossBigFocus = Image
						.createImage("/cross_big_focus.png");

				imgButtonDeleteFocus = Image.createImage("/delete_focus.png");
				imgButtonDeleteRelease = Image
						.createImage("/delete_release.png");

				imgButtonNewArmyFocus = Image
						.createImage("/new_army_focus.png");
				imgButtonNewArmyRelease = Image
						.createImage("/new_army_release.png");
				imgButtonInfoFocus = Image.createImage("/info_focus.png");
				imgButtonInfoRelease = Image.createImage("/info_release.png");

				imgTextBox = Image.createImage("/text_box.png");
				imgBigBox = Image.createImage("/big_box.png");
				imgMediumBox = Image.createImage("/medium_box.png");
				imgSmallBox = Image.createImage("/small_box.png");
				imgNotificationBox = Image.createImage("/notification_box.png");
				imgButtonCombatRelease = Image
						.createImage("/combat_release.png");
				imgButtonCombatFocus = Image.createImage("/combat_focus.png");
				imgButtonCardRelease = Image.createImage("/card_release.png");
				imgButtonCardFocus = Image.createImage("/card_focus.png");
				imgButtonFlagHelmetRelease = Image
						.createImage("/flag_helmet_release.png");
				imgButtonFlagHelmetFocus = Image
						.createImage("/flag_helmet_focus.png");
				imgButtonFlagCastleRelease = Image
						.createImage("/flag_castle_release.png");
				imgButtonFlagCastleFocus = Image
						.createImage("/flag_castle_focus.png");
				
				imgButtonHelmetRelease = Image.createImage("/helmet_release.png");
				imgButtonHelmetFocus = Image.createImage("/helmet_focus.png");

				imgButtonMapRelease = Image.createImage("/icon_map_release.png");
				imgButtonMapFocus = Image.createImage("/icon_map_release.png");
				imgButtonCrownRelease = Image.createImage("/icon_crown_release.png");
				imgButtonCrownFocus = Image.createImage("/icon_crown_release.png");

				imgButtonChestRelease = Image.createImage("/chest_release.png");
				imgButtonChestFocus = Image.createImage("/chest_focus.png");

				imgButtonYellowRelease = Image
						.createImage("/button_yellow_release.png");
				imgButtonYellowFocus = Image
						.createImage("/button_yellow_focus.png");
				imgButtonGreenRelease = Image
						.createImage("/button_green_release.png");
				imgButtonGreenFocus = Image
						.createImage("/button_green_focus.png");
				imgButtonRedRelease = Image
						.createImage("/button_red_release.png");
				imgButtonRedFocus = Image.createImage("/button_red_focus.png");

				imgFlagList = new ArrayList<Image>();
				imgFlagSmallList = new ArrayList<Image>();
				imgFlagBigList = new ArrayList<Image>();

				imgTerrain = new ArrayList<Image>();
				imgTerrainBox = new ArrayList<Image>();

				imgTerrain.add(Image.createImage("/plain.png"));
				imgTerrain.add(Image.createImage("/forest.png"));
				imgTerrain.add(Image.createImage("/montain.png"));
				imgTerrain.add(Image.createImage("/big_city.png"));

				imgTerrainBox.add(Image.createImage("/plain_box.png"));
				imgTerrainBox.add(Image.createImage("/forest_box.png"));
				imgTerrainBox.add(Image.createImage("/montain_box.png"));
				imgTerrainBox.add(Image.createImage("/city_box.png"));
				imgTerrainBox.add(imgTerrainBox.get(3));
				imgTerrainBox.add(imgTerrainBox.get(3));

				imgFlagList.add(Image.createImage("/flag1.png"));
				imgFlagList.add(Image.createImage("/flag2.png"));
				imgFlagList.add(Image.createImage("/flag3.png"));
				imgFlagList.add(Image.createImage("/flag4.png"));
				imgFlagList.add(Image.createImage("/flag5.png"));
				imgFlagList.add(Image.createImage("/flag6.png"));
				imgFlagList.add(Image.createImage("/flag7.png"));
				imgFlagList.add(Image.createImage("/flag8.png"));

				imgFlagSmallList.add(Image.createImage("/flag_small1.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small2.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small3.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small4.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small5.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small6.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small7.png"));
				imgFlagSmallList.add(Image.createImage("/flag_small8.png"));

				imgFlagBigList.add(Image.createImage("/flag_big1.png"));
				imgFlagBigList.add(Image.createImage("/flag_big2.png"));
				imgFlagBigList.add(Image.createImage("/flag_big3.png"));
				imgFlagBigList.add(Image.createImage("/flag_big4.png"));
				imgFlagBigList.add(Image.createImage("/flag_big5.png"));
				imgFlagBigList.add(Image.createImage("/flag_big6.png"));
				imgFlagBigList.add(Image.createImage("/flag_big7.png"));
				imgFlagBigList.add(Image.createImage("/flag_big8.png"));

				imgBigTroop = new ArrayList<Image>();
				imgSmallTroop = new ArrayList<Image>();
				imgIconTroop = new ArrayList<Image>();
				imgBigTroop.add(Image.createImage("/infantry_big.png"));
				imgBigTroop.add(Image.createImage("/knigths_big.png"));
				imgBigTroop.add(Image.createImage("/harassers_big.png"));
				imgBigTroop.add(Image.createImage("/siege_big.png"));
				imgSmallTroop.add(Image.createImage("/infantry_small.png"));
				imgSmallTroop.add(Image.createImage("/knigths_small.png"));
				imgSmallTroop.add(Image.createImage("/harassers_small.png"));
				imgSmallTroop.add(Image.createImage("/siege_small.png"));
				imgIconTroop.add(Image.createImage("/infantry_icon.png"));
				imgIconTroop.add(Image.createImage("/knigths_icon.png"));
				imgIconTroop.add(Image.createImage("/harassers_icon.png"));
				imgIconTroop.add(Image.createImage("/siege_icon.png"));

				imgVillagers = Image.createImage("/villagers.png");

				imgShieldIcon = Image.createImage("/shield_icon.png");
				imgCrossIcon = Image.createImage("/cross_icon.png");
				imgOkIcon = Image.createImage("/ok_icon.png");
				imgShield = Image.createImage("/shield.png");
				imgRollList = new ArrayList<Image>();
				imgRollList.add(Image.createImage("/roll1.png"));
				imgRollList.add(Image.createImage("/roll2.png"));
				imgRollList.add(Image.createImage("/roll3.png"));
				imgRollList.add(Image.createImage("/roll4.png"));
				imgRollList.add(Image.createImage("/roll5.png"));
				imgRollList.add(Image.createImage("/roll6.png"));
				imgRollList.add(Image.createImage("/roll7.png"));
				imgRollList.add(Image.createImage("/roll8.png"));
				imgRollList.add(Image.createImage("/roll9.png"));
				imgRollList.add(Image.createImage("/roll10.png"));

				imgCheckRelease = Image.createImage("/check_release.png");
				imgCheckFocus = Image.createImage("/check_focus.png");
				imgUncheckRelease = Image.createImage("/uncheck_release.png");
				imgUncheckFocus = Image.createImage("/uncheck_focus.png");
				imgEnglishRelease = Image.createImage("/english_release.png");
				imgEnglishFocus = Image.createImage("/english_focus.png");
				imgSpanishRelease = Image.createImage("/spanish_release.png");
				imgSpanishFocus = Image.createImage("/spanish_focus.png");
				imgCatalaRelease = Image.createImage("/catala_release.png");
				imgCatalaFocus = Image.createImage("/catala_focus.png");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			break;
		}
	}

	public static void loadMenuGfx() throws Exception {

		imgButtonMenuBigRelease = Image
				.createImage("/button_menu_big_release.png");
		imgButtonMenuBigFocus = Image.createImage("/button_menu_big_focus.png");
		imgButtonMenuMediumRelease = Image
				.createImage("/button_menu_medium_release.png");
		imgButtonMenuMediumFocus = Image
				.createImage("/button_menu_medium_focus.png");
		imgButtonMenuSmallRelease = Image
				.createImage("/button_menu_small_release.png");
		imgButtonMenuSmallFocus = Image
				.createImage("/button_menu_small_focus.png");
		imgButtonOkRelease = Image.createImage("/ok_release.png");
		imgButtonOkFocus = Image.createImage("/ok_focus.png");
		imgButtonCancelRelease = Image.createImage("/cancel_release.png");
		imgButtonCancelFocus = Image.createImage("/cancel_focus.png");

		imgButtonArrowBackRelease = Image
				.createImage("/arrow_back_release.png");
		imgButtonArrowBackFocus = Image.createImage("/arrow_back_focus.png");
		imgButtonArrowNextRelease = Image
				.createImage("/arrow_next_release.png");
		imgButtonArrowNextFocus = Image.createImage("/arrow_next_focus.png");
		imgButtonCrossBigRelease = Image.createImage("/cross_big_release.png");
		imgButtonCrossBigFocus = Image.createImage("/cross_big_focus.png");
		imgButtonSearchBigRelease = Image
				.createImage("/search_big_release.png");
		imgButtonSearchBigFocus = Image.createImage("/search_big_focus.png");
		imgButtonInvisible = Image.createImage("/button_invisible.png");
		imgButtonOptionsRelease = Image.createImage("/options_big_release.png");
		imgButtonOptionsFocus = Image.createImage("/options_big_focus.png");
		imgButtonRankingRelease = Image.createImage("/ranking_release.png");
		imgButtonRankingFocus = Image.createImage("/ranking_focus.png");

		imgButtonKeyboardRelease = Image
				.createImage("/button_keyboard_release.png");
		imgButtonKeyboardFocus = Image
				.createImage("/button_keyboard_focus.png");
		imgButtonKeyboardReleaseSp = Image
				.createImage("/button_keyboard_release_sp.png");
		imgButtonKeyboardFocusSp = Image
				.createImage("/button_keyboard_focus_sp.png");

		imgButtonInfoFocus = Image.createImage("/info_focus.png");
		imgButtonInfoRelease = Image.createImage("/info_release.png");

		imgSmallBox = Image.createImage("/small_box.png");
		imgMediumBox = Image.createImage("/medium_box.png");
		imgBigBox = Image.createImage("/big_box.png");

		imgMainBG = Image.createImage("/main_bg.png");
		imgSwordBG = Image.createImage("/sword_bg.png");
		imgCloudBG = Image.createImage("/cloud_bg.png");
		imgBlackBG = Image.createImage("/black_bg.png");
		imgTitle = Image.createImage("/title.png");

		imgInputBox = Image.createImage("/input_box.png");
		imgTextPointer = Image.createImage("/text_pointer.png");

		imgFlagList = new ArrayList<Image>();
		imgFlagList.add(Image.createImage("/flag1.png"));
		imgFlagList.add(Image.createImage("/flag2.png"));
		imgFlagList.add(Image.createImage("/flag3.png"));
		imgFlagList.add(Image.createImage("/flag4.png"));
		imgFlagList.add(Image.createImage("/flag5.png"));
		imgFlagList.add(Image.createImage("/flag6.png"));
		imgFlagList.add(Image.createImage("/flag7.png"));
		imgFlagList.add(Image.createImage("/flag8.png"));

		imgCheckRelease = Image.createImage("/check_release.png");
		imgCheckFocus = Image.createImage("/check_focus.png");
		imgUncheckRelease = Image.createImage("/uncheck_release.png");
		imgUncheckFocus = Image.createImage("/uncheck_focus.png");
		imgEnglishRelease = Image.createImage("/english_release.png");
		imgEnglishFocus = Image.createImage("/english_focus.png");
		imgSpanishRelease = Image.createImage("/spanish_release.png");
		imgSpanishFocus = Image.createImage("/spanish_focus.png");
		imgCatalaRelease = Image.createImage("/catala_release.png");
		imgCatalaFocus = Image.createImage("/catala_focus.png");

        imgCoin = Image.createImage("/coin.png");
        imgSwordRanking = Image.createImage("/sword_ranking.png");
        imgCityRanking = Image.createImage("/city_ranking.png");

		imgNotificationBox = Image.createImage("/notification_box.png");

        imgFlagSmallList = new ArrayList<Image>();
        imgFlagSmallList.add(Image.createImage("/flag_small1.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small2.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small3.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small4.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small5.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small6.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small7.png"));
        imgFlagSmallList.add(Image.createImage("/flag_small8.png"));

        //Imagenes no desacopladas:
		imgTerrain = new ArrayList<Image>();
        imgTerrain.add(Image.createImage("/plain.png"));
        imgTerrain.add(Image.createImage("/forest.png"));
        imgTerrain.add(Image.createImage("/montain.png"));
        imgTerrain.add(Image.createImage("/big_city.png"));
	}

	public static void deleteMenuGFX() {
		imgButtonMenuBigRelease = null;
		imgButtonMenuBigFocus = null;
		imgButtonMenuMediumRelease = null;
		imgButtonMenuMediumFocus = null;
		imgButtonMenuSmallRelease = null;
		imgButtonMenuSmallFocus = null;
		imgButtonCancelRelease = null;
		imgButtonCancelFocus = null;

		imgButtonArrowBackRelease = null;
		imgButtonArrowBackFocus = null;
		imgButtonArrowNextRelease = null;
		imgButtonArrowNextFocus = null;
		imgButtonOkRelease = null;
		imgButtonOkFocus = null;
		imgButtonInvisible = null;

		imgButtonInfoRelease = null;
		imgButtonInfoFocus = null;
		imgButtonRankingRelease = null;
		imgButtonRankingFocus = null;

		imgSmallBox = null;
		imgMediumBox = null;
		imgBigBox = null;

		imgButtonKeyboardRelease = null;
		imgButtonKeyboardFocus = null;
		imgButtonKeyboardReleaseSp = null;
		imgButtonKeyboardFocusSp = null;

		imgButtonCrossBigRelease = null;
		imgButtonCrossBigFocus = null;
		imgButtonSearchBigRelease = null;
		imgButtonCrossBigRelease = null;

		imgButtonMapRelease = null;
		imgButtonMapFocus = null;
		imgButtonCrownRelease = null;
		imgButtonCrownFocus = null;

		imgInputBox = null;
		imgTextPointer = null;

		imgMainBG = null;
		imgSwordBG = null;
		imgCloudBG = null;
		imgTitle = null;

		imgFlagList = null;

		imgNotificationBox = null;

		imgCheckRelease = null;
		imgCheckFocus = null;
		imgUncheckRelease = null;
		imgUncheckFocus = null;
		imgEnglishFocus = null;
		imgSpanishRelease = null;
		imgSpanishFocus = null;
		imgCatalaRelease = null;
		imgCatalaFocus = null;

        imgCoin = null;
        imgSwordRanking = null;
        imgCityRanking = null;
        imgFlagSmallList = null;


		//Imagenes no desacopladas:
		imgTerrain = null;

		System.gc();
	}

	public static void deleteGameGFX() {

		// Menu commons
		imgProtection = null;
		imgProtectionRes = null;
		imgButtonMenuBigRelease = null;
		imgButtonMenuBigFocus = null;

		imgTowerList = null;
		imgTowerBNList = null;
		imgMarketList = null;
		imgMarketBNList = null;
		imgChurchList = null;
		imgChurchBNList = null;

		imgNickBox = null;

		imgButtonInvisible = null;

		imgMapList = null;
		imgMist = null;

		imgTextBG = null;

		imgPadWest = null;
		imgPadEast = null;

		imgTerrainOk = null;

		imgTargetDomain = null;
		imgTargetAggregation = null;
		imgTargetBattle = null;

		imgMapSelectGreen = null;
		imgMapSelectRed = null;

		imgArmyIdle = null;
		imgArmyRun = null;
		imgArmyAtack = null;
		imgArmyDead = null;

		imgGameHud = null;
		imgCoinSmall = null;
        imgCoin = null;
        imgSwordRanking = null;
        imgCityRanking = null;
		imgCrown = null;
		imgButtonPauseRelease = null;
		imgButtonPauseFocus = null;
		imgButtonDebugPauseRelease = null;
		imgButtonDebugPauseFocus = null;
		imgButtonCancelRelease = null;
		imgButtonCancelFocus = null;
		imgButtonNextRelease = null;
		imgButtonNextFocus = null;
		imgButtonOkRelease = null;
		imgButtonOkFocus = null;
		imgButtonCrossRelease = null;
		imgButtonCrossFocus = null;
		imgButtonCrossBigRelease = null;
		imgButtonCrossBigFocus = null;
		imgButtonYellowRelease = null;
		imgButtonYellowFocus = null;
		imgButtonGreenRelease = null;
		imgButtonGreenFocus = null;
		imgButtonRedRelease = null;
		imgButtonRedFocus = null;
        imgButtonMapRelease = null;
        imgButtonMapFocus = null;

		imgButtonDeleteFocus = null;
		imgButtonDeleteRelease = null;

		imgButtonNewArmyFocus = null;
		imgButtonNewArmyRelease = null;
		imgButtonInfoFocus = null;
		imgButtonInfoRelease = null;

		imgTextBox = null;
		imgBigBox = null;
		imgMediumBox = null;
		imgSmallBox = null;
		imgNotificationBox = null;
		imgButtonCombatRelease = null;
		imgButtonCombatFocus = null;
		imgButtonCardRelease = null;
		imgButtonCardFocus = null;
		imgButtonFlagHelmetRelease = null;
		imgButtonFlagHelmetFocus = null;
		imgButtonFlagCastleRelease = null;
		imgButtonFlagCastleFocus = null;
		imgButtonHelmetRelease = null;
		imgButtonHelmetFocus = null;

		imgFlagList = null;
		imgFlagSmallList = null;
		imgFlagBigList = null;

		imgTerrain = null;
		imgTerrainBox = null;

		imgBigTroop = null;
		imgSmallTroop = null;
		imgIconTroop = null;

		imgVillagers = null;

		imgShieldIcon = null;
		imgCrossIcon = null;
		imgOkIcon = null;
		imgShield = null;
		imgRollList = null;

		imgCheckRelease = null;
		imgCheckFocus = null;
		imgUncheckRelease = null;
		imgUncheckFocus = null;
		imgEnglishFocus = null;
		imgSpanishRelease = null;
		imgSpanishFocus = null;
		imgCatalaRelease = null;
		imgCatalaFocus = null;
		System.gc();
	}

}

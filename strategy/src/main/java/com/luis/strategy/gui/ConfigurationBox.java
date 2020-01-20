package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.fileio.FileIO;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.graphics.Image;
import com.luis.lgameengine.implementation.input.MultiTouchHandler;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.game.GameManager;

public class ConfigurationBox extends MenuBox {

    private int languageY;
    private int soundY;
    private int notificationsY;
    private int game3DY;

    private int optLabelX;//center | right
    private int optValueX;

    private Button languageButton;
    private Button soundButton;
    private Button notificationsButton;
    private Button game3DButton;

    private int language;
    private boolean sound;
    private boolean notifications;
    private boolean game3D;

    public ConfigurationBox() {
        super(
                Define.SIZEX, Define.SIZEY,
                GfxManager.imgBigBox, null, null,
                Define.SIZEX2, Define.SIZEY2,
                RscManager.allText[RscManager.TXT_OPTIONS],
                null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_NEXT);

        btnList.add(new Button(
                GfxManager.imgButtonOkRelease,
                GfxManager.imgButtonOkFocus,
                screenWidth / 2,
                screenHeight / 2 + imgBox.getHeight() / 2,
                null,
                -1) {
            @Override
            public void onButtonPressDown() {
                super.onButtonPressDown();
            }

            @Override
            public void onButtonPressUp() {
                SndManager.getInstance().playFX(Main.FX_NEXT, 0);
                cancel();
            }
        });
    }

    public void start() {
        super.start();
        int numOptions = 4;//Language, sound, enable notifications, game3D

        int sepY = Define.SIZEY64;
        int sepX = Define.SIZEX16;

        int optionsHeight = GfxManager.imgCheckRelease.getHeight() * numOptions + (sepY * (numOptions - 1));
        languageY =
                Define.SIZEY2 - (optionsHeight / 2) + (int) Font.getFontHeight(Font.FONT_MEDIUM) +
                        GfxManager.imgCheckRelease.getHeight() / 2;
        soundY =
                Define.SIZEY2 - (optionsHeight / 2) + (int) Font.getFontHeight(Font.FONT_MEDIUM) +
                        (int) (GfxManager.imgCheckRelease.getHeight() * 1.5f) + sepY;

        notificationsY =
                Define.SIZEY2 - (optionsHeight / 2) + (int) Font.getFontHeight(Font.FONT_MEDIUM) +
                        (int) (GfxManager.imgCheckRelease.getHeight() * 2.5f) + sepY * 2;


        game3DY =
                Define.SIZEY2 - (optionsHeight / 2) + (int) Font.getFontHeight(Font.FONT_MEDIUM) +
                        (int) (GfxManager.imgCheckRelease.getHeight() * 3.5f) + sepY * 3;


        //Cargo las opciones por defecto
        String dataConfig = FileIO.getInstance().loadData(Define.DATA_CONFIG, Main.getInstance().getActivity());
        language = Integer.parseInt(dataConfig.split("\n")[0]);
        sound = dataConfig.split("\n")[1].equals("true");
        notifications = dataConfig.split("\n")[2].equals("true");
        game3D = dataConfig.split("\n")[3].equals("true");

        optLabelX = Define.SIZEX2 - sepX - GfxManager.imgCheckRelease.getWidth() / 2;
        optValueX = Define.SIZEX2 + sepX + GfxManager.imgCheckRelease.getWidth() / 2;

        languageButton = new Button(
                GfxManager.imgEnglishRelease,
                GfxManager.imgEnglishFocus,
                optValueX, languageY, null, -1) {
            @Override
            public void onButtonPressDown() {
                super.onButtonPressDown();
            }

            @Override
            public void onButtonPressUp() {
                reset();
                SndManager.getInstance().playFX(Main.FX_NEXT, 0);
                language = (language + 1) % 3;
                updateLanguageButton();
                RscManager.loadLanguage(language);
                saveConfiguration();
            }
        };

        soundButton = new Button(
                GfxManager.imgCheckRelease,
                GfxManager.imgCheckFocus,
                optValueX, soundY, null, -1) {
            @Override
            public void onButtonPressDown() {
                super.onButtonPressDown();
            }

            @Override
            public void onButtonPressUp() {
                reset();
                SndManager.getInstance().playFX(Main.FX_NEXT, 0);
                sound = !sound;
                updateSoundButton();
                SndManager.getInstance().setSound(sound);
                if (!sound) {
                    SndManager.getInstance().stopMusic();
                } else {
                    if (Main.state < Define.ST_GAME_INIT_PASS_AND_PLAY) {
                        SndManager.getInstance().playMusic(Main.MUSIC_INTRO, true);
                    } else {
                        SndManager.getInstance().playMusic(Main.MUSIC_MAP, true);
                    }
                }
                saveConfiguration();
            }
        };

        notificationsButton = new Button(
                GfxManager.imgCheckRelease,
                GfxManager.imgCheckFocus,
                optValueX, notificationsY, null, -1) {
            @Override
            public void onButtonPressDown() {
                super.onButtonPressDown();
            }

            @Override
            public void onButtonPressUp() {
                reset();
                SndManager.getInstance().playFX(Main.FX_NEXT, 0);
                notifications = !notifications;
                updateNotificationsButton();
                saveConfiguration();
            }
        };

        game3DButton = new Button(
                GfxManager.imgCheckRelease,
                GfxManager.imgCheckFocus,
                optValueX, game3DY, null, -1) {
            @Override
            public void onButtonPressDown() {
                super.onButtonPressDown();
            }

            @Override
            public void onButtonPressUp() {
                reset();
                SndManager.getInstance().playFX(Main.FX_NEXT, 0);
                game3D = !game3D;
                GameManager.game3D = game3D;
                updateGame3DButton();
                saveConfiguration();
            }
        };


        updateLanguageButton();
        updateSoundButton();
        updateNotificationsButton();
        updateGame3DButton();
    }

    private void updateLanguageButton() {

        if (language == 0) {
            languageButton.setImgRelese(GfxManager.imgEnglishRelease);
            languageButton.setImgFocus(GfxManager.imgEnglishFocus);
        } else if (language == 1) {
            languageButton.setImgRelese(GfxManager.imgSpanishRelease);
            languageButton.setImgFocus(GfxManager.imgSpanishFocus);
        } else {
            languageButton.setImgRelese(GfxManager.imgCatalaRelease);
            languageButton.setImgFocus(GfxManager.imgCatalaFocus);
        }
    }

    private void updateSoundButton() {
        soundButton.setImgRelese(sound ? GfxManager.imgCheckRelease : GfxManager.imgUncheckRelease);
        soundButton.setImgFocus(sound ? GfxManager.imgCheckFocus : GfxManager.imgUncheckFocus);
    }

    private void updateNotificationsButton() {
        notificationsButton.setImgRelese(notifications ? GfxManager.imgCheckRelease : GfxManager.imgUncheckRelease);
        notificationsButton.setImgFocus(notifications ? GfxManager.imgCheckFocus : GfxManager.imgUncheckFocus);
    }

    private void updateGame3DButton() {
        game3DButton.setImgRelese(game3D ? GfxManager.imgCheckRelease : GfxManager.imgUncheckRelease);
        game3DButton.setImgFocus(game3D ? GfxManager.imgCheckFocus : GfxManager.imgUncheckFocus);
    }

    @Override
    public boolean update(MultiTouchHandler touchHandler, float delta) {
        if (isActive()) {
            languageButton.update(touchHandler);
            soundButton.update(touchHandler);
            if (notificationsButton != null) notificationsButton.update(touchHandler);
            if (game3DButton != null) game3DButton.update(touchHandler);
        }
        return super.update(touchHandler, delta);
    }


    public void draw(Graphics g, Image imgBG) {
        if (state != STATE_UNACTIVE) {
            super.draw(g, imgBG);

            TextManager.drawSimpleText(g, Font.FONT_SMALL, RscManager.allText[RscManager.TXT_LANGUAGE],
                    optLabelX + (int) modPosX, languageY, Graphics.VCENTER | Graphics.RIGHT);
            languageButton.draw(g, (int) modPosX, 0);
            TextManager.drawSimpleText(g, Font.FONT_SMALL, RscManager.allText[RscManager.TXT_SOUND],
                    optLabelX + (int) modPosX, soundY, Graphics.VCENTER | Graphics.RIGHT);
            soundButton.draw(g, (int) modPosX, 0);

            TextManager.drawSimpleText(g, Font.FONT_SMALL, RscManager.allText[RscManager.TXT_PUSH_NOTIFICATIONS],
                    optLabelX + (int) modPosX, notificationsY, Graphics.VCENTER | Graphics.RIGHT);
            notificationsButton.draw(g, (int) modPosX, 0);

            TextManager.drawSimpleText(g, Font.FONT_SMALL, "Mode-7",
                    optLabelX + (int) modPosX, game3DY, Graphics.VCENTER | Graphics.RIGHT);
            game3DButton.draw(g, (int) modPosX, 0);
        }
    }

    private void saveConfiguration() {
        String dataConfig = "" + language + "\n" + sound + "\n" + notifications + "\n" + game3D;
        FileIO.getInstance().saveData(dataConfig, Define.DATA_CONFIG, Main.getInstance().getActivity());
    }

    public int getLanguage() {
        return language;
    }

    public boolean isSound() {
        return sound;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public boolean isGame3D() {
        return game3D;
    }


}

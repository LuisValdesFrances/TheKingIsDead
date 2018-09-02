package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.map.Player;

import java.util.List;

public class EconomyBox extends MenuBox {

    private Player[] pList;

    public EconomyBox() {
        super(
                Define.SIZEX, Define.SIZEY, GfxManager.imgBigBox, null, null,
                Define.SIZEX2, Define.SIZEY2-GfxManager.imgGameHud.getHeight()/2,
                null,
                null, Font.FONT_MEDIUM, Font.FONT_SMALL, -1, Main.FX_NEXT);

        btnList.add(new Button(
                GfxManager.imgButtonCancelRelease,
                GfxManager.imgButtonCancelFocus,
                getX() - GfxManager.imgBigBox.getWidth()/2,
                getY() - GfxManager.imgBigBox.getHeight()/2,
                null,
                -1){
            @Override
            public void onButtonPressDown() {}

            @Override
            public void  onButtonPressUp(){
                SndManager.getInstance().playFX(Main.FX_BACK, 0);
                cancel();
            }
        });
    }

    public void start(List<Player>playerList){
        super.start();
        int nPlayer = 0;
        for(Player player : playerList){
            if(player.getCapitalkingdom() != null){
                nPlayer++;
            }
        }
        this.pList = new Player[nPlayer];
        int index = 0;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getCapitalkingdom() != null){
                this.pList[index++] = playerList.get(i);
            }
        }

        //Ordeno
        for(int i = 0; i < pList.length-1; i++){
            for(int j = 0; j < pList.length-1; j++){
                if(pList[j].getTaxes() <  pList[j+1].getTaxes()){
                    Player aux = pList[j+1];
                    pList[j+1] = pList[j];
                    pList[j] = aux;
                }
            }
        }
    }

    public void draw(Graphics g){
        super.draw(g, GfxManager.imgBlackBG);
        if(state != STATE_UNACTIVE){
            drawRanking(g);
        }
    }

    private void drawRanking(Graphics g){


        float modH = 0.8f;
        int fileH = (int)(GfxManager.imgFlagSmallList.get(0).getHeight()*modH);
        int totalH = Font.getFontHeight(Font.FONT_MEDIUM) + Font.getFontHeight(Font.FONT_SMALL) + fileH * (pList.length+1);
        int startY = getY() - totalH/2 + Font.getFontHeight(Font.FONT_SMALL)/2;
        int startX =
                getX()- GfxManager.imgBigBox.getWidth()/2 + Define.SIZEX32 + GfxManager.imgFlagSmallList.get(0).getWidth()/2 + (int)getModPosX();

        TextManager.drawSimpleText(g, Font.FONT_SMALL,
                RscManager.allText[RscManager.TXT_GAME_ECONOMY],
                getX() + (int)modPosX,
                startY,
                Graphics.VCENTER | Graphics.HCENTER);

        startY = startY + Font.getFontHeight(Font.FONT_SMALL)/2 + fileH;
        TextManager.drawSimpleText(g, Font.FONT_MEDIUM,
                RscManager.allText[RscManager.TXT_GAME_PLAYER],
                startX + Define.SIZEX32 + GfxManager.imgFlagSmallList.get(0).getWidth(),
                startY,
                Graphics.VCENTER | Graphics.HCENTER);

        TextManager.drawSimpleText(g, Font.FONT_MEDIUM,
                RscManager.allText[RscManager.TXT_GAME_EARNING],
                startX + Define.SIZEX4 - Define.SIZEX24 + Define.SIZEX8,
                startY,
                Graphics.VCENTER | Graphics.HCENTER);

        TextManager.drawSimpleText(g, Font.FONT_MEDIUM,
                RscManager.allText[RscManager.TXT_GAME_SALARY],
                startX + Define.SIZEX4 - Define.SIZEX24 + Define.SIZEX8 + Define.SIZEX4,
                startY,
                Graphics.VCENTER | Graphics.HCENTER);

        for(int i = 0; i < pList.length; i++){
            g.drawImage(GfxManager.imgFlagSmallList.get(pList[i].getFlag()),
                    startX,
                    startY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + ((i+1)*fileH),
                    Graphics.VCENTER | Graphics.HCENTER);

            String playerName = pList[i].getName();
            int maxNameLenght = 10;
            if(pList[i].getName().length() > maxNameLenght){
                playerName = "";
                for(int c = 0; c < maxNameLenght; c++){
                    playerName += pList[i].getName().charAt(c);
                }
                playerName += "...";
            }
            TextManager.drawSimpleText(g, Font.FONT_SMALL,
                    playerName,
                    startX + Define.SIZEX16 + GfxManager.imgFlagSmallList.get(0).getWidth() - (Font.getFontWidth(Font.FONT_MEDIUM)*(RscManager.allText[RscManager.TXT_GAME_PLAYER].length()/2)),
                    startY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + ((i+1)*fileH),
                    Graphics.VCENTER | Graphics.LEFT);

            TextManager.drawSimpleText(g, Font.FONT_SMALL,
                    "+" + pList[i].getTaxes(),
                    startX + Define.SIZEX4 - Define.SIZEX24 + Define.SIZEX8,
                    startY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + ((i+1)*fileH),
                    Graphics.VCENTER | Graphics.HCENTER);

            TextManager.drawSimpleText(g, Font.FONT_SMALL,
                    "-" + pList[i].getCost(false),
                    startX + Define.SIZEX4 - Define.SIZEX24 + Define.SIZEX8 + Define.SIZEX4,
                    startY + Font.getFontHeight(Font.FONT_MEDIUM)/2 + ((i+1)*fileH),
                    Graphics.VCENTER | Graphics.HCENTER);
        }

    }

}

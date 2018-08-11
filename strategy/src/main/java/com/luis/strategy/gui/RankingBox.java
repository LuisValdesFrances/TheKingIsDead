package com.luis.strategy.gui;

import com.luis.lgameengine.gameutils.fonts.Font;
import com.luis.lgameengine.gameutils.fonts.TextManager;
import com.luis.lgameengine.gameutils.gameworld.WorldConver;
import com.luis.lgameengine.gui.Button;
import com.luis.lgameengine.gui.MenuBox;
import com.luis.lgameengine.implementation.graphics.Graphics;
import com.luis.lgameengine.implementation.sound.SndManager;
import com.luis.strategy.GfxManager;
import com.luis.strategy.Main;
import com.luis.strategy.RscManager;
import com.luis.strategy.constants.Define;
import com.luis.strategy.constants.GameParams;
import com.luis.strategy.map.Player;

import java.util.List;

public class RankingBox extends MenuBox {

    private List<Player> playerList;

    public RankingBox(WorldConver worldConver, int numberPartsW, int numberPartsH) {
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
        this.playerList = playerList;
    }

    public void draw(Graphics g){
        super.draw(g, GfxManager.imgBlackBG);
        if(state != STATE_UNACTIVE){
            drawRanking(g);
        }
    }

    private void drawRanking(Graphics g){
        int nPlayer = 0;
        for(Player player : playerList){
            if(player.getCapitalkingdom() != null){
                nPlayer++;
            }
        }
        Player[] pList = new Player[nPlayer];
        int index = 0;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getCapitalkingdom() != null){
                pList[index++] = playerList.get(i);
            }
        }

        //Ordeno
        for(int i = 0; i < pList.length-1; i++){
            for(int j = 0; j < pList.length-1; j++){
                if(pList[j].getTaxes() >  pList[j+1].getTaxes()){
                    Player aux = pList[j+1];
                    pList[j+1] = pList[j];
                    pList[j] = aux;
                }
            }
        }

        float modH = 0.8f;
        int fileH = (int)(GfxManager.imgFlagSmallList.get(0).getHeight()*modH);
        int totalH = Font.getFontHeight(Font.FONT_MEDIUM) + fileH *playerList.size();
        int startY = getY() - totalH/2;
        int startX =
                getX()- GfxManager.imgBigBox.getWidth()/2 + Define.SIZEX32 + GfxManager.imgFlagSmallList.get(0).getWidth()/2 + (int)getModPosX();

        g.setClip(0, 0, Define.SIZEX, Define.SIZEY);


        String player = RscManager.allText[RscManager.TXT_GAME_PLAYER];


        TextManager.drawSimpleText(g, Font.FONT_MEDIUM,
                player,
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

        for(int i = pList.length-1; i > -1; i--){



            g.drawImage(GfxManager.imgFlagSmallList.get(pList[i].getFlag()),
                    startX,
                    startY +  Font.getFontHeight(Font.FONT_MEDIUM) + Font.getFontHeight(Font.FONT_SMALL) /2 + (i*fileH),
                    Graphics.VCENTER | Graphics.HCENTER);

            TextManager.drawSimpleText(g, Font.FONT_SMALL,
                    pList[i].getName(),
                    startX + Define.SIZEX16 + GfxManager.imgFlagSmallList.get(0).getWidth() - (Font.getFontWidth(Font.FONT_MEDIUM)*(player.length()/2)),
                    startY +  Font.getFontHeight(Font.FONT_MEDIUM) + Font.getFontHeight(Font.FONT_SMALL) /2 + (i*fileH),
                    Graphics.VCENTER | Graphics.LEFT);

            TextManager.drawSimpleText(g, Font.FONT_SMALL,
                    "+" + pList[i].getTaxes(),
                    startX + Define.SIZEX4 - Define.SIZEX24 + Define.SIZEX8,
                    startY +  Font.getFontHeight(Font.FONT_MEDIUM) + Font.getFontHeight(Font.FONT_SMALL) /2 + (i*fileH),
                    Graphics.VCENTER | Graphics.HCENTER);

            TextManager.drawSimpleText(g, Font.FONT_SMALL,
                    "-" + pList[i].getCost(false),
                    startX + Define.SIZEX4 - Define.SIZEX24 + Define.SIZEX8 + Define.SIZEX4,
                    startY +  Font.getFontHeight(Font.FONT_MEDIUM) + Font.getFontHeight(Font.FONT_SMALL) /2 + (i*fileH),
                    Graphics.VCENTER | Graphics.HCENTER);
        }

    }

}
